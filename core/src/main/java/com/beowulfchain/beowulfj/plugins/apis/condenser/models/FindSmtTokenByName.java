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

import com.beowulfchain.beowulfj.protocol.AccountName;
import com.beowulfchain.beowulfj.protocol.AssetInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FindSmtTokenByName {
    private int id;
    private AssetInfo liquid_symbol;
    private AccountName control_account;
    private String phase;
    private long current_supply;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AssetInfo getLiquid_symbol() {
        return liquid_symbol;
    }

    public void setLiquid_symbol(AssetInfo liquid_symbol) {
        this.liquid_symbol = liquid_symbol;
    }

    public AccountName getControl_account() {
        return control_account;
    }

    public void setControl_account(AccountName control_account) {
        this.control_account = control_account;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public long getCurrent_supply() {
        return current_supply;
    }

    public void setCurrent_supply(long current_supply) {
        this.current_supply = current_supply;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
