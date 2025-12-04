package View;

import Controller.ReservaController;
import Model.entidades.ListaReservas;
import Model.entidades.Reserva;
import Model.services.EstadoMesasManager;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ReservasHoyView extends JFrame implements RefrescableView {

    private final ReservaController reservaController;
    private List<Reserva> reservasActuales; // Lista de reservas actuales para acceso desde botones

    private JTable tableReservas;
    private DefaultTableModel modeloTabla;
    private JButton btnVolver;
    private JButton btnActualizar;
    private JLabel lblTotal;

    // Paleta de colores consistente
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_BG = new Color(248, 250, 252);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_SUCCESS = new Color(16, 185, 129);
    private final Color COLOR_DANGER = new Color(220, 38, 38);
    private final Color COLOR_BORDER = new Color(226, 232, 240);

    public ReservasHoyView(EstadoMesasManager estadosManager, ListaReservas listaReservas) {
        reservaController = new ReservaController(estadosManager, listaReservas);
        initComponents();

        // Cancelar automáticamente reservas pendientes expiradas al abrir la vista
        int canceladas = reservaController.cancelarReservasPendientesExpiradas();
        if (canceladas > 0) {
            JOptionPane.showMessageDialog(this,
                    "Se cancelaron automáticamente " + canceladas + " reserva(s) pendiente(s) expirada(s)",
                    "Cancelación Automática",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        cargarReservas();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas - Reservas de Hoy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PRIMARY);
        panel.setPreferredSize(new Dimension(0, 75));

        JLabel lblTitulo = new JLabel("RESERVAS DE HOY", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(COLOR_WHITE);

        panel.add(lblTitulo, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 20, 40));

        // Panel superior con información
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelInfo.setBackground(COLOR_BG);

        lblTotal = new JLabel("Total de reservas: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTotal.setForeground(COLOR_PRIMARY);

        btnActualizar = crearBoton("Actualizar", COLOR_SUCCESS);
        btnActualizar.setPreferredSize(new Dimension(140, 35));
        btnActualizar.addActionListener(e -> {
            cargarReservas();
            JOptionPane.showMessageDialog(this,
                    "Lista de reservas actualizada correctamente",
                    "Actualización Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panelInfo.add(lblTotal);
        panelInfo.add(btnActualizar);

        // Tabla de reservas
        String[] columnas = { "N°", "Cliente", "DNI", "Fecha", "Hora", "Mesa", "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableReservas = new JTable(modeloTabla);
        tableReservas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableReservas.setRowHeight(35);
        tableReservas.setSelectionBackground(new Color(226, 232, 240));
        tableReservas.setSelectionForeground(Color.BLACK);
        // Asegurar que el texto de las celdas sea negro
        tableReservas.setForeground(Color.BLACK);
        tableReservas.setGridColor(COLOR_BORDER);
        tableReservas.setShowGrid(true);

        // Estilo del encabezado
        JTableHeader header = tableReservas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(COLOR_PRIMARY);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(0, 40));

        // Alineación de columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableReservas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableReservas.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableReservas.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableReservas.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableReservas.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableReservas.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        // Anchos de columnas
        tableReservas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableReservas.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableReservas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableReservas.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableReservas.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableReservas.getColumnModel().getColumn(5).setPreferredWidth(70);
        tableReservas.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(tableReservas);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scrollPane.getViewport().setBackground(COLOR_WHITE);

        panel.add(panelInfo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        btnVolver = crearBoton("Volver al Panel", COLOR_DANGER);
        btnVolver.setPreferredSize(new Dimension(180, 42));

        panel.add(btnVolver);
        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(COLOR_WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
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

    public JButton getBtnVolver() {
        return btnVolver;
    }

    private void cargarReservas() {
        // Obtener reservas registradas HOY por fecha_registro desde BD
        this.reservasActuales = reservaController.obtenerReservasRegistradasHoy();

        modeloTabla.setRowCount(0);

        if (reservasActuales.isEmpty()) {
            lblTotal.setText("Total de reservas registradas hoy: 0");
        } else {
            int contador = 1;
            for (Reserva r : reservasActuales) {
                String nombreCompleto = r.getNombreCliente() + " " + r.getApellidoCliente();
                modeloTabla.addRow(new Object[] {
                    contador++,
                    nombreCompleto,
                    r.getDniCliente(),
                    r.getFecha(),
                    r.getHora(),
                    r.getIdMesa(),
                    r.getEstado()
                });
            }
        }

        lblTotal.setText("Total de reservas registradas hoy: " + reservasActuales.size());
    }



    /**
     * Método público para refrescar la tabla después de cambios
     * Llamado por CambiarEstadoDialog después de actualizar el estado
     */
    public void refrescarTabla() {
        cargarReservas();
    }
}