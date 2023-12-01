package Model;

import java.util.List;
import java.util.Random;

public abstract class Despachador extends Persona implements Acciones, consultarInformacion{
    private int numDespachador;

    public Despachador(int numDespachador, String nombre) {
        super(nombre);
        this.numDespachador = numDespachador;
    }

    public int getNumDespachador() {
        return numDespachador;
    }

    public void setNumDespachador(int numDespachador) {
        this.numDespachador = numDespachador;
    }
    @Override
    public void realizarVenta(double totalPagar, List<Producto> productos, int cantidadProductos) {
        System.out.println("Despachador " + numDespachador + " ha realizado una venta con un total de: " + totalPagar);
        System.out.println("Productos vendidos: " + productos);
        System.out.println("Cantidad de productos: " + cantidadProductos);
    }
    @Override
    public void pasarCaja(Cliente cliente) {
        Random random = new Random();
        int despachadorAtiende = random.nextInt(5) + 1; 
        System.out.println("Despachador " + numDespachador + " está atendiendo a cliente: " + cliente.getNombre());
    }

    @Override
    public void consultarInfo() {
        System.out.println("Información del despachador:");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Número Despachador: " + numDespachador);
    }
}

