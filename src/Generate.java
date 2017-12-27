import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.util.Random;

public class Generate {

    //static String[] file_list = ((new File(Bank.dir)).list());
    static ChoiceBox<Integer> choices = new ChoiceBox<>();

    static void getChoice() {

        Image image = new Image(Generate.class.getResourceAsStream("abstract.jpg"));
// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(800, 500, true, true, true, false);
// new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
// new Background(images...)
        Background background = new Background(backgroundImage);
        System.out.println(Bank.dir);
        int num = new File(Bank.dir).list().length;

        Label l = new Label("Choose number of questions");
        l.setTextFill(Color.WHITE);
        l.setFont(new Font("NanumMyeongjo", 40));

        Button next = new Button("Next");
        next.setFont(new Font("NanumMyeongjo", 20));
        next.setOnAction(event -> {
            try {
                generateQuestions();
            } catch (IOException e) {
            }
        });

        Button cancel = new Button("Cancel");
        cancel.setFont(new Font("NanumMyeongjo", 20));
        cancel.setOnAction(event -> {
            choices.getItems().clear();
            Bank.window.setScene(Bank.scene);
        });

        for (int i = 1; i <= num; i++)
            choices.getItems().add(i);

        VBox box = new VBox();
        box.setBackground(background);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15, 12, 15, 12));
        box.setSpacing(10);
        //box.setStyle("-fx-background-color: radial-gradient(radius 100%, red, #6a6a6a, #f4fdff)");
        box.getChildren().addAll(l, choices, next, cancel);
        Scene choice_scene = new Scene(box, 800, 500);
        Bank.window.setScene(choice_scene);
    }

    private static void generateQuestions() throws IOException {
        String[] file_list = ((new File(Bank.dir)).list());
        //int max = Integer.parseInt(file_list[file_list.length - 1].substring(0, 1));
        int required = choices.getSelectionModel().getSelectedItem();
        int[] available = new int[100];
        for (String aFile_list : file_list)
            available[Integer.parseInt(aFile_list.substring(0, 1))] = 1;

        FileWriter writer = new FileWriter("/home/prajwal/Desktop/Question_Paper.txt");
        writer.write("\t\t\tBirla Institute of Technology and Science, Pilani\n\t\t\t\tDiscrete Structures for Computer Science\n\n");
        FileWriter swriter = new FileWriter("/home/prajwal/Desktop/Solutions.txt");
        writer.flush();
        writer.close();
        swriter.close();
/*
        for(int i=1; i<=10; i++)
            System.out.println(available[i]);*/

        int qnum = 1;
        while (required > 0) {
            Random r = new Random();
            int number = r.nextInt(20);
            if (available[number] == 1) {
                available[number] = 0;
                required--;
                try {
                    printToFile(number, qnum++);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        AlertBox.display("SUCCESS", "Question Paper has been generated and stored in Desktop.");
        choices.getItems().clear();
        Bank.window.setScene(Bank.scene);
    }

    private static void printToFile(int fnum, int qnum) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(Bank.dir + "/" + fnum + ".txt"));
        FileWriter writer = new FileWriter("/home/prajwal/Desktop/Question_Paper.txt", true);
        FileWriter swriter = new FileWriter("/home/prajwal/Desktop/Solutions.txt", true);
        String qtype = br.readLine();
        writer.write(qnum + ".\t" + br.readLine() + "\n");
        char opt = 'a';
        if (qtype.equals("1")) {
            for (int i = 1; i <= 4; i++) {
                br.readLine();
                writer.write(opt);
                writer.write(".\t" + br.readLine() + "\n");
                opt++;
            }
        }
        writer.write("\n");
        writer.flush();
        writer.close();
        br.readLine();
        swriter.write(qnum + ".\t" + br.readLine() + "\n");
        swriter.flush();
        swriter.close();
    }

}
