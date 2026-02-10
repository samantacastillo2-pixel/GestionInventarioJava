import java.util.ArrayList;

public class Inventario {

    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public boolean eliminarProducto(String codigo) {
        return productos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("Inventario vacío");
        } else {
            for (Producto p : productos) {
                System.out.println(p);
            }
        }
    }
}
