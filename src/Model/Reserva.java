package Model;
public class Reserva {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private int[] mesas;          
    private String[] horarios;     
    private String fecha;         
    private String estado;       

    public Reserva(int id, String nombre, String apellido, String dni,
            int[] mesas, String[] horarios, String fecha,
            String estado) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.mesas = mesas;
        this.horarios = horarios;
        this.fecha = fecha;
        this.estado = estado;
    }

}
