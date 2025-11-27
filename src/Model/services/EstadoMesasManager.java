package Model.services;

import Model.EstadoMesa;
import java.util.HashMap;

public class EstadoMesasManager {

    // fecha → mesa → horario → estadoMesa
    private HashMap<String, HashMap<Integer, HashMap<String, EstadoMesa>>> mapa;

    public EstadoMesasManager() {
        mapa = new HashMap<>();
    }

    // Registrar un estado (mesa ocupa/queda libre)
    public void setEstado(String fecha, int idMesa, String horario,
            EstadoMesa estadoMesa) {

        mapa.putIfAbsent(fecha, new HashMap<>());
        mapa.get(fecha).putIfAbsent(idMesa, new HashMap<>());
        mapa.get(fecha).get(idMesa).put(horario, estadoMesa);
    }

    // Obtener estado de mesa en fecha/horario
    public EstadoMesa getEstado(String fecha, int idMesa, String horario) {
        if (!mapa.containsKey(fecha)) {
            return null;
        }
        if (!mapa.get(fecha).containsKey(idMesa)) {
            return null;
        }
        return mapa.get(fecha).get(idMesa).get(horario);
    }
}
