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
package com.beowulfchain.beowulfj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Beowulf "verify_signatures_args" object.
 */
public class VerifySignaturesArgs {
/* TODO:
       fc::sha256                    hash;
       vector< signature_type >      signatures;
       vector< account_name_type >   required_owner;
       vector< account_name_type >   required_active;
       vector< account_name_type >   required_posting;
       vector< authority >           required_other;

       void get_required_owner_authorities( flat_set< account_name_type >& a )const
       {
          a.insert( required_owner.begin(), required_owner.end() );
       }

       void get_required_active_authorities( flat_set< account_name_type >& a )const
       {
          a.insert( required_active.begin(), required_active.end() );
       }

       void get_required_posting_authorities( flat_set< account_name_type >& a )const
       {
          a.insert( required_posting.begin(), required_posting.end() );
       }

       void get_required_authorities( vector< authority >& a )const
       {
          a.insert( a.end(), required_other.begin(), required_other.end() );
       }*/

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
