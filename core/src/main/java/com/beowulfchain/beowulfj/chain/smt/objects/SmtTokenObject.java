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
package com.beowulfchain.beowulfj.chain.smt.objects;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

public class SmtTokenObject {
    private long amount;
    private int precision;
    private String name;

    public SmtTokenObject(BigDecimal amount, String name, int precision) {
        this.setName(name);
        this.setPrecision(precision);
        this.setAmount(amount);
    }

    public SmtTokenObject(BigDecimal amount, String name) {
        this.setName(name);
        this.setAmount(amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount.scale() > this.getPrecision()) {
            throw new InvalidParameterException("The provided 'amount' has a 'scale' of " + amount.scale()
                    + ", but needs to have a 'scale' of " + this.getPrecision() + " when " + this.getName()
                    + " is used as a AssetSymbolType.");
        }

        this.amount = amount.multiply(BigDecimal.valueOf(Math.pow(10, this.getPrecision()))).longValue();
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
