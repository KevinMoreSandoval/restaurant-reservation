package View;

import Controller.MesaController;
import Controller.ReservaController;
import Model.entidades.ListaReservas;
import Model.entidades.Mesa;
import Model.entidades.Reserva;
import Model.services.EstadoMesasManager;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class RegistrarReservaView extends JFrame {

    private final ReservaController reservaController;
    private final MesaController mesaController;

    // Componentes
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDni;
    private JDateChooser dateChooser;
    private JSpinner spnNumPersonas;
    private JComboBox<String> cmbHorario;
    private JComboBox<Mesa> cmbMesa;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnFiltrarMesas;

    // Paleta de colores consistente
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_SUCCESS = new Color(16, 185, 129);
    private final Color COLOR_DANGER = new Color(220, 38, 38);
    private final Color COLOR_INFO = new Color(37, 99, 235);
    private final Color COLOR_BG = new Color(248, 250, 252);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT = new Color(52, 73, 94);

    public RegistrarReservaView(EstadoMesasManager estadosManager, ListaReservas listaReservas) {
        reservaController = new ReservaController(estadosManager, listaReservas);
        mesaController = new MesaController();
        initComponents();
        cargarMesas();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(COLOR_BG);

        contentPanel.add(crearPanelTitulo(), BorderLayout.NORTH);
        contentPanel.add(crearPanelFormulario(), BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PRIMARY);
        panel.setPreferredSize(new Dimension(0, 75));
        panel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("REGISTRO DE RESERVAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_WHITE);

        panel.add(lblTitulo, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(COLOR_BG);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Nombre del Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtNombre = crearTextField();
        panelPrincipal.add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Apellido del Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtApellido = crearTextField();
        panelPrincipal.add(txtApellido, gbc);

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("DNI del Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDni = crearTextField();
        txtDni.setToolTipText("Ingrese 8 dígitos");
        panelPrincipal.add(txtDni, gbc);

        // Fecha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Fecha de Reserva:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(0, 40));
        dateChooser.setMinSelectableDate(new Date());
        dateChooser.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 1));
        dateChooser.addPropertyChangeListener("date", evt -> actualizarHorarios());
        panelPrincipal.add(dateChooser, gbc);

        // Horario
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Horario:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] horarios = { "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "24:00" };
        cmbHorario = new JComboBox<>(horarios);
        cmbHorario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbHorario.setPreferredSize(new Dimension(0, 40));
        panelPrincipal.add(cmbHorario, gbc);

        // Número de personas
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Número de Personas:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JPanel panelPersonas = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panelPersonas.setBackground(COLOR_BG);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 1, 20, 1);
        spnNumPersonas = new JSpinner(spinnerModel);
        spnNumPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spnNumPersonas.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        spnNumPersonas.setPreferredSize(new Dimension(100, 40));

        btnFiltrarMesas = crearBoton("Filtrar Mesas", COLOR_INFO);
        btnFiltrarMesas.setPreferredSize(new Dimension(150, 40));
        btnFiltrarMesas.addActionListener(e -> filtrarMesasPorCapacidad());

        panelPersonas.add(spnNumPersonas);
        panelPersonas.add(btnFiltrarMesas);
        panelPrincipal.add(panelPersonas, gbc);

        // Mesa
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Seleccionar Mesa:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cmbMesa = new JComboBox<>();
        cmbMesa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbMesa.setPreferredSize(new Dimension(0, 40));
        panelPrincipal.add(cmbMesa, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 20, 10);
        panelPrincipal.add(crearPanelBotones(), gbc);

        return panelPrincipal;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setBackground(COLOR_BG);

        btnRegistrar = crearBoton("Registrar Reserva", COLOR_SUCCESS);
        btnRegistrar.addActionListener(e -> registrarReserva());

        btnLimpiar = crearBoton("Limpiar Formulario", COLOR_DANGER);
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panel.add(btnRegistrar);
        panel.add(btnLimpiar);
        return panel;
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(COLOR_TEXT);
        return label;
    }

    private JTextField crearTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(0, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return textField;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(COLOR_WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(190, 44));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void cargarMesas() {
        cmbMesa.removeAllItems();
        List<Mesa> mesas = mesaController.obtenerMesas();

        if (mesas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay mesas disponibles en la base de datos",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Mesa mesa : mesas) {
                cmbMesa.addItem(mesa);
            }
        }
    }

    private void filtrarMesasPorCapacidad() {
        int numPersonas = (int) spnNumPersonas.getValue();
        cmbMesa.removeAllItems();

        List<Mesa> mesasFiltradas = mesaController.obtenerMesasPorCapacidad(numPersonas);

        if (mesasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay mesas disponibles para " + numPersonas + " personas",
                    "Sin Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Mesa mesa : mesasFiltradas) {
                cmbMesa.addItem(mesa);
            }
            JOptionPane.showMessageDialog(this,
                    "Se encontraron " + mesasFiltradas.size() + " mesa(s) para " + numPersonas + " personas",
                    "Filtrado Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void registrarReserva() {
        if (!validarCampos())
            return;

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();

        Date fechaSeleccionada = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(fechaSeleccionada);

        String hora = (String) cmbHorario.getSelectedItem();
        Mesa mesaSeleccionada = (Mesa) cmbMesa.getSelectedItem();

        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una mesa",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMesa = mesaSeleccionada.getIdMesa();
        int numPersonas = (int) spnNumPersonas.getValue();

        if (mesaSeleccionada.getCapacidad() < numPersonas) {
            JOptionPane.showMessageDialog(this,
                    "La mesa seleccionada tiene capacidad para " + mesaSeleccionada.getCapacidad()
                            + " personas, pero necesita " + numPersonas + " personas",
                    "Capacidad Insuficiente",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!reservaController.mesaDisponible(fecha, idMesa, hora)) {
            JOptionPane.showMessageDialog(this,
                    "La mesa " + idMesa + " no está disponible para " + fecha + " a las " + hora + "\n" +
                    "Por favor seleccione otra mesa u horario.",
                    "Mesa No Disponible",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Reserva reserva = new Reserva(nombre, apellido, dni, fecha, hora, idMesa, "PENDIENTE");

        if (reservaController.registrarReserva(reserva)) {
            int totalEnLista = reservaController.contarReservasEnLista();
            JOptionPane.showMessageDialog(this,
                    "Reserva registrada exitosamente\n\n" +
                            "Cliente: " + nombre + " " + apellido + " (DNI: " + dni + ")\n" +
                            "Mesa: " + idMesa + " (Capacidad: " + mesaSeleccionada.getCapacidad() + ") | " +
                            "Personas: " + numPersonas + "\n" +
                            "Fecha: " + fecha + " | Hora: " + hora + "\n\n" +
                            "Total de reservas en lista: " + totalEnLista,
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar la reserva. Intente nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del cliente es obligatorio", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El apellido del cliente es obligatorio", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            txtApellido.requestFocus();
            return false;
        }
        if (txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El DNI del cliente es obligatorio", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        if (!txtDni.getText().matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            txtDni.requestFocus();
            return false;
        }
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de reserva", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cmbMesa.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay mesas disponibles", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        dateChooser.setDate(null);
        cmbHorario.setSelectedIndex(0);
        spnNumPersonas.setValue(2);
        cargarMesas();
        txtNombre.requestFocus();
    }

    /**
     * Actualizar horarios disponibles según la fecha seleccionada.
     * Si es hoy, solo muestra horarios posteriores a la hora actual.
     */
    private void actualizarHorarios() {
        Date fechaSeleccionada = dateChooser.getDate();
        if (fechaSeleccionada == null) {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = sdf.format(fechaSeleccionada);
        LocalDate fechaLocal = LocalDate.parse(fechaString);
        LocalDate hoy = LocalDate.now();

        cmbHorario.removeAllItems();
        String[] horariosCompletos = { "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00" };

        if (fechaLocal.equals(hoy)) {
            // Si es hoy, filtrar horarios según la hora local actual
            LocalTime horaActual = LocalTime.now();
            for (String horario : horariosCompletos) {
                LocalTime horaReserva = LocalTime.parse(horario);
                // Solo mostrar horarios que sean al menos 1 hora después de la hora actual
                if (horaReserva.isAfter(horaActual.plusHours(1))) {
                    cmbHorario.addItem(horario);
                }
            }

            if (cmbHorario.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No hay horarios disponibles para hoy. Por favor seleccione otra fecha.",
                        "Sin Horarios Disponibles",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // Si es otra fecha, mostrar todos los horarios
            for (String horario : horariosCompletos) {
                cmbHorario.addItem(horario);
            }
        }
    }
}