import javafx.scene.Scene;

import java.io.*;

public class Edit {
    static void editQuestion() throws IOException {
        String question = Bank.ques.getSelectionModel().getSelectedItem();
        try {
            char file = question.charAt(0);
            try {
                FileReader reader = new FileReader(Bank.dir + "/" + file + ".txt");
                BufferedReader br = new BufferedReader(reader);
                Insert.qtype = Integer.parseInt(br.readLine());
                String q = "";
                String t= br.readLine();
                while(!t.equals("*****"))
                {
                    t+="\n";
                    q+=t;
                    t=br.readLine();
                }
                //String q = br.readLine();
                Insert.t.setText(q);
                //br.readLine();
                if (Insert.qtype == 1) {
                    Insert.opt1.setText(br.readLine());
                    br.readLine();
                    Insert.opt2.setText(br.readLine());
                    br.readLine();
                    Insert.opt3.setText(br.readLine());
                    br.readLine();
                    Insert.opt4.setText(br.readLine());
                    br.readLine();
                    Insert.opt5.setText(br.readLine());
                    br.readLine();
                } else {
                    Insert.opt1.setText(br.readLine());
                }


            } catch (FileNotFoundException e) {
            }
            Scene s = Insert.getQuestion(file);
            Bank.window.setScene(s);
        } catch (Exception e) {
            AlertBox.display("ERROR", "Please select a question from the list");
        }

    }
}
