package swing;

import javax.swing.*;

public class StartPage {
    private JTable tabResult;
    private JPanel PanelMain;
    private JButton btnTestCon;


    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPage");
        frame.setContentPane(new StartPage().PanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}