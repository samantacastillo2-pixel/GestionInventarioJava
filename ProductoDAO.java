package modelo;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;

public class ProductoDAO {

    // INSERTAR
    public void insertar(String nombre, double precio, int cantidad) {
        String sql = "INSERT INTO productos (nombre, precio, cantidad) VALUES (?, ?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, cantidad);
            ps.executeUpdate();

            System.out.println("Producto insertado");

        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
        }
    }

    // LISTAR
    public void listar() {
        String sql = "SELECT * FROM productos";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " - " +
                    rs.getString("nombre") + " - " +
                    rs.getDouble("precio") + " - " +
                    rs.getInt("cantidad")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }
    }

    // ACTUALIZAR
    public void actualizar(int id, String nombre, double precio, int cantidad) {
        String sql = "UPDATE productos SET nombre=?, precio=?, cantidad=? WHERE id=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, cantidad);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("Producto actualizado");

        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
        }
    }

    // ELIMINAR
    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Producto eliminado");

        } catch (SQLException e) {
            System.out.println("Error eliminar: " + e.getMessage());
        }
    }

    // OBTENER TODOS LOS PRODUCTOS
    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error obtenerProductos: " + e.getMessage());
        }

        return lista;
    }

    // AUMENTAR STOCK
    public void aumentarStock(int id, int cantidadExtra) {
        String sql = "UPDATE productos SET cantidad = cantidad + ? WHERE id = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidadExtra);
            ps.setInt(2, id);
            ps.executeUpdate();

            System.out.println("Stock aumentado al producto ID " + id);

        } catch (SQLException e) {
            System.out.println("Error aumentarStock: " + e.getMessage());
        }
    }

    // DISMINUIR STOCK
    public void disminuirStock(int id, int cantidadVenta) {
        String sql = "UPDATE productos SET cantidad = cantidad - ? WHERE id = ? AND cantidad >= ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidadVenta);
            ps.setInt(2, id);
            ps.setInt(3, cantidadVenta);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Venta realizada al producto ID " + id);
            } else {
                System.out.println("No hay suficiente stock para el producto ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error disminuirStock: " + e.getMessage());
        }
    }
}
