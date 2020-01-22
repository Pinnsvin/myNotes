````python
SyntaxError: 'return' outside function
````
语法错误：``return``在函数之外.   
造成原因：没有缩进。  

---
````python
ModuleNotFoundError: No module named 'a'
````
找不到模块错误：没有一个叫``a``的模块。  
解决方案：
````python
import sys 
sys.path
````
获取目录后，把模块放在``lib``目录下（如果有包含lib目录），否则放在其中一个目录下。  

---
