package restauranteApp;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private String nombre, apellido, dni;
    private LocalDate fecha;
    private LocalTime hora;
    private int idMesa;

    public Reserva(String nombre, String apellido, String dni, LocalDate fecha, LocalTime hora, int idMesa) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha = fecha;
        this.hora = hora;
        this.idMesa = idMesa;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDni() { return dni; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public int getIdMesa() { return idMesa; }
}
