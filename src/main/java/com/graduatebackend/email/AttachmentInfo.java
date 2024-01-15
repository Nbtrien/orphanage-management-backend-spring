package com.graduatebackend.email;

public class AttachmentInfo {
	private String fileName;
	private String contentType;
	private byte[] data;

	public AttachmentInfo(String fileName, String contentType, byte[] data) {
		this.fileName = fileName;
		this.contentType = contentType;
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public byte[] getData() {
		return data;
	}
}