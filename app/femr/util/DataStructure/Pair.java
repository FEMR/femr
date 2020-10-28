/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.util.DataStructure;

/**
 * Created by Danny on 2/13/14.
 *
 * This is a data structure that implements a pair structure
 * to use in a list or what ever.  Like am map but better
 */

/**
 * Creates an object that holds two different data types like a key value system
 * @param <Key> the data type for the first value
 * @param <Value> the data type for the second value
 */
public class Pair<Key,Value> {

    private Key key;
    private Value value;

    /**
     * Creates a pair data type that holds a key and value of any type
     * @param key the key or first value
     * @param value the value or second value
     */
    public Pair(Key key, Value value){
        this.key = key;
        this.value = value;
    }

    public void set(Key key, Value value){
        this.key = key;
        this.value = value;
    }

    public Pair<Key,Value> get(){
        return new Pair<Key,Value>(this.key,this.value);
    }

    public Key getKey(){
        return this.key;
    }

    public Value getValue() {
        return this.value;
    }

}
