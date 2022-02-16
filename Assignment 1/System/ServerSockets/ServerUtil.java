import java.util.ArrayList;
import java.util.StringTokenizer;

public class ServerUtil {
    public ArrayList<String> stringTokens(String inpuString){

        StringTokenizer stringTokenizer = new StringTokenizer(inpuString, " ");
        ArrayList<String> strings = new ArrayList<>();
        int index = 0;

        while (stringTokenizer.hasMoreTokens()) {
            index++;
            strings.add( stringTokenizer.nextToken() );
            System.out.println(strings.get(index-1));
        }

        return strings;

    }
}
