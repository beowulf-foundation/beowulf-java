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
package com.beowulfchain.beowulfj.enums;

public enum OperationType {
    /**
     * The transfer_operation type.
     */
    TRANSFER_OPERATION(0),
    /**
     * The transfer_to_vesting_operation type.
     */
    TRANSFER_TO_VESTING_OPERATION(1),
    /**
     * The withdraw_vesting_operation type.
     */
    WITHDRAW_VESTING_OPERATION(2),
    /**
     * The account_create_operation type.
     */
    ACCOUNT_CREATE_OPERATION(3),
    /**
     * The account_update_operation type.
     */
    ACCOUNT_UPDATE_OPERATION(4),
    /**
     * The supernode_update_operation type.
     */
    SUPERNODE_UPDATE_OPERATION(5),
    /**
     * The account_supernode_vote_operation type.
     */
    ACCOUNT_SUPERNODE_VOTE_OPERATION(6),

    SMT_CREATE_OPERATION(7),

    SMART_CONTRACT_OPERATION(8),

    FILL_VESTING_WITHDRAW_OPERATION(9),

    SHUTDOWN_SUPERNODE_OPERATION(10),

    HARDFORK_OPERATION(11),

    PRODUCER_REWARD_OPERATION(12),

    CLEAR_NULL_ACCOUNT_BALANCE_OPERATION(13);

    /**
     * The id of an operation. The id is used for the byte transformation and
     * changing it can cause an unexpected behavior.
     */
    private int orderId;

    /**
     * Set the id of the operation.
     *
     * @param orderId The id of the operation to set.
     */
    OperationType(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Get the id of the operation.
     *
     * @return The id of the operation.
     */
    public int getOrderId() {
        return orderId;
    }
}
