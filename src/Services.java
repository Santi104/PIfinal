import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Services extends JFrame {
    private JLabel Title;
    private JPanel panelS;
    private JButton closeButton;
    private JButton petShopButton;

    public Services() {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        petShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Products product = new Products();
            }
        });
    }

    public void openServices() {
        Services services1 = new Services();
        services1.setContentPane(services1.panelS);
        services1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        services1.setVisible(true);
        services1.pack();
    }
}
