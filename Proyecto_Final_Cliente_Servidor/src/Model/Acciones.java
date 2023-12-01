
package Model;

import java.util.List;

public interface Acciones {
    void realizarVenta(double totalPagar, List<Producto> productos, int cantidadProductos);
    void realizarCompra(double precioTotal, List<Producto> productos, int cantidadProductos);
    void pasarCaja(Cliente cliente);
}
