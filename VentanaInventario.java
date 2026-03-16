import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaInventario extends JFrame {

    InventarioVentana inventario = new InventarioVentana();

    JTextField txtCodigo = new JTextField();
    JTextField txtNombre = new JTextField();
    JTextField txtCantidad = new JTextField();
    JTextField txtPrecio = new JTextField();

    JButton btnAgregar = new JButton("Agregar");
    JButton btnEditar = new JButton("Editar");
    JButton btnEliminar = new JButton("Eliminar");

    JTable tabla;
    DefaultTableModel modelo;

    public VentanaInventario() {

        setTitle("Inventario");
        setSize(600,400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtCodigo.setBounds(20,20,100,25);
        txtNombre.setBounds(140,20,100,25);
        txtCantidad.setBounds(260,20,100,25);
        txtPrecio.setBounds(380,20,100,25);

        add(txtCodigo);
        add(txtNombre);
        add(txtCantidad);
        add(txtPrecio);

        btnAgregar.setBounds(20,60,100,25);
        btnEditar.setBounds(140,60,100,25);
        btnEliminar.setBounds(260,60,100,25);

        add(btnAgregar);
        add(btnEditar);
        add(btnEliminar);

        modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20,100,540,220);

        add(scroll);

        // BOTON AGREGAR
        btnAgregar.addActionListener(e -> {

            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            ProductoVentana p = new ProductoVentana(codigo,nombre,cantidad,precio);

            inventario.agregarProducto(p);

            actualizarTabla();
        });

        // BOTON ELIMINAR
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){

                String codigo = modelo.getValueAt(fila,0).toString();

                inventario.eliminarProducto(codigo);

                actualizarTabla();

            }

        });

        // BOTON EDITAR
        btnEditar.addActionListener(e -> {

            String codigo = txtCodigo.getText();

            ProductoVentana p = inventario.buscarProducto(codigo);

            if(p != null){

                p.setNombre(txtNombre.getText());
                p.setCantidad(Integer.parseInt(txtCantidad.getText()));
                p.setPrecio(Double.parseDouble(txtPrecio.getText()));

                actualizarTabla();

            }

        });

    }

    public void actualizarTabla(){

        modelo.setRowCount(0);

        for(ProductoVentana p : inventario.getProductos()){

            modelo.addRow(new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getCantidad(),
                p.getPrecio()
            });

        }

    }

}
