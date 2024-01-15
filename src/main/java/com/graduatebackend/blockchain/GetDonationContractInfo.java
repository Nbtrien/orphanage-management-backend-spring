package com.graduatebackend.blockchain;

import lombok.Getter;
import lombok.Setter;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.Arrays;

@Getter
@Setter
public class GetDonationContractInfo extends DynamicStruct {
    public BigInteger donationId;

    public String donorToken;

    public String donorName;

    public BigInteger amount;

    public BigInteger donationTime;

    public BigInteger purposeId;

    public String purpose;

    public BigInteger familyId;

    public BigInteger blockHash;

    public byte[] donationHash;

    public GetDonationContractInfo() {

    }

    public GetDonationContractInfo(BigInteger donationId, String donorToken, String donorName, BigInteger amount,
                                   BigInteger donationTime, BigInteger purposeId, String purpose, BigInteger familyId,
                                   BigInteger blockHash, byte[] donationHash) {
        super(new org.web3j.abi.datatypes.generated.Uint256(donationId),
              new org.web3j.abi.datatypes.Utf8String(donorToken),
              new org.web3j.abi.datatypes.Utf8String(donorName),
              new org.web3j.abi.datatypes.generated.Uint256(amount),
              new org.web3j.abi.datatypes.generated.Uint256(donationTime),
              new org.web3j.abi.datatypes.generated.Uint256(purposeId),
              new org.web3j.abi.datatypes.Utf8String(purpose),
              new org.web3j.abi.datatypes.generated.Uint256(familyId),
              new org.web3j.abi.datatypes.generated.Uint256(blockHash),
              new org.web3j.abi.datatypes.generated.Bytes32(donationHash));
        this.donationId = donationId;
        this.donorToken = donorToken;
        this.donorName = donorName;
        this.amount = amount;
        this.donationTime = donationTime;
        this.purposeId = purposeId;
        this.purpose = purpose;
        this.familyId = familyId;
        this.blockHash = blockHash;
        this.donationHash = donationHash;
    }

    public GetDonationContractInfo(Uint256 donationId, Utf8String donorToken, Utf8String donorName, Uint256 amount,
                                   Uint256 donationTime, Uint256 purposeId, Utf8String purpose, Uint256 familyId,
                                   Uint256 blockHash, Bytes32 donationHash) {
        super(donationId, donorToken, donorName, amount, donationTime, purposeId, purpose, familyId, blockHash,
              donationHash);
        this.donationId = donationId.getValue();
        this.donorToken = donorToken.getValue();
        this.donorName = donorName.getValue();
        this.amount = amount.getValue();
        this.donationTime = donationTime.getValue();
        this.purposeId = purposeId.getValue();
        this.purpose = purpose.getValue();
        this.familyId = familyId.getValue();
        this.blockHash = blockHash.getValue();
        this.donationHash = donationHash.getValue();
    }

    @Override
    public String toString() {
        return "GetDonationContractInfo{" +
                "donationId=" + donationId +
                ", donorToken='" + donorToken + '\'' +
                ", donorName='" + donorName + '\'' +
                ", amount=" + amount +
                ", donationTime=" + donationTime +
                ", blockHash=" + blockHash +
                ", donationHash=" + Arrays.toString(donationHash) +
                '}';
    }
}