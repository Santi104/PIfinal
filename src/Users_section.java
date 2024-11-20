import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Users_section extends JFrame {
    private JTable tabla;
    private JPanel userPanel;
    private JTextField IdField;
    private JButton deleteButton;
    private JButton button1;
    Connection conexion;
    DefaultListModel modLista= new DefaultListModel<>();
    String[] columnas = {"id","nombre","apellido","telefono","correo","rol"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null , columnas);
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    public Users_section() {

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    void conectar() {
        try {
            conexion= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");

        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void openUsersSection() {
        setContentPane(userPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        try {
            consultar();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void consultar() throws SQLException {
        conectar();
        modTabla.setRowCount(0);  // Limpiar filas antes de cargar
        tabla.setModel(modTabla);
        st = conexion.createStatement();
        rs = st.executeQuery("select * from Usuarios");  // Asegúrate del nombre correcto de la tabla
        while (rs.next()) {
            registros[0] = rs.getString("Id_usuario");
            registros[1] = rs.getString("nombre");
            registros[2] = rs.getString("apellido");
            registros[3] = rs.getString("num_telefono");
            registros[4] = rs.getString("correo");
            registros[5] = rs.getString("tipo");


            modTabla.addRow(registros);
        }
    }



    void deleteSection() throws SQLException {
        ps = conexion.prepareStatement("Delete from Usuarios where id_usuario=?");
        ps.setString(1, IdField.getText());

        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null,"Remove User");
            consultar();
        }
    }

}
