package form.Kitchen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class KitchenStartNed extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        File file1 = new File("src/form/Kitchen/KitchenNed.fxml");
        URL url1 = file1.toURI().toURL();
        Parent root1 = FXMLLoader.load(url1);

        primaryStage.setTitle("KITCHEN - Nederlands");
        primaryStage.setScene(new Scene(root1, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
