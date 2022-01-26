import java.util.StringTokenizer;

public class ServerUtil {
    public String[] stringTokens(String inpuString){

        StringTokenizer stringTokenizer = new StringTokenizer(inpuString, " ");
        String[] strings = new String[3];
        int index = 0;

        while (stringTokenizer.hasMoreTokens()) {
            strings[index++] = stringTokenizer.nextToken();
        }

        return strings;

    }
}
