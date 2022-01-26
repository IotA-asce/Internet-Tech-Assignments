package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ServerUtils {
    
    HashMap<String, Integer> strings;
    public String[]  tokenizer(String input) {
        
        String[] stringTokens = new String[3];
        int id = 0;
        StringTokenizer stringTokenizer = new StringTokenizer(
            input,
            " "
        );

        // strings.put(stringTokenizer.nextToken(), 1);
        stringTokens[id++] = stringTokenizer.nextToken(); 
        return stringTokens;

    }

    // private void detectTokens(String input){
        
    //     if(strings.containsKey("GET")){

    //     }
    //     else if(strings.containsKey("PUT")){

    //     }
    //     else{

    //     }

    // }

    public String FindOperation(String input){

        strings = new HashMap<>();
        tokenizer(input);

        return "";
    }
}