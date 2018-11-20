package swing;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import toolbox.shop;
import toolbox.search;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable{

    private ObservableList<shop> items_results;
    private ObservableList<search> items_favorites;
    @FXML
    private ListView<shop> results;

    @FXML
    private ListView<search> favorites;

    @FXML
    private TextField search_name;

    @FXML
    private TextField search_category;

    @FXML
    private TextField search_distance;

    @FXML
    private ComboBox search_poi;

    @FXML
    private Label search_error;

    @FXML
    private Label add_error;

    @FXML
    private TextField add_name;

    @FXML
    private TextField add_homepage;

    @FXML
    private TextField add_longitude;

    @FXML
    private TextField add_latitude;

    @FXML
    private ComboBox add_category;

    @FXML
    private Button show_all;

    @FXML
    private Button save_search;

    @FXML
    private Button search;

    @FXML
    private Button add;

    @FXML
    private void searchAction(ActionEvent e){
        System.out.print("Hello");
    }

    @FXML
    private void addAction(ActionEvent event){
        System.out.print("Hello");
    }



    @FXML
    private void showAllAction(ActionEvent e){
        System.out.print("Hello");
    }

    @FXML
    private void saveAction(ActionEvent e){
        System.out.print("Hello");
    }

    @FXML
    private void favoriteClick(MouseEvent e){
        search selected = favorites.getSelectionModel().getSelectedItem();

        Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
        popup.setTitle("Edit Favorite");
        ButtonType again = new ButtonType("Search Again");
        ButtonType edit = new ButtonType("Edit");
        ButtonType delete = new ButtonType("Delete");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        popup.getButtonTypes().setAll(again, edit, delete, cancel);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name = new TextField();
        name.setText(selected.getName());
        TextField category = new TextField();
        category.setText(selected.getCategory());
        TextField distance = new TextField();
        //distance.setText(selected.getDistance());
        TextField poi = new TextField();
        poi.setText(selected.getPoi());



        grid.add(new Label("Name"),0,0);
        grid.add(name,1,0);
        grid.add(new Label("distance around"),0,2);
        grid.add(distance,1,2);
        grid.add(new Label("POI"),0,3);
        grid.add(poi,1,3);
        grid.add(new Label("Category"),0,1);
        grid.add(category,1,1);

        popup.getDialogPane().setContent(grid);

        Optional<ButtonType> result = popup.showAndWait();
        if(result.get()== edit) {
            System.out.println(name.getText());
        } else if(result.get() == delete){

        } else if (result.get() == again) {

        } else {

        }

    }

    @FXML
    private void searchClick(MouseEvent e){
        shop selected = results.getSelectionModel().getSelectedItem();
        System.out.println(selected.getId());

        Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
        popup.setTitle("Edit Shop");
        ButtonType edit = new ButtonType("Edit");
        ButtonType delete = new ButtonType("Delete");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        popup.getButtonTypes().setAll(edit, delete, cancel);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name = new TextField();
        name.setText(selected.getName());
        TextField longitude = new TextField();
        //longitude.setText(selected.getLongitude().toString());
        TextField latitude = new TextField();
        //latitude.setText(selected.getLatitude().toString());
        TextField homepage = new TextField();
        homepage.setText(selected.getHomepage());
        TextField category = new TextField();
        category.setText(selected.getCategory());


        grid.add(new Label("Name"),0,0);
        grid.add(name,1,0);
        grid.add(new Label("Longitude"),0,1);
        grid.add(longitude,1,1);
        grid.add(new Label("Latitude"),0,2);
        grid.add(latitude,1,2);
        grid.add(new Label("Homepage"),0,3);
        grid.add(homepage,1,3);
        grid.add(new Label("Category"),0,4);
        grid.add(category,1,4);

        popup.getDialogPane().setContent(grid);

        Optional<ButtonType> result = popup.showAndWait();
        if(result.get()== edit) {
            System.out.println(name.getText());
        } else if(result.get() == delete){

        } else{

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        items_results = FXCollections.observableArrayList();
        shop buffer = new shop("Franz", 12.3456, 54.12345, "Laden", "www.wunderwelt.de",456);

        items_results.add(buffer);
        results.setItems(items_results);

        items_favorites = FXCollections.observableArrayList();
        search buffer2 = new search("Billa", "Ladem",100, "Uni");
        items_favorites.add(buffer2);
        favorites.setItems(items_favorites);
    }

}
