package swing;

import toolbox.datahandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class StartPage {
    private JTable tabResult;
    private JPanel PanelMain;
    private JButton btnTestCon;


    public StartPage() {
        btnTestCon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                // Database Connection
                datahandler dh = new datahandler();
                try {
                    Connection connection = dh.connectToDB();


                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPage");
        frame.setContentPane(new StartPage().PanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        String Colums[] = {"name", "ID"};

        String data[][] = {{"alex", "1"},{"Daniel", "2"}};

        JTable table = new JTable(data, Colums);
        table.setPreferredScrollableViewportSize( new Dimension(500, 50));
        //table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
    }

}