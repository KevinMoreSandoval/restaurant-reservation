package Model;

public class EstadoMesa {

    private int idMesa;
    private String fecha;       // "2025-03-01"
    private String horario;     // "12:00-13:00"
    private String estado;      // "libre" o "ocupada"
    private int idReserva;      // reserva que ocupa esa mesa/hora

    public EstadoMesa(int idMesa, String fecha, String horario,
            String estado, int idReserva) {
        this.idMesa = idMesa;
        this.fecha = fecha;
        this.horario = horario;
        this.estado = estado;
        this.idReserva = idReserva;
    }
    
}
