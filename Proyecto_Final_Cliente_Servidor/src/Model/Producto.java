package Model;

public class Producto implements consultarInformacion {

    private String nombre;
    private double precio;
    private int cantidad;
    private int ID;

    public Producto(String nombre, double precio, int cantidad, int ID) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void consultarInfo() {
        System.out.println("Información del producto:");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Precio: " + getPrecio());
        System.out.println("Cantidad disponible: " + getCantidad());
        System.out.println("ID: " + getID());
    }
}
