import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaInventario extends JFrame {

    InventarioVentana inventario = new InventarioVentana();
    
    // Campos de texto
    JTextField txtCodigo = new JTextField();
    JTextField txtNombre = new JTextField();
    JTextField txtCantidad = new JTextField();
    JTextField txtPrecio = new JTextField();

    // Botones
    JButton btnAgregar = new JButton("Agregar");
    JButton btnEditar = new JButton("Editar");
    JButton btnEliminar = new JButton("Eliminar");

    JTable tabla;
    DefaultTableModel modelo;

    public VentanaInventario() {
        // Cargar datos guardados al iniciar
        ArrayList<ProductoVentana> datosCargados = guardado.cargar();
        for(ProductoVentana p : datosCargados) {
            inventario.agregarProducto(p);
        }

        setTitle("Gestion de Inventario - Blue Style");
        setSize(650, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255)); // Azul muy claro de fondo

        // Estilo de etiquetas (Labels) arriba de los campos
        crearEtiqueta("Código", 20, 10);
        txtCodigo.setBounds(20, 35, 130, 30);
        
        crearEtiqueta("Nombre", 170, 10);
        txtNombre.setBounds(170, 35, 130, 30);
        
        crearEtiqueta("Cantidad", 320, 10);
        txtCantidad.setBounds(320, 35, 130, 30);
        
        crearEtiqueta("Precio", 470, 10);
        txtPrecio.setBounds(470, 35, 130, 30);

        // Estilo de Botones (Azul fuerte)
        Color azulBoton = new Color(51, 153, 255);
        configurarBoton(btnAgregar, 120, 85, azulBoton);
        configurarBoton(btnEditar, 260, 85, azulBoton);
        configurarBoton(btnEliminar, 400, 85, new Color(255, 102, 102)); // Rojo para eliminar

        // Agregar componentes al frame
        add(txtCodigo); add(txtNombre); add(txtCantidad); add(txtPrecio);
        add(btnAgregar); add(btnEditar); add(btnEliminar);

        // Configuración de la Tabla
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio ($)");

        tabla = new JTable(modelo);
        tabla.setSelectionBackground(new Color(173, 216, 230)); // Azul claro al seleccionar fila
        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 140, 590, 280);
        add(sp);

        actualizarTabla();

        // --- LÓGICA DE BOTONES ---

        btnAgregar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText();
                String nombre = txtNombre.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = Double.parseDouble(txtPrecio.getText());

                inventario.agregarProducto(new ProductoVentana(codigo, nombre, cantidad, precio));
                guardarCambios();
                actualizarTabla();
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Revisa los datos ingresados");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String codigo = modelo.getValueAt(fila, 0).toString();
                inventario.eliminarProducto(codigo);
                guardarCambios();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar");
            }
        });

        btnEditar.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            ProductoVentana p = inventario.buscarProducto(codigo);
            if (p != null) {
                p.setNombre(txtNombre.getText());
                p.setCantidad(Integer.parseInt(txtCantidad.getText()));
                p.setPrecio(Double.parseDouble(txtPrecio.getText()));
                guardarCambios();
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Introduce un código válido para editar");
            }
        });
    }

    // Métodos auxiliares para diseño
    private void crearEtiqueta(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(x, y, 100, 25);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(0, 51, 102)); // Azul oscuro
        add(lbl);
    }

    private void configurarBoton(JButton btn, int x, int y, Color color) {
        btn.setBounds(x, y, 120, 35);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
    }

    private void guardarCambios() {
        guardado.guardar(inventario.getProductos());
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (ProductoVentana p : inventario.getProductos()) {
            modelo.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getCantidad(), p.getPrecio()
            });
        }
    }
}
