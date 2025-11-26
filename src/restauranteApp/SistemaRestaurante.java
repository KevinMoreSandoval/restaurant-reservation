package restauranteApp;
import javax.swing.*;
import java.time.*;
import java.util.Date;

public class SistemaRestaurante extends JFrame {

    // --- Elementos Login ---
    JTextField txtUsuario = new JTextField("admin");
    JPasswordField txtPass = new JPasswordField("1234");
    JButton btnLogin = new JButton("Ingresar");

    // --- Elementos Reserva ---
    JTextField txtNombre = new JTextField();
    JTextField txtApellido = new JTextField();
    JTextField txtDni = new JTextField();
    JComboBox<String> cbMesa = new JComboBox<>(new String[]{"1", "2", "3", "4"});
    JComboBox<String> cbHora = new JComboBox<>(new String[]{"10:00", "11:00", "18:00", "19:00", "20:00"});
    JSpinner spFecha = new JSpinner(new SpinnerDateModel());
    JButton btnReservar = new JButton("Reservar");

    ReservaDAO dao = new ReservaDAO();

    public SistemaRestaurante() {
        mostrarLogin();
    }

    private void mostrarLogin() {
        setTitle("Login Administrador");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Usuario:")).setBounds(20, 20, 80, 25);
        txtUsuario.setBounds(100, 20, 150, 25); add(txtUsuario);

        add(new JLabel("Contraseña:")).setBounds(20, 60, 80, 25);
        txtPass.setBounds(100, 60, 150, 25); add(txtPass);

        btnLogin.setBounds(100, 100, 100, 30); add(btnLogin);

        btnLogin.addActionListener(e -> {
            if (txtUsuario.getText().equals("admin") && 
                String.valueOf(txtPass.getPassword()).equals("1234")) {
                mostrarReservas();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Credenciales incorrectas");
            }
        });
    }

    private void mostrarReservas() {
        getContentPane().removeAll();
        setTitle("Sistema de Reservas");
        setSize(400, 330);
        setLayout(null);
        repaint();

        add(new JLabel("Nombre:")).setBounds(20, 20, 80, 25);
        txtNombre.setBounds(100, 20, 200, 25); add(txtNombre);

        add(new JLabel("Apellido:")).setBounds(20, 60, 80, 25);
        txtApellido.setBounds(100, 60, 200, 25); add(txtApellido);

        add(new JLabel("DNI:")).setBounds(20, 100, 80, 25);
        txtDni.setBounds(100, 100, 200, 25); add(txtDni);

        add(new JLabel("Mesa:")).setBounds(20, 140, 80, 25);
        cbMesa.setBounds(100, 140, 200, 25); add(cbMesa);

        add(new JLabel("Hora:")).setBounds(20, 180, 80, 25);
        cbHora.setBounds(100, 180, 200, 25); add(cbHora);

        add(new JLabel("Fecha:")).setBounds(20, 220, 80, 25);
        spFecha.setBounds(100, 220, 200, 25); add(spFecha);

        btnReservar.setBounds(130, 260, 100, 30); add(btnReservar);

        btnReservar.addActionListener(e -> registrar());
        setLocationRelativeTo(null);
    }

    private void registrar() {
        try {
            if (txtDni.getText().length() != 8)
                throw new Exception("DNI debe tener 8 dígitos");

            int mesa = Integer.parseInt(cbMesa.getSelectedItem().toString());
            LocalDate fecha = Instant.ofEpochMilli(((Date) spFecha.getValue()).getTime())
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime hora = LocalTime.parse(cbHora.getSelectedItem().toString());

            Reserva r = new Reserva(txtNombre.getText(), txtApellido.getText(), txtDni.getText(), fecha, hora, mesa);

            if (!dao.verificarDisponibilidad(mesa, java.sql.Date.valueOf(fecha), java.sql.Time.valueOf(hora))) {
                JOptionPane.showMessageDialog(this, "⚠ Mesa no disponible en ese horario");
                return;
            }

            if (dao.registrarReserva(r)) {
                JOptionPane.showMessageDialog(this, "✔ Reserva registrada");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new SistemaRestaurante().setVisible(true);
    }
}
