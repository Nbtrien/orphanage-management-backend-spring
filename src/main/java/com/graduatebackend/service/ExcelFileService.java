package com.graduatebackend.service;

import java.io.ByteArrayInputStream;

public interface ExcelFileService {
	/**
	 * export donations excel file
	 * 
	 * @return
	 */
	ByteArrayInputStream exportDonationsExcelFile();

	/**
	 * export donation usage excel file
	 * 
	 * @return
	 */
	ByteArrayInputStream exportDonationsUsageExcelFile();

	/**
	 * export family donations statistics excel file
	 * 
	 * @return
	 */
	ByteArrayInputStream exportFamilyDonationStatsExcelFile();

	/**
	 * export donor excel file
	 */
	ByteArrayInputStream exportDonorsExcelFile();
}
