import java.util.ArrayList;
import java.util.HashMap;

public class ServerMemory {

    private final int MEMORY_SIZE = 10000;
    private HashMap<String, String> pair;
    private ArrayList<String> keys; // contains key of all the pairs

    ServerMemory() {

        /*
         ***************************************
         * Constructor for initializing the memory
         * of the server
         ***************************************
         */

        pair = new HashMap<>();
        keys = new ArrayList<>();

    }

    // UTILITY FUNCTIONS

    public String GET(String key) {

        return pair.containsKey(key) ? this.pair.get(key) : "\t'KEY NOT FOUND' : exception";

    }

    public String PUT(String key, String value) {

        String status = "\tREGISTRATION SUCCESS!";

        if (pair.containsKey(key)) {
            return "\tKEY VALUE PAIR ALREADY EXISTS";
        }

        try {

            this.pair.put(key, value);
            this.keys.add(key);

        } catch (Exception e) {
            status = "\tERROR";
        }

        return status;
    }

    public String DELETE(String key, int flag) {
        if (flag != 1)
            return "\tAccess Denied : Not admin client";
        this.keys.remove(key);
        return pair.containsKey(key) ? this.pair.remove(key) : "\t'KEY NOT FOUND' : exception";

    }

    public String MEMORY_STATUS(Integer statusFlag) {

        String output = "";
        String nl = "\n";
        String sp = " ";
        String admin = nl + nl + "\t-------- Admin pannel --------";

        output += (statusFlag == 1) ? (admin + nl) : "";

        output += nl +
                "----------------------------------------------" + nl +
                "Memory size\t:\t\t" + MEMORY_SIZE + nl +
                "----------------------------------------------" + nl +
                "Memory in use\t:\t\t" + (this.pair.size()) + nl;

        if (statusFlag == 1) {

            // output += admin + nl;

            output += "----------------------------------------------" + nl +
                    "----------------------------------------------" + nl +
                    "\tKEY\t\t|\t\tVALUE\t\t\t" + nl +
                    "----------------------------------------------" + nl;

            for (String s : keys) {
                output += "\t" + s + "\t\t|\t\t" + pair.get(s) + "\t\t\t" + nl;
            }

            output += "----------------------------------------------" + nl;

        }

        return output;

    }
}
