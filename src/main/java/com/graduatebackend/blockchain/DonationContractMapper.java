package com.graduatebackend.blockchain;

import com.graduatebackend.dto.employee.GetEmployeePositionResponseDto;
import com.graduatebackend.entity.EmployeePosition;
import com.graduatebackend.mapper.DonationMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface DonationContractMapper {
    DonationContractMapper INSTANCE = Mappers.getMapper(DonationContractMapper.class);

    /**
     * Convert GetDonationContractInfo to GetDonationContractResponseDto
     *
     * @param donationContractInfo
     * @return
     */
    @Mapping(target = "donationTime", expression = "java(convertBigIntToTimestamp(donationContractInfo" +
            ".getDonationTime()))")
    @Mapping(target = "donationHash", expression = "java(convertBytesToHex(donationContractInfo.getDonationHash()))")
    @Named("toGetDonationContractResponseDto")
    GetDonationContractResponseDto toGetDonationContractResponseDto(GetDonationContractInfo donationContractInfo);

    /**
     * Convert GetDonationContractInfo list to GetDonationContractResponseDto list
     *
     * @param donationContractInfoList
     * @return
     */
    @IterableMapping(qualifiedByName = "toGetDonationContractResponseDto")
    List<GetDonationContractResponseDto> toGetDonationContractResponseDtoList(
            List<GetDonationContractInfo> donationContractInfoList);

    /**
     * Covert from BigInteger to Timestamp
     *
     * @param time
     * @return
     */
    default Timestamp convertBigIntToTimestamp(BigInteger time) {
        return new Timestamp(time.longValue());
    }

    /**
     * Convert from byte[] to hex string
     *
     * @param bytes
     * @return
     */
    default String convertBytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b));
        }
        return hexStringBuilder.toString();
    }
}
