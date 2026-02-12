import java.util.ArrayList;

// clase que se encarga de administrar la lista de productos
public class Inventario {

     // lista donde se guardan los productos del inventario
    private ArrayList<Producto> productos;

    // constructor que inicializa la lista al crear el inventario
    public Inventario() {
        productos = new ArrayList<>();
    }

    // metodo para añadir un nuevo producto a la lista
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    // metodo que elimina un producto segun su codigo
    // devuelve true si se elimino correctamente
    public boolean eliminarProducto(String codigo) {
        return productos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }

     // metodo que busca un producto por su codigo
    // si lo encuentra lo devuelve, si no retorna null
    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    // metodo que muestra todos los productos almacenados
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

