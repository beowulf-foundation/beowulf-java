/*
 *     This file is part of BeowulfJ (formerly known as 'Beowulf-Java-Api-Wrapper')
 *
 *     BeowulfJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     BeowulfJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.beowulfchain.beowulfj.plugins.apis.database.models;

import com.beowulfchain.beowulfj.protocol.Symbol;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {
    @JsonProperty("IS_TEST_NET")
    private boolean isTestNet;
    @JsonProperty("BEOWULF_ENABLE_SMT")
    private boolean isBeowulfEnableSmt;
    @JsonProperty("SMT_TOKEN_CREATION_FEE")
    private long smtTokenCreationFee;
    @JsonProperty("WD_SYMBOL")
    private Symbol wdSymbol;
    @JsonProperty("BEOWULF_100_PERCENT")
    private short beowulf100Percent;
    @JsonProperty("BEOWULF_1_PERCENT")
    private short beowulf1Percent;
    @JsonProperty("BEOWULF_1_TENTH_PERCENT")
    private short beowulf1TenthPercent;
    @JsonProperty("BEOWULF_ACCOUNT_RECOVERY_REQUEST_EXPIRATION_PERIOD")
    private String beowulfAccountRecoveryRequestExpirationPeriod;
    @JsonProperty("BEOWULF_ADDRESS_PREFIX")
    private String beowulfAddressPrefix;
    @JsonProperty("BEOWULF_BLOCKCHAIN_HARDFORK_VERSION")
    private String beowulfBlockchainHardforkVersion;
    @JsonProperty("BEOWULF_BLOCKCHAIN_VERSION")
    private String beowulfBlockchainVersion;
    @JsonProperty("BEOWULF_BLOCK_INTERVAL")
    private int beowulfBlockInterval;
    @JsonProperty("BEOWULF_BLOCKS_PER_DAY")
    private int beowulfBlocksPerDay;
    @JsonProperty("BEOWULF_BLOCKS_PER_YEAR")
    private long beowulfBlocksPerYear;
    @JsonProperty("BEOWULF_CASHOUT_WINDOW_SECONDS")
    private int beowulfCashoutWindowSeconds;
    @JsonProperty("BEOWULF_CASHOUT_WINDOW_SECONDS_PRE_HF12")
    private int beowulfCashoutWindowSecondsPreHf12;
    @JsonProperty("BEOWULF_CASHOUT_WINDOW_SECONDS_PRE_HF17")
    private int beowulfCashoutWindowSecondsPreHf17;
    @JsonProperty("BEOWULF_CHAIN_ID")
    private String beowulfChainId;
    @JsonProperty("BEOWULF_COMMENT_REWARD_FUND_NAME")
    private String beowulfCommentRewardFundName;
    @JsonProperty("BEOWULF_CONTENT_APR_PERCENT")
    private int beowulfContentAprPercent;
    @JsonProperty("BEOWULF_CONTENT_CONSTANT_HF0")
    private String beowulfContentConstantHf0;
    @JsonProperty("BEOWULF_CONTENT_REWARD_PERCENT")
    private short beowulfContentRewardPercent;
    @JsonProperty("BEOWULF_CONVERSION_DELAY_PRE_HF_16")
    private long beowulfConversionDelayPreHf16;
    @JsonProperty("BEOWULF_CREATE_ACCOUNT_DELEGATION_RATIO")
    private int beowulfCreateAccountDelegationRatio;
    @JsonProperty("BEOWULF_CREATE_ACCOUNT_DELEGATION_TIME")
    private long beowulfCreateAccountDelegationTime;
    @JsonProperty("BEOWULF_CURATE_APR_PERCENT")
    private int beowulfCurateAprPercent;
    @JsonProperty("BEOWULF_DEFAULT_WD_INTEREST_RATE")
    private int beowulfDefaultWdInterestRate;
    @JsonProperty("BEOWULF_FEED_HISTORY_WINDOW_PRE_HF_16")
    private String beowulfFeedHistoryWindowPreHf16;
    @JsonProperty("BEOWULF_FREE_TRANSACTIONS_WITH_NEW_ACCOUNT")
    private int beowulfFreeTransactionsWithNewAccount;
    @JsonProperty("BEOWULF_GENESIS_TIME")
    private String beowulfGenesisTime;
    @JsonProperty("BEOWULF_HARDFORK_REQUIRED_SUPERNODES")
    private int beowulfHardforkRequiredSupernode;
    @JsonProperty("BEOWULF_INFLATION_NARROWING_PERIOD")
    private String beowulfInflationNarrowingPeriod;
    @JsonProperty("BEOWULF_INFLATION_RATE_START_PERCENT")
    private short beowulfInflationRateStartPercent;
    @JsonProperty("BEOWULF_INFLATION_RATE_STOP_PERCENT")
    private short beowulfInflationRateStopPercent;
    @JsonProperty("BEOWULF_INIT_MINER_NAME")
    private String beowulfInitMinerName;
    @JsonProperty("BEOWULF_INIT_PUBLIC_KEY_STR")
    private String beowulfInitPublicKeyStr;
    @JsonProperty("BEOWULF_INIT_SUPPLY")
    private long beowulfInitSupply;
    @JsonProperty("WD_INIT_SUPPLY")
    private long wdInitSupply;
    @JsonProperty("BEOWULF_IRREVERSIBLE_THRESHOLD")
    private int beowulfIrreversibleThreshold;
    @JsonProperty("BEOWULF_MAX_ACCOUNT_NAME_LENGTH")
    private int beowulfMaxAccountNameLength;
    @JsonProperty("BEOWULF_MAX_ACCOUNT_SUPERNODE_VOTES")
    private int beowulfMaxAccountSupernodeVotes;
    @JsonProperty("BEOWULF_MAX_AUTHORITY_MEMBERSHIP")
    private int beowulfMaxAuthorityMembership;
    @JsonProperty("BEOWULF_MAX_CASHOUT_WINDOW_SECONDS")
    private long beowulfMaxCashoutWindowSeconds;
    @JsonProperty("BEOWULF_MAX_COMMENT_DEPTH")
    private int beowulfMaxCommentDepth;
    @JsonProperty("BEOWULF_MAX_COMMENT_DEPTH_PRE_HF17")
    private int beowulfMaxCommentDepthPreHf17;
    @JsonProperty("BEOWULF_MAX_MEMO_SIZE")
    private int beowulfMaxMemoSize;
    @JsonProperty("BEOWULF_MAX_SUPERNODES")
    private int beowulfMaxSupernodes;
    @JsonProperty("BEOWULF_MAX_MINER_SUPERNODES_HF0")
    private int beowulfMaxMinerSupernodesHf0;
    @JsonProperty("BEOWULF_MAX_MINER_SUPERNODES_HF17")
    private int beowulfMaxMinerSupernodesHf17;
    @JsonProperty("BEOWULF_MAX_PERMLINK_LENGTH")
    private int beowulfMaxPermlinkLength;
    @JsonProperty("BEOWULF_MAX_RATION_DECAY_RATE")
    private long beowulfMaxRationDecayRate;
    @JsonProperty("BEOWULF_MAX_RUNNER_SUPERNODES_HF0")
    private int beowulfMaxRunnerSupernodesHf0;
    @JsonProperty("BEOWULF_MAX_RUNNER_SUPERNODES_HF17")
    private int beowulfMaxRunnerSupernodesHf17;
    @JsonProperty("BEOWULF_MAX_SHARE_SUPPLY")
    private String beowulfMAxShareSupply;
    @JsonProperty("BEOWULF_MAX_SIG_CHECK_DEPTH")
    private int beowulfMaxSigCheckDepth;
    @JsonProperty("BEOWULF_MAX_SIG_CHECK_ACCOUNTS")
    private int beowulfMaxSigCheckAccounts;
    @JsonProperty("BEOWULF_MAX_TIME_UNTIL_EXPIRATION")
    private int beowulfMaxTimeUntilExpiration;
    @JsonProperty("BEOWULF_MAX_TRANSACTION_SIZE")
    private long beowulfMaxTransactionSize;
    @JsonProperty("BEOWULF_MAX_UNDO_HISTORY")
    private int beowulfMaxUndoHistory;
    @JsonProperty("BEOWULF_MAX_VOTED_SUPERNODES_HF0")
    private int beowulfMaxVotedSupernodesHf0;
    @JsonProperty("BEOWULF_MAX_VOTED_SUPERNODES_HF17")
    private int beowulfMaxVotedSupernodesHf17;
    @JsonProperty("BEOWULF_SOFT_MAX_BLOCK_SIZE")
    private int beowulfSoftMaxBlockSize;
    @JsonProperty("BEOWULF_MIN_ACCOUNT_CREATION_FEE")
    private int beowulfMinAccountCreationFee;
    @JsonProperty("BEOWULF_MIN_ACCOUNT_NAME_LENGTH")
    private int beowulfMinAccountNameLength;
    @JsonProperty("BEOWULF_MIN_BLOCK_SIZE")
    private int beowulfMinBlockSize;
    @JsonProperty("BEOWULF_MIN_TRANSFER_FEE")
    private int beowulfMinTransferFee;
    @JsonProperty("BEOWULF_MIN_SUPERNODE_FUND")
    private long beowulfMinSupernodeFund;
    @JsonProperty("BEOWULF_MIN_BLOCK_SIZE_LIMIT")
    private int beowulfMinBlockSizeLimit;
    @JsonProperty("BEOWULF_MIN_CONTENT_REWARD")
    private String beowulfMinContentReward;
    @JsonProperty("BEOWULF_MIN_CURATE_REWARD")
    private String beowulfMinCurateReward;
    @JsonProperty("BEOWULF_MIN_PERMLINK_LENGTH")
    private int beowulfMinPermlinkLength;
    @JsonProperty("BEOWULF_MIN_REPLY_INTERVAL")
    private int beowulfMinReplyInterval;
    @JsonProperty("BEOWULF_MIN_ROOT_COMMENT_INTERVAL")
    private int beowulfMinRootCommentInterval;
    @JsonProperty("BEOWULF_MIN_VOTE_INTERVAL_SEC")
    private long beowulfMinVoteIntervalSec;
    @JsonProperty("BEOWULF_MINER_ACCOUNT")
    private String beowulfMinerAccount;
    @JsonProperty("BEOWULF_MINER_PAY_PERCENT")
    private int beowulfMinerPayPercent;
    @JsonProperty("BEOWULF_MIN_RATION")
    private long beowulfMinRation;
    @JsonProperty("BEOWULF_NULL_ACCOUNT")
    private String beowulfNullAccount;
    @JsonProperty("BEOWULF_NUM_INIT_MINERS")
    private int beowulfNumInitMiners;
    @JsonProperty("BEOWULF_ORIGINAL_MIN_ACCOUNT_CREATION_FEE")
    private long beowulfOriginalMinAccountCreationFee;
    @JsonProperty("BEOWULF_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM")
    private long beowulfOwnerAuthHistoryTrackingStartBlockNum;
    @JsonProperty("BEOWULF_OWNER_AUTH_RECOVERY_PERIOD")
    private long beowulfOwnerAuthRecoveryPeriod;
    @JsonProperty("BEOWULF_OWNER_UPDATE_LIMIT")
    private int beowulfOwnerUpdateLimit;
    @JsonProperty("BEOWULF_POST_AVERAGE_WINDOW")
    private int beowulfPostAvarageWindow;
    @JsonProperty("BEOWULF_POST_MAX_BANDWIDTH")
    private long beowulfPostMaxBandwidth;
    @JsonProperty("BEOWULF_POST_REWARD_FUND_NAME")
    private String beowulfPostRewardFundName;
    @JsonProperty("BEOWULF_POST_WEIGHT_CONSTANT")
    private int beowulfPostWeightConstant;
    @JsonProperty("BEOWULF_WD_INTEREST_COMPOUND_INTERVAL_SEC")
    private long beowulfWDInterestCompoundIntervalSec;
    @JsonProperty("BEOWULF_RECENT_RSHARES_DECAY_RATE_HF19")
    private long beowulfRecentRSharesDecayRateHf19;
    @JsonProperty("BEOWULF_RECENT_RSHARES_DECAY_RATE_HF17")
    private long beowulfRecentRSharesDecayRateHf17;
    @JsonProperty("BEOWULF_REVERSE_AUCTION_WINDOW_SECONDS")
    private int beowulfReverseAuctionWindowSeconds;
    @JsonProperty("BEOWULF_ROOT_POST_PARENT")
    private String beowulfRootPostParent;
    @JsonProperty("BEOWULF_SAVINGS_WITHDRAW_REQUEST_LIMIT")
    private long beowulfSavingsWithdrawRequestLimit;
    @JsonProperty("BEOWULF_SAVINGS_WITHDRAW_TIME")
    private long beowulfSavingsWithdrawTime;
    @JsonProperty("BEOWULF_WD_START_PERCENT")
    private short beowulfWdStartPercent;
    @JsonProperty("BEOWULF_WD_STOP_PERCENT")
    private short beowulfWdStopPercent;
    @JsonProperty("BEOWULF_SECOND_CASHOUT_WINDOW")
    private long beowulfSecondCashcoutWindow;
    @JsonProperty("BEOWULF_SOFT_MAX_COMMENT_DEPTH")
    private long beowulfStartMinerVotingBlock;
    @JsonProperty("BEOWULF_SYMBOL")
    private Symbol beowulfSymbol;
    @JsonProperty("BEOWULF_TEMP_ACCOUNT")
    private String beowulfTempAccount;
    @JsonProperty("BEOWULF_UPVOTE_LOCKOUT_HF7")
    private long beowulfUpvoteLockoutHf7;
    @JsonProperty("BEOWULF_UPVOTE_LOCKOUT_HF17")
    private long beowulfUpvoteLockoutHf17;
    @JsonProperty("BEOWULF_VESTING_FUND_PERCENT")
    private short beowulfVestingFundPercent;
    @JsonProperty("BEOWULF_VESTING_WITHDRAW_INTERVALS")
    private int beowulfVestingWithdrawIntervals;
    @JsonProperty("BEOWULF_VESTING_WITHDRAW_INTERVALS_PRE_HF_16")
    private int beowulfVestingWithdrawIntervalsPreHf16;
    @JsonProperty("BEOWULF_VESTING_WITHDRAW_INTERVAL_SECONDS")
    private int beowulfVestingWithdrawIntervalSeconds;
    @JsonProperty("BEOWULF_VOTE_CHANGE_LOCKOUT_PERIOD")
    private int beowulfVoteChangeLockoutPeriod;
    @JsonProperty("BEOWULF_VOTE_DUST_THRESHOLD")
    private int beowulfVoteDustThreshold;
    @JsonProperty("BEOWULF_VOTE_REGENERATION_SECONDS")
    private int beowulfVoteRegenerationSeconds;
    @JsonProperty("STMD_SYMBOL")
    private long stmdSymbol;
    @JsonProperty("VESTS_SYMBOL")
    private Symbol vestsSymbol;
    @JsonProperty("BEOWULF_VIRTUAL_SCHEDULE_LAP_LENGTH2")
    private String beowulfVirtualScheduleLapLength2;
    @JsonProperty("BEOWULF_1_BEOWULF")
    private long beowulf1Beowulf;
    @JsonProperty("BEOWULF_1_VESTS")
    private long beowulf1Vests;
    @JsonProperty("BEOWULF_MAX_TOKEN_PER_ACCOUNT")
    private long beowulfMaxTokenPerAccount;
    @JsonProperty("VIRTUAL_SCHEDULE_LAP_LENGTH")
    private BigInteger virtualScheduleLapLength;
    @JsonProperty("VIRTUAL_SCHEDULE_LAP_LENGTH2")
    private BigInteger virtualScheduleLapLength2;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Config() {
    }

    public boolean isTestNet() {
        return isTestNet;
    }

    public boolean isBeowulfEnableSmt() {
        return isBeowulfEnableSmt;
    }

    public long getSmtTokenCreationFee() {
        return smtTokenCreationFee;
    }

    public Symbol getWdSymbol() {
        return wdSymbol;
    }

    public short getBeowulf100Percent() {
        return beowulf100Percent;
    }

    public short getBeowulf1Percent() {
        return beowulf1Percent;
    }

    public short getBeowulf1TenthPercent() {
        return beowulf1TenthPercent;
    }

    public String getBeowulfAccountRecoveryRequestExpirationPeriod() {
        return beowulfAccountRecoveryRequestExpirationPeriod;
    }

    public String getBeowulfAddressPrefix() {
        return beowulfAddressPrefix;
    }

    public String getBeowulfBlockchainHardforkVersion() {
        return beowulfBlockchainHardforkVersion;
    }

    public String getBeowulfBlockchainVersion() {
        return beowulfBlockchainVersion;
    }

    public int getBeowulfBlockInterval() {
        return beowulfBlockInterval;
    }

    public int getBeowulfBlocksPerDay() {
        return beowulfBlocksPerDay;
    }

    public long getBeowulfBlocksPerYear() {
        return beowulfBlocksPerYear;
    }

    public int getBeowulfCashoutWindowSeconds() {
        return beowulfCashoutWindowSeconds;
    }

    public int getBeowulfCashoutWindowSecondsPreHf12() {
        return beowulfCashoutWindowSecondsPreHf12;
    }

    public int getBeowulfCashoutWindowSecondsPreHf17() {
        return beowulfCashoutWindowSecondsPreHf17;
    }

    public String getBeowulfChainId() {
        return beowulfChainId;
    }

    public String getBeowulfCommentRewardFundName() {
        return beowulfCommentRewardFundName;
    }

    public int getBeowulfContentAprPercent() {
        return beowulfContentAprPercent;
    }

    public String getBeowulfContentConstantHf0() {
        return beowulfContentConstantHf0;
    }

    public short getBeowulfContentRewardPercent() {
        return beowulfContentRewardPercent;
    }

    public long getBeowulfConversionDelayPreHf16() {
        return beowulfConversionDelayPreHf16;
    }

    public int getBeowulfCreateAccountDelegationRatio() {
        return beowulfCreateAccountDelegationRatio;
    }

    public long getBeowulfCreateAccountDelegationTime() {
        return beowulfCreateAccountDelegationTime;
    }

    public int getBeowulfCurateAprPercent() {
        return beowulfCurateAprPercent;
    }

    public int getBeowulfDefaultWdInterestRate() {
        return beowulfDefaultWdInterestRate;
    }

    public String getBeowulfFeedHistoryWindowPreHf16() {
        return beowulfFeedHistoryWindowPreHf16;
    }

    public int getBeowulfFreeTransactionsWithNewAccount() {
        return beowulfFreeTransactionsWithNewAccount;
    }

    public String getBeowulfGenesisTime() {
        return beowulfGenesisTime;
    }

    public int getBeowulfHardforkRequiredSupernode() {
        return beowulfHardforkRequiredSupernode;
    }

    public String getBeowulfInflationNarrowingPeriod() {
        return beowulfInflationNarrowingPeriod;
    }

    public short getBeowulfInflationRateStartPercent() {
        return beowulfInflationRateStartPercent;
    }

    public short getBeowulfInflationRateStopPercent() {
        return beowulfInflationRateStopPercent;
    }

    public String getBeowulfInitMinerName() {
        return beowulfInitMinerName;
    }

    public String getBeowulfInitPublicKeyStr() {
        return beowulfInitPublicKeyStr;
    }

    public long getBeowulfInitSupply() {
        return beowulfInitSupply;
    }

    public long getWdInitSupply() {
        return wdInitSupply;
    }

    public int getBeowulfIrreversibleThreshold() {
        return beowulfIrreversibleThreshold;
    }

    public int getBeowulfMaxAccountNameLength() {
        return beowulfMaxAccountNameLength;
    }

    public int getBeowulfMaxAccountSupernodeVotes() {
        return beowulfMaxAccountSupernodeVotes;
    }

    public int getBeowulfMaxAuthorityMembership() {
        return beowulfMaxAuthorityMembership;
    }

    public long getBeowulfMaxCashoutWindowSeconds() {
        return beowulfMaxCashoutWindowSeconds;
    }

    public int getBeowulfMaxCommentDepth() {
        return beowulfMaxCommentDepth;
    }

    public int getBeowulfMaxCommentDepthPreHf17() {
        return beowulfMaxCommentDepthPreHf17;
    }

    public int getBeowulfMaxMemoSize() {
        return beowulfMaxMemoSize;
    }

    public int getBeowulfMaxSupernodes() {
        return beowulfMaxSupernodes;
    }

    public int getBeowulfMaxMinerSupernodesHf0() {
        return beowulfMaxMinerSupernodesHf0;
    }

    public int getBeowulfMaxMinerSupernodesHf17() {
        return beowulfMaxMinerSupernodesHf17;
    }

    public int getBeowulfMaxPermlinkLength() {
        return beowulfMaxPermlinkLength;
    }

    public long getBeowulfMaxRationDecayRate() {
        return beowulfMaxRationDecayRate;
    }

    public int getBeowulfMaxRunnerSupernodesHf0() {
        return beowulfMaxRunnerSupernodesHf0;
    }

    public int getBeowulfMaxRunnerSupernodesHf17() {
        return beowulfMaxRunnerSupernodesHf17;
    }

    public String getBeowulfMAxShareSupply() {
        return beowulfMAxShareSupply;
    }

    public int getBeowulfMaxSigCheckDepth() {
        return beowulfMaxSigCheckDepth;
    }

    public int getBeowulfMaxSigCheckAccounts() {
        return beowulfMaxSigCheckAccounts;
    }

    public int getBeowulfMaxTimeUntilExpiration() {
        return beowulfMaxTimeUntilExpiration;
    }

    public long getBeowulfMaxTransactionSize() {
        return beowulfMaxTransactionSize;
    }

    public int getBeowulfMaxUndoHistory() {
        return beowulfMaxUndoHistory;
    }

    public int getBeowulfMaxVotedSupernodesHf0() {
        return beowulfMaxVotedSupernodesHf0;
    }

    public int getBeowulfMaxVotedSupernodesHf17() {
        return beowulfMaxVotedSupernodesHf17;
    }

    public int getBeowulfSoftMaxBlockSize() {
        return beowulfSoftMaxBlockSize;
    }

    public int getBeowulfMinAccountCreationFee() {
        return beowulfMinAccountCreationFee;
    }

    public int getBeowulfMinAccountNameLength() {
        return beowulfMinAccountNameLength;
    }

    public int getBeowulfMinBlockSize() {
        return beowulfMinBlockSize;
    }

    public int getBeowulfMinTransferFee() {
        return beowulfMinTransferFee;
    }

    public long getBeowulfMinSupernodeFund() {
        return beowulfMinSupernodeFund;
    }

    public int getBeowulfMinBlockSizeLimit() {
        return beowulfMinBlockSizeLimit;
    }

    public String getBeowulfMinContentReward() {
        return beowulfMinContentReward;
    }

    public String getBeowulfMinCurateReward() {
        return beowulfMinCurateReward;
    }

    public int getBeowulfMinPermlinkLength() {
        return beowulfMinPermlinkLength;
    }

    public int getBeowulfMinReplyInterval() {
        return beowulfMinReplyInterval;
    }

    public int getBeowulfMinRootCommentInterval() {
        return beowulfMinRootCommentInterval;
    }

    public long getBeowulfMinVoteIntervalSec() {
        return beowulfMinVoteIntervalSec;
    }

    public String getBeowulfMinerAccount() {
        return beowulfMinerAccount;
    }

    public int getBeowulfMinerPayPercent() {
        return beowulfMinerPayPercent;
    }

    public long getBeowulfMinRation() {
        return beowulfMinRation;
    }

    public String getBeowulfNullAccount() {
        return beowulfNullAccount;
    }

    public int getBeowulfNumInitMiners() {
        return beowulfNumInitMiners;
    }

    public long getBeowulfOriginalMinAccountCreationFee() {
        return beowulfOriginalMinAccountCreationFee;
    }

    public long getBeowulfOwnerAuthHistoryTrackingStartBlockNum() {
        return beowulfOwnerAuthHistoryTrackingStartBlockNum;
    }

    public long getBeowulfOwnerAuthRecoveryPeriod() {
        return beowulfOwnerAuthRecoveryPeriod;
    }

    public int getBeowulfOwnerUpdateLimit() {
        return beowulfOwnerUpdateLimit;
    }

    public int getBeowulfPostAvarageWindow() {
        return beowulfPostAvarageWindow;
    }

    public long getBeowulfPostMaxBandwidth() {
        return beowulfPostMaxBandwidth;
    }

    public String getBeowulfPostRewardFundName() {
        return beowulfPostRewardFundName;
    }

    public int getBeowulfPostWeightConstant() {
        return beowulfPostWeightConstant;
    }

    public long getBeowulfWDInterestCompoundIntervalSec() {
        return beowulfWDInterestCompoundIntervalSec;
    }

    public long getBeowulfRecentRSharesDecayRateHf19() {
        return beowulfRecentRSharesDecayRateHf19;
    }

    public long getBeowulfRecentRSharesDecayRateHf17() {
        return beowulfRecentRSharesDecayRateHf17;
    }

    public int getBeowulfReverseAuctionWindowSeconds() {
        return beowulfReverseAuctionWindowSeconds;
    }

    public String getBeowulfRootPostParent() {
        return beowulfRootPostParent;
    }

    public long getBeowulfSavingsWithdrawRequestLimit() {
        return beowulfSavingsWithdrawRequestLimit;
    }

    public long getBeowulfSavingsWithdrawTime() {
        return beowulfSavingsWithdrawTime;
    }

    public short getBeowulfWdStartPercent() {
        return beowulfWdStartPercent;
    }

    public short getBeowulfWdStopPercent() {
        return beowulfWdStopPercent;
    }

    public long getBeowulfSecondCashcoutWindow() {
        return beowulfSecondCashcoutWindow;
    }

    public long getBeowulfStartMinerVotingBlock() {
        return beowulfStartMinerVotingBlock;
    }

    public Symbol getBeowulfSymbol() {
        return beowulfSymbol;
    }

    public String getBeowulfTempAccount() {
        return beowulfTempAccount;
    }

    public long getBeowulfUpvoteLockoutHf7() {
        return beowulfUpvoteLockoutHf7;
    }

    public long getBeowulfUpvoteLockoutHf17() {
        return beowulfUpvoteLockoutHf17;
    }

    public short getBeowulfVestingFundPercent() {
        return beowulfVestingFundPercent;
    }

    public int getBeowulfVestingWithdrawIntervals() {
        return beowulfVestingWithdrawIntervals;
    }

    public int getBeowulfVestingWithdrawIntervalsPreHf16() {
        return beowulfVestingWithdrawIntervalsPreHf16;
    }

    public int getBeowulfVestingWithdrawIntervalSeconds() {
        return beowulfVestingWithdrawIntervalSeconds;
    }

    public int getBeowulfVoteChangeLockoutPeriod() {
        return beowulfVoteChangeLockoutPeriod;
    }

    public int getBeowulfVoteDustThreshold() {
        return beowulfVoteDustThreshold;
    }

    public int getBeowulfVoteRegenerationSeconds() {
        return beowulfVoteRegenerationSeconds;
    }

    public long getStmdSymbol() {
        return stmdSymbol;
    }

    public Symbol getVestsSymbol() {
        return vestsSymbol;
    }

    public String getBeowulfVirtualScheduleLapLength2() {
        return beowulfVirtualScheduleLapLength2;
    }

    public long getBeowulf1Beowulf() {
        return beowulf1Beowulf;
    }

    public long getBeowulf1Vests() {
        return beowulf1Vests;
    }

    public long getBeowulfMaxTokenPerAccount() {
        return beowulfMaxTokenPerAccount;
    }

    public BigInteger getVirtualScheduleLapLength() {
        return virtualScheduleLapLength;
    }

    public BigInteger getVirtualScheduleLapLength2() {
        return virtualScheduleLapLength2;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
