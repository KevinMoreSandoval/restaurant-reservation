package View;

import Controlador.administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    // Componentes
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;

    // Colores
    private final Color COLOR_PRIMARY = new Color(41, 98, 255);
    private final Color COLOR_PRIMARY_DARK = new Color(30, 70, 200);
    private final Color COLOR_BACKGROUND = new Color(240, 242, 245);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT = new Color(33, 37, 41);
    private final Color COLOR_TEXT_LIGHT = new Color(108, 117, 125);
    private final Color COLOR_ERROR = new Color(220, 53, 69);
    private final Color COLOR_BORDER = new Color(206, 212, 218);

    public LoginView() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión - Panel Administrativo");
        setSize(1000, 650);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel principal centrado
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setPreferredSize(new Dimension(500, 550));
        panelPrincipal.setBackground(COLOR_WHITE);
        panelPrincipal.setLayout(null);
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Panel de encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 500, 140);
        panelHeader.setBackground(COLOR_PRIMARY);
        panelHeader.setLayout(null);

        // Título principal
        JLabel lblTitulo = new JLabel("Sistema de Gestión");
        lblTitulo.setBounds(0, 35, 500, 35);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(COLOR_WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Restaurante - Panel Administrativo");
        lblSubtitulo.setBounds(0, 75, 500, 25);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(200, 220, 255));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Línea decorativa
        JPanel lineaDecorativa = new JPanel();
        lineaDecorativa.setBounds(150, 110, 200, 2);
        lineaDecorativa.setBackground(new Color(255, 255, 255, 100));

        panelHeader.add(lblTitulo);
        panelHeader.add(lblSubtitulo);
        panelHeader.add(lineaDecorativa);

        // Mensaje de bienvenida
        JLabel lblBienvenida = new JLabel("Inicie sesión para acceder al sistema");
        lblBienvenida.setBounds(50, 165, 400, 25);
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblBienvenida.setForeground(COLOR_TEXT_LIGHT);
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        // Label Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(50, 215, 400, 22);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(COLOR_TEXT);

        // Campo Usuario
        txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 242, 400, 50);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        txtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDER, 1),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });

        // Label Contraseña
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setBounds(50, 312, 400, 22);
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(COLOR_TEXT);

        // Campo Contraseña
        txtPassword = new JPasswordField();
        txtPassword.setBounds(50, 339, 400, 50);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        txtPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDER, 1),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });

        // Label de error
        lblError = new JLabel("");
        lblError.setBounds(50, 399, 400, 30);
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblError.setForeground(COLOR_ERROR);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setVisible(false);

        // Botón de Login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(50, 439, 400, 50);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setForeground(COLOR_WHITE);
        btnLogin.setBackground(COLOR_PRIMARY);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(COLOR_PRIMARY_DARK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(COLOR_PRIMARY);
            }
        });

        btnLogin.addActionListener(e -> iniciarSesion());

        // Link de recuperación
        JLabel lblRecuperar = new JLabel("¿Olvidó su contraseña?");
        lblRecuperar.setBounds(50, 502, 400, 20);
        lblRecuperar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRecuperar.setForeground(COLOR_PRIMARY);
        lblRecuperar.setHorizontalAlignment(SwingConstants.CENTER);
        lblRecuperar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblRecuperar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblRecuperar.setText("<html><u>¿Olvidó su contraseña?</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblRecuperar.setText("¿Olvidó su contraseña?");
            }
        });

        // Agregar componentes al panel principal
        panelPrincipal.add(panelHeader);
        panelPrincipal.add(lblBienvenida);
        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(lblPassword);
        panelPrincipal.add(txtPassword);
        panelPrincipal.add(lblError);
        panelPrincipal.add(btnLogin);
        panelPrincipal.add(lblRecuperar);

        // Agregar panel principal al frame centrado
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelPrincipal, gbc);

        // Enter para enviar
        txtPassword.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validación de campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        // Deshabilitar botón durante el proceso
        btnLogin.setEnabled(false);
        btnLogin.setText("Validando credenciales...");

        // Ejecutar en un hilo separado para no bloquear la UI
        new Thread(() -> {
            try {
                administrador admin = new administrador();
                boolean loginExitoso = admin.validaciónlogin(usuario, password);

                SwingUtilities.invokeLater(() -> {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar Sesión");

                    if (loginExitoso) {
                        JOptionPane.showMessageDialog(this,
                                "Bienvenido al sistema, " + usuario + ".",
                                "Acceso Concedido",
                                JOptionPane.INFORMATION_MESSAGE);
                        // Aquí puedes abrir la ventana principal del sistema
                        // new VentanaPrincipal().setVisible(true);
                        // dispose();
                    } else {
                        mostrarError("Usuario o contraseña incorrectos");
                        txtPassword.setText("");
                    }
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar Sesión");
                    mostrarError("Error al conectar con la base de datos");
                    ex.printStackTrace();
                });
            }
        }).start();
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);

        // Ocultar el error después de 5 segundos
        Timer timer = new Timer(5000, e -> lblError.setVisible(false));
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginView());
    }
}
