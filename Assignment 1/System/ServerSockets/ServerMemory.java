import java.util.HashMap;

public class ServerMemory {

    private final int MEMORY_SIZE = 10000;
    private HashMap<String, String> pair;

    ServerMemory(){

        /*
        ***************************************
        Constructor for initializing the memory
        of the server
        ***************************************
        */

        pair = new HashMap<>();

    }


    // UTILITY FUNCTIONS

    public String GET(String key){

        return pair.containsKey(key) ? this.pair.get(key) : "'KEY NOT FOUND' : exception";

    }

    public String PUT(String key, String value){

        String status = "REGISTRATION SUCCESS!";

        if(pair.containsKey(key)){
            return "KEY VALUE PAIR ALREADY EXISTS";
        }

        try {
            this.pair.put(key, value);
        } catch (Exception e) {
            status = "ERROR";
        }

        return status;
    }  

    public String MEMORY_STATUS(){

        return 
            "---------------------------------------------------\n" + 
            "\tMEMORY SIZE\t:\t"  + MEMORY_SIZE          + "\n" +
            "\tUSED_STORAGE\t:\t" + ( this.pair.size() ) + "\n" +
            "---------------------------------------------------\n" 
        ;

    }
}
