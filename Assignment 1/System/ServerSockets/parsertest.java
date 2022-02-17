import java.util.ArrayList;
import java.util.Scanner;

public class parsertest {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            String input = scan.nextLine();
            ArrayList<String> toShow = new ServerUtil().stringTokens(input);

            if (!toShow.isEmpty()) {
                for (String token : toShow)
                    System.out.println("-> "+token);
            } else{
                System.out.println("ERROR");
            }

            toShow.clear();
        }
    }
}
