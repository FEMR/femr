package femr.util.DataStructure;

/**
 * Created by Danny on 2/13/14.
 *
 * This is a data structure that implements a pair structure
 * to use in a list or what ever
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

    public Pair<Key,Value> getPair(){
        return new Pair<Key,Value>(this.key,this.value);
    }

    public Key getKey(){
        return this.key;
    }

    public Value getValue() {
        return this.value;
    }

}
