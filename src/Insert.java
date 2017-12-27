import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Insert {

    static Stage window = Bank.window;
    static int qtype;
    static TextArea t = new TextArea();
    static Scene options;
    static TextField opt1 = new TextField(), opt2 = new TextField(), opt3 = new TextField(), opt4 = new TextField(), opt5 = new TextField();


    static Scene addQuestion() {
        Label type = new Label("Type:");
        RadioButton mcq = new RadioButton("MCQ");
        RadioButton tf = new RadioButton("True/False");
        RadioButton fitb = new RadioButton("Fill in the blanks");
        Button ok = new Button("Next");
        ToggleGroup group = new ToggleGroup();
        mcq.setToggleGroup(group);
        tf.setToggleGroup(group);
        fitb.setToggleGroup(group);

        t.clear();
        opt1.clear();
        opt2.clear();
        opt3.clear();
        opt4.clear();
        opt5.clear();

        Button cancel = new Button("Cancel");
        cancel.setFont(new Font("NanumMyeongjo", 20));
        cancel.setOnAction(event -> Bank.window.setScene(Bank.scene));

        type.setFont(new Font("NanumMyeongjo", 50));
        type.setPadding(new Insets(25, 25, 25, 25));
        mcq.setFont(new Font("NanumMyeongjo", 25));
        tf.setFont(new Font("NanumMyeongjo", 25));
        fitb.setFont(new Font("NanumMyeongjo", 25));
        ok.setFont(Font.font("NanumMyeongjo", FontWeight.BOLD, 20));

        ok.setOnAction(event -> {
            try {
                getSelected(mcq, tf, fitb);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(qtype);
            try {
                window.setScene(getQuestion());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 25, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: linear-gradient(#2bd219,aqua)");
        vbox.getChildren().addAll(type, mcq, tf, fitb, ok, cancel);
        vbox.setAlignment(Pos.CENTER);
        options = new Scene(vbox, 800, 500);
        return options;
    }

    private static void getSelected(RadioButton mcq, RadioButton tf, RadioButton fitb) throws IOException {
        if (mcq.isSelected())
            qtype = 1;
        else if (tf.isSelected())
            qtype = 2;
        else
            qtype = 3;
    }

    static Scene getQuestion(char... file) throws IOException {
        try {
            new File(Bank.dir + "/" + file[0] + ".txt").delete();
        } catch (Exception e) {
            //System.out.println(e);
        }
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: linear-gradient(#e0cc06,coral)");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setSnapToPixel(true);

        Label question = new Label("Question");
        question.setFont(new Font("NanumMyeongjo", 25));
        GridPane.setConstraints(question, 0, 0);

        Button back = new Button("Back");
        back.setFont(new Font("NanumMyeongjo", 15));
        back.setOnAction(event -> Bank.window.setScene(options));
        GridPane.setConstraints(back, 0, 11);

        Button cancel = new Button("Cancel");
        cancel.setFont(new Font("NanumMyeongjo", 15));
        cancel.setOnAction(event -> Bank.window.setScene(Bank.scene));
        GridPane.setConstraints(cancel, 0, 12, 2, 1);

        t.setPromptText("Enter Question");
        t.setWrapText(true);
        t.setPrefRowCount(3);
        GridPane.setConstraints(t, 0, 1);

        Label tell, ans;


        if (qtype == 1) {
            tell = new Label("Enter options");
            tell.setFont(new Font("NanumMyeongjo", 25));
            GridPane.setConstraints(tell, 0, 3);
            GridPane.setConstraints(opt1, 0, 4);
            GridPane.setConstraints(opt2, 0, 5);
            GridPane.setConstraints(opt3, 0, 6);
            GridPane.setConstraints(opt4, 0, 7);
            ans = new Label("Answer");
            ans.setFont(new Font("NanumMyeongjo", 25));
            GridPane.setConstraints(ans, 0, 8);
            GridPane.setConstraints(opt5, 0, 9);
            grid.getChildren().addAll(ans, opt2, opt3, opt4, opt5);
            grid.getChildren().addAll(tell, opt1);
        } else if (qtype == 2) {
            tell = new Label("Enter True/False");
            tell.setFont(new Font("NanumMyeongjo", 25));
            GridPane.setConstraints(tell, 0, 3);
            GridPane.setConstraints(opt1, 0, 4);
            grid.getChildren().addAll(tell, opt1);
        } else {
            tell = new Label("Enter Answer");
            tell.setFont(new Font("NanumMyeongjo", 25));
            GridPane.setConstraints(tell, 0, 3);
            GridPane.setConstraints(opt1, 0, 4);
            grid.getChildren().addAll(tell, opt1);
        }

        Button ok = new Button("Finish");
        ok.setFont(new Font("NanumMyeongjo", 20));
        GridPane.setConstraints(ok, 0, 10);
        ok.setOnAction(event -> {
            try {
                check();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        grid.getChildren().addAll(t, ok, question,  cancel);
        return new Scene(grid, 1000, 600);
    }

    private static void check() throws IOException {
        //System.out.println(opt1.getText());
        if(t.getText().equalsIgnoreCase(""))
            Abox.display("ERROR","Please enter question");
        else if(qtype==2)
        {
            if((opt1.getText().equalsIgnoreCase("true") || opt1.getText().equalsIgnoreCase("false")))
                extractQuestion();

            else
                Abox.display("ERROR","Please enter True or False");
        }

        else
            extractQuestion();
    }

    private static void extractQuestion() throws IOException {

        File dir_file = new File(Bank.dir);
        String[] files = dir_file.list();

        int[] empty_files = new int[100];
        try {
            for (int i = 0; i < files.length; i++)
                empty_files[Integer.parseInt(files[i].substring(0, 1))]++;
        } catch (Exception e) {
        }

        for (int i = 1; i < 100; i++) {
            if (empty_files[i] == 0) {
                FileWriter writer = new FileWriter(Bank.dir + "/" + i + ".txt");
                // System.out.println("New File: "+(i));
                writer.write((qtype + 48));
                writer.write("\n");
                writer.write(t.getText().trim() + "\n");
                writer.write("*****\n");
                writer.write(opt1.getText() + "\n");
                if (qtype == 1) {

                    writer.write("*****\n");
                    writer.write(opt2.getText() + "\n");
                    writer.write("*****\n");
                    writer.write(opt3.getText() + "\n");
                    writer.write("*****\n");
                    writer.write(opt4.getText() + "\n");
                    writer.write("*****\n");
                    writer.write(opt5.getText() + "\n");
                }
                writer.close();
                break;
            }
        }
        Bank.refresh_list();
        window.setScene(Bank.scene);
    }
}
