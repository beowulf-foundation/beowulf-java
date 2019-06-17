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

public enum RequestMethod {
    // account_by_key_api
    /**
     * Find accounts by their public key.
     */
    GET_KEY_REFERENCES,
    // account_history_api
    /**
     * Get all operations in a block.
     */
    GET_OPS_IN_BLOCK,
    /**
     * Get a transaction by its Id.
     */
    GET_TRANSACTION,
    /**
     * Get the full history of an account.
     */
    GET_ACCOUNT_HISTORY,
    /**
     * Get balance of specific asset in account
     */
    GET_BALANCE,
    // block_api
    /**
     *
     */
    GET_BLOCK,
    /**
     *
     */
    GET_BLOCK_HEADER,
    // chain_api
    /**
     *
     */
    GET_NEXT_SCHEDULED_HARDFORK,
    /**
     *
     */
    GET_ACCOUNTS,
    /**
     *
     */
    GET_ACCOUNT_COUNT,
    /**
     *
     */
    GET_SUPERNODE_BY_ACCOUNT,
    /**
     *
     */
    LOOKUP_SUPERNODE_ACCOUNTS,
    /**
     *
     */
    LOOKUP_ACCOUNTS,
    /**
     *
     */
    GET_SUPERNODES,
    /**
     *
     */
    GET_SUPERNODE_COUNT,
    /**
     *
     */
    GET_SUPERNODES_BY_VOTE,
    /**
     *
     */
    GET_HARDFORK_VERSION,
    // database_api
    /**
     *
     */
    GET_CONFIG,
    /**
     *
     */
    GET_DYNAMIC_GLOBAL_PROPERTIES,
    /**
     *
     */
    GET_SUPERNODE_SCHEDULE,
    /**
     *
     */
    GET_ACTIVE_SUPERNODES,
    /**
     *
     */
    FIND_SMT_TOKENS_BY_NAME,
    /**
     *
     */
    GET_TRANSACTION_HEX,
    /**
     *
     */
    GET_REQUIRED_SIGNATURES,
    /**
     *
     */
    GET_POTENTIAL_SIGNATURES,
    /**
     *
     */
    VERIFY_AUTHORITY,
    /**
     *
     */
    VERIFY_ACCOUNT_AUTHORITY,
    // network_broadcast_api
    /**
     *
     */
    BROADCAST_TRANSACTION,
    /**
     *
     */
    BROADCAST_TRANSACTION_SYNCHRONOUS,
    /**
     *
     */
    BROADCAST_BLOCK,
}