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
package com.beowulfchain.beowulfj.chain.network;

import com.beowulfchain.beowulfj.chain.NetworkProperties;
import com.beowulfchain.beowulfj.protocol.Asset;
import com.beowulfchain.beowulfj.protocol.enums.AssetSymbolType;

public class Mainnet implements NetworkProperties {
    private static final String chain_id = "e2222eeabcf9224632c82ec86ba3d77b359e3b5cb8a089ddd45090c31c98e3f2";
    private static final String prefix = "BEO";
    private static final String beowulf_symbol = "BWF";
    private static final String wd_symbol = "W";
    private static final String vests_symbol = "M";
    private static final Asset account_creation_fee = new Asset(10000L, AssetSymbolType.W);
    private static final Asset smt_creation_fee = new Asset(100000000L, AssetSymbolType.W);
    private static final Asset transaction_fee = new Asset(1000L, AssetSymbolType.W);

    @Override
    public String getChain_id() {
        return chain_id;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getBeowulf_symbol() {
        return beowulf_symbol;
    }

    @Override
    public String getWd_symbol() {
        return wd_symbol;
    }

    @Override
    public String getVests_symbol() {
        return vests_symbol;
    }

    @Override
    public Asset getTransactionFee() {
        return transaction_fee;
    }

    @Override
    public Asset getSmtCreationFee() {
        return smt_creation_fee;
    }

    @Override
    public Asset getAccountCreationFee() {
        return account_creation_fee;
    }
}