package VuelosTP;

import java.util.*;

public class Grafo {

    private Map<String, Map<String, Vuelo>> vuelos;
    private static final Set<String> RUTAS_VALIDAS = new HashSet<>();

    public Grafo() {
        vuelos = new HashMap<>();
        inicializarRutasValidas();
        inicializarVuelos(); // Inicializa los vuelos con los precios especificados
    }

    private void inicializarRutasValidas() {
        // Definir rutas válidas de la aerolínea
        RUTAS_VALIDAS.add("Cordoba-Buenos Aires");
        RUTAS_VALIDAS.add("Cordoba-Jujuy");
        RUTAS_VALIDAS.add("Cordoba-Bariloche");
        RUTAS_VALIDAS.add("Buenos Aires-Cordoba");
        RUTAS_VALIDAS.add("Buenos Aires-Jujuy");
        RUTAS_VALIDAS.add("Buenos Aires-Bariloche");
        RUTAS_VALIDAS.add("Buenos Aires-Misiones");
        RUTAS_VALIDAS.add("Buenos Aires-Santa Cruz");
        RUTAS_VALIDAS.add("Jujuy-Cordoba");
        RUTAS_VALIDAS.add("Jujuy-Buenos Aires");
        RUTAS_VALIDAS.add("Jujuy-Salta");
        RUTAS_VALIDAS.add("Jujuy-Tucuman");
        RUTAS_VALIDAS.add("Bariloche-Cordoba");
        RUTAS_VALIDAS.add("Bariloche-Buenos Aires");
        RUTAS_VALIDAS.add("Bariloche-Santa Cruz");
        RUTAS_VALIDAS.add("Misiones-Buenos Aires");
        RUTAS_VALIDAS.add("Salta-Cordoba");
        RUTAS_VALIDAS.add("Salta-Buenos Aires");
        RUTAS_VALIDAS.add("Salta-Jujuy");
        RUTAS_VALIDAS.add("Salta-Tucuman");
        RUTAS_VALIDAS.add("Tucuman-Cordoba");
        RUTAS_VALIDAS.add("Tucuman-Buenos Aires");
        RUTAS_VALIDAS.add("Tucuman-Jujuy");
        RUTAS_VALIDAS.add("Santa Cruz-Buenos Aires");
        RUTAS_VALIDAS.add("Santa Cruz-Bariloche");
    }

    private void inicializarVuelos() {
        // Inicializar vuelos con precios específicos
        agregarVuelo("Cordoba", "Buenos Aires", 120000);
        agregarVuelo("Cordoba", "Jujuy", 80000);
        agregarVuelo("Cordoba", "Bariloche", 200000);

        agregarVuelo("Buenos Aires", "Cordoba", 150000);
        agregarVuelo("Buenos Aires", "Jujuy", 120000);
        agregarVuelo("Buenos Aires", "Bariloche", 250000);
        agregarVuelo("Buenos Aires", "Misiones", 200000);
        agregarVuelo("Buenos Aires", "Santa Cruz", 350000);

        agregarVuelo("Jujuy", "Cordoba", 75000);
        agregarVuelo("Jujuy", "Buenos Aires", 120000);
        agregarVuelo("Jujuy", "Salta", 55000);
        agregarVuelo("Jujuy", "Tucuman", 68000);

        agregarVuelo("Bariloche", "Cordoba", 220000);
        agregarVuelo("Bariloche", "Buenos Aires", 350000);
        agregarVuelo("Bariloche", "Santa Cruz", 550000);

        agregarVuelo("Misiones", "Buenos Aires", 170000);

        agregarVuelo("Salta", "Cordoba", 60000);
        agregarVuelo("Salta", "Buenos Aires", 110000);
        agregarVuelo("Salta", "Jujuy", 80000);
        agregarVuelo("Salta", "Tucuman", 60000);

        agregarVuelo("Tucuman", "Cordoba", 55000);
        agregarVuelo("Tucuman", "Buenos Aires", 1150000);
        agregarVuelo("Tucuman", "Jujuy", 20000);

        agregarVuelo("Santa Cruz", "Buenos Aires", 380000);
        agregarVuelo("Santa Cruz", "Bariloche", 550000);
    }

    public void agregarVuelo(String origen, String destino, int precioBase) {
        String ruta = origen + "-" + destino;
        if (!RUTAS_VALIDAS.contains(ruta)) {
            System.out.println("No existe un destino válido de " + origen + " a " + destino);
            return;
        }
        vuelos.putIfAbsent(origen, new HashMap<>());
        vuelos.get(origen).put(destino, new Vuelo(origen, destino, precioBase));
    }

    public Vuelo getVuelo(String origen, String destino) {
        if (existeRuta(origen, destino)) {
            return vuelos.get(origen).get(destino);
        }
        return null;
    }

    public boolean existeRuta(String origen, String destino) {
        return vuelos.containsKey(origen) && vuelos.get(origen).containsKey(destino);
    }

    public List<Vuelo> buscarVueloMasBarato(String origen, String destino) {
        PriorityQueue<Ruta> cola = new PriorityQueue<>(Comparator.comparingDouble(r -> r.costoTotal));
        Set<String> visitados = new HashSet<>();

        // Inicializa la cola con el origen
        cola.add(new Ruta(origen, 0.0, new ArrayList<>()));

        while (!cola.isEmpty()) {
            Ruta rutaActual = cola.poll();
            String ciudadActual = rutaActual.ciudadActual;

            // Si llegamos al destino, devolvemos la ruta más barata
            if (ciudadActual.equals(destino)) {
                return rutaActual.vuelos;
            }

            // Marcar la ciudad actual como visitada
            if (!visitados.contains(ciudadActual)) {
                visitados.add(ciudadActual);

                // Explorar vuelos desde la ciudad actual
                Map<String, Vuelo> vuelosDesdeCiudad = vuelos.get(ciudadActual);
                if (vuelosDesdeCiudad != null) {
                    for (Vuelo vuelo : vuelosDesdeCiudad.values()) {
                        String siguienteCiudad = vuelo.getDestino();

                        // Evitar ciclos
                        if (!visitados.contains(siguienteCiudad)) {
                            // Calcular el precio ajustado del vuelo
                            double precioAjustado = vuelo.calcularPrecioAjustado();
                            double nuevoCostoTotal = rutaActual.costoTotal + precioAjustado;

                            // Crear una nueva ruta con el vuelo añadido
                            List<Vuelo> nuevaListaVuelos = new ArrayList<>(rutaActual.vuelos);
                            nuevaListaVuelos.add(vuelo);

                            Ruta nuevaRuta = new Ruta(siguienteCiudad, nuevoCostoTotal, nuevaListaVuelos);

                            // Añadir la nueva ruta a la cola
                            cola.add(nuevaRuta);
                        }
                    }
                }
            }
        }

        // Si no se encuentra una ruta, devuelve null o lanza una excepción según sea necesario
        return null;
    }

    // Clase auxiliar para representar una ruta
    private class Ruta {
        String ciudadActual;
        double costoTotal;
        List<Vuelo> vuelos;

        Ruta(String ciudadActual, double costoTotal, List<Vuelo> vuelos) {
            this.ciudadActual = ciudadActual;
            this.costoTotal = costoTotal;
            this.vuelos = vuelos;
        }
    }

    // Método para registrar un tripulante en un vuelo existente
    public void registrarTripulanteEnVuelo(String origen, String destino, Pasajero pasajero) {
        if (!existeRuta(origen, destino)) {
            System.out.println("No se encontró el vuelo de " + origen + " a " + destino);
            return;
        }

        Vuelo vuelo = vuelos.get(origen).get(destino);
        vuelo.agregarPasajero(pasajero);
    }

    public List<Pasajero> getPasajeros(String origen, String destino) {
        if (!existeRuta(origen, destino)) {
            return new ArrayList<>();
        }
        return vuelos.get(origen).get(destino).getPasajeros();
    }

    public void imprimirVueloConPasajeros(String origen, String destino) {
        Vuelo vuelo = getVuelo(origen, destino);
        if (vuelo != null) {
            System.out.println("Vuelo de " + origen + " a " + destino);
            System.out.println("Pasajeros:");
            for (Pasajero pasajero : vuelo.getPasajeros()) {
                System.out.println("- " + pasajero);
            }
        } else {
            System.out.println("No hay vuelo de " + origen + " a " + destino);
        }
    }
}
