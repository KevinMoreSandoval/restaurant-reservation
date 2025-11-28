package View;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private JButton btnRegistrar;
    private JButton btnVerificar;

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
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(new Color(30, 41, 59));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Gestione sus reservas de manera eficiente");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(71, 85, 105));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelEncabezado.add(titulo);
        panelEncabezado.add(Box.createRigidArea(new Dimension(0, 10)));
        panelEncabezado.add(subtitulo);

        // Panel central con los botones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setOpaque(false);

        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setLayout(new GridLayout(1, 2, 30, 0));
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(60, 60, 60, 60)
        ));

        // Botón Registrar Reserva
        btnRegistrar = crearBoton(
                "Registrar Reserva",
                "Crear una nueva reserva en el sistema",
                new Color(37, 99, 235),
                new Color(29, 78, 216)
        );

        // Botón Verificar Reserva
        btnVerificar = crearBoton(
                "Verificar Reserva",
                "Consultar el estado de una reserva",
                new Color(16, 185, 129),
                new Color(5, 150, 105)
        );

        panelContenedor.add(btnRegistrar);
        panelContenedor.add(btnVerificar);

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

    private JButton crearBoton(String titulo, String descripcion, Color colorBase, Color colorHover) {
        JButton boton = new JButton() {
            private Color currentColor = colorBase;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo con bordes redondeados
                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Texto
                g2d.setColor(Color.WHITE);

                // Título
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 20));
                FontMetrics fm1 = g2d.getFontMetrics();
                int tituloX = (getWidth() - fm1.stringWidth(titulo)) / 2;
                int tituloY = (getHeight() / 2) - 10;
                g2d.drawString(titulo, tituloX, tituloY);

                // Descripción
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                FontMetrics fm2 = g2d.getFontMetrics();
                int descX = (getWidth() - fm2.stringWidth(descripcion)) / 2;
                int descY = (getHeight() / 2) + 20;
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

        boton.setPreferredSize(new Dimension(250, 150));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);

        return boton;
    }

    // Getters para acceder a los botones desde el controlador
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }
}
