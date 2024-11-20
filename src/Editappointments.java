import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Editappointments extends  JFrame{
    private JButton consultButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField dateField;
    private JTextField timeField;
    private JList lista;
    private JTable tabla;
    private JTextField statusField;
    private JPanel eAppointments;
    private JTextField reasonField;
    private JTextField IdField;
    private JButton button1;
    private JTextField petField;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;
    Connection conexion;
    DefaultListModel modLista = new DefaultListModel<>();
    String columnas[] = {"id","name", "petName","phone","date","time","reason","status"};
    String filas[]= new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null, columnas);

    public Editappointments() {

        consultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consultar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateSection();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Ocurrio un error: "+ ex.getMessage());
                }
            }
        });
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

    void openEditAppointments() {
        setContentPane(eAppointments);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    void conectar() {
        try {
        conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");

        }catch (SQLException e) {
            throw new RuntimeException();
        }

    }


    void consultar() throws SQLException {
        conectar();
        modTabla.setRowCount(0);
        tabla.setModel(modTabla);

        String sql = "SELECT cita.cod_cita, cliente.nombre, cliente.nombre_mascota, cliente.telefono, " +
                "cita.fecha, cita.hora, cita.motivo, cita.estado " +
                "FROM cita " +
                "INNER JOIN cliente ON cita.cod_cliente = cliente.cod_cliente";

        PreparedStatement psSql = conexion.prepareStatement(sql);
        rs = psSql.executeQuery();

        while (rs.next()) {
            filas[0] = rs.getString("cod_cita");
            filas[1] = rs.getString("nombre");
            filas[2] = rs.getString("nombre_mascota");
            filas[3] = rs.getString("telefono");
            filas[4] = rs.getString("fecha");
            filas[5] = rs.getString("hora");
            filas[6] = rs.getString("motivo");
            filas[7] = rs.getString("estado");
            modTabla.addRow(filas);
        }
    }



    void updateSection() throws SQLException {
        try {
            conectar();

            // Primera consulta: Actualizar cliente
            ps = conexion.prepareStatement("update cliente set nombre = ?, nombre_mascota = ?, telefono = ? " +
                    "where cod_cliente = (select cod_cliente from cita where cod_cita = ?)");
            ps.setString(1, nameField.getText());
            ps.setString(2, petField.getText());
            ps.setString(3, phoneField.getText());
            ps.setInt(4, Integer.parseInt(IdField.getText()));

            ps.executeUpdate();

            // Segunda consulta: Actualizar cita
            ps = conexion.prepareStatement("update cita set fecha = ?, hora = ?, motivo = ?, estado = ? where cod_cita = ?");
            ps.setString(1, dateField.getText());
            ps.setString(2, timeField.getText());
            ps.setString(3, reasonField.getText());
            ps.setString(4, statusField.getText());
            ps.setInt(5, Integer.parseInt(IdField.getText()));

            if (ps.executeUpdate() > 0) {
                lista.setModel(modLista);
                modLista.removeAllElements();
                modLista.addElement("Cita actualizada");

                // Limpiar campos
                IdField.setText("");
                nameField.setText("");
                petField.setText("");
                phoneField.setText("");
                dateField.setText("");
                timeField.setText("");
                reasonField.setText("");
                statusField.setText("");
                consultar();
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejar errores adecuadamente
        } finally {
            // Cerrar recursos
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void deleteSection() throws SQLException {
        ps = conexion.prepareStatement("Delete from cita where cod_cita=?");
        ps.setString(1, IdField.getText());

        if (ps.executeUpdate() > 0) {
            lista.setModel(modLista);
            modLista.removeAllElements();
            modLista.addElement("Remove Element");

            nameField.setText("");
            consultar();
        }
    }

}
