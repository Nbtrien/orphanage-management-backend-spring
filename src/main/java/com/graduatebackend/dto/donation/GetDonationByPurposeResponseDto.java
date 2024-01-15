package com.graduatebackend.dto.donation;

import com.graduatebackend.blockchain.GetDonationStatsResponseDto;
import com.graduatebackend.dto.PageApiResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDonationByPurposeResponseDto implements Serializable {
    private Long total;
    private Integer limit;
    private Integer totalPage;
    private Integer page;
    private List<GetDonationResponseDto> records;
    private GetDonationStatsResponseDto stats;

    public GetDonationByPurposeResponseDto(final Page<GetDonationResponseDto> page, GetDonationStatsResponseDto stats) {
        this.records = page.getContent();
        this.limit = page.getSize();
        this.page = page.getNumber() + 1;
        this.total = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.stats = stats;
    }
}
