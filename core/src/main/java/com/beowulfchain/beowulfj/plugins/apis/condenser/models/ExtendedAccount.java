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
package com.beowulfchain.beowulfj.plugins.apis.condenser.models;

import com.beowulfchain.beowulfj.base.models.Account;
import com.beowulfchain.beowulfj.base.models.deserializer.GuestBloggerPairDeserializer;
import com.beowulfchain.beowulfj.base.models.deserializer.OperationHistoryHashMapDeserializer;
import com.beowulfchain.beowulfj.base.models.deserializer.TagUsagePairDeserializer;
import com.beowulfchain.beowulfj.plugins.apis.account.history.models.AppliedOperation;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * This class represents a Beowulf "extended_account" object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedAccount extends Account {
    /**
     * Convert vesting_shares to vesting Beowulf.
     */
    @JsonProperty("vesting_balance")
    private Asset vestingBalance;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long reputation;
    // The original tpye is map<uint64_t,applied_operation>
    /**
     * Transfer to/from vesting.
     */
    @JsonProperty("transfer_history")
    @JsonDeserialize(using = OperationHistoryHashMapDeserializer.class)
    private Map<BigInteger, AppliedOperation> transferHistory;
    // The original tpye is map<uint64_t,applied_operation>
    /**
     * Limit order / cancel / fill.
     */
    @JsonProperty("market_history")
    @JsonDeserialize(using = OperationHistoryHashMapDeserializer.class)
    private Map<BigInteger, AppliedOperation> marketHistory;
    // The original tpye is map<uint64_t,applied_operation>
    @JsonProperty("post_history")
    @JsonDeserialize(using = OperationHistoryHashMapDeserializer.class)
    private Map<BigInteger, AppliedOperation> postHistory;
    // The original tpye is map<uint64_t,applied_operation>
    @JsonProperty("vote_history")
    @JsonDeserialize(using = OperationHistoryHashMapDeserializer.class)
    private Map<BigInteger, AppliedOperation> voteHistory;
    // The original tpye is map<uint64_t,applied_operation>
    @JsonProperty("other_history")
    @JsonDeserialize(using = OperationHistoryHashMapDeserializer.class)
    private Map<BigInteger, AppliedOperation> otherHistory;
    // Original type is set<string>.
    @JsonProperty("witness_votes")
    private List<AccountName> witnessVotes;
    // Original type is "vector<pair<string,uint32_t>>".
    @JsonProperty("tags_usage")
    @JsonDeserialize(using = TagUsagePairDeserializer.class)
    private List<Pair<String, Long>> tagsUsage;
    // Original type is "vector<pair<account_name_type,uint32_t>>".
    @JsonProperty("guest_bloggers")
    @JsonDeserialize(using = GuestBloggerPairDeserializer.class)
    private List<Pair<AccountName, Long>> guestBloggers;
    // Original type is "optional<map<uint32_t,extended_limit_order>>".
    @JsonProperty("open_orders")
    private List<String> openOrders;
    // Original type is "optional<vector<string>>".
    /**
     * Permlinks for this user.
     */
    @JsonProperty("comments")
    private List<String> comments;
    // Original type is "optional<vector<string>>".
    /**
     * Blog posts for this user.
     */
    @JsonProperty("blog")
    private List<String> blog;
    // Original type is "optional<vector<string>>".
    /**
     * Feed posts for this user.
     */
    @JsonProperty("feed")
    private List<String> feed;
    // Original type is "optional<vector<string>>".
    /**
     * Blog posts for this user.
     */
    @JsonProperty("recent_replies")
    private List<String> recentReplies;
    // Original type is "optional<vector<string>>".
    /**
     * Posts recommened for this user.
     */
    @JsonProperty("recommended")
    private List<String> recommended;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ExtendedAccount() {
    }

    /**
     * @return the vestingBalance
     */
    public Asset getVestingBalance() {
        return vestingBalance;
    }

    /**
     * @param vestingBalance the vestingBalance to set
     */
    public void setVestingBalance(Asset vestingBalance) {
        this.vestingBalance = vestingBalance;
    }

    /**
     * @return the reputation
     */
    public long getReputation() {
        return reputation;
    }

    /**
     * @param reputation the reputation to set
     */
    public void setReputation(long reputation) {
        this.reputation = reputation;
    }

    /**
     * @return the transferHistory
     */
    public Map<BigInteger, AppliedOperation> getTransferHistory() {
        return transferHistory;
    }

    /**
     * @param transferHistory the transferHistory to set
     */
    public void setTransferHistory(Map<BigInteger, AppliedOperation> transferHistory) {
        this.transferHistory = transferHistory;
    }

    /**
     * @return the marketHistory
     */
    public Map<BigInteger, AppliedOperation> getMarketHistory() {
        return marketHistory;
    }

    /**
     * @param marketHistory the marketHistory to set
     */
    public void setMarketHistory(Map<BigInteger, AppliedOperation> marketHistory) {
        this.marketHistory = marketHistory;
    }

    /**
     * @return the postHistory
     */
    public Map<BigInteger, AppliedOperation> getPostHistory() {
        return postHistory;
    }

    /**
     * @param postHistory the postHistory to set
     */
    public void setPostHistory(Map<BigInteger, AppliedOperation> postHistory) {
        this.postHistory = postHistory;
    }

    /**
     * @return the voteHistory
     */
    public Map<BigInteger, AppliedOperation> getVoteHistory() {
        return voteHistory;
    }

    /**
     * @param voteHistory the voteHistory to set
     */
    public void setVoteHistory(Map<BigInteger, AppliedOperation> voteHistory) {
        this.voteHistory = voteHistory;
    }

    /**
     * @return the otherHistory
     */
    public Map<BigInteger, AppliedOperation> getOtherHistory() {
        return otherHistory;
    }

    /**
     * @param otherHistory the otherHistory to set
     */
    public void setOtherHistory(Map<BigInteger, AppliedOperation> otherHistory) {
        this.otherHistory = otherHistory;
    }

    /**
     * @return the witnessVotes
     */
    public List<AccountName> getWitnessVotes() {
        return witnessVotes;
    }

    /**
     * @param witnessVotes the witnessVotes to set
     */
    public void setWitnessVotes(List<AccountName> witnessVotes) {
        this.witnessVotes = witnessVotes;
    }

    /**
     * @return the tagsUsage
     */
    public List<Pair<String, Long>> getTagsUsage() {
        return tagsUsage;
    }

    /**
     * @param tagsUsage the tagsUsage to set
     */
    public void setTagsUsage(List<Pair<String, Long>> tagsUsage) {
        this.tagsUsage = tagsUsage;
    }

    /**
     * @return the guestBloggers
     */
    public List<Pair<AccountName, Long>> getGuestBloggers() {
        return guestBloggers;
    }

    /**
     * @param guestBloggers the guestBloggers to set
     */
    public void setGuestBloggers(List<Pair<AccountName, Long>> guestBloggers) {
        this.guestBloggers = guestBloggers;
    }

    /**
     * @param openOrders the openOrders to set
     */
    public void setOpenOrders(List<String> openOrders) {
        this.openOrders = openOrders;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    /**
     * @param blog the blog to set
     */
    public void setBlog(List<String> blog) {
        this.blog = blog;
    }

    /**
     * @param feed the feed to set
     */
    public void setFeed(List<String> feed) {
        this.feed = feed;
    }

    /**
     * @param recentReplies the recentReplies to set
     */
    public void setRecentReplies(List<String> recentReplies) {
        this.recentReplies = recentReplies;
    }

    /**
     * @param recommended the recommended to set
     */
    public void setRecommended(List<String> recommended) {
        this.recommended = recommended;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
