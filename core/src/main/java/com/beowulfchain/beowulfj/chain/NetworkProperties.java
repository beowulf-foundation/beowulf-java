package com.beowulfchain.beowulfj.chain;

import com.beowulfchain.beowulfj.protocol.Asset;

public interface NetworkProperties {

    String getChain_id();

    String getPrefix();

    String getBeowulf_symbol();

    String getWd_symbol();

    String getVests_symbol();

    Asset getTransactionFee();

    Asset getSmtCreationFee();

    Asset getAccountCreationFee();
}
