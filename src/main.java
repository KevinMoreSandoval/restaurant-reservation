import View.LoginView;
import View.Panel;
import View.RegistrarReservaView;
import View.VerificarReservaView;
import javax.swing.*;

/**
 * Clase principal del Sistema de Reservas
 * Punto de entrada de la aplicación
 */
public class main {

    public static void main(String[] args) {
        // Configurar el Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Muestra la vista para registrar una nueva reserva
     */
    private static void mostrarRegistrarReserva() {
        RegistrarReservaView registrarView = new RegistrarReservaView();

        // Agregar botón de regreso al panel principal
        JButton btnVolver = new JButton("← Volver al Panel");
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
        VerificarReservaView verificarView = new VerificarReservaView();

        // Agregar botón de regreso al panel principal
        JButton btnVolver = new JButton("← Volver al Panel");
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

        verificarView.setVisible(true);
    }
}
