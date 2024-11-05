package VuelosTP;

import java.util.ArrayList;
import java.util.List;

public class Vuelo {

    private String origen;
    private String destino;
    private int precioBase;
    private int capacidad;
    private int ocupacionActual;
    private List<Pasajero> pasajeros;

    public Vuelo(String origen, String destino, int precioBase) {
        this.origen = origen;
        this.destino = destino;
        this.precioBase = precioBase;
        this.capacidad = 10; // Capacidad total del vuelo
        this.ocupacionActual = 0; // Ocupación inicial
        this.pasajeros = new ArrayList<>(); // Lista de pasajeros
    }

    public void agregarPasajero(Pasajero pasajero) {
        if (ocupacionActual < capacidad) {
            pasajeros.add(pasajero);
            ocupacionActual++;
        } else {
            System.out.println("La capacidad máxima del vuelo ha sido alcanzada.");
        }
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public double calcularPrecioAjustado() {
        double descuento = 0.0;
        double ocupacionPorcentaje = (double) ocupacionActual / capacidad * 100;

        // Aplicar descuentos según la ocupación
        if (ocupacionPorcentaje >= 100) {
            descuento = 0.40; // 40% de descuento si está lleno
        } else if (ocupacionPorcentaje >= 70) {
            descuento = 0.20; // 20% de descuento si al menos 70% de ocupación
        } else if (ocupacionPorcentaje >= 50) {
            descuento = 0.10; // 10% de descuento si al menos 50% de ocupación
        }

        return precioBase * (1 - descuento); // Retorna el precio ajustado
    }

    public int getOcupacionActual() {
        return ocupacionActual; // Devuelve la ocupación actual
    }

    public int getCapacidad() {
        return capacidad; // Devuelve la capacidad del vuelo
    }

    public int getPrecioBase() {
        return precioBase; // Devuelve el precio base del vuelo
    }

    public String getOrigen() {
    return origen; // Devuelve el origen del vuelo
}

public String getDestino() {
    return destino; // Devuelve el destino del vuelo
}

}
