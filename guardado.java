import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Clase Producto
class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    // Representación en texto para guardar en archivo
    @Override
    public String toString() {
        return nombre + "," + precio + "," + cantidad;
    }

    // Método para reconstruir desde una línea del archivo
    public static Producto fromString(String linea) {
        String[] partes = linea.split(",");
        String nombre = partes[0];
        double precio = Double.parseDouble(partes[1]);
        int cantidad = Integer.parseInt(partes[2]);
        return new Producto(nombre, precio, cantidad);
    }
}

// Clase Inventario
class Inventario {
    private List<Producto> productos = new ArrayList<>();
    private final String archivo = "productos.txt";

    // Cargar productos desde archivo
    public void cargar() {
        productos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                productos.add(Producto.fromString(linea));
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo, se creará uno nuevo.");
        }
    }

    // Guardar productos en archivo
    public void guardar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Producto p : productos) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    // Métodos de gestión
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

    public void mostrarProductos() {
        for (Producto p : productos) {
            System.out.println(p.getNombre() + " - Precio: " + p.getPrecio() + " - Cantidad: " + p.getCantidad());
        }
    }
}

// Clase principal
public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        // Al iniciar, cargar productos desde archivo
        inventario.cargar();

        // Agregar productos de ejemplo
        inventario.agregarProducto(new Producto("Laptop", 1200.50, 5));
        inventario.agregarProducto(new Producto("Mouse", 25.99, 20));

        // Mostrar productos actuales
        System.out.println("Inventario actual:");
        inventario.mostrarProductos();

        // Guardar productos en archivo
        inventario.guardar();
    }
}
