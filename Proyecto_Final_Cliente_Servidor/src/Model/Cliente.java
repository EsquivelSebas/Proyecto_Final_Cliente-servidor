package Model;

import java.util.List;

public abstract class Cliente extends Persona implements Acciones, consultarInformacion {

    private String correo;
    private String password;
    private boolean membresia;
    private double precioTotal;

    public Cliente(String correo, String password, boolean membresia, String nombre, double precioTotal) {
        super(nombre);
        this.correo = correo;
        this.password = password;
        this.membresia = membresia;
        this.precioTotal = precioTotal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMembresia() {
        return membresia;
    }

    public void setMembresia(boolean membresia) {
        this.membresia = membresia;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    

    @Override
    public void pasarCaja() {
        System.out.println(getNombre() + " ha pasado por caja.");
    }

    @Override
    public void consultarInfo() {
        System.out.println("Información del cliente:");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Correo: " + correo);
        System.out.println("Membresía: " + membresia);
    }
    @Override
    public void realizarCompra(double precioTotal, List<Producto> productos, int cantidadProductos) {
        System.out.println(getNombre() + " ha realizado una compra con un precio total de $" + precioTotal);
        System.out.println("Productos comprados: " + productos);
        System.out.println("Cantidad de productos: " + cantidadProductos);
    }

    @Override
    public void realizarVenta() {

    }

}
