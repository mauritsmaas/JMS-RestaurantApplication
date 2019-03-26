package form.Kitchen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class KitchenStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        File file = new File("src/form/Kitchen/Kitchen.fxml");
        URL url = file.toURI().toURL();
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("KITCHEN");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
