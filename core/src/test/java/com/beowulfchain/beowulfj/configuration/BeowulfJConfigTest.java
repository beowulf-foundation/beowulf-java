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
package com.beowulfchain.beowulfj.configuration;

import com.beowulfchain.beowulfj.BeowulfJ;
import com.beowulfchain.beowulfj.IntegrationTest;
import com.beowulfchain.beowulfj.enums.PrivateKeyType;
import com.beowulfchain.beowulfj.protocol.AccountName;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BeowulfJConfigTest {
    private static final String BEOWULFJ_API_USERNAME = "customnodename";
    private static final String BEOWULFJ_API_PASSWORD = "test1234";
    private static final String BEOWULFJ_KEY_ACCOUNTNAME = "alice";
    private static final String BEOWULFJ_KEY_OWNER = "5Hxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    /**
     * Setup the required system properties.
     */
    @BeforeClass
    public static void setUp() {
        System.setProperty("beowulfj.api.username", BEOWULFJ_API_USERNAME);
        System.setProperty("beowulfj.api.password", BEOWULFJ_API_PASSWORD);
        System.setProperty("beowulfj.default.account", BEOWULFJ_KEY_ACCOUNTNAME);
        System.setProperty("beowulfj.default.account.owner.key", BEOWULFJ_KEY_OWNER);

        // As there may have been other tests earlier we need to create a new
        // BeowulfJConfig instance so the parameter above will take effect.
        BeowulfJConfig.getNewInstance();
    }

    /**
     * Test if the system properties have been parsed correctly by BeowulfJ.
     */
    @Test
    public void testSettingsThroughSystemProperties() {
        assertThat(BeowulfJConfig.getInstance().getApiUsername(), equalTo(new AccountName(BEOWULFJ_API_USERNAME)));
        assertThat(String.valueOf(BeowulfJConfig.getInstance().getApiPassword()), equalTo(BEOWULFJ_API_PASSWORD));

        AccountName accountName = BeowulfJConfig.getInstance().getPrivateKeyStorage().getAccounts().get(0);
        assertThat(accountName, equalTo(new AccountName(BEOWULFJ_KEY_ACCOUNTNAME)));
        assertThat(BeowulfJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.OWNER, accountName)
                .decompress().getPrivateKeyEncoded(128).toBase58(), equalTo(BEOWULFJ_KEY_OWNER));
    }

    /**
     * Test if the version and the application name have been set correctly
     * during the build process.
     */
    @Test
    @Category({IntegrationTest.class})
    @Ignore
    public void testVersionAndName() {
        System.out.println(BeowulfJ.class.getPackage().getImplementationVersion());
        assertThat(BeowulfJConfig.getBeowulfJVersion(), notNullValue());
        assertThat(BeowulfJConfig.getBeowulfJAppName(), notNullValue());
    }
}
