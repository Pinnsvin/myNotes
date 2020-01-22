## 需求简述
一个数据表中包含此数据的录入时间，此数据的初始状态是**有效**，五天后系统自动置该数据的状态为**无效**。

## 方案
1. 写一个存储过程，用于更新字段（改状态）；
2. 写一个job，用于定时执行存储过程；

## 方案逻辑
### 存储过程
1. 前提条件：此数据的状态为**有效**状态；
1. 获取当前系统时间；
2. 获取数据输入时间；
3. 计算二者差值；如果二者差值大于5，置数据状态为**无效**，反之，不做操作。

### Job
1. 设置每天**0:00**自动执行存储过程；（为了尽快看到测试结果，这里设置时间为每分钟执行一次job。）

### 测试小案例

1. 创建表
````sql
--创建表
create table test(name varchar2(30), passwd varchar2(30),inputtime date,status varchar2(2));
````

2. 插入数据
````sql
--插入数据
declare 
     c_name varchar2(30) := 'TestUser';
     c_pass varchar2(30) := 'TestPass';
     c_inputTime date;
     c_status varchar2(2) := '01';
begin
  select sysdate into c_inputTime from dual;
     for i in 0..99 loop
       c_name := c_name || to_char(i);
       c_pass := c_pass || to_char(i);
       c_inputTime := c_inputTime-1;
       dbms_output.put_line('name='||c_name||', passWd='||c_pass||', inputtime='||c_inputTime||' status='||c_status);
       insert into test (name,passwd,inputtime,status) values (c_name,c_pass,c_inputTime,c_status);
       c_name := 'TestUser';
       c_pass := 'TestPass';
     end loop;
     commit;
end;
````

3. 存储过程
````sql
--创建存储过程，用于更新status
create or replace procedure pro_test is
begin
declare
  NUM number :=5;
  d date;
  cursor cur_test is 
  select name,inputtime,status from test where status='01' order by inputtime desc;--创建游标，用于存储结果集
  begin
  select sysdate into d from dual;--获取系统时间
  for temp in cur_test
    loop
      if round(to_number(d-temp.inputtime))>NUM then
        update test set status='00' where name = temp.name;--更新表数据
      end if;
    end loop;
    commit;--提交事务
  end;
end pro_test;
````

4. 定时任务
````sql 
declare
    job_test number;
begin
    dbms_job.submit(job_test, 'pro_test;',sysdate, 'sysdate+1/24/60');--每分钟执行一次
end;
````
