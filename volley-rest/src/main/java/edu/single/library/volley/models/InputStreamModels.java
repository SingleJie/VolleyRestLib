package edu.single.library.volley.models;

import java.io.InputStream;

public class InputStreamModels {
	
	public InputStreamModels(String dataName,String contentType,InputStream data)
	{
		this.data = data;
		this.dataName = dataName;
		this.contentType = contentType;
	}
	
	
	public InputStreamModels()
	{
		
	}

	/**数据*/
	public InputStream data;
	
	/**Http ContentType*/
	public String contentType;
	
	/**数据名称*/
	public String dataName;
}
