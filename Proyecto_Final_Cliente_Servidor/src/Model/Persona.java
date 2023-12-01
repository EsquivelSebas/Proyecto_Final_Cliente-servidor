
package Model;

public abstract class Persona {
    private String nombre;

    public Persona(String nombre) {
        this.nombre = nombre;
    }
   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public abstract void realizarCompra();
    public abstract void realizarVenta();
    public abstract void pasarCaja();
    public abstract void consultarInfo();
}
