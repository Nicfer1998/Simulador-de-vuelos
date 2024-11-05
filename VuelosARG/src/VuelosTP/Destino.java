package VuelosTP;

import java.util.ArrayList;
import java.util.List;

public class Destino {
    private String nombre;
    private int precioBase;
    private int capacidad;
    private int ocupacionActual;
    private List<Pasajero> pasajeros;

    // Constructor
    public Destino(String nombre, int precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.capacidad = 10; // Capacidad máxima de 10 tripulantes
        this.ocupacionActual = 0;
        this.pasajeros = new ArrayList<>(); // Inicializar la lista de pasajeros
    }

    public void agregarPasajero(Pasajero pasajero) {
        if (ocupacionActual < capacidad) {
            pasajeros.add(pasajero);
            incrementarOcupacion();
        } else {
            System.out.println("La capacidad máxima del vuelo ha sido alcanzada.");
        }
    }

    // Métodos getter
    public String getNombre() {
        return nombre;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public int getOcupacionActual() {
        return ocupacionActual;
    }

    public int getCapacidad() {
        return capacidad;
    }

    // Método para incrementar la ocupación
    public void incrementarOcupacion() {
        if (ocupacionActual < capacidad) {
            ocupacionActual++;
        }
    }

    // Método para calcular el precio ajustado según la ocupación
    public double calcularPrecioAjustado() {
        double descuento = 0.0;

        // Calcula el porcentaje de ocupación
        double ocupacionPorcentaje = (double) ocupacionActual / capacidad * 100;

        // Determina el descuento basado en la ocupación
        if (ocupacionPorcentaje >= 100) {
            descuento = 0.40;  // 40% de descuento
        } else if (ocupacionPorcentaje >= 70) {
            descuento = 0.20;  // 20% de descuento
        } else if (ocupacionPorcentaje >= 50) {
            descuento = 0.10;  // 10% de descuento
        }

        // Calcula y retorna el precio ajustado
        return precioBase * (1 - descuento);
    }

    // Método para obtener la lista de pasajeros
    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }
}
