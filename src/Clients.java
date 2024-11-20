import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Clients extends JFrame {
    private JButton deleteButton;
    private JTextField idField;
    private JTable tabla;
    private JButton button1;
    private JPanel clientPanel;
    Connection conexion;
    DefaultListModel modLista= new DefaultListModel<>();
    String[] columnas = {"id","nombre","apellido","nombre_mascota","direccion","telefono","correo"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null , columnas);
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    public Clients() {

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

    public void openClientSection() {
        setContentPane(clientPanel);
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
        rs = st.executeQuery("select * from Cliente");  // Asegúrate del nombre correcto de la tabla
        while (rs.next()) {
            registros[0] = rs.getString("cod_cliente");
            registros[1] = rs.getString("nombre");
            registros[2] = rs.getString("apellido");
            registros[3] = rs.getString("nombre_mascota");
            registros[4] = rs.getString("direccion");
            registros[5] = rs.getString("telefono");
            registros[6] = rs.getString("correo");


            modTabla.addRow(registros);
        }
    }



    void deleteSection() throws SQLException {
        ps = conexion.prepareStatement("Delete from Cliente where cod_cliente=?");
        ps.setString(1, idField.getText());

        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null,"Remove User");
            consultar();
        }
    }

}

