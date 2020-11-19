# 【XML】xStream浅录
XStream可以用来转换对象-XML，或者XML-对象。   
官网地址：http://x-stream.github.io  

## 小案例：
### 实体类
**FileVo.java**  
````java 
package cn.pinnsvin.xml.vo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class FileVo {

	private String owner;
	private Date createDate;
	private Date lastUpdateDate;
	private String authority;
	private BigInteger size;
	private String title;
	private String content;
	@Override
	public String toString() {
		return "FileVo [owner=" + owner + ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate
				+ ", authority=" + authority + ", size=" + size + ", title=" + title + ", content=" + content + "]";
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public BigInteger getSize() {
		return size;
	}
	public void setSize(BigInteger size) {
		this.size = size;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
````

**FolderVo.java**  
````java 
package cn.pinnsvin.xml.vo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class FolderVo {

	private List<FileVo> files;
	private List<FolderVo> folders;
	private String owner;
	private Date createDate;
	private Date lastUpdateDate;
	private String authority;
	private BigInteger allSize;
	private BigInteger usedSize;
	@Override
	public String toString() {
		return "FolderVo [files=" + files + ", folders=" + folders + ", owner=" + owner + ", createDate=" + createDate
				+ ", lastUpdateDate=" + lastUpdateDate + ", authority=" + authority + ", allSize=" + allSize
				+ ", usedSize=" + usedSize + "]";
	}
	public List<FileVo> getFiles() {
		return files;
	}
	public void setFiles(List<FileVo> files) {
		this.files = files;
	}
	public List<FolderVo> getFolders() {
		return folders;
	}
	public void setFolders(List<FolderVo> folders) {
		this.folders = folders;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public BigInteger getAllSize() {
		return allSize;
	}
	public void setAllSize(BigInteger allSize) {
		this.allSize = allSize;
	}
	public BigInteger getUsedSize() {
		return usedSize;
	}
	public void setUsedSize(BigInteger usedSize) {
		this.usedSize = usedSize;
	}
}
````
### 工具类
**XStreamUtils.java**
````java 
package cn.pinnsvin.xml.utils;

import java.io.File;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cn.pinnsvin.xml.vo.FileVo;
import cn.pinnsvin.xml.vo.FolderVo;

public class XStreamUtils {
	private static XStream xStream;
	static{
		init();
	}
	public static void init(){
		xStream = new XStream(new DomDriver());
		xStream.registerConverter(new cn.pinnsvin.xml.utils.DateConverter("yyyyMMdd"));
		myAlias();
	}
	public static void myAlias(){
		xStream.alias("Folder", FolderVo.class);
		xStream.alias("File", FileVo.class);
	}
	public static String objectToXML(Object object){
		String xml = xStream.toXML(object);
		return xml;
	}
	public static Object xmlToObject(String xml){
		Object obj = xStream.fromXML(xml);
		return obj;
	}
	public static Object xmlToObject(File file){
		Object obj = xStream.fromXML(file);
		return obj;
	}
}
````
### 日期格式转换类
**DateConverter.java**
````java 
package cn.pinnsvin.xml.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
public class DateConverter implements Converter{
	private String format;
	public DateConverter(String format) {
		super();
		this.format = format;
	}

	@Override
	public boolean canConvert(Class clazz) {
		return java.util.Date.class.isAssignableFrom(clazz);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        java.util.Date date = (Date) source;
        DateFormat formatter = new SimpleDateFormat(format);
        writer.setValue(formatter.format(date));
	}

	@Override
	 public Object unmarshal(HierarchicalStreamReader reader,
             UnmarshallingContext context) {
     java.util.Date date = new Date();
     DateFormat formatter = new SimpleDateFormat(format);
     try {
    	 date = formatter.parse(reader.getValue());
     } catch (ParseException e) {
             throw new ConversionException(e.getMessage(), e);
     }
     return date;
}
}
````
### 调用
````java 
package cn.pinnsvin;

import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pinnsvin.xml.utils.XStreamUtils;
import cn.pinnsvin.xml.vo.FileVo;
import cn.pinnsvin.xml.vo.FolderVo;
import cn.pinnsvin.xml.vo.MyDate;

public class Main {

	public static void main(String[] args) throws ParseException {
		getS();
	}
	
	public static void getS() throws ParseException{
			FolderVo folder = new FolderVo();
			FileVo file = new FileVo();
			FileVo file1 = new FileVo();
			List<FileVo> files = new ArrayList();
			file.setAuthority("读写");
			file.setContent("XStream可以用来转换对象-XML，或者XML-对象。");
			file.setTitle("xml解析");
			file.setCreateDate(new SimpleDateFormat("yyyyMMdd").parse("20161125"));
			file1.setAuthority("读");
			file1.setContent("XStream可以用来转换对象-XML，或者XML-对象。");
			file1.setTitle("xml解析之XStream");
			files.add(file);
			files.add(file1);
			folder.setFiles(files);
			folder.setAuthority("读写");
			folder.setOwner("Pinnsvin");
			folder.setCreateDate(new Date());
			
			XStreamUtils streamUtils = new XStreamUtils();
			System.out.println(streamUtils.objectToXML(folder));
			FolderVo fd = (FolderVo)streamUtils.xmlToObject(new File("D:\\2.xml"));
			System.out.println(fd.toString());
			List<FileVo> fl = fd.getFiles();
//			System.out.println(fl.toString()); 
	}
}
````
### 输出
````xml
<Folder>
  <files>
    <File>
      <createDate>20161125</createDate>
      <authority>读写</authority>
      <title>xml解析</title>
      <content>XStream可以用来转换对象-XML，或者XML-对象。</content>
    </File>
    <File>
      <authority>读</authority>
      <title>xml解析之XStream</title>
      <content>XStream可以用来转换对象-XML，或者XML-对象。</content>
    </File>
  </files>
  <owner>Pinnsvin</owner>
  <createDate>20171028</createDate>
  <authority>读写</authority>
</Folder>
FolderVo [files=[FileVo [owner=null, createDate=Fri Nov 25 00:00:00 GMT+08:00 2016, lastUpdateDate=null, authority=读写, size=null, title=xml解析, content=XStream可以用来转换对象-XML，或者XML-对象。], FileVo [owner=null, createDate=null, lastUpdateDate=null, authority=读, size=null, title=xml解析之XStream, content=XStream可以用来转换对象-XML，或者XML-对象。]], folders=null, owner=Pinnsvin, createDate=Sat Oct 28 00:00:00 GMT+08:00 2017, lastUpdateDate=null, authority=读写, allSize=null, usedSize=null]
````
