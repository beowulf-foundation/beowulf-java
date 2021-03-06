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
package com.beowulfchain.beowulfj.base.models;

import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.interfaces.ByteTransformable;
import com.beowulfchain.beowulfj.interfaces.Validatable;
import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

public class BeneficiaryRouteType implements ByteTransformable, Validatable {
    @JsonProperty("account")
    private AccountName account;
    // Original type is uint16_t.
    @JsonProperty("weight")
    private short weight;

    /**
     * Create a new beneficiary route type.
     *
     * @param account The account who is the beneficiary of this comment (see
     *                {@link #setAccount(AccountName)}).
     * @param weight  The percentage the <code>account</code> will receive from the
     *                comment payout (see {@link #setWeight(short)}).
     * @throws InvalidParameterException If a parameter does not fulfill the requirements.
     */
    @JsonCreator
    public BeneficiaryRouteType(@JsonProperty("account") AccountName account, @JsonProperty("weight") short weight) {
        this.setAccount(account);
        this.setWeight(weight);
    }

    /**
     * Get the account who is the beneficiary of this comment.
     *
     * @return The beneficiary.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Define the beneficiary for this comment.
     *
     * @param account The beneficiary account.
     * @throws InvalidParameterException If the <code>account</code> is null.
     */
    public void setAccount(AccountName account) {
        if (account == null) {
            throw new InvalidParameterException("The account cannot be null.");
        }

        this.account = account;
    }

    /**
     * Get the percentage the {@link #getAccount() account} will receive from
     * the comment payout.
     *
     * @return The percentage of the payout to relay.
     */
    public short getWeight() {
        return weight;
    }

    /**
     * Set the percentage the {@link #getAccount() account} will receive from
     * the comment payout.
     *
     * @param weight The percentage of the payout to relay.
     * @throws InvalidParameterException If the <code>weight</code> is negative.
     */
    public void setWeight(short weight) {
        if (weight < 0) {
            throw new InvalidParameterException("The weight cannot be negative.");
        }

        this.weight = weight;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedBeneficiaryRouteType = new ByteArrayOutputStream()) {
            serializedBeneficiaryRouteType.write(this.getAccount().toByteArray());
            serializedBeneficiaryRouteType.write(BeowulfJUtils.transformShortToByteArray(this.getWeight()));

            return serializedBeneficiaryRouteType.toByteArray();
        } catch (IOException e) {
            throw new BeowulfInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!validationType.equals(ValidationType.SKIP_VALIDATION) && this.getWeight() > 10000) {
            throw new InvalidParameterException("Cannot allocate more than 100% of rewards to one account.");
        }
    }
}
