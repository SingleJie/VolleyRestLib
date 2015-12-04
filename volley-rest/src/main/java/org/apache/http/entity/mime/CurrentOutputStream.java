package org.apache.http.entity.mime;

import java.io.IOException;
import java.io.OutputStream;

public class CurrentOutputStream extends OutputStream {

	private OutputStream mOutputStream;
	private OnUploadListener mListener;
	
	public CurrentOutputStream(OutputStream mOutputStream,OnUploadListener mListener)
	{
		this.mOutputStream = mOutputStream;
		this.mListener = mListener;
	}
	
	@Override
	public void write(int oneByte) throws IOException 
	{
		if(mListener!=null)
		{
			mListener.onUploadCallBack(oneByte,0);
		}
		
		mOutputStream.write(oneByte);
	}
	
	@Override
	public void write(byte[] buffer, int offset, int count) throws IOException 
	{
		if(mListener!=null)
		{
			mListener.onUploadCallBack(count-offset,0);
		}
		mOutputStream.write(buffer, offset, count);
	}
	
	@Override
	public void write(byte[] buffer) throws IOException
	{
		if(mListener!=null)
		{
			mListener.onUploadCallBack(buffer.length,0);
		}
		mOutputStream.write(buffer);
	}

}
