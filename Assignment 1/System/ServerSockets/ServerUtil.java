import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.String;

public class ServerUtil {
    public ArrayList<String> stringTokens(String inpuString) {

        StringTokenizer stringTokenizer = new StringTokenizer(inpuString, " ");
        ArrayList<String> strings_ = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        int index = 0;

        while (stringTokenizer.hasMoreTokens()) {
            index++;
            strings_.add(stringTokenizer.nextToken());
        }
        boolean correct = true;
        for (int i = 0; i < strings_.size() && correct;) {
            switch (strings_.get(i).toUpperCase()) {
                case "PUT":
                    if (i + 2 < strings_.size()) {
                        strings.add(strings_.get(i++));
                        strings.add(strings_.get(i++));
                        strings.add(strings_.get(i++));
                    } else
                        correct = false;
                    break;
                case "GET":
                case "DELETE":
                case "PASSWORD" :
                    if (i + 1 < strings_.size()) {
                        strings.add(strings_.get(i++));
                        strings.add(strings_.get(i++));
                    } else
                        correct = false;
                    break;
                default:
                    correct = false;
                    break;
            }
        }

        return correct ? strings : new ArrayList<String>();

    }
}
