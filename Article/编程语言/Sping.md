[TOC]

# spring 基本概念
> spring的目标是简化企业级Java开发。为减低Java开发的复杂性，spring采取了4种关键策略：
* 基于POJO的轻量级和最小侵入性编程；
* 通过依赖注入和面向接口实现松耦合；
* 基于切面和惯例进行声明式编程；
* 通过切面和模板减少版样式代码；

1. IOC/ID

2. AOP


## 装配bean
> 三种主要的装配机制：
* 在XML中进行显式配置；
* 在Java中进行显示配置；
* 隐式的bean发现机制和自动装配
> 尽量使用使用注解自动化配置，以避免显示配置所带来的维护成本；显示配置spring应该优先选择基于Java的配置，它比基于XML的配置更加强大、类型安全且易于重构。

### 1. 使用注解自动化装配bean
spring从两个角度来实现自动化装配：
* 组件扫描（component scanning）：spring会自动发现应用上下文中所创建的bean
* 自动装配（autowiring）：spring自动满足bean之间的依赖

1. @Component  
> 一个类上使用了这个注解，表明该类会作为组件类，并告知spring要为这个类创建bean。要想成功创建bean还需要开启组件扫描，组件扫描默认是不启用的。   

 通过设置``@Component``注解的``value``属性添加bean的标识名称。
 ```java
    @Component("bean标识")
 ```

 使用Java依赖注入规范中提供的``@Named``注解具有同样的效果，二者之间有细微的区别。
 
2. @ComponentScan  
> 该注解能在spring中启用组件扫描，默认是扫描与配置类相同的包。  

 通过设置``@Component``注解的``value``属性指明扫描的包。
 ```java
    @ComponentScan("要扫描的包名")
 ```

 ``basePackages``属性更加清晰的表明需要扫描的基础包，所设置的基础包名是以String类型表示的，这种方法是类型不安全的。如果重构的话，所指定的基础包可能会出现错误。
```java
    @ComponentScan(basePackages="要扫描的包名")
    @ComponentScan(basePackages={"包1","包2"})
```

``backPackageClasses``属性值为一个类或接口，这些类所在的包会成为扫描组件的基础包。
```java
    @ComponentScan(basePackageClasses=类.class)
    @ComponentScan(basePackageClasses={类.class, 接口.class})
```

3. ``@Autowired`` spring特有注解
> ``@Autowired``注解可以用在类的任何方法上。它会在spring创建所在bean的时候，自动实例化需要类型的bean。 如果没有匹配的bean，在创建上下文的时候，spring会抛出异常。为了避免异常出现，可以将``@Autowired``的``required``属性设置为``false``。

``required``属性设置为``false``，spring尝试自动装配，如果没有找到匹配的bean的话，spring会让这个bean处于喂装配的状态。代码中可能会出现``NullPointerException``异常。

``@Inject``注解源于Java依赖注入规范，也可以用于自动装配。


### 2. 使用XML装配bean
可以使用spring context命名空间的``<context:component-scan>``元素来配置。
````xml
<context:component-scan base-package="要扫描的包"/>
````

使用``<bean>``标签声明一个bean。
```xml
    <!-- 指定id -->
    <bean id="bean名称" class="全类名"/>
    <!-- 没有指定id，会根据全限定类名来命名，类似全类名#0,#0是计数形式，如果声明了另外一个相同的bean并没有指定id，id为全类名#1 -->
    <bean class="全类名">
```

借助构造器注入初始化bean  
1. 使用``<constructor-arg>``元素  
2. 使用spring3.0所引入的c-命名空间

假设bean1具有bean2属性，并通过构造器注入。
```java
public class Bean1{
    private Bean2 bean2;

    public Bean1(Bean2 bean2){
        this.bean2 = bean2;
    }
}
```
两种不同的注入方式
```xml
<bean id="bean1" class="Bean1">
    <constructor-arg ref="bean2"/>
</bean>
```
使用c命名空间需要在文档顶部声明其模式。
```xml
<bean id="bean1" class="bean1" c:bean2-ref="bean2"/>
```
c命名空间属性格式解释
```xml
c:bean2-ref="bean2"
//c：命名空间前缀
//bean2: 构造器参数名
//-ref: 注入bean的引用
//bean2: 要注入的bean的ID
```

假设bean如下：
```java
public class Bean{
    private String name；
    private String pass；
    private List sons；
    public Bean(String name, String pass, List sons){
        this.name = name;
        this.pass = pass;
        this.sons = sons;
    }
}
```
通过xml方式为其注入属性值，实例化Bean。c命名空间属性无法装配集合。
```xml
<bean id="bean" class="Bean">
    <construtor-arg value="myName"/>
    <construtor-arg value="myPass"/>
    <construtor-arg>
        <list>
            <value>son1</value>
            <value>son2</value>
        </list>
    </construtor-arg>
</bean>
```

```xml
<bean id="bean" class="Bean" c:_name="myName" c:_pass="myPass"/>
```

使用p命名空间对属性注入



### 3. 使用JavaConfig装配bean
> 进行显示配置时，JavaConfig是更好的方案，因为他更强大、类型安全、对重构友好。

``JavaConfig``类的关键在于为其增加``@Configuration``注解。  

* ``@Bean``
> 该注解会告诉spring这个方法将会返回一个对象，该对象要注册spring应用上下文中的bean。

默认情况下，spring中的bean都是单例的，没必要创建多余的相同实例，所以spring在创建相同实例的时候会拦截。  
``name``属性用于指定bean实例的不同名字。

## 混合使用

