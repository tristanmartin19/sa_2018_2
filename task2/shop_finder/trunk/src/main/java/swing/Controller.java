package swing;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.net.URL;
import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import toolbox.datahandler;
import toolbox.shop;
import toolbox.search;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable{
    private search actual_search = new search("","",0,"");
    private ObservableList<shop> items_results;
    private ObservableList<search> items_favorites;
    @FXML
    private ListView<shop> results;

    @FXML
    private ListView<search> favorites;

    @FXML
    private TextField search_name;

    @FXML
    private ComboBox search_category;

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
        items_results.clear();

        String category_sel = search_category.getSelectionModel().getSelectedItem().toString();
        String poi_sel = search_poi.getSelectionModel().getSelectedItem().toString();

        if ((category_sel.equals("<Select>") ) || (category_sel.equals("<Show All>")))
            category_sel = "";

        if ((poi_sel.equals("<Select>")) || (poi_sel.equals("<Show All>")))
            poi_sel = "";

        int distance_int;

        try {
            distance_int = Integer.parseInt(search_distance.getText());
        }
        catch (NumberFormatException ex1) {distance_int = 0;}




        results.setItems(FXCollections.observableArrayList(actual_search.getShops(search_name.getText(),
                category_sel, poi_sel,distance_int)));
    }

    @FXML
    private void addAction(ActionEvent event){
        System.out.print("Hello");
    }



    @FXML
    private void showAllAction(ActionEvent e){
        items_results.clear();

        //clear sheet
        search_name.setText("");
        search_category.setValue("<Show all>");
        search_poi.setValue("<Show all>");
        search_distance.setText("");


        results.setItems(FXCollections.observableArrayList(actual_search.getAllShops()));
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
        longitude.setText(Double.toString(selected.getLongitude()));
        TextField latitude = new TextField();
        latitude.setText(Double.toString(selected.getLatitude()));
        TextField homepage = new TextField();
        homepage.setText(selected.getHomepage());
        ComboBox<String> category = new ComboBox<>();
        category.setItems(FXCollections.observableArrayList());


        try {
            datahandler data_handler_search = new datahandler();
            Connection connection = data_handler_search.connectToDB();

            ResultSet categories = data_handler_search.getCategories(connection);
            ObservableList<String> categories_list = FXCollections.observableArrayList();

            while (categories.next())
            {
                categories_list.add(categories.getString("name_category"));
            }

            category.setItems(categories_list);
        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}

        category.setValue(selected.getCategory());



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

            String longitude_text = longitude.getText();
            String latitude_text = latitude.getText();

            try {
                selected.editShop(name.getText(), Double.parseDouble(longitude_text), Double.parseDouble(latitude_text),homepage.getText());
            }
            catch (NumberFormatException ex1) {}


            results.refresh();


        } else if(result.get() == delete){
            items_results.remove(selected);
            selected.deleteShop();
            results.refresh();


        } else{

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        items_results = FXCollections.observableArrayList();
        //shop buffer = new shop("Franz", 12.3456, 54.12345, "Laden", "www.wunderwelt.de",456);

        //prepare ComboBoxes:

        try {

            datahandler data_h = new datahandler();
            Connection connection = data_h.connectToDB();

            ResultSet categories = data_h.getCategories(connection);
            ObservableList<String> categories_list = FXCollections.observableArrayList();
            categories_list.add("<Show all>");

            while (categories.next())
            {
                categories_list.add(categories.getString("name_categorY"));
            }

            search_category.setItems(categories_list);
            search_category.setValue("<Select>");

            ResultSet pois = data_h.getPOI(connection);
            ObservableList<String> poi_list = FXCollections.observableArrayList();
            poi_list.add("<Show all>");

            while (pois.next()) {
                poi_list.add(pois.getString("name"));
            }

            search_poi.setItems(poi_list);
            search_poi.setValue("<Select>");

        }
        catch (SQLException ex1) {}
        catch (ClassNotFoundException ex2) {}





        //items_results.add(buffer);
        //results.setItems(items_results);

        items_favorites = FXCollections.observableArrayList();
        search buffer2 = new search("Billa", "Ladem",100, "Uni");
        items_favorites.add(buffer2);
        favorites.setItems(items_favorites);
    }

}
