package View;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private JButton btnRegistrar;
    private JButton btnVerificar;
    private JButton btnReservasHoy;

    // Paleta de colores consistente
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_TEXT_LIGHT = new Color(71, 85, 105);
    private final Color COLOR_BORDER = new Color(226, 232, 240);
    private final Color COLOR_CARD_BG = Color.WHITE;

    // Colores para botones
    private final Color COLOR_BTN_1 = new Color(37, 99, 235);
    private final Color COLOR_BTN_1_HOVER = new Color(29, 78, 216);
    private final Color COLOR_BTN_2 = new Color(16, 185, 129);
    private final Color COLOR_BTN_2_HOVER = new Color(5, 150, 105);
    private final Color COLOR_BTN_3 = new Color(220, 38, 38);
    private final Color COLOR_BTN_3_HOVER = new Color(185, 28, 28);

    public Panel() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Panel de encabezado
        JPanel panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BoxLayout(panelEncabezado, BoxLayout.Y_AXIS));
        panelEncabezado.setOpaque(false);

        JLabel titulo = new JLabel("Sistema de Reservas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(COLOR_PRIMARY);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Gestione sus reservas de manera profesional y eficiente");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(COLOR_TEXT_LIGHT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelEncabezado.add(titulo);
        panelEncabezado.add(Box.createRigidArea(new Dimension(0, 10)));
        panelEncabezado.add(subtitulo);

        // Panel central con los botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setOpaque(false);

        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(COLOR_CARD_BG);
        panelContenedor.setLayout(new GridLayout(1, 3, 25, 0));
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(45, 45, 45, 45)));

        // Botón Registrar Reserva
        btnRegistrar = crearBoton(
                "Registrar Reserva",
                "Crear una nueva reserva",
                COLOR_BTN_1,
                COLOR_BTN_1_HOVER,
                "📝");

        // Botón Verificar Reserva
        btnVerificar = crearBoton(
                "Verificar Reserva",
                "Consultar estado de reserva",
                COLOR_BTN_2,
                COLOR_BTN_2_HOVER,
                "🔍");

        // Botón Reservas de Hoy
        btnReservasHoy = crearBoton(
                "Reservas de Hoy",
                "Ver reservas del día",
                COLOR_BTN_3,
                COLOR_BTN_3_HOVER,
                "📅");

        panelContenedor.add(btnRegistrar);
        panelContenedor.add(btnVerificar);
        panelContenedor.add(btnReservasHoy);

        panelCentral.add(panelContenedor, BorderLayout.CENTER);

        // Agregar componentes al panel
        add(panelEncabezado, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(248, 250, 252);
        Color color2 = new Color(226, 232, 240);
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    private JButton crearBoton(String titulo, String descripcion, Color colorBase, Color colorHover, String icono) {
        JButton boton = new JButton() {
            private Color currentColor = colorBase;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo con bordes redondeados
                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Icono
                g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
                FontMetrics fmIcono = g2d.getFontMetrics();
                int iconoX = (getWidth() - fmIcono.stringWidth(icono)) / 2;
                int iconoY = (getHeight() / 2) - 25;
                g2d.setColor(Color.WHITE);
                g2d.drawString(icono, iconoX, iconoY);

                // Título
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                FontMetrics fm1 = g2d.getFontMetrics();
                int tituloX = (getWidth() - fm1.stringWidth(titulo)) / 2;
                int tituloY = (getHeight() / 2) + 15;
                g2d.drawString(titulo, tituloX, tituloY);

                // Descripción
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                FontMetrics fm2 = g2d.getFontMetrics();
                int descX = (getWidth() - fm2.stringWidth(descripcion)) / 2;
                int descY = (getHeight() / 2) + 40;
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.drawString(descripcion, descX, descY);
            }

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        currentColor = colorHover;
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        repaint();
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        currentColor = colorBase;
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        repaint();
                    }
                });
            }
        };

        boton.setPreferredSize(new Dimension(250, 160));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);

        return boton;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    public JButton getBtnReservasHoy() {
        return btnReservasHoy;
    }
}