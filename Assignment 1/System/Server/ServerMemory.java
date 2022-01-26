package Server;

import java.util.HashMap;

/*
Consists of all key value pairs of 
*/

public class ServerMemory {

    private final int MEMORY_LIMIT = 5000;

    private HashMap<String, String> pair;
    private int index;

    ServerMemory(){
        pair = new HashMap<>();
        index = 0;
    }

    public String GET(String key){
        
        if(pair.containsKey(key)){
            return pair.get(key);
        }
        else{
            return "'KEY NOT FOUND' : exception";
        }
    }

    public boolean PUT(String key, String value){
        
        boolean state = true;

        // condition to check if key, value pair already exists

        try {
            pair.put(key, value);
        } catch (Exception e) {
            state = false;
        }
        this.index++;

        return state;
    }

    public void MEMORY_STATUS(){
        System.out.println(
            "\n" +
            "__________________________________________________\n" +
            "MEMORY_SIZE  :\t" + MEMORY_LIMIT + "\n" +
            "USED_STORAGE :\t" + (this.index - 1) + "\n" +
            "__________________________________________________"
        );
    }
}
