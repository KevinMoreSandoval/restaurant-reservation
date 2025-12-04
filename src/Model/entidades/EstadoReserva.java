package Model.entidades;

/**
 * Enum que define los estados posibles de una reserva
 */
public enum EstadoReserva {
    PENDIENTE, // Reserva creada, esperando confirmación
    CONFIRMADA, // Reserva confirmada por el restaurante
    CANCELADA // Reserva cancelada (no ocupa mesa)
}
