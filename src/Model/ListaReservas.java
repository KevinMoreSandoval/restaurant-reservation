package Model;

public class ListaReservas {

    private NodoReserva cabeza;

    public void agregar(Reserva reserva) {
        NodoReserva nuevo = new NodoReserva(reserva);

        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

        NodoReserva actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }

        actual.siguiente = nuevo;
    }
}
