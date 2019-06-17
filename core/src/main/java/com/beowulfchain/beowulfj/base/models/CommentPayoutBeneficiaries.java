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

import com.beowulfchain.beowulfj.enums.CommentOptionsExtensionsType;
import com.beowulfchain.beowulfj.enums.ValidationType;
import com.beowulfchain.beowulfj.exceptions.BeowulfInvalidTransactionException;
import com.beowulfchain.beowulfj.util.BeowulfJUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

@JsonDeserialize
public class CommentPayoutBeneficiaries extends CommentOptionsExtension {
    @JsonProperty("beneficiaries")
    private List<BeneficiaryRouteType> beneficiaries;

    /**
     * @return The beneficiaries.
     */
    public List<BeneficiaryRouteType> getBeneficiaries() {
        return beneficiaries;
    }

    /**
     * @param beneficiaries The beneficiaries to set.
     */
    public void setBeneficiaries(List<BeneficiaryRouteType> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    @Override
    public byte[] toByteArray() throws BeowulfInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentPayoutBeneficiaries = new ByteArrayOutputStream()) {
            serializedCommentPayoutBeneficiaries.write(BeowulfJUtils.transformIntToVarIntByteArray(
                    CommentOptionsExtensionsType.COMMENT_PAYOUT_BENEFICIARIES.ordinal()));

            serializedCommentPayoutBeneficiaries
                    .write(BeowulfJUtils.transformLongToVarIntByteArray(this.getBeneficiaries().size()));

            for (BeneficiaryRouteType beneficiaryRouteType : this.getBeneficiaries()) {
                serializedCommentPayoutBeneficiaries.write(beneficiaryRouteType.toByteArray());
            }

            return serializedCommentPayoutBeneficiaries.toByteArray();
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
        if (!validationType.equals(ValidationType.SKIP_VALIDATION)) {
            if (this.getBeneficiaries().isEmpty()) {
                throw new InvalidParameterException("Must specify at least one beneficiary.");
            } else if (this.getBeneficiaries().size() >= 128) {
                // Require size serialization fits in one byte.
                throw new InvalidParameterException("Cannot specify more than 127 beneficiaries.");
            }

            // TODO: Verify if the list is sorted correctly.
            // FC_ASSERT( beneficiaries[i - 1] < beneficiaries[i], "Benficiaries
            // must be specified in sorted order (account ascending)" );

            int sum = 0;
            for (BeneficiaryRouteType beneficiaryRouteType : this.getBeneficiaries()) {
                sum += beneficiaryRouteType.getWeight();
                if (sum > 10000) {
                    throw new InvalidParameterException("Cannot allocate more than 100% of rewards to a comment");
                }
            }
        }
    }
}
