import java.util.StringTokenizer;

public class ServerUtil {
    public String[] stringTokens(String inpuString){

        StringTokenizer stringTokenizer = new StringTokenizer(inpuString, " ");
        String[] strings = new String[3];
        int index = 0;

        while (stringTokenizer.hasMoreTokens() && index<3) {
            strings[index++] = stringTokenizer.nextToken();
            System.out.println(strings[index-1]);
        }

        return strings;

    }
}
