import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaInventario extends JFrame {

    Inventario inventario = new Inventario();

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
        txtNombre.setBounds(130,20,100,25);
        txtCantidad.setBounds(240,20,80,25);
        txtPrecio.setBounds(330,20,80,25);

        add(txtCodigo);
        add(txtNombre);
        add(txtCantidad);
        add(txtPrecio);

        btnAgregar.setBounds(20,60,100,30);
        btnEditar.setBounds(130,60,100,30);
        btnEliminar.setBounds(240,60,100,30);

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
        scroll.setBounds(20,110,540,200);
        add(scroll);

        // BOTON AGREGAR
        btnAgregar.addActionListener(e -> {

            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            Producto p = new Producto(codigo,nombre,precio,cantidad);

            inventario.agregarProducto(p);

            modelo.addRow(new Object[]{codigo,nombre,cantidad,precio});

        });

        // BOTON ELIMINAR
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){

                String codigo = modelo.getValueAt(fila,0).toString();

                inventario.eliminarProducto(codigo);

                modelo.removeRow(fila);

            }

        });

        // BOTON EDITAR
        btnEditar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if(fila >= 0){

                modelo.setValueAt(txtCodigo.getText(),fila,0);
                modelo.setValueAt(txtNombre.getText(),fila,1);
                modelo.setValueAt(txtCantidad.getText(),fila,2);
                modelo.setValueAt(txtPrecio.getText(),fila,3);

            }

        });

    }

}
