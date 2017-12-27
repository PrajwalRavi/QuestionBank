import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class Bank {

    static Stage window;
    static Scene scene;
    static ListView<String> ques;
    static String dir = "/home/prajwal/Desktop/oop_project/src/questions/oops";


    public static Scene getScene(Stage main_window) throws IOException {
        window = main_window;
        BorderPane border = new BorderPane();
        HBox top = addHBox();
        VBox left = addVbox();
        VBox middle = addMiddle();

        border.setCenter(middle);
        border.setTop(top);
        border.setLeft(left);
        scene = new Scene(border, 1600, 900);
        scene.getStylesheets().add("styles.css");
        return scene;
    }

    private static VBox addVbox() throws IOException {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: linear-gradient(#a00e1b,#010305)");

        Button new_ques = new Button("New");
        Button edit = new Button("Edit");
        Button delete = new Button("Delete");
        Button generate = new Button("Generate questions");

        new_ques.setFont(new Font("NanumMyeongjo", 15));
        edit.setFont(new Font("NanumMyeongjo", 15));
        delete.setFont(new Font("NanumMyeongjo", 15));
        generate.setFont(new Font("NanumMyeongjo", 15));



        new_ques.setOnAction(event -> {
            Scene s = Insert.addQuestion();
            window.setScene(s);
        });

        edit.setOnAction(event -> {
            try {
                Edit.editQuestion();
            } catch (IOException e) {
            }
        });

        generate.setOnAction(event -> Generate.getChoice());

        delete.setOnAction(event -> deleteQuestion());

        vbox.getChildren().addAll(new_ques, edit, delete, generate);
        return vbox;
    }


    private static void deleteQuestion() {
        String question = ques.getSelectionModel().getSelectedItem();
        try {
            char file = question.charAt(0);
            File f = new File(dir + "/" + file + ".txt");
            f.delete();
            try {
                refresh_list();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            AlertBox.display("ERROR", "Please select a question from the list");
        }
    }


    static void refresh_list() throws IOException {
        ques.getItems().clear();
        for (int i = 1; i <= 1000; i++)
            try {
                FileReader f2 = new FileReader(dir + "/" + i + ".txt");
                BufferedReader br = new BufferedReader(f2);
                StringBuffer display = new StringBuffer(i + ".  ");

                String type = br.readLine();

                String question = "";
                String t= br.readLine();
                while(!t.equals("*****"))
                {                t+="\n";

                    question+=t;
                    t=br.readLine();
                }
                //System.out.println(s);

                question += "\n\n";
                display.append(question);
                String dummy;

                if (type.equals("1")) {
                    display.append("Options are:-\n");
                    for (int j = 1; j <= 4; j++) {
                        String opt = br.readLine();
                        opt += "\n";
                        display.append(opt);
                        dummy = br.readLine();
                    }
                }

                display.append("Answer:-\t");

               // dummy = br.readLine();
                String opt1 = br.readLine();
                opt1 += "\n";
                display.append(opt1);


                String to_display = new String(display);
                ques.getItems().add(to_display);

            } catch (FileNotFoundException e) {
            }
    }

    private static VBox addMiddle() throws IOException {
        ques = new ListView<>();
        ques.setPrefHeight(8000);
        refresh_list();
        VBox vbox = new VBox();
        vbox.getChildren().add(ques);
        return vbox;
    }


    private static HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: linear-gradient(#010305,#b71e23)");

        Button closer = new Button("Close");
        closer.setPrefSize(100, 20);
        closer.setOnAction(event -> window.close());

        ChoiceBox<String> subjects = new ChoiceBox<>();
        subjects.getItems().addAll("Logic in CS", "OOPs", "Discrete Structures");
        subjects.setValue("OOPs");
        subjects.setOnAction(event -> {
            setDirectory(subjects);
            try {
                refresh_list();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hbox.getChildren().addAll(subjects, spacer, closer);

        return hbox;
    }

    private static void setDirectory(ChoiceBox subjects) {
        if(subjects.getSelectionModel().getSelectedItem().equals("OOPs"))
            dir = "/home/prajwal/Desktop/oop_project/src/questions/oops";
        else if(subjects.getSelectionModel().getSelectedItem().equals("Discrete Structures"))
            dir = "/home/prajwal/Desktop/oop_project/src/questions/disco";
        else
            dir = "/home/prajwal/Desktop/oop_project/src/questions/logic";

    }
}
