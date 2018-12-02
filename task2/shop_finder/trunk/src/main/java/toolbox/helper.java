package toolbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class helper {

    public String inputToClass(String text)
    {
        if (text.equals("<Show All>"))
            return "undefined";
        else
            return text;
    }

    public String ClassToInput(String text)
    {
        if (text.equals("undefined"))
            return "<Show All>";
        else
            return text;
    }

    public ObservableList<String> getAllCategories() {
        ObservableList<String> result_list = FXCollections.observableArrayList();

        try {

            datahandler data_h = new datahandler();
            Connection connection = data_h.connectToDB();

            ResultSet categories = data_h.getCategories(connection);




            while (categories.next()) {
                result_list.add(ClassToInput(categories.getString("category_name")));
            }


        } catch (SQLException ex1) {
        } catch (ClassNotFoundException ex2) {
        }

        return result_list.sorted();
    }

    public ObservableList<String> toSearchList(ObservableList<String> current_list) {
        ObservableList<String> search_list = FXCollections.observableArrayList(current_list);



        return search_list;
    }

    public ObservableList<String> getAllPois() {
        ObservableList<String> result_list = FXCollections.observableArrayList();

        try {

            datahandler data_h = new datahandler();
            Connection connection = data_h.connectToDB();

            ResultSet pois = data_h.getPOI(connection);

            while (pois.next()) {
                result_list.add(ClassToInput(pois.getString("name")));
            }

        } catch (SQLException ex1) {
            String error = ex1.getMessage();

        } catch (ClassNotFoundException ex2) {
            String error = ex2.getMessage();
        }
        return result_list.sorted();
    }

}
