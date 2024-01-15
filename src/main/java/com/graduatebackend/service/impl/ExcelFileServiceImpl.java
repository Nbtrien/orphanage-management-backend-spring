package com.graduatebackend.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.graduatebackend.blockchain.BlockChainService;
import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import com.graduatebackend.dto.donation.GetDonorListResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationStatsResponseDto;
import com.graduatebackend.entity.Donation;
import com.graduatebackend.entity.Donor;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.FundingUsage;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.repository.DonationRepository;
import com.graduatebackend.repository.DonorRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.repository.FundingUsageRepository;
import com.graduatebackend.service.ExcelFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelFileServiceImpl implements ExcelFileService {
	private final DonationRepository donationRepository;
	private final FundingUsageRepository fundingUsageRepository;
	private final FamilyRepository familyRepository;
	private final DonorRepository donorRepository;
	private final BlockChainService blockChainService;

	@Override
	public ByteArrayInputStream exportDonationsExcelFile() {
		List<Donation> donations = donationRepository.findAll();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Contacts");
			Row row = sheet.createRow(0);

			CellStyle headerCellStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontName("Times New Roman");
			headerCellStyle.setFont(headerFont);

			Cell cell = row.createCell(0);
			cell.setCellValue("ID");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Người tài trợ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Số tiền (VND)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(3);
			cell.setCellValue("Thời gian");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("Chiến dịch tài trợ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(5);
			cell.setCellValue("Lời nhắn");
			cell.setCellStyle(headerCellStyle);

			CreationHelper createHelper = workbook.getCreationHelper();

			Font timesNewRomanFont = workbook.createFont();
			timesNewRomanFont.setFontName("Times New Roman");
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
			dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy h:mm;@"));
			dataColumnDateFormatStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnCurrencyStyle = workbook.createCellStyle();
			dataColumnCurrencyStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
			dataColumnCurrencyStyle.setFont(timesNewRomanFont);

			for (int i = 0; i < donations.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				Cell column0 = dataRow.createCell(0);
				column0.setCellStyle(cellStyle);
				column0.setCellValue(donations.get(i).getDonationId());

				Cell column1 = dataRow.createCell(1);
				column1.setCellStyle(cellStyle);
				column1.setCellValue(donations.get(i).getDonor().getDonorFullName());

				Cell columnDonationAmount = dataRow.createCell(2);
				columnDonationAmount.setCellStyle(dataColumnCurrencyStyle);
				columnDonationAmount.setCellValue(donations.get(i).getAmount());

				Cell columnDonationTime = dataRow.createCell(3);
				columnDonationTime.setCellStyle(dataColumnDateFormatStyle);
				columnDonationTime.setCellValue(donations.get(i).getDonationTime());

				Cell column4 = dataRow.createCell(4);
				column4.setCellStyle(cellStyle);
				column4.setCellValue(donations.get(i).getPurpose().getPurpose());

				Cell column5 = dataRow.createCell(5);
				column5.setCellStyle(cellStyle);
				column5.setCellValue(donations.get(i).getDonationMessage());
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ByteArrayInputStream exportDonationsUsageExcelFile() {
		List<FundingUsage> fundingUsages = fundingUsageRepository.findAll();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Contacts");
			Row row = sheet.createRow(0);

			CellStyle headerCellStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontName("Times New Roman");
			headerCellStyle.setFont(headerFont);

			Cell cell = row.createCell(0);
			cell.setCellValue("ID");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Thời gian");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Số tiền (VND)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(3);
			cell.setCellValue("Chiến dịch tài trợ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("Chi tiết");
			cell.setCellStyle(headerCellStyle);

			CreationHelper createHelper = workbook.getCreationHelper();

			Font timesNewRomanFont = workbook.createFont();
			timesNewRomanFont.setFontName("Times New Roman");
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
			dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy h:mm;@"));
			dataColumnDateFormatStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnCurrencyStyle = workbook.createCellStyle();
			dataColumnCurrencyStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
			dataColumnCurrencyStyle.setFont(timesNewRomanFont);

			for (int i = 0; i < fundingUsages.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				Cell column0 = dataRow.createCell(0);
				column0.setCellStyle(cellStyle);
				column0.setCellValue(fundingUsages.get(i).getFundingUsageId());

				Cell column1 = dataRow.createCell(1);
				column1.setCellStyle(dataColumnDateFormatStyle);
				column1.setCellValue(fundingUsages.get(i).getUsageTime());

				Cell column2 = dataRow.createCell(2);
				column2.setCellStyle(dataColumnCurrencyStyle);
				column2.setCellValue(fundingUsages.get(i).getAmount());

				Cell column3 = dataRow.createCell(3);
				column3.setCellStyle(cellStyle);
				column3.setCellValue(fundingUsages.get(i).getPurpose().getPurpose());

				Cell column4 = dataRow.createCell(4);
				column4.setCellStyle(cellStyle);
				column4.setCellValue(fundingUsages.get(i).getUsageNote());
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ByteArrayInputStream exportFamilyDonationStatsExcelFile() {
		List<Family> families = familyRepository.findByIsDeleteIsFalse();
		List<GetFamilyDonationStatsResponseDto> stats = families.stream().map(family -> {
			GetFamilyDonationStatsResponseDto familyDonationStats = new GetFamilyDonationStatsResponseDto();
			familyDonationStats.setFamilyId(family.getFamilyId());
			familyDonationStats.setFamilyName(family.getFamilyName());

			List<Object[]> familyDonationInfoObjects = familyRepository.getFamilyDonationInfo(family.getFamilyId());
			familyDonationStats.setDonationCount((Long) familyDonationInfoObjects.get(0)[0]);
			familyDonationStats.setTotalDonationAmount(
					familyDonationInfoObjects.get(0)[1] == null ? 0 : (Double) familyDonationInfoObjects.get(0)[1]);
			List<Object[]> familyFundingInfoObject = familyRepository.getFamilyFundingInfo(family.getFamilyId());
			if (familyFundingInfoObject.get(0) != null) {
				familyDonationStats.setFundingAmount(
						familyFundingInfoObject.get(0)[0] == null ? 100 : (Double) familyFundingInfoObject.get(0)[0]);
			} else {
				familyDonationStats.setFundingAmount((double) 0);
			}
			return familyDonationStats;
		}).toList();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Contacts");
			Row row = sheet.createRow(0);

			CellStyle headerCellStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontName("Times New Roman");
			headerCellStyle.setFont(headerFont);

			Cell cell = row.createCell(0);
			cell.setCellValue("ID");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Gia đình");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Số lượt tài trợ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(3);
			cell.setCellValue("Số tiền tài trợ (VND)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("Số tiền đã phân bổ (VND)");
			cell.setCellStyle(headerCellStyle);

			CreationHelper createHelper = workbook.getCreationHelper();

			Font timesNewRomanFont = workbook.createFont();
			timesNewRomanFont.setFontName("Times New Roman");
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
			dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy h:mm;@"));
			dataColumnDateFormatStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnCurrencyStyle = workbook.createCellStyle();
			dataColumnCurrencyStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
			dataColumnCurrencyStyle.setFont(timesNewRomanFont);

			for (int i = 0; i < stats.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				Cell column0 = dataRow.createCell(0);
				column0.setCellStyle(cellStyle);
				column0.setCellValue(stats.get(i).getFamilyId());

				Cell column1 = dataRow.createCell(1);
				column1.setCellStyle(cellStyle);
				column1.setCellValue(stats.get(i).getFamilyName());

				Cell column2 = dataRow.createCell(2);
				column2.setCellStyle(cellStyle);
				column2.setCellValue(stats.get(i).getDonationCount());

				Cell column3 = dataRow.createCell(3);
				column3.setCellStyle(dataColumnCurrencyStyle);
				column3.setCellValue(stats.get(i).getTotalDonationAmount());

				Cell column4 = dataRow.createCell(4);
				column4.setCellStyle(dataColumnCurrencyStyle);
				column4.setCellValue(stats.get(i).getFundingAmount());
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ByteArrayInputStream exportDonorsExcelFile() {
		List<Donor> donors = donorRepository.findByIsDeleteIsFalse();
		List<GetDonorListResponseDto> responseDtos = donors.stream().map(donor -> {
			GetDonorListResponseDto responseDto = DonationMapper.INSTANCE.toGetDonorListResponseDto(donor);
			GetDonationStatsResponseDto donationStats = blockChainService.getDonorStats(donor.getDonorToken());
			responseDto.setStats(donationStats);
			return responseDto;
		}).toList();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Contacts");
			Row row = sheet.createRow(0);

			CellStyle headerCellStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontName("Times New Roman");
			headerCellStyle.setFont(headerFont);

			Cell cell = row.createCell(0);
			cell.setCellValue("ID");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Họ và tên");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Ngày sinh");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(3);
			cell.setCellValue("Địa chỉ email");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("Số điện thoại");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(5);
			cell.setCellValue("Số lượt tài trợ");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(6);
			cell.setCellValue("Số tiền tài trợ (VND)");
			cell.setCellStyle(headerCellStyle);

			CreationHelper createHelper = workbook.getCreationHelper();

			Font timesNewRomanFont = workbook.createFont();
			timesNewRomanFont.setFontName("Times New Roman");
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
			dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			dataColumnDateFormatStyle.setFont(timesNewRomanFont);

			CellStyle dataColumnCurrencyStyle = workbook.createCellStyle();
			dataColumnCurrencyStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0"));
			dataColumnCurrencyStyle.setFont(timesNewRomanFont);

			for (int i = 0; i < responseDtos.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				Cell column0 = dataRow.createCell(0);
				column0.setCellStyle(cellStyle);
				column0.setCellValue(responseDtos.get(i).getDonorId());

				Cell column1 = dataRow.createCell(1);
				column1.setCellStyle(cellStyle);
				column1.setCellValue(responseDtos.get(i).getDonorFullName());

				Cell column2 = dataRow.createCell(2);
				column2.setCellStyle(dataColumnDateFormatStyle);
				column2.setCellValue(responseDtos.get(i).getDonorDateOfBirth());

				Cell column3 = dataRow.createCell(3);
				column3.setCellStyle(dataColumnCurrencyStyle);
				column3.setCellValue(responseDtos.get(i).getDonorMailAddress());

				Cell column4 = dataRow.createCell(4);
				column4.setCellStyle(dataColumnCurrencyStyle);
				column4.setCellValue(responseDtos.get(i).getDonorPhoneNumber());

				Cell column5 = dataRow.createCell(5);
				column5.setCellStyle(dataColumnCurrencyStyle);
				column5.setCellValue(responseDtos.get(i).getStats().getCount());

				Cell column6 = dataRow.createCell(6);
				column6.setCellStyle(dataColumnCurrencyStyle);
				column6.setCellValue(responseDtos.get(i).getStats().getAmount());
			}

			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
