package toolbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


// class helper
// provides additional functions for the interface
//
public class helper {

    public final String SELECT_ALL_BOX = "--";

    // function inputToClass
    // changes the string S to the string "undefined"
    // to translate user interface to DB, class names
    public String inputToClass(String text) {
        if (text.equals(SELECT_ALL_BOX))
            return "undefined";
        else
            return text;
    }

    // function classToInput
    // changes the string "undefined" to SELECT_ALL_BOX
    // to translate DB, class names to user-friendly names
    public String classToInput(String text) {
        if (text.equals("undefined"))
            return SELECT_ALL_BOX;
        else
            return text;
    }

    // function getAllCategories
    // returns all categories in a ObservableList
    // to prepare ComboBoxes
    public ObservableList<String> getAllCategories() {
        ObservableList<String> result_list = FXCollections.observableArrayList();

        try {

            datahandler data_h = new datahandler();
            Connection connection = data_h.connectToDB();

            ResultSet categories = data_h.getCategories(connection);


            while (categories.next()) {
                result_list.add(classToInput(categories.getString("category_name")));
            }


        } catch (SQLException ex1) {
        } catch (ClassNotFoundException ex2) {
        }

        return result_list.sorted();
    }

    public ObservableList<String> toAddList(ObservableList<String> current_list) {
        ObservableList<String> add_list = FXCollections.observableArrayList(current_list);
        add_list.remove(SELECT_ALL_BOX);

        return add_list;
    }

    public ObservableList<String> getAllPois() {
        ObservableList<String> result_list = FXCollections.observableArrayList();

        try {

            datahandler data_h = new datahandler();
            Connection connection = data_h.connectToDB();

            ResultSet pois = data_h.getPOI(connection);

            while (pois.next()) {
                result_list.add(classToInput(pois.getString("name")));
            }

        } catch (SQLException ex1) {
            String error = ex1.getMessage();

        } catch (ClassNotFoundException ex2) {
            String error = ex2.getMessage();
        }
        return result_list.sorted();
    }

}
