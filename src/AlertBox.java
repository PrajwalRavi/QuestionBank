import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(new Font("NanumMyeongjo", 25));
        Button closeButton = new Button("Close");
        closeButton.setFont(new Font("NanumMyeongjo", 20));
        closeButton.setOnAction(e -> {
            Bank.window.setScene(Bank.scene);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: lightgoldenrodyellow");
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 150);
        window.setScene(scene);
        window.showAndWait();
    }

}