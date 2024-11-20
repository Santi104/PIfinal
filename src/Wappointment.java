import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Wappointment extends JFrame {
    private JPanel panelE;
    private JButton backToStartButton;
    private JLabel WelcomeText;
    private JTextField Name;
    private JTextField Phone;
    private JTextField Date;
    private JTextField Time;
    private JButton createButton;
    private JTextField petName;
    private JTextField typePet;
    private JTextField petDate;
    private JTextField breedField;
    private JTextField reasonField;
    private JTextField adressField;
    private JTextField lastNameField;
    private JTextField emailField;
    private Connection conexion;
    Statement st;
    ResultSet rs;

    public Wappointment(String nombreUsuario) {
        WelcomeText.setText("¡Welcome, " + nombreUsuario + "!");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataInsert();
            }
        });
        backToStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void openWindow(String nameUser) {
        Wappointment window1 = new Wappointment(nameUser);
        window1.setContentPane(window1.panelE);
        window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window1.setVisible(true);
        window1.pack();
    }

    private void conect() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectoIntegrador", "root", "Santi104");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
    }

    private void dataInsert() {
        conect(); // Tu método para conectar a la base de datos.

        String name = Name.getText();
        String lastName = lastNameField.getText();
        String phone = Phone.getText();
        String petsName = petName.getText();
        String address = adressField.getText();
        String email = emailField.getText();
        String date = Date.getText();
        String time = Time.getText();
        String reason = reasonField.getText();
        String type = typePet.getText();
        String datePet = petDate.getText();
        String petBreed = breedField.getText();

        String queryClient = "INSERT INTO cliente (nombre, apellido, nombre_mascota, telefono, direccion, correo) VALUES (?, ?, ?, ?, ?, ?)";
        String queryPet = "INSERT INTO mascota (nombre, tipo, fecha_nacimiento, raza) VALUES (?, ?, ?, ?)";
        String queryAppointment = "INSERT INTO cita (nombre, num_telefono, cod_cliente, cod_mascota, fecha, hora, motivo, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryLastId = "SELECT LAST_INSERT_ID()";

        try {
            // Inserta en cliente
            PreparedStatement psClient = conexion.prepareStatement(queryClient);
            psClient.setString(1, name);
            psClient.setString(2, lastName);
            psClient.setString(3, petsName);
            psClient.setString(4, phone);
            psClient.setString(5, address);
            psClient.setString(6, email);
            psClient.executeUpdate();

            // Obtén el cod_cliente generado
            st = conexion.createStatement();
            rs = st.executeQuery(queryLastId);
            int codCliente = 0;
            if (rs.next()) {
                codCliente = rs.getInt(1);
            } else {
                JOptionPane.showMessageDialog(null, "Error al obtener el ID del cliente.");
            }

            // Inserta en mascota
            PreparedStatement psPet = conexion.prepareStatement(queryPet);
            psPet.setString(1, petsName);
            psPet.setString(2, type);
            psPet.setString(3, datePet);
            psPet.setString(4, petBreed);
            psPet.executeUpdate();

            // Obtén el cod_mascota generado
            st = conexion.createStatement();
            rs = st.executeQuery(queryLastId);
            int codMascota = 0;
            if (rs.next()) {
                codMascota = rs.getInt(1);
            } else {
                JOptionPane.showMessageDialog(null, "Error al obtener el ID de la mascota.");
            }

            // Inserta en cita utilizando cod_cliente y cod_mascota
            PreparedStatement psAppointment = conexion.prepareStatement(queryAppointment);
            psAppointment.setString(1, name);
            psAppointment.setString(2, phone);
            psAppointment.setInt(3, codCliente); // Referencia al cliente
            psAppointment.setInt(4, codMascota); // Referencia a la mascota
            psAppointment.setString(5, date);
            psAppointment.setString(6, time);
            psAppointment.setString(7, reason);
            psAppointment.setString(8, "Pendiente"); // Estado por defecto
            psAppointment.executeUpdate();

            JOptionPane.showMessageDialog(null, "Solicitud de cita creada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la cita: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión con la base de datos: " + ex.getMessage());
            }
        }
    }

}
