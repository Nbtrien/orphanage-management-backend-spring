package com.graduatebackend.blockchain;

import com.graduatebackend.utils.ConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class BlockChainService {
    private final Web3j web3j;

    private final Credentials credentials = Credentials.create(
            "0x9bb0a778e09eb593d8fd251a8de34d65e82615cd9bc06d4bcd1bd583f0d46711");

    private static final String CONTRACT_ADDRESS = "0xAAB93B36a673f2A5177f1a7F51daDA4850769719";

    public BlockChainService(Web3j web3j) {
        this.web3j = web3j;
    }

    /**
     * Add new Donation to Smart contract
     *
     * @param requestDto AddNewDonationContractRequestDto
     * @return AddNewDonationContractResponseDto
     */
    public AddNewDonationContractResponseDto addDonation(AddNewDonationContractRequestDto requestDto) {
        log.info("add new donation to blockchain start.");
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        CompletableFuture<TransactionReceipt> s = contract.addDonation(requestDto)
                .sendAsync();
        try {
            List<DonationContract.DonationAddedEventResponse> responses = contract.getDonationAddedEvents(s.get());
            DonationContract.DonationAddedEventResponse eventResponse = responses.get(0);
            AddNewDonationContractResponseDto responseDto =
                    AddNewDonationContractResponseDto.builder().transactionHash(s.join().getTransactionHash())
                            .donationId(Math.toIntExact(eventResponse.donationId.longValue()))
                            .donorToken(eventResponse.donorToken).blockHash(eventResponse.blockHash.intValue())
                            .donationHash(ConvertUtils.bytesToHex(eventResponse.donationHash)).build();
            log.info("add new donation to blockchain done.");
            return responseDto;
        } catch (InterruptedException | ExecutionException e) {
            log.info("add new donation to blockchain failed.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Add new Donation usage to smart contract
     *
     * @param requestDto AddNewDonationUsageRequestDto
     * @return DonationUsageAddedEventResponse
     * @throws ExecutionException
     */
    public DonationContract.DonationUsageAddedEventResponse addDonationUsage(
            AddNewDonationUsageRequestDto requestDto) throws ExecutionException {
        log.info("add new donation usage to blockchain start.");
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        CompletableFuture<TransactionReceipt> s = contract.addDonationUsage(requestDto)
                .sendAsync();

        try {
            TransactionReceipt transactionReceipt = s.get();
            List<DonationContract.DonationUsageAddedEventResponse> responses =
                    contract.getDonationUsageAddedEvents(transactionReceipt);
            DonationContract.DonationUsageAddedEventResponse eventResponse = responses.get(0);
            log.info("add new donation usage to blockchain done.");
            return eventResponse;
        } catch (InterruptedException e) {
            log.info("add new donation usage to blockchain failed.");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.info("add new donation usage to blockchain failed.");
            throw new ExecutionException(e);
        }
    }

    /**
     * get all donation from smart contract
     *
     * @return List<GetDonationContractResponseDto>
     */
    public List<GetDonationContractResponseDto> getDonations() {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        RemoteFunctionCall<List> remoteFunctionCall = contract.getDonations();
        CompletableFuture<List> transactionReceipt = remoteFunctionCall.sendAsync();
        List<GetDonationContractInfo> result = transactionReceipt.join();
        if (!result.isEmpty()) {
            List<GetDonationContractInfo> donations = convertToDonations(result);
            return DonationContractMapper.INSTANCE.toGetDonationContractResponseDtoList(donations);
        } else {
            return null;
        }
    }

    /**
     * get donation by donor tokens from smart contract
     *
     * @param donorToken String
     * @return List<GetDonationContractResponseDto>
     */
    public List<GetDonationContractResponseDto> getDonationsByDonorToken(String donorToken) {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        RemoteFunctionCall<List> remoteFunctionCall = contract.getDonationsByDonorToken(donorToken);
        CompletableFuture<List> transactionReceipt = remoteFunctionCall.sendAsync();
        List<GetDonationContractInfo> result = transactionReceipt.join();
        if (!result.isEmpty()) {
            List<GetDonationContractInfo> donations = convertToDonations(result);
            return DonationContractMapper.INSTANCE.toGetDonationContractResponseDtoList(donations);
        } else {
            return null;
        }
    }

    /**
     * count donation from smart contract
     *
     * @return BigInteger
     */
    public BigInteger getDonationCount() {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        RemoteFunctionCall<Uint256> countCall = contract.getDonationCount();
        CompletableFuture<Uint256> transactionReceipt = countCall.sendAsync();
        Uint256 count = transactionReceipt.join();
        BigInteger countValue = count.getValue();
        return countValue;
    }

    /**
     * get donation statistics by purpose from smart contract
     *
     * @param purposeId Integer
     * @return GetDonationStatsResponseDto
     */
    public GetDonationStatsResponseDto getPurposeStats(Integer purposeId) {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> call =
                contract.getPurposeStats(purposeId);
        CompletableFuture<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> transactionReceipt = call.sendAsync();
        Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> tuple4 = transactionReceipt.join();
        GetDonationStatsResponseDto donationStats =
                GetDonationStatsResponseDto.builder()
                        .count(tuple4.component1().intValueExact())
                        .amount(tuple4.component2().longValueExact())
                        .usedAmount(tuple4.component3().longValueExact())
                        .remainingAmount(tuple4.component4().longValueExact())
                        .build();
        return donationStats;
    }

    /**
     * Get donation statistics by donor from smart contract
     *
     * @param donorToken String
     * @return GetDonationStatsResponseDto
     */
    public GetDonationStatsResponseDto getDonorStats(String donorToken) {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        RemoteFunctionCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> call =
                contract.getDonorStats(donorToken);
        CompletableFuture<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> transactionReceipt = call.sendAsync();
        Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> tuple4 = transactionReceipt.join();
        GetDonationStatsResponseDto donationStats =
                GetDonationStatsResponseDto.builder()
                        .count(tuple4.component1().intValueExact())
                        .amount(tuple4.component2().longValueExact())
                        .usedAmount(tuple4.component4().longValueExact())
                        .remainingAmount(tuple4.component4().longValueExact())
                        .build();
        return donationStats;
    }

    /**
     * get donations list for usage from smart contract
     *
     * @param purposeId Integer
     * @param amount    double
     * @return List<GetDonationContractResponseDto>
     */
    public List<GetDonationContractResponseDto> getDonationByPurposeForUse(Integer purposeId, double amount) {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        RemoteFunctionCall<List> remoteFunctionCall = contract.getDonationsByPurposeForUse(purposeId, amount);
        CompletableFuture<List> transactionReceipt = remoteFunctionCall.sendAsync();
        List<GetDonationContractInfo> result = transactionReceipt.join();
        if (!result.isEmpty()) {
            List<GetDonationContractInfo> donations = convertToDonations(result);
            return DonationContractMapper.INSTANCE.toGetDonationContractResponseDtoList(donations);
        } else {
            return null;
        }
    }

    /**
     * get donation by donation hash from smart contract
     *
     * @param donationHash String
     * @return GetDonationContractResponseDto
     */
    public GetDonationContractResponseDto getDonationByDonationHash(String donationHash) {
        DonationContract contract = DonationContract.load(CONTRACT_ADDRESS, web3j, credentials,
                                                          new DefaultGasProvider());
        byte[] bytes = ConvertUtils.hexToBytes(donationHash);

        RemoteFunctionCall<GetDonationContractInfo> call =
                contract.getByDonationHash(bytes);
        CompletableFuture<GetDonationContractInfo> transactionReceipt = call.sendAsync();
        GetDonationContractInfo donationContractInfo = transactionReceipt.join();

        return DonationContractMapper.INSTANCE.toGetDonationContractResponseDto(donationContractInfo);
    }

    private List<GetDonationContractInfo> convertToDonations(List<GetDonationContractInfo> result) {
        List<GetDonationContractInfo> donations = new ArrayList<>();
        for (Object ob : result) {
            Object donation = new GetDonationContractInfo();
            donation = ob;
            GetDonationContractInfo donation1 = new GetDonationContractInfo();
            try {
                donation1.setDonationId((BigInteger) donation.getClass().getField("donationId").get(donation));
                donation1.setDonorName((String) donation.getClass().getField("donorName").get(donation));
                donation1.setAmount((BigInteger) donation.getClass().getField("amount").get(donation));
                donation1.setBlockHash((BigInteger) donation.getClass().getField("blockHash").get(donation));
                donation1.setDonationTime((BigInteger) donation.getClass().getField("donationTime").get(donation));
                donation1.setDonorToken((String) donation.getClass().getField("donorToken").get(donation));
                donation1.setDonationHash((byte[]) donation.getClass().getField("donationHash").get(donation));
                donation1.setPurposeId((BigInteger) donation.getClass().getField("purposeId").get(donation));
                donation1.setPurpose((String) donation.getClass().getField("purpose").get(donation));
                donation1.setFamilyId((BigInteger) donation.getClass().getField("familyId").get(donation));
                donations.add(donation1);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        return donations;
    }

}
