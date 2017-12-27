import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Welcome extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    static Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Scene start_scene;
        Scene next_scene = Bank.getScene(window);
        primaryStage.setTitle("Welcome");

        Image welcome2 = new Image("file:/home/prajwal/Desktop/oop_project/wel_img.jpg");
        ImageView view_img = new ImageView(welcome2);
        view_img.setFitHeight(300);
        view_img.setFitWidth(1000);
        view_img.setPreserveRatio(true);

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: radial-gradient(radius 100%, red, darkgray, #ffb71a)");
        // grid.setStyle("-fx-background-color: radial-gradient(focus-angle 45deg, focus-distance 20%, center 25% 25%, radius 50%, reflect, gray, darkgray 75%, dimgray)\n");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        GridPane.setConstraints(view_img, 0, 0, 2, 1);

        Label user = new Label("Username:");
        user.setFont(new Font("NanumMyeongjo", 25));
        user.setTextAlignment(TextAlignment.CENTER);
        user.setPrefHeight(50);
        GridPane.setConstraints(user, 0, 1);

        Label password = new Label("Password:");
        password.setFont(new Font("NanumMyeongjo", 25));
        password.setTextAlignment(TextAlignment.CENTER);
        password.setPrefHeight(50);
        GridPane.setConstraints(password, 0, 2);

        TextField name = new TextField();
        name.setFont(new Font("NanumMyeongjo", 25));
        GridPane.setConstraints(name, 1, 1);

        PasswordField pass = new PasswordField();
        pass.setFont(new Font("NanumMyeongjo", 25));
        GridPane.setConstraints(pass, 1, 2);

        Label message = new Label("Wrong username or password");
        message.setFont(new Font("NanumMyeongjo", 20));
        message.setStyle("-fx-text-fill: blue");
        message.setVisible(false);
        GridPane.setConstraints(message, 1, 3,2,1);

        Button next = new Button("Log In");
        next.setFont(new Font("NanumMyeongjo", 22));
        next.setDefaultButton(true);
        //next.setStyle("-fx-border-radius: 30px");
        next.setPrefHeight(35);
        GridPane.setConstraints(next, 0, 4, 3, 1);
        GridPane.setHalignment(next, HPos.CENTER);
        next.defaultButtonProperty().bind(next.focusedProperty());
        next.setOnAction(event -> {
            try {
                verify(pass.getText(),name.getText(), message);
            } catch (IOException e) {            }
        });

        grid.getChildren().addAll(password, next, user, pass, name, view_img, message);
        start_scene = new Scene(grid, 1000, 600);

        //window.setResizable(false);
        window.setScene(start_scene);
        window.show();
    }

    private void verify(String password,String user, Label message) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/home/prajwal/Desktop/oop_project/src/pass.txt"));
        if (!user.equals(br.readLine()) || !password.equals(br.readLine()))
            message.setVisible(true);
        else
        {
            window.setTitle("Bank");
            window.setScene(Bank.scene);
        }

    }


}

