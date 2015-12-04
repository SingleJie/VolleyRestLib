package edu.single.library.volley.models;

import java.io.File;

/**
 * 用于文件上传实体类
 * @author Single
 *
 */
public class FileModels {
	
	public FileModels(String dataName,String contentType,File data)
	{
		this.data = data;
		this.dataName = dataName;
		this.contentType = contentType;
	}
	
	public FileModels()
	{
		
	}

	/**数据*/
	public File data;
	
	/**Http ContentType*/
	public String contentType;
	
	/**数据名称*/
	public String dataName;
}
