package View;

import Controller.ReservaController;
import Model.entidades.ListaReservas;
import Model.entidades.Reserva;
import Model.services.EstadoMesasManager;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VerificarReservaView extends JFrame {

    private final ReservaController reservaController;

    // Componentes
    private JDateChooser dateChooser;
    private JButton btnBuscarFecha;
    private JButton btnReservasHoy;
    private JTable tableResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalReservas;

    // Paleta de colores consistente
    private final Color COLOR_PRIMARY = new Color(30, 41, 59);
    private final Color COLOR_SUCCESS = new Color(16, 185, 129);
    private final Color COLOR_INFO = new Color(37, 99, 235);
    private final Color COLOR_BG = new Color(248, 250, 252);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_BORDER = new Color(226, 232, 240);

    public VerificarReservaView(EstadoMesasManager estadosManager, ListaReservas listaReservas) {
        // Inicializar controlador con instancias compartidas
        reservaController = new ReservaController(estadosManager, listaReservas);
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Reservas - Verificar Reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        // Quitar fondo oscuro del título: usar fondo claro del resto del layout
        panel.setBackground(COLOR_BG);
        panel.setPreferredSize(new Dimension(0, 75));

        JLabel lblTitulo = new JLabel("VERIFICAR RESERVAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        // Texto oscuro para mantener contraste sobre fondo claro
        lblTitulo.setForeground(COLOR_PRIMARY);

        panel.add(lblTitulo, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(COLOR_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panel.add(crearPanelBusqueda(), BorderLayout.NORTH);
        panel.add(crearPanelResultados(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(COLOR_WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER, 1),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setBackground(COLOR_WHITE);

        JLabel lblBuscar = new JLabel("Seleccionar Fecha:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscar.setForeground(COLOR_PRIMARY);

        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(180, 40));
        dateChooser.setBackground(COLOR_WHITE);
        dateChooser.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARY, 1));
        dateChooser.getDateEditor().getUiComponent().setBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnBuscarFecha = crearBoton("Buscar Reservas", COLOR_INFO);
        btnBuscarFecha.setPreferredSize(new Dimension(180, 40));
        btnBuscarFecha.addActionListener(e -> buscarPorFecha());

        btnReservasHoy = crearBoton("Reservas de Hoy", COLOR_SUCCESS);
        btnReservasHoy.setPreferredSize(new Dimension(180, 40));
        // Listener se configura en main.java para abrir nueva vista

        panel.add(lblBuscar);
        panel.add(dateChooser);
        panel.add(btnBuscarFecha);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(btnReservasHoy);

        panelContenedor.add(panel, BorderLayout.CENTER);

        return panelContenedor;
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(COLOR_BG);

        // Panel superior con título y total
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelInfo.setBackground(COLOR_BG);

        JLabel lblResultados = new JLabel("Resultados de la búsqueda:");
        lblResultados.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblResultados.setForeground(COLOR_PRIMARY);

        lblTotalReservas = new JLabel("");
        lblTotalReservas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalReservas.setForeground(COLOR_INFO);

        panelInfo.add(lblResultados);
        panelInfo.add(lblTotalReservas);

        // Tabla de resultados
        String[] columnas = { "N°", "Cliente", "DNI", "Fecha", "Hora", "Mesa", "Estado" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableResultados = new JTable(modeloTabla);
        tableResultados.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableResultados.setRowHeight(35);
        tableResultados.setSelectionBackground(new Color(226, 232, 240));
        tableResultados.setSelectionForeground(Color.BLACK);
        // Asegurar que el texto de las celdas sea negro
        tableResultados.setForeground(Color.BLACK);
        tableResultados.setGridColor(COLOR_BORDER);
        tableResultados.setShowGrid(true);

        // Estilo del encabezado
        JTableHeader header = tableResultados.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(COLOR_PRIMARY);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(0, 40));

        // Alineación de columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableResultados.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableResultados.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableResultados.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableResultados.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableResultados.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableResultados.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        // Anchos de columnas
        tableResultados.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableResultados.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableResultados.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableResultados.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableResultados.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableResultados.getColumnModel().getColumn(5).setPreferredWidth(70);
        tableResultados.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(tableResultados);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scrollPane.getViewport().setBackground(COLOR_WHITE);

        panel.add(panelInfo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

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

    public JButton getBtnReservasHoy() {
        return btnReservasHoy;
    }

    /**
     * Buscar reservas por fecha usando el JDateChooser y el árbol (búsqueda
     * eficiente O(log n))
     */
    private void buscarPorFecha() {
        // Obtener la fecha seleccionada del JDateChooser
        Date fechaSeleccionada = dateChooser.getDate();

        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione una fecha para buscar",
                    "Fecha no Seleccionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Formatear la fecha a YYYY-MM-DD para la búsqueda en el árbol
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(fechaSeleccionada);

        // Buscar en el árbol (O(log n))
        List<Reserva> reservasFecha = reservaController.buscarPorFechaEnArbol(fecha);

        // Limpiar tabla
        modeloTabla.setRowCount(0);

        if (reservasFecha.isEmpty()) {
            lblTotalReservas.setText("");
            JOptionPane.showMessageDialog(this,
                    "No se encontraron reservas para la fecha: " + fecha,
                    "Sin Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Formatear la fecha para mostrar
            SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = sdfDisplay.format(fechaSeleccionada);

            mostrarReservasEnTabla(reservasFecha);
            lblTotalReservas.setText("(" + reservasFecha.size() + " encontradas)");

            JOptionPane.showMessageDialog(this,
                    "Se encontraron " + reservasFecha.size() + " reserva(s) para " + fechaFormateada +
                            "\n(Búsqueda realizada en árbol - O(log n))",
                    "Búsqueda Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarReservasEnTabla(List<Reserva> reservas) {
        modeloTabla.setRowCount(0);
        int contador = 1;
        for (Reserva r : reservas) {
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
}