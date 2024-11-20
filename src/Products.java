import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Products extends JFrame {
    private JButton addToCartButton;
    private JButton addToCartButton1;
    private JButton addToCartButton2;
    private JButton addToCartButton3;
    private JButton addToCartButton4;
    private JButton addToCartButton5;
    private JButton buyButton;
    private JPanel productPanel;
    private JButton button1;
    Connection conexion;
    Statement st;
    ResultSet rs;
    PreparedStatement ps;


    public Products() {
        setContentPane(productPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 1;

            }
        });
        addToCartButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 2;
            }
        });
        addToCartButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 3;
            }
        });
        addToCartButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 4;
            }
        });
        addToCartButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 5;
            }
        });
        addToCartButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 6;
            }
        });
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
