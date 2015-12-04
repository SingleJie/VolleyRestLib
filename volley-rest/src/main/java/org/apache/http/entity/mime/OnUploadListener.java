package org.apache.http.entity.mime;

public interface OnUploadListener {

	public void onUploadCallBack(long current,long totalLength);
}
