import java.util.ArrayList;

public class InventarioVentana {

    private ArrayList<ProductoVentana> listaProductos = new ArrayList<>();

    public ArrayList<ProductoVentana> getProductos() {
        return listaProductos;
    }

    public void agregarProducto(ProductoVentana p) {
        listaProductos.add(p);
    }

    public void eliminarProducto(String codigo) {
        listaProductos.removeIf(p -> p.getCodigo().equals(codigo));
    }

    public ProductoVentana buscarProducto(String codigo) {

        for (ProductoVentana p : listaProductos) {

            if (p.getCodigo().equals(codigo)) {
                return p;
            }

        }

        return null;
    }
}
