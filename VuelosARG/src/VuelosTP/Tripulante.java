package VuelosTP;

public class Tripulante implements Comparable<Tripulante> {
    private String nombre;
    private String apellido;
    private String documento;
    private int asiento;

    public Tripulante(String nombre, String apellido, String documento, int asiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.asiento = asiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public int getAsiento() {
        return asiento;
    }

    @Override
    public int compareTo(Tripulante o) {
        return this.documento.compareTo(o.documento); // Comparar por documento
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (Doc: " + documento + ", Asiento: " + asiento + ")";
    }
}