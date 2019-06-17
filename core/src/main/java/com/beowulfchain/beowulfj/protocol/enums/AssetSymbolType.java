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
package com.beowulfchain.beowulfj.protocol.enums;

/**
 * This enum stores all available asset symbols.
 */
public enum AssetSymbolType {
    /**
     * Beowulf Power (SP) Symbol
     */
    M,
    /**
     * Beowulf Sybol
     */
    BWF,
    /**
     * Beowulf Backed Dollar (W) Symbol
     */
    W;

    public static AssetSymbolType getValue(String symbol) {

        if (symbol.toUpperCase().equals(AssetSymbolType.M.name())) {
            return AssetSymbolType.M;
        } else if (symbol.toUpperCase().equals(AssetSymbolType.BWF.name())) {
            return AssetSymbolType.BWF;
        } else if (symbol.toUpperCase().equals(AssetSymbolType.W.name())) {
            return AssetSymbolType.W;
        }
        return null;
    }
}
