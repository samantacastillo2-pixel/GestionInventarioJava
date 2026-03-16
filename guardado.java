import java.io.*;
import java.util.ArrayList;

public class guardado {

    public static void guardar(ArrayList<ProductoVentana> lista) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("productos.txt"));

            for (ProductoVentana p : lista) {

                bw.write(p.getCodigo() + "," +
                         p.getNombre() + "," +
                         p.getCantidad() + "," +
                         p.getPrecio());

                bw.newLine();

            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar");
        }

    }

    public static ArrayList<ProductoVentana> cargar() {

        ArrayList<ProductoVentana> lista = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader("productos.txt"));

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(",");

                ProductoVentana p = new ProductoVentana(
                        datos[0],
                        datos[1],
                        Integer.parseInt(datos[2]),
                        Double.parseDouble(datos[3])
                );

                lista.add(p);

            }

            br.close();

        } catch (IOException e) {
            System.out.println("No hay archivo aún");
        }

        return lista;

    }
}
}
