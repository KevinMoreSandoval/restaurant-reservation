package Model.entidades;

public class Reserva {

    private int id;
    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;
    private String fecha;
    private String hora;
    private int idMesa;
    private String estado;

    // Constructor completo (para recuperar de BD)
    public Reserva(int id, String nombreCliente, String apellidoCliente, String dniCliente,
            String fecha, String hora, int idMesa, String estado) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.dniCliente = dniCliente;
        this.fecha = fecha;
        this.hora = hora;
        this.idMesa = idMesa;
        this.estado = estado;
    }

    // Constructor sin ID (para nuevas reservas)
    public Reserva(String nombreCliente, String apellidoCliente, String dniCliente,
            String fecha, String hora, int idMesa, String estado) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.dniCliente = dniCliente;
        this.fecha = fecha;
        this.hora = hora;
        this.idMesa = idMesa;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
