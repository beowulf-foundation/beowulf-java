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
package com.beowulfchain.beowulfj.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class WalletObject {
    private String cipher_keys;
    private String cipher_type;
    private String salt;
    private AccountName name;

    public String getCipher_keys() {
        return cipher_keys;
    }

    public void setCipher_keys(String cipher_keys) {
        this.cipher_keys = cipher_keys;
    }

    public String getCipher_type() {
        return cipher_type;
    }

    public void setCipher_type(String cipher_type) {
        this.cipher_type = cipher_type;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public AccountName getName() {
        return name;
    }

    public void setName(AccountName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (IOException e) {
            return null;
        }

    }
}
