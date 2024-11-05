package VuelosTP;

public class Pasajero {
    private String nombre;
    private String apellido;
    private String documento;

    public Pasajero(String nombre, String apellido, String documento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
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

    @Override
    public String toString() {
        return nombre + " " + apellido + " (Doc: " + documento + ")";
    }
}
