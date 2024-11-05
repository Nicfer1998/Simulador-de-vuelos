package VuelosTP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AerolineaUI extends JFrame {

    private JComboBox<String> origenComboBox;
    private JComboBox<String> destinoComboBox;
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField documentoField;
    private JLabel contadorPasajerosLabel;
    private JLabel capacidadPorcentajeLabel;
    private Grafo grafo;

    public AerolineaUI() {
        grafo = new Grafo(); // Inicializa el grafo
        inicializarVuelos(); // Asegúrate de inicializar los vuelos

        setTitle("Sistema de Aerolínea");
        setSize(400, 400); // Aumenta el tamaño para más componentes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 2)); // Ajustar el número de filas

        // Componentes de origen y destino
        origenComboBox = new JComboBox<>(new String[]{"Cordoba", "Buenos Aires", "Jujuy", "Bariloche", "Misiones", "Salta", "Tucuman", "Santa Cruz"});
        destinoComboBox = new JComboBox<>(new String[]{"Cordoba", "Buenos Aires", "Jujuy", "Bariloche", "Misiones", "Salta", "Tucuman", "Santa Cruz"});

        panel.add(new JLabel("Origen:"));
        panel.add(origenComboBox);
        panel.add(new JLabel("Destino:"));
        panel.add(destinoComboBox);

        // Campos para datos del pasajero
        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        panel.add(apellidoField);

        panel.add(new JLabel("Documento:"));
        documentoField = new JTextField();
        panel.add(documentoField);

        // Botón para añadir pasajero
        JButton anadirPasajeroButton = new JButton("Añadir Pasajero");
        anadirPasajeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirPasajero();
            }
        });
        panel.add(anadirPasajeroButton);

        // Botón para buscar vuelo más barato
        JButton buscarVueloButton = new JButton("Buscar Vuelo Más Barato");
        buscarVueloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVueloMasBarato();
            }
        });
        panel.add(buscarVueloButton);

        // Botón para mostrar pasajeros en el vuelo
        JButton mostrarPasajerosButton = new JButton("Mostrar Pasajeros");
        mostrarPasajerosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPasajeros((String) origenComboBox.getSelectedItem(), (String) destinoComboBox.getSelectedItem());
            }
        });
        panel.add(mostrarPasajerosButton);

        // Etiquetas para mostrar ocupación y capacidad
        contadorPasajerosLabel = new JLabel("Pasajeros: 0");
        capacidadPorcentajeLabel = new JLabel("Capacidad: 0%");
        panel.add(contadorPasajerosLabel);
        panel.add(capacidadPorcentajeLabel);

        add(panel);
        setVisible(true);
    }

    private void anadirPasajero() {
        String origen = (String) origenComboBox.getSelectedItem();
        String destino = (String) destinoComboBox.getSelectedItem();

        if (origen.equals(destino)) {
            JOptionPane.showMessageDialog(this, "El destino no puede ser igual al origen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!grafo.existeRuta(origen, destino)) {
            JOptionPane.showMessageDialog(this, "No hay vuelos de " + origen + " a " + destino, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener datos del pasajero desde los campos de texto
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String documento = documentoField.getText();

        // Crear el pasajero
        Pasajero pasajero = new Pasajero(nombre, apellido, documento);
        grafo.registrarTripulanteEnVuelo(origen, destino, pasajero);

        // Calcular el precio ajustado del vuelo
        Vuelo vuelo = grafo.getVuelo(origen, destino); // Asumiendo que tienes un método para obtener el vuelo
        double precioAjustado = vuelo.calcularPrecioAjustado();

        // Mostrar el precio ajustado al añadir el pasajero
        JOptionPane.showMessageDialog(this, "Pasajero añadido. Precio ajustado: " + precioAjustado, "Información", JOptionPane.INFORMATION_MESSAGE);

        mostrarPasajeros(origen, destino);

        // Limpiar los campos de texto
        nombreField.setText("");
        apellidoField.setText("");
        documentoField.setText("");

        // Actualizar ocupación y contadores
        actualizarContadorOcupacion(origen, destino);
    }

    private void buscarVueloMasBarato() {
    String origen = (String) origenComboBox.getSelectedItem();
    String destino = (String) destinoComboBox.getSelectedItem();

    List<Vuelo> rutaMasBarata = grafo.buscarVueloMasBarato(origen, destino);
    
    if (rutaMasBarata != null && !rutaMasBarata.isEmpty()) {
        double costoTotal = rutaMasBarata.stream().mapToDouble(Vuelo::calcularPrecioAjustado).sum();

        // Construir un mensaje para mostrar los detalles de la ruta más barata
        StringBuilder mensaje = new StringBuilder("La ruta más barata de " + origen + " a " + destino + " incluye:\n");
        
        for (Vuelo vuelo : rutaMasBarata) {
            mensaje.append(" - Vuelo de ").append(vuelo.getOrigen())
                   .append(" a ").append(vuelo.getDestino())
                   .append(" con precio ajustado: ").append(vuelo.calcularPrecioAjustado()).append("\n");
        }
        
        mensaje.append("Costo total: ").append(costoTotal);

        JOptionPane.showMessageDialog(this, mensaje.toString(), "Ruta Más Barata", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró una ruta de " + origen + " a " + destino, "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void inicializarVuelos() {
        // Agregar vuelos válidos con sus precios específicos
        grafo.agregarVuelo("Cordoba", "Buenos Aires", 120000);
        grafo.agregarVuelo("Cordoba", "Jujuy", 80000);
        grafo.agregarVuelo("Cordoba", "Bariloche", 200000);

        grafo.agregarVuelo("Buenos Aires", "Cordoba", 150000);
        grafo.agregarVuelo("Buenos Aires", "Jujuy", 120000);
        grafo.agregarVuelo("Buenos Aires", "Bariloche", 250000);
        grafo.agregarVuelo("Buenos Aires", "Misiones", 200000);
        grafo.agregarVuelo("Buenos Aires", "Santa Cruz", 350000);

        grafo.agregarVuelo("Jujuy", "Cordoba", 75000);
        grafo.agregarVuelo("Jujuy", "Buenos Aires", 120000);
        grafo.agregarVuelo("Jujuy", "Salta", 55000);
        grafo.agregarVuelo("Jujuy", "Tucuman", 68000);

        grafo.agregarVuelo("Bariloche", "Cordoba", 220000);
        grafo.agregarVuelo("Bariloche", "Buenos Aires", 350000);
        grafo.agregarVuelo("Bariloche", "Santa Cruz", 550000);

        grafo.agregarVuelo("Misiones", "Buenos Aires", 170000);

        grafo.agregarVuelo("Salta", "Cordoba", 60000);
        grafo.agregarVuelo("Salta", "Buenos Aires", 110000);
        grafo.agregarVuelo("Salta", "Jujuy", 80000);
        grafo.agregarVuelo("Salta", "Tucuman", 60000);

        grafo.agregarVuelo("Tucuman", "Cordoba", 55000);
        grafo.agregarVuelo("Tucuman", "Buenos Aires", 1150000);
        grafo.agregarVuelo("Tucuman", "Jujuy", 20000);

        grafo.agregarVuelo("Santa Cruz", "Buenos Aires", 380000);
        grafo.agregarVuelo("Santa Cruz", "Bariloche", 550000);
    }

    private void mostrarPasajeros(String origen, String destino) {
        List<Pasajero> pasajeros = grafo.getPasajeros(origen, destino);
        StringBuilder sb = new StringBuilder("Pasajeros en el vuelo de " + origen + " a " + destino + ":\n");
        for (Pasajero pasajero : pasajeros) {
            sb.append(pasajero.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Lista de Pasajeros", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarContadorOcupacion(String origen, String destino) {
        // Obtiene el vuelo directo desde el grafo para la ruta especificada
        Vuelo vuelo = grafo.getVuelo(origen, destino);

        if (vuelo != null) {
            int ocupacionActual = vuelo.getOcupacionActual();
            int capacidad = vuelo.getCapacidad();
            double porcentaje = (double) ocupacionActual / capacidad * 100;

            // Actualiza el contador de pasajeros y el porcentaje de capacidad
            contadorPasajerosLabel.setText("Pasajeros: " + ocupacionActual);
            capacidadPorcentajeLabel.setText(String.format("Capacidad: %.0f%%", porcentaje));
        } else {
            contadorPasajerosLabel.setText("Pasajeros: 0");
            capacidadPorcentajeLabel.setText("Capacidad: 0%");
            JOptionPane.showMessageDialog(this, "No se encontró el vuelo de " + origen + " a " + destino, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AerolineaUI());
    }
}
