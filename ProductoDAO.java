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
}