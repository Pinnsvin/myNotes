# Java使用Apache POI操作Excel

## 概述
> HSSF是POI Project的Excel '97（-2007）文件格式的纯Java实现。XSSF是POI Project的Excel 2007 OOXML（.xlsx）文件格式的纯Java实现。
> 
> HSSF和XSSF提供读取电子表格的方式来创建，修改，读取和写入XLS电子表格。
> 
> 如果您只是阅读电子表格数据，则可以在org.apache.poi.hssf.eventusermodel包或org.apache.poi.xssf.eventusermodel包中使用eventmodel api，具体取决于您的文件格式。
> 
> 如果您要修改电子表格数据，请使用usermodel api。您也可以通过这种方式生成电子表格。
> 
> 请注意，usermodel系统比低级别的eventusermodel具有更高的内存占用量，但主要优点是使用起来更简单。另请注意，由于支持新的XSSF Excel 2007 OOXML（.xlsx）文件是基于XML的，所以处理它们的内存占用量要比旧的HSSF支持的（.xls）二进制文件高。[^overView]

## 创建一个工作簿
```java
Workbook wb = new HSSFWorkbook();
FileOutputStream fileOut = new FileOutputStream("workbook.xls");
wb.write(fileOut);
fileOut.close();

Workbook wb = new XSSFWorkbook();
FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
wb.write(fileOut);
fileOut.close();
```


[^overView]:[Apache POI OverView](http://poi.apache.org/spreadsheet/index.html)
