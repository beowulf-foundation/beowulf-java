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
package com.beowulfchain.beowulfj;

import com.beowulfchain.beowulfj.configuration.BeowulfJConfig;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.protocol.AccountName;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseUT extends BaseTest {
    protected static final String PRIVATE_OWNER_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static BeowulfJConfig config;

    /**
     * Prepare a the environment for standard unit tests.
     */
    protected static void setupUnitTestEnvironment() {
        config = BeowulfJConfig.getNewInstance();

        List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();
        privateKeys.add(new ImmutablePair<>(PrivateKeyType.OWNER, PRIVATE_OWNER_KEY));

        config.getPrivateKeyStorage().addAccount(new AccountName("trongcauhcmus"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("foobara"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("foobarc"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("foo"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("beowulfj"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("xeroc"), privateKeys);
    }
}
