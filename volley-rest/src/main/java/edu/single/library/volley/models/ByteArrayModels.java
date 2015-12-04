package edu.single.library.volley.models;


public class ByteArrayModels {

	public ByteArrayModels(String dataName,String contentType,byte[] data)
	{
		this.data = data;
		this.dataName = dataName ;
		this.contentType = contentType ;
	}
	
	public ByteArrayModels()
	{
		
	}
	
	/**数据*/
	public byte[] data;
	
	/**Http ContentType*/
	public String contentType;
	
	/**数据名称*/
	public String dataName;
}
