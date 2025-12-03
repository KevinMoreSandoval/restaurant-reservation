import Model.entidades.ListaReservas;
import Model.services.EstadoMesasManager;
import View.LoginView;
import View.Panel;
import View.RegistrarReservaView;
import View.ReservasHoyView;
import View.VerificarReservaView;
import javax.swing.*;

public class main {

    // Instancias compartidas
    private static EstadoMesasManager estadosManager;
    private static ListaReservas listaReservas;

    public static void main(String[] args) {
        // Configurar el Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializar datos compartidos
        estadosManager = new EstadoMesasManager();
        listaReservas = new ListaReservas();
        // Cargar solo las reservas de hoy desde la BD
        listaReservas.cargarReservasDeHoyDesdeBD();

        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            iniciarAplicacion();
        });
    }

    /**
     * Inicializa la aplicación mostrando la vista de login
     */
    private static void iniciarAplicacion() {
        // Crear y mostrar la vista de login
        LoginView loginView = new LoginView();

        // Agregar listener para cuando el login sea exitoso
        loginView.setOnLoginSuccess(() -> {
            loginView.dispose(); // Cerrar la ventana de login
            mostrarPanelPrincipal(); // Mostrar el panel principal
        });
    }

    /**
     * Muestra el panel principal con las opciones del sistema
     */
    private static void mostrarPanelPrincipal() {
        JFrame frame = new JFrame("Sistema de Reservas - Panel Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        // Crear el panel principal
        Panel panel = new Panel();

        // Configurar los eventos de los botones
        panel.getBtnRegistrar().addActionListener(e -> {
            frame.dispose(); // Cerrar el panel principal
            mostrarRegistrarReserva(); // Abrir vista de registro
        });

        panel.getBtnVerificar().addActionListener(e -> {
            frame.dispose(); // Cerrar el panel principal
            mostrarVerificarReserva(); // Abrir vista de verificación
        });

        panel.getBtnReservasHoy().addActionListener(e -> {
            frame.dispose(); // Cerrar el panel principal
            mostrarVerificarReservaHoy(); // Abrir vista con reservas de hoy
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Muestra la vista para registrar una nueva reserva
     */
    private static void mostrarRegistrarReserva() {
        // Pasar instancias compartidas
        RegistrarReservaView registrarView = new RegistrarReservaView(estadosManager, listaReservas);

        // Agregar botón de regreso al panel principal
        JButton btnVolver = new JButton("Volver al Panel");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnVolver.setBackground(new java.awt.Color(127, 29, 29));
        btnVolver.setForeground(java.awt.Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setPreferredSize(new java.awt.Dimension(150, 35));
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver.addActionListener(e -> {
            registrarView.dispose();
            mostrarPanelPrincipal();
        });

        // Agregar el botón en la parte superior de la ventana
        JPanel topPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        topPanel.setBackground(new java.awt.Color(248, 250, 252));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(btnVolver);
        registrarView.add(topPanel, java.awt.BorderLayout.NORTH);

        registrarView.setVisible(true);
    }

    /**
     * Muestra la vista para verificar reservas existentes
     */
    private static void mostrarVerificarReserva() {
        // Pasar instancias compartidas
        VerificarReservaView verificarView = new VerificarReservaView(estadosManager, listaReservas);

        // Agregar botón de regreso al panel principal
        JButton btnVolver = new JButton("Volver al Panel");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btnVolver.setBackground(new java.awt.Color(127, 29, 29));
        btnVolver.setForeground(java.awt.Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setPreferredSize(new java.awt.Dimension(150, 35));
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver.addActionListener(e -> {
            verificarView.dispose();
            mostrarPanelPrincipal();
        });

        // Agregar el botón en la parte superior de la ventana
        JPanel topPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        topPanel.setBackground(new java.awt.Color(248, 250, 252));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(btnVolver);
        verificarView.add(topPanel, java.awt.BorderLayout.NORTH);

        // Configurar acción del botón "Reservas de Hoy" para abrir la nueva vista
        verificarView.getBtnReservasHoy().addActionListener(e -> {
            verificarView.dispose();
            mostrarVerificarReservaHoy();
        });

        verificarView.setVisible(true);
    }

    /**
     * Muestra la vista con todas las reservas (Reservas de Hoy)
     */
    private static void mostrarVerificarReservaHoy() {
        // Pasar instancias compartidas
        ReservasHoyView reservasHoyView = new ReservasHoyView(estadosManager, listaReservas);

        reservasHoyView.getBtnVolver().addActionListener(e -> {
            reservasHoyView.dispose();
            mostrarPanelPrincipal();
        });

        reservasHoyView.setVisible(true);
    }
}
