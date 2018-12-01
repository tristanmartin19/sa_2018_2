package swing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import toolbox.shop;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Shop Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //info PopUp
        Alert popup = new Alert(AlertType.INFORMATION);
        popup.setTitle("Information");
        popup.setHeaderText("To edit or delete a shop in your search results or in your favorites, just click on it!");

        popup.showAndWait();
        //shop buffer = new shop("Franz", 12.3456, 54.12345, "Laden", "www.wunderwelt.de",123);
     //   primaryStage.
    }


    public static void main(String[] args) {
        launch(args);
    }
}
