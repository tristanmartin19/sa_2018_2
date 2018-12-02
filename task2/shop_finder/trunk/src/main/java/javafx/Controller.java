package javafx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.net.URL;

import javafx.fxml.Initializable;


import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import toolbox.datahandler;
import toolbox.helper;
import toolbox.shop;
import toolbox.search;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable {
    private search actual_search = new search(0, "","", "", 0, "");
    private ObservableList<shop> items_results;
    private ObservableList<search> items_searches;
    private ObservableList<String> items_categories;
    private ObservableList<String> items_pois;
    private helper init_helper =  new helper();
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
    private Tab tab_search;

    @FXML
    private TabPane tabPane;

    @FXML
    private void searchAction(ActionEvent e) {
        items_results.clear();

        String category_sel = search_category.getSelectionModel().getSelectedItem().toString();
        String poi_sel = search_poi.getSelectionModel().getSelectedItem().toString();


        category_sel = init_helper.inputToClass(category_sel);

        poi_sel = init_helper.inputToClass(poi_sel);

        int distance_int;

        try {
            distance_int = Integer.parseInt(search_distance.getText());
        } catch (NumberFormatException ex1) {
            distance_int = 0;
        }


        results.setItems(FXCollections.observableArrayList(actual_search.getShops(search_name.getText(),
                category_sel, poi_sel, distance_int)));
    }

    @FXML
    private void addAction(ActionEvent event) {

        try {
            datahandler new_dh = new datahandler();
            shop new_shop = new shop(add_name.getText(),
                    Double.parseDouble(add_longitude.getText()),
                    Double.parseDouble(add_latitude.getText()),
                    add_category.getSelectionModel().getSelectedItem().toString(),
                    add_homepage.getText(), 0, new_dh);

            new_shop.addShop();

            add_name.setText("");
            add_category.setValue("");
            add_longitude.setText("");
            add_latitude.setText("");
            add_homepage.setText("");
        } catch (NumberFormatException ex1) {
        }

    }


    @FXML
    private void showAllAction(ActionEvent e) {
        items_results.clear();

        //clear sheet
        search_name.setText("");
        search_category.setValue("<Show All>");
        search_poi.setValue("<Show All>");
        search_distance.setText("");


        results.setItems(FXCollections.observableArrayList(actual_search.getAllShops()));
    }

    @FXML
    private void saveAction(ActionEvent e) {


        Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
        popup.setTitle("Add Favorite");
        ButtonType save = new ButtonType("Save");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        popup.getButtonTypes().setAll(save, cancel);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name = new TextField();
        name.setText("");


        grid.add(new Label("Search name:"), 0, 0);
        grid.add(name, 1, 0);


        popup.getDialogPane().setContent(grid);

        Optional<ButtonType> result = popup.showAndWait();
        if (result.get() == save) {
            try {
                // control search_distance:
                int search_distance_2 = 0;
                if (!search_distance.getText().equals(""))
                    search_distance_2 = Integer.parseInt(search_distance.getText());



                //create a new search item:
                search new_search = new search(0, name.getText(), search_name.getText(),
                        init_helper.inputToClass(search_category.getSelectionModel().getSelectedItem().toString()),
                        search_distance_2,
                        init_helper.inputToClass(search_poi.getSelectionModel().getSelectedItem().toString()));
                new_search.addSearch();
                items_searches.add(new_search);


            } catch (NumberFormatException ex1) {


            }


        }


    }

    @FXML
    private void favoriteClick(MouseEvent e) {

        search selected = favorites.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
            popup.setTitle("Edit Favorite");
            ButtonType again = new ButtonType("Search");
            ButtonType edit = new ButtonType("OK");
            ButtonType delete = new ButtonType("Delete");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            popup.getButtonTypes().setAll(again, edit, delete, cancel);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField fav_name = new TextField();
            fav_name.setText(selected.getSearchName());

            TextField name = new TextField();
            name.setText(selected.getName());

            ComboBox<String> category = new ComboBox<>();

            TextField distance = new TextField();
            ComboBox<String> poi = new ComboBox<>();


            category.setItems(items_categories);
            category.setValue(selected.getCategory());

            poi.setItems(items_pois);
            poi.setValue(selected.getPoi());

            distance.setText(Integer.toString(selected.getDistance()));

            grid.add(new Label("Search Name"), 0, 0);
            grid.add(fav_name, 1, 0);
            grid.add(new Label("Shop Name"), 0, 1);
            grid.add(name, 1, 1);
            grid.add(new Label("Distance (meters)"), 0, 3);
            grid.add(distance, 1, 3);
            grid.add(new Label("Point of Interest"), 0, 4);
            grid.add(poi, 1, 4);
            grid.add(new Label("Shop Category"), 0, 2);
            grid.add(category, 1, 2);

            popup.getDialogPane().setContent(grid);

            Optional<ButtonType> result = popup.showAndWait();
            if (result.get() == edit) {
                try {
                    selected.editSearch( fav_name.getText(), name.getText(), category.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(distance.getText()),
                            poi.getSelectionModel().getSelectedItem().toString());
                } catch (NumberFormatException ex1) {
                }

                favorites.refresh();

            } else if (result.get() == delete) {
                items_searches.remove(selected);
                selected.deleteSearch();
                favorites.refresh();


            } else if (result.get() == again) {
                //fill in search in search boxes
                search_name.setText(name.getText());
                search_category.setValue(category.getValue());
                search_distance.setText(distance.getText());
                search_poi.setValue(poi.getValue());

                search.fire();

            } else {

            }

        }

    }

    @FXML
    private void searchClick(MouseEvent e) {
        shop selected = results.getSelectionModel().getSelectedItem();

        if (selected != null) {
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


            category.setItems(items_categories);
            category.setValue(selected.getCategory());


            grid.add(new Label("Name"), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label("Longitude"), 0, 1);
            grid.add(longitude, 1, 1);
            grid.add(new Label("Latitude"), 0, 2);
            grid.add(latitude, 1, 2);
            grid.add(new Label("Homepage"), 0, 3);
            grid.add(homepage, 1, 3);
            grid.add(new Label("Category"), 0, 4);
            grid.add(category, 1, 4);

            popup.getDialogPane().setContent(grid);

            Optional<ButtonType> result = popup.showAndWait();
            if (result.get() == edit) {

                String longitude_text = longitude.getText();
                String latitude_text = latitude.getText();

                try {
                    selected.editShop(name.getText(), Double.parseDouble(longitude_text), Double.parseDouble(latitude_text), homepage.getText(), category.getSelectionModel().getSelectedItem().toString());
                } catch (NumberFormatException ex1) {
                }


                results.refresh();


            } else if (result.get() == delete) {
                items_results.remove(selected);
                selected.deleteShop();
                results.refresh();


            } else {

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        items_results = FXCollections.observableArrayList();




        items_categories = init_helper.getAllCategories();

        add_category.setItems(items_categories);
        search_category.setItems(init_helper.toSearchList(items_categories));


        items_pois = init_helper.getAllPois();

        search_poi.setItems(init_helper.toSearchList(items_pois));


        items_searches = FXCollections.observableArrayList(actual_search.getAllSearches());
        favorites.setItems(items_searches);
    }

}
