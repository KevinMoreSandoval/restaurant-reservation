package View;

import Controller.ReservaController;
import Model.entidades.ListaReservas;
import Model.entidades.Reserva;
import Model.services.EstadoMesasManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Vista para verificar y consultar reservas existentes
 */
public class VerificarReservaView extends JFrame {

    private final ReservaController reservaController;

    // Componentes
    private JTextField txtBuscarDni;
    private JButton btnBuscar;
    private JButton btnMostrarTodas;
    private JButton btnVolver;
    private JTextArea txtAreaResultados;

    // Colores
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_SUCCESS = new Color(21, 87, 36);
    private final Color COLOR_INFO = new Color(37, 99, 235);
    private final Color COLOR_BG = new Color(248, 250, 252);
    private final Color COLOR_WHITE = Color.WHITE;

    public VerificarReservaView() {
        // Inicializar controlador
        EstadoMesasManager estadosManager = new EstadoMesasManager();
        ListaReservas listaReservas = new ListaReservas();
        reservaController = new ReservaController(estadosManager, listaReservas);

        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas - Verificar Reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setMinimumSize(new Dimension(800, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        // Panel superior - Título
        add(crearPanelTitulo(), BorderLayout.NORTH);

        // Panel central - Búsqueda y resultados
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PRIMARY);
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("VERIFICAR RESERVAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(COLOR_WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(22, 20, 22, 20));

        panel.add(lblTitulo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBusqueda.setBackground(COLOR_WHITE);
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));

        JLabel lblBuscar = new JLabel("Buscar por DNI:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscar.setForeground(COLOR_PRIMARY);

        txtBuscarDni = new JTextField(15);
        txtBuscarDni.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscarDni.setPreferredSize(new Dimension(200, 35));
        txtBuscarDni.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        btnBuscar = crearBoton("Buscar", COLOR_INFO);
        btnBuscar.addActionListener(e -> buscarPorDni());

        btnMostrarTodas = crearBoton("Mostrar Todas", COLOR_SUCCESS);
        btnMostrarTodas.addActionListener(e -> mostrarTodasReservas());

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarDni);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodas);

        // Panel de resultados
        JPanel panelResultados = new JPanel(new BorderLayout(0, 10));
        panelResultados.setBackground(COLOR_BG);

        JLabel lblResultados = new JLabel("Resultados:");
        lblResultados.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblResultados.setForeground(COLOR_PRIMARY);

        txtAreaResultados = new JTextArea(20, 60);
        txtAreaResultados.setEditable(false);
        txtAreaResultados.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtAreaResultados.setLineWrap(true);
        txtAreaResultados.setWrapStyleWord(true);
        txtAreaResultados.setBackground(COLOR_WHITE);
        txtAreaResultados.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(txtAreaResultados);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 2));

        panelResultados.add(lblResultados, BorderLayout.NORTH);
        panelResultados.add(scrollPane, BorderLayout.CENTER);

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(panelResultados, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(COLOR_WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
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

    private void buscarPorDni() {
        String dni = txtBuscarDni.getText().trim();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un DNI para buscar",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe tener 8 dígitos",
                    "DNI inválido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Reserva> reservas = reservaController.obtenerReservasDesdeListaEnlazada();
        List<Reserva> reservasFiltradas = reservas.stream()
                .filter(r -> r.getDniCliente().equals(dni))
                .toList();

        if (reservasFiltradas.isEmpty()) {
            txtAreaResultados.setText("No se encontraron reservas para el DNI: " + dni);
        } else {
            mostrarReservas(reservasFiltradas, "Reservas encontradas para DNI: " + dni);
        }
    }

    private void mostrarTodasReservas() {
        List<Reserva> reservas = reservaController.obtenerReservasDesdeListaEnlazada();

        if (reservas.isEmpty()) {
            txtAreaResultados.setText("No hay reservas registradas en el sistema.");
        } else {
            mostrarReservas(reservas, "Todas las reservas del sistema");
        }
    }

    private void mostrarReservas(List<Reserva> reservas, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  ").append(titulo.toUpperCase()).append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("Total de reservas: ").append(reservas.size()).append("\n\n");

        int contador = 1;
        for (Reserva r : reservas) {
            sb.append("───────────────────────────────────────────────────────────────\n");
            sb.append(String.format("RESERVA #%d\n", contador));
            sb.append("───────────────────────────────────────────────────────────────\n");
            sb.append(String.format("Cliente: %s %s\n", r.getNombreCliente(), r.getApellidoCliente()));
            sb.append(String.format("DNI: %s\n", r.getDniCliente()));
            sb.append(String.format("Fecha: %s | Hora: %s\n", r.getFecha(), r.getHora()));
            sb.append(String.format("Mesa: %d\n", r.getIdMesa()));
            sb.append(String.format("Estado: %s\n", r.getEstado()));
            sb.append("\n");
            contador++;
        }

        sb.append("═══════════════════════════════════════════════════════════════\n");
        txtAreaResultados.setText(sb.toString());
        txtAreaResultados.setCaretPosition(0);
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            VerificarReservaView vista = new VerificarReservaView();
            vista.setVisible(true);
        });
    }
}
