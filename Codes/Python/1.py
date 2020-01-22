print("hello")
message = "world"
print(message)
print("hh is 'hh'")
print('"hh" is not h')
name = 'ada lovelace'
print(name.title())
print(name.upper())
print(name.upper().lower())
a = 'a\tb\tc\nd\t'
print(a)
print(a.strip())

print("数值与字符串转换")
#int to str
print(str(13))#十进制
print(hex(13))#十六进制
#str to int
print(int("13"))#十进制
print(int("13",16))#十六进制
print("数值与字符串转换\n")

names = ['a','b',"c","d"]
print(names)
print(names[1])
print(name[-1])
print(name[-3])
names[1] = "e"
print(names)
names.append("ff")
print(names)
names.insert(2,"zz")
print(names)
del names[1]
print(names)

names1 = names.pop()
print(names)
print(names1)
print(names.pop(1))

print(names)
print(names.remove("c"))
print(names)

language = ["java","python","c","html","javascript"]
language.sort()
print(language)
language.sort(reverse=True)
print(language)
print("\n")

language1 = ["Java","python","c","Html","javascript"]
language1.sort()
print(language1)
language1.sort(reverse=True)
print(language1)
print("\n")

language2 = ["jAva","pythOn","c","hTMl","javaScript"]
language2.sort()
print(language2)
language2.sort(reverse=True)
print(language2)
print("\n")

aa =["aa","aA","Aa","AA","bb","bB","Bb","BB","ab","aB","Ab","AB"]
aa.sort()
print(aa)
print("\n")

bb = ["c","ccb","cad","opp","help","append","applicy","hope"]
print(sorted(bb))
print(sorted(bb,reverse=True))
print(bb)
print("\n")

bb.reverse()
print(bb)
print(len(bb))
print("\n")

for b in bb:
    print(b);
print("\n")

print(range(1,5))
ms = range(1,9)
print(ms)
for num in range(1,5):
    print(num)
nums = list(range(1,9))
print(nums)
print("\n")
#等差数列
n = 0;
m = 100;
add = 11;
even_nums = list(range(n,m,add))
print(even_nums)
print("\n")
#等比数列
squares = []
square_nums = []
n = 1;
m = 10;
for value in range(n,m):
    square = value**2
    square_num = value*3
    squares.append(square)
    square_nums.append(square_num)
print(squares)
print(square_nums)
print("\n")

ss = []
for value in range(0,100):
    ss.append(value)
print(max(ss))
print(min(ss))
print(sum(ss))
print("\n")

lists = [value**3 for value in range(1,10)]
print(lists)
print("\n")

str0 = []
for i in range(0,20):
  str0.append(i);
print(str0)
print(str0[6:14])
print(str0[6:])
print(str0[:9])
print(str0[-7:])
print(str0[-7:-1])
print(str0[-1])
print("\n")

str1 = ["a",'B',"c"]
str2 = str1[:]
str1.append("str1")
str2.append("str2")
print(str1)
print(str2)
print("\n")

str3 = ("Pinnsvin","It`s me.")
for s in str3:
    print(s)
print(str3)
for s in range(0,len(str3)):
    print(str3[s])
    # str3[s] = "Pinnsvin"+str(1)
print("\n")

n = 98;
if n < 0 or n > 100:
    print("不合法")
elif n > 0 and n < 60:
    print("不及格")
elif n >= 60 and n < 80:
    print("及格")
else:
    print("优秀")

ls = ["a",'b',"c","d"]
ls2 = []
l = " "

if ls:
    print("列表不为空")
    if l in ls:
        print(l+"在列表中")
    else:
        print(l+"不在列表中")
else:
    print("列表为空")

# 字典
my = {"name":"pinnsvin","site":"pinnsvin.cn"}
print(my["name"])
print(my["site"])
## 添加键-值对
my["sex"] = "man"
my["location"] = "beijing"
print(my)
# 修改字典中的值
my["location"] = "shanghai"
print(my)

# 删除字典中的键-值对
del my["location"]
print(my)

# 遍历字典
user = {
    "username":"Pinnsvin",
    "password":"password",
    "registerDate":"2017/10/22",
    "isMember":"false"
}
print(user)
# 遍历字典中的键-值对
for key,value in user.items():
    print("\nkey:" + key)
    print("value:" + value)
# 遍历字典中的键
for key in user.keys():
    print(key)
print("遍历字典时，会默认遍历所有的键。故可以去掉keys方法")
for key in user:
    print(key)

print("\n")
# 按顺序遍历
for key in sorted(user.keys()):
    print(key)
print("\n")
# 遍历所有值
user["password"] = "Pinnsvin"
for value in user.values():
    print(value)
print("用set集合去掉重复值")
for value in set(user.values()):
    print(value)
print("\n")
# 嵌套
user_1 = {"username":"joe","age":24,"sex":"man"}
user_2 = {"username":"anne","age":18,"sex":"female"}
user_3 = {"username":"linus","age":45,"sex":"man"}
users = [user_1,user_2,user_3]
for value in users:
    print(value)
print("\n")

# 在字典中存储列表
username = ["joe","anne","linus"]
age = [24,18,45,45]
sex = ["man","female"]
users = {"username":username,"age":age,"sex":sex}
for key,value in users.items():
    print("\n"+key)
    print(value)
# 在字典中存储字典
users = {
    "Pinnsvin":{
        "first_name":"Pinnsvin",
        "last_name":" ",
        "location":"beijing"
    },
    "文章":{
        "first_name":"文",
        "last_name":"章",
        "location":"beijing"
    }
}
for username,userInfo in users.items():
    print("\n"+username)
    print("全名:"+userInfo["first_name"].title()+" "+userInfo["last_name"].title())
    print("居住地:"+userInfo["location"])
