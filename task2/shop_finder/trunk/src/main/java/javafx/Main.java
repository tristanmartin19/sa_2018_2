package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import toolbox.shop;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        root.getStylesheets().clear();
        root.getStylesheets().add("style.css");
        primaryStage.setTitle("Shop Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //info pop-up
        Alert popup = new Alert(AlertType.INFORMATION);
        popup.getDialogPane().getStylesheets().add("style.css");
        popup.setTitle("Information");
        popup.setHeaderText("To edit or delete a shop in your search results or favorites, just click on it!");
        popup.showAndWait();

     //   primaryStage.
    }


    public static void main(String[] args) {
        launch(args);
    }
}
