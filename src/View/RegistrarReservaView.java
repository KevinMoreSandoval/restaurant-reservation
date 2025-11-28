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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegistrarReservaView extends JFrame {

    private final ReservaController reservaController;
    private final MesaController mesaController;
    private final ListaReservas listaReservas;

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
    private JTextArea txtAreaResultado;

    // Colores del tema
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_SUCCESS = new Color(21, 87, 36);
    private final Color COLOR_DANGER = new Color(127, 29, 29);
    private final Color COLOR_INFO = new Color(37, 99, 235);
    private final Color COLOR_BG = new Color(248, 250, 252);
    private final Color COLOR_WHITE = Color.WHITE;

    public RegistrarReservaView() {
        // Inicializar controladores
        EstadoMesasManager estadosManager = new EstadoMesasManager();
        listaReservas = new ListaReservas();
        reservaController = new ReservaController(estadosManager, listaReservas);
        mesaController = new MesaController();

        initComponents();
        setupFrame();
        cargarMesas();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1100, 750));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);

        // Layout principal con scroll
        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(COLOR_BG);

        // Panel superior - Título
        contentPanel.add(crearPanelTitulo(), BorderLayout.NORTH);

        // Panel central - Formulario y Resultado juntos
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(COLOR_BG);
        centerPanel.add(crearPanelFormulario(), BorderLayout.CENTER);
        centerPanel.add(crearPanelResultado(), BorderLayout.SOUTH);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Agregar scroll
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PRIMARY);
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("REGISTRO DE RESERVAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(COLOR_WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(22, 20, 22, 20));

        panel.add(lblTitulo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(COLOR_BG);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 60, 20, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 0.0;

        // Nombre del cliente
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

        // Fecha con JDateChooser
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Fecha de Reserva:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(0, 38));
        dateChooser.setMinSelectableDate(new Date());
        dateChooser.getJCalendar().setTodayButtonVisible(true);
        dateChooser.getJCalendar().setWeekOfYearVisible(false);
        dateChooser.setBackground(COLOR_WHITE);
        dateChooser.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 1));
        dateChooser.getDateEditor().getUiComponent().setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelPrincipal.add(dateChooser, gbc);

        // Horario
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        panelPrincipal.add(crearLabel("Horario:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] horarios = {
                "10:00", "12:00", "14:00", "16:00", "18:00", "20:00"

        };
        cmbHorario = new JComboBox<>(horarios);
        cmbHorario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbHorario.setPreferredSize(new Dimension(0, 38));
        panelPrincipal.add(cmbHorario, gbc);

        // Número de personas
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelPrincipal.add(crearLabel("Número de Personas:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel panelPersonas = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panelPersonas.setBackground(COLOR_BG);

        SpinnerNumberModel spinnerModelPersonas = new SpinnerNumberModel(2, 1, 20, 1);
        spnNumPersonas = new JSpinner(spinnerModelPersonas);
        spnNumPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spnNumPersonas.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        spnNumPersonas.setPreferredSize(new Dimension(100, 38));

        btnFiltrarMesas = crearBoton("Filtrar Mesas", COLOR_INFO);
        btnFiltrarMesas.setPreferredSize(new Dimension(150, 38));
        btnFiltrarMesas.addActionListener(e -> filtrarMesasPorCapacidad());

        panelPersonas.add(spnNumPersonas);
        panelPersonas.add(btnFiltrarMesas);
        panelPrincipal.add(panelPersonas, gbc);

        // Selección de mesa
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelPrincipal.add(crearLabel("Seleccionar Mesa:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        cmbMesa = new JComboBox<>();
        cmbMesa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbMesa.setPreferredSize(new Dimension(0, 38));
        panelPrincipal.add(cmbMesa, gbc);

        // Panel de botones
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 25, 10);
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

        JButton btnVerLista = crearBoton("Ver Lista Enlazada", COLOR_INFO);
        btnVerLista.addActionListener(e -> mostrarReservasEnLista());

        panel.add(btnRegistrar);
        panel.add(btnLimpiar);
        panel.add(btnVerLista);

        return panel;
    }

    private JPanel crearPanelResultado() {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(COLOR_BG);
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(0, 60, 30, 60));

        // Panel con borde para mensajes
        JPanel panelMensajes = new JPanel(new BorderLayout(8, 8));
        panelMensajes.setBackground(COLOR_WHITE);
        panelMensajes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)));

        JLabel lblResultado = new JLabel("Mensajes del Sistema:");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResultado.setForeground(COLOR_PRIMARY);
        lblResultado.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        txtAreaResultado = new JTextArea(3, 50);
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtAreaResultado.setLineWrap(true);
        txtAreaResultado.setWrapStyleWord(true);
        txtAreaResultado.setBackground(COLOR_WHITE);
        txtAreaResultado.setBorder(null);

        JScrollPane scroll = new JScrollPane(txtAreaResultado);
        scroll.setBorder(null);
        scroll.setBackground(COLOR_WHITE);
        scroll.setPreferredSize(new Dimension(0, 80));

        panelMensajes.add(lblResultado, BorderLayout.NORTH);
        panelMensajes.add(scroll, BorderLayout.CENTER);

        panelContenedor.add(panelMensajes, BorderLayout.CENTER);

        return panelContenedor;
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JTextField crearTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(0, 38));
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
        button.setPreferredSize(new Dimension(190, 42));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
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
            mostrarMensaje("No hay mesas disponibles en la base de datos", false);
        } else {
            for (Mesa mesa : mesas) {
                cmbMesa.addItem(mesa);
            }
            mostrarMensaje("Se cargaron " + mesas.size() + " mesas desde la base de datos", true);
        }
    }

    private void filtrarMesasPorCapacidad() {
        int numPersonas = (int) spnNumPersonas.getValue();
        cmbMesa.removeAllItems();

        List<Mesa> mesasFiltradas = mesaController.obtenerMesasPorCapacidad(numPersonas);

        if (mesasFiltradas.isEmpty()) {
            mostrarMensaje("No hay mesas disponibles para " + numPersonas + " personas", false);
        } else {
            for (Mesa mesa : mesasFiltradas) {
                cmbMesa.addItem(mesa);
            }
            mostrarMensaje("Se encontraron " + mesasFiltradas.size() + " mesa(s) para " + numPersonas + " personas",
                    true);
        }
    }

    private void registrarReserva() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String dni = txtDni.getText().trim();

        // Obtener fecha del JDateChooser
        Date fechaSeleccionada = dateChooser.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(fechaSeleccionada);

        String hora = (String) cmbHorario.getSelectedItem();

        Mesa mesaSeleccionada = (Mesa) cmbMesa.getSelectedItem();
        if (mesaSeleccionada == null) {
            mostrarMensaje("Debe seleccionar una mesa", false);
            return;
        }

        int idMesa = mesaSeleccionada.getIdMesa();
        int numPersonas = (int) spnNumPersonas.getValue();

        // Verificar que la mesa tenga capacidad suficiente
        if (mesaSeleccionada.getCapacidad() < numPersonas) {
            mostrarMensaje("La mesa seleccionada tiene capacidad para " + mesaSeleccionada.getCapacidad()
                    + " personas, pero necesita " + numPersonas + " personas", false);
            return;
        }

        // Verificar disponibilidad
        if (!reservaController.mesaDisponible(fecha, idMesa, hora)) {
            mostrarMensaje("La mesa " + idMesa + " no está disponible para "
                    + fecha + " a las " + hora, false);
            return;
        }

        // Crear y registrar reserva
        Reserva reserva = new Reserva(nombre, apellido, dni, fecha, hora, idMesa, "CONFIRMADA");

        if (reservaController.registrarReserva(reserva)) {
            int totalEnLista = reservaController.contarReservasEnLista();
            mostrarMensaje("✅ Reserva registrada exitosamente\n"
                    + "Cliente: " + nombre + " " + apellido + " (DNI: " + dni + ")\n"
                    + "Mesa: " + idMesa + " (Capacidad: " + mesaSeleccionada.getCapacidad() + ") | "
                    + "Personas: " + numPersonas + "\n"
                    + "Fecha: " + fecha + " | Hora: " + hora + "\n"
                    + "📊 Total de reservas en lista enlazada: " + totalEnLista, true);
            limpiarFormulario();
        } else {
            mostrarMensaje("Error al registrar la reserva. Intente nuevamente.", false);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre del cliente es obligatorio", false);
            txtNombre.requestFocus();
            return false;
        }

        if (txtApellido.getText().trim().isEmpty()) {
            mostrarMensaje("El apellido del cliente es obligatorio", false);
            txtApellido.requestFocus();
            return false;
        }

        if (txtDni.getText().trim().isEmpty()) {
            mostrarMensaje("El DNI del cliente es obligatorio", false);
            txtDni.requestFocus();
            return false;
        }

        // Validar formato de DNI (8 dígitos)
        if (!txtDni.getText().matches("\\d{8}")) {
            mostrarMensaje("El DNI debe tener 8 dígitos", false);
            txtDni.requestFocus();
            return false;
        }

        if (dateChooser.getDate() == null) {
            mostrarMensaje("Debe seleccionar una fecha de reserva", false);
            return false;
        }

        if (cmbMesa.getItemCount() == 0) {
            mostrarMensaje("No hay mesas disponibles. Verifique la conexión a la base de datos", false);
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

    private void mostrarMensaje(String mensaje, boolean exito) {
        txtAreaResultado.setText(mensaje);
        txtAreaResultado.setForeground(exito ? COLOR_SUCCESS : COLOR_DANGER);
    }

    private void setupFrame() {
        // Hacer la ventana responsiva
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    private void mostrarReservasEnLista() {
        List<Reserva> reservas = reservaController.obtenerReservasDesdeListaEnlazada();

        if (reservas.isEmpty()) {
            mostrarMensaje("No hay reservas en la lista enlazada.", false);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RESERVAS EN LISTA ENLAZADA ===\n");
        sb.append("Total: ").append(reservas.size()).append(" reserva(s)\n\n");

        int contador = 1;
        for (Reserva r : reservas) {
            sb.append(contador).append(". ")
                    .append(r.getNombreCliente()).append(" ").append(r.getApellidoCliente())
                    .append(" | DNI: ").append(r.getDniCliente())
                    .append("\n   Fecha: ").append(r.getFecha())
                    .append(" | Hora: ").append(r.getHora())
                    .append(" | Mesa: ").append(r.getIdMesa())
                    .append(" | Estado: ").append(r.getEstado())
                    .append("\n");
            contador++;
        }

        mostrarMensaje(sb.toString(), true);

        // También mostrar en consola
        reservaController.mostrarReservasEnLista();
    }

    // Método main para probar la vista
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RegistrarReservaView vista = new RegistrarReservaView();
            vista.setVisible(true);
        });
    }
}
