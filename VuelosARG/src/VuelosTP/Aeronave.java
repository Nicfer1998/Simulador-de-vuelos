package VuelosTP;

public class Aeronave {
    private String nombre;
    private AVLTree<Tripulante> tripulantes;
    private int capacidad;

    public Aeronave(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tripulantes = new AVLTree<>();
    }

    public boolean agregarTripulante(Tripulante tripulante) {
        return tripulantes.insertar(tripulante); // Insertar en el árbol AVL
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double calcularPrecio(double precioBase, int ocupacionActual) {
        double descuento = 0.0;
        double ocupacionPorcentaje = (double) ocupacionActual / capacidad * 100;

        if (ocupacionPorcentaje >= 100) {
            descuento = 0.40; // 40% de descuento
        } else if (ocupacionPorcentaje >= 70) {
            descuento = 0.20; // 20% de descuento
        } else if (ocupacionPorcentaje >= 50) {
            descuento = 0.10; // 10% de descuento
        }

        return precioBase * (1 - descuento);
    }

    public void imprimirTripulantes() {
        tripulantes.imprimirInorden(); // Método para imprimir los tripulantes
    }

    public String getNombre() {
        return nombre;
    }
}
