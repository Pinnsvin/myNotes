# 用户输入
# mes = input("Please input your grade:")
# if int(mes) < 0 or int(mes) > 100:
#     print("输入成绩不合法")
# elif int(mes) < 60:
#     print("你不及格，lower")
# elif int(mes) >= 60 and int(mes) < 80:
#     print("及格了")
# else:
#     print("考那么高，有什么用？")
#
# prompt = "Input something by your way,\n'quit' can quit this\ninput:"
# message = ""
# while message != "quit":
#     message = input(prompt)
#     print(message)
#
# def hello(username):# 函数定义
#     """简单函数定义"""# 文档注释
#     print("Hello, "+username.title())# 函数体
#
# hello("linus")#函数调用
# hello(username="linus")#关键字实参
#
# def message(user,mess='hello'):#设置形参默认值
#     print(user.title()+","+mess)
#
# def getFullName(first_name,last_name,middle_name=""):
#     if middle_name:
#         full_name = first_name.title()+" "+middle_name.title()+" "+last_name.title
#     else:
#         full_name = first_name.title()+" "+last_name
#     return full_name
#
# message("linus")
# message("linus","linux's father")
# print(getFullName("Pinnsvin","cn"))


# def getJoinner(*names):#获取参加者的姓名
#     re = {"names":names,"len":len(names)}
#     return re
# print(getJoinner("jone","anner","joe","lusi"))

from . import person

print(person.eat("fish"))
# print(person.work("miscrsoft"))
