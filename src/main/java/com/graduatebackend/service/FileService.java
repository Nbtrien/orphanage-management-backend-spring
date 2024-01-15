package com.graduatebackend.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.graduatebackend.dto.file.PresignedUrlResponseDto;
import com.lowagie.text.pdf.BaseFont;

@Service
public class FileService {
	@Autowired
	private AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	/**
	 * Generate presigned url
	 *
	 * @param fileName
	 * @param folderPath
	 * @return
	 */
	public PresignedUrlResponseDto generateUrl(String fileName, String folderPath) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);

		fileName = UUID.randomUUID() + "-" + fileName;

		String filePath = folderPath + "/" + fileName;

		String presignedUrl = amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), HttpMethod.PUT)
				.toString();
		return PresignedUrlResponseDto.builder().fileName(fileName).filePath(filePath).presignedUrl(presignedUrl)
				.build();
	}

	/**
	 * generate file url
	 *
	 * @param fileName
	 * @return
	 */
	public String generateFileUrl(String fileName) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName);
		URL fileUrl = amazonS3.generatePresignedUrl(request);
		return fileUrl.toString();
	}

	/**
	 * generate file pdf from file html
	 *
	 * @param htmlContent
	 * @return
	 */
	public byte[] generatePdf(String htmlContent) throws Exception {
		ITextRenderer renderer = new ITextRenderer();
		Resource fontResource = new ClassPathResource("fonts/times.ttf");
		String fontPath = fontResource.getURL().getPath();

		renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			renderer.createPDF(outputStream);
			return outputStream.toByteArray();
		}
	}

	/**
	 * Upload file to s3
	 *
	 * @param data
	 * @param fileName
	 */
	public String uploadFileToS3(byte[] data, String folderPath, String fileName) {
		InputStream inputStream = new ByteArrayInputStream(data);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(data.length);

		String filePath = folderPath + "/" + generateFileName(fileName);

		amazonS3.putObject(new PutObjectRequest(bucketName, filePath, inputStream, metadata));

		return filePath;
	}

	/**
	 * Download file from S3
	 *
	 * @param fileName
	 * @return
	 */
	public byte[] downloadFromS3(String fileName) {
		try {
			S3Object s3Object = amazonS3.getObject(bucketName, fileName);
			try (InputStream inputStream = s3Object.getObjectContent()) {
				return IOUtils.toByteArray(inputStream);
			} finally {
				s3Object.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String generateFileName(String fileName) {
		return new Date().getTime() + "-" + fileName.replace(" ", "_");
	}
}
