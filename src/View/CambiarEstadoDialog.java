package View;

import Controller.ReservaController;
import Model.entidades.Reserva;
import javax.swing.*;
import java.awt.*;

public class CambiarEstadoDialog extends JDialog {

    private final Reserva reserva;
    private final ReservaController controller;
    private final RefrescableView parentView;

    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_SUCCESS = new Color(16, 185, 129);
    private final Color COLOR_DANGER = new Color(220, 38, 38);
    private final Color COLOR_WARNING = new Color(245, 158, 11);
    private final Color COLOR_WHITE = Color.WHITE;

    public CambiarEstadoDialog(JFrame parent, Reserva reserva, ReservaController controller,
            RefrescableView parentView) {
        super(parent, "Cambiar Estado de Reserva", true);
        this.reserva = reserva;
        this.controller = controller;
        this.parentView = parentView;

        initComponents();
    }

    private void initComponents() {
        setSize(450, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // Panel de información
        JPanel infoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        infoPanel.setBackground(COLOR_WHITE);

        infoPanel.add(crearLabel("Cliente:"));
        infoPanel.add(crearLabel(reserva.getNombreCliente() + " " + reserva.getApellidoCliente()));

        infoPanel.add(crearLabel("DNI:"));
        infoPanel.add(crearLabel(reserva.getDniCliente()));

        infoPanel.add(crearLabel("Fecha:"));
        infoPanel.add(crearLabel(reserva.getFecha()));

        infoPanel.add(crearLabel("Hora:"));
        infoPanel.add(crearLabel(reserva.getHora()));

        infoPanel.add(crearLabel("Mesa:"));
        infoPanel.add(crearLabel(String.valueOf(reserva.getIdMesa())));

        infoPanel.add(crearLabel("Estado Actual:"));
        infoPanel.add(crearLabelEstado(reserva.getEstado()));

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(COLOR_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Mostrar botones según el estado actual
        if (reserva.getEstado().equals("PENDIENTE")) {
            JButton btnConfirmar = crearBoton("Confirmar", COLOR_SUCCESS);
            btnConfirmar.addActionListener(e -> cambiarEstado("CONFIRMADA"));
            buttonPanel.add(btnConfirmar);

            JButton btnCancelar = crearBoton("Cancelar", COLOR_DANGER);
            btnCancelar.addActionListener(e -> cambiarEstado("CANCELADA"));
            buttonPanel.add(btnCancelar);
        } else if (reserva.getEstado().equals("CONFIRMADA")) {
            JButton btnCancelar = crearBoton("Cancelar Reserva", COLOR_DANGER);
            btnCancelar.addActionListener(e -> cambiarEstado("CANCELADA"));
            buttonPanel.add(btnCancelar);
        } else {
            JLabel lblNoAcciones = new JLabel("No hay acciones disponibles para reservas canceladas");
            lblNoAcciones.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            lblNoAcciones.setForeground(Color.GRAY);
            buttonPanel.add(lblNoAcciones);
        }

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }

    private JLabel crearLabelEstado(String estado) {
        JLabel label = new JLabel(estado);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));

        switch (estado) {
            case "PENDIENTE":
                label.setForeground(COLOR_WARNING);
                break;
            case "CONFIRMADA":
                label.setForeground(COLOR_SUCCESS);
                break;
            case "CANCELADA":
                label.setForeground(COLOR_DANGER);
                break;
        }

        return label;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(COLOR_WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
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

    private void cambiarEstado(String nuevoEstado) {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de cambiar el estado a " + nuevoEstado + "?",
                "Confirmar Cambio",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controller.actualizarEstadoReserva(reserva.getId(), nuevoEstado)) {
                JOptionPane.showMessageDialog(this,
                        "Estado actualizado exitosamente a: " + nuevoEstado,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                // Actualizar la vista padre
                parentView.refrescarTabla();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar el estado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
