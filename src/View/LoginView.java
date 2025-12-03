package View;

import Model.dao.AdministradorDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    // Componentes
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Paleta de colores profesional y consistente
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);        // Azul oscuro elegante
    private final Color COLOR_PRIMARY_HOVER = new Color(51, 65, 85);  
    private final Color COLOR_ACCENT = new Color(37, 99, 235);        // Azul vibrante
    private final Color COLOR_BACKGROUND = new Color(248, 250, 252);  
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_TEXT = new Color(30, 41, 59);
    private final Color COLOR_TEXT_LIGHT = new Color(100, 116, 139);
    private final Color COLOR_BORDER = new Color(226, 232, 240);

    // Callback para cuando el login sea exitoso
    private Runnable onLoginSuccess;

    public void setOnLoginSuccess(Runnable callback) {
        this.onLoginSuccess = callback;
    }

    public LoginView() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Sistema de Reservas - Acceso");
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BACKGROUND);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel principal centrado
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setPreferredSize(new Dimension(480, 540));
        panelPrincipal.setBackground(COLOR_WHITE);
        panelPrincipal.setLayout(null);
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        // Sombra sutil
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(COLOR_BORDER, 1)));

        // Panel de encabezado
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 480, 120);
        panelHeader.setBackground(COLOR_PRIMARY);
        panelHeader.setLayout(null);

        // Título principal
        JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
        lblTitulo.setBounds(0, 30, 480, 35);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Panel de Administración");
        lblSubtitulo.setBounds(0, 70, 480, 22);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(226, 232, 240));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelHeader.add(lblTitulo);
        panelHeader.add(lblSubtitulo);

        // Mensaje de bienvenida
        JLabel lblBienvenida = new JLabel("Ingrese sus credenciales para continuar");
        lblBienvenida.setBounds(40, 145, 400, 22);
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblBienvenida.setForeground(COLOR_TEXT_LIGHT);
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);

        // Label Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(40, 190, 400, 20);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsuario.setForeground(COLOR_TEXT);

        // Campo Usuario
        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 215, 400, 45);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));

        txtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_ACCENT, 2),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDER, 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)));
            }
        });

        // Label Contraseña
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setBounds(40, 280, 400, 20);
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(COLOR_TEXT);

        // Campo Contraseña
        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 305, 400, 45);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));

        txtPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_ACCENT, 2),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDER, 1),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)));
            }
        });

        // Botón de Login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(40, 380, 400, 48);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(COLOR_WHITE);
        btnLogin.setBackground(COLOR_PRIMARY);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(COLOR_PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(COLOR_PRIMARY);
            }
        });

        btnLogin.addActionListener(e -> iniciarSesion());

        // Información adicional
        JLabel lblInfo = new JLabel("Sistema de gestión profesional de reservas");
        lblInfo.setBounds(40, 445, 400, 18);
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(COLOR_TEXT_LIGHT);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelHeader);
        panelPrincipal.add(lblBienvenida);
        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(lblPassword);
        panelPrincipal.add(txtPassword);
        panelPrincipal.add(btnLogin);
        panelPrincipal.add(lblInfo);

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
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Deshabilitar botón durante el proceso
        btnLogin.setEnabled(false);
        btnLogin.setText("Validando...");

        // Ejecutar en un hilo separado para no bloquear la UI
        new Thread(() -> {
            try {
                AdministradorDAO admin = new AdministradorDAO();
                boolean loginExitoso = admin.validarLogin(usuario, password);

                SwingUtilities.invokeLater(() -> {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar Sesión");

                    if (loginExitoso) {
                        JOptionPane.showMessageDialog(LoginView.this,
                                "Bienvenido al sistema, " + usuario + ".",
                                "Acceso Concedido",
                                JOptionPane.INFORMATION_MESSAGE);
                        
                        if (onLoginSuccess != null) {
                            onLoginSuccess.run();
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginView.this,
                                "Usuario o contraseña incorrectos.\nPor favor, verifique sus credenciales.",
                                "Error de Autenticación",
                                JOptionPane.ERROR_MESSAGE);
                        txtPassword.setText("");
                    }
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar Sesión");
                    JOptionPane.showMessageDialog(LoginView.this,
                            "Error al conectar con la base de datos.\nVerifique su conexión.",
                            "Error de Conexión",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                });
            }
        }).start();
    }
}