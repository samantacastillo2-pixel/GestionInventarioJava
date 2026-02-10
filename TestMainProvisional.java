import java.util.Scanner;

public class MainProvisional {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Inventario inventario = new Inventario();

        int opcion;

        do {
            System.out.println("\n===== PRUEBA INVENTARIO =====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Buscar producto");
            System.out.println("4. Listar productos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Código: ");
                    String codigo = sc.nextLine();

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Precio: ");
                    double precio = sc.nextDouble();

                    System.out.print("Cantidad: ");
                    int cantidad = sc.nextInt();
                    sc.nextLine();

                    Producto nuevo = new Producto(codigo, nombre, precio, cantidad);
                    inventario.agregarProducto(nuevo);

                    System.out.println("Producto agregado");
                    break;

                case 2:
                    System.out.print("Código a eliminar: ");
                    String eliminar = sc.nextLine();

                    if (inventario.eliminarProducto(eliminar)) {
                        System.out.println("Producto eliminado");
                    } else {
                        System.out.println("Producto no encontrado");
                    }
                    break;

                case 3:
                    System.out.print("Código a buscar: ");
                    String buscar = sc.nextLine();

                    Producto encontrado = inventario.buscarProducto(buscar);

                    if (encontrado != null) {
                        System.out.println(encontrado);
                    } else {
                        System.out.println("No existe");
                    }
                    break;

                case 4:
                    inventario.listarProductos();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 0);

        sc.close();
    }
}
