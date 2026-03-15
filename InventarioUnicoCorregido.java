import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

@SuppressWarnings("unused")
public class InventarioUnicoCorregido extends JFrame {

    // Campos de entrada 
    private final JTextField tfCodigo = new JTextField();
    private final JTextField tfNombre = new JTextField();
    private final JTextField tfCantidad = new JTextField();
    private final JTextField tfPrecio = new JTextField();
    private final JTextField tfBuscar = new JTextField();

    private final JLabel resultadoLabel = new JLabel(" "); // para mensajes rápidos
    private final JLabel header = new JLabel("ANYI - Inventario Singular", SwingConstants.CENTER);

    private final JButton btnGuardar = new JButton("Guardar (Ctrl+S)");
    private final JButton btnBuscar = new JButton("Buscar (Ctrl+F)");
    private final JButton btnListar = new JButton("Listar (Ctrl+L)");
    private final JButton btnMostrar = new JButton("Mostrar");

    private final Repository repo = new BinaryRepository("productos.dat");

    // Resultado interno 
    private Producto ultimoEncontrado = null;

    public InventarioUnicoCorregido() {
        setTitle("ANYI - Inventario (único)");
        setSize(420, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // diseño absoluto como tu ejemplo
        setLocationRelativeTo(null);

        // Cabecera animada
        header.setBounds(0, 0, 420, 50);
        header.setOpaque(true);
        header.setBackground(new Color(30, 144, 255));
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        add(header);
        startHeaderAnimation();

        // Etiquetas y campos 
        JLabel lCodigo = new JLabel("Código:");
        lCodigo.setBounds(20, 70, 80, 20);
        tfCodigo.setBounds(100, 70, 200, 24);

        JLabel lNombre = new JLabel("Nombre:");
        lNombre.setBounds(20, 100, 80, 20);
        tfNombre.setBounds(100, 100, 200, 24);

        JLabel lCantidad = new JLabel("Cantidad:");
        lCantidad.setBounds(20, 130, 80, 20);
        tfCantidad.setBounds(100, 130, 80, 24);

        JLabel lPrecio = new JLabel("Precio:");
        lPrecio.setBounds(200, 130, 50, 20);
        tfPrecio.setBounds(250, 130, 80, 24);

        JLabel lBuscar = new JLabel("Buscar:");
        lBuscar.setBounds(20, 170, 80, 20);
        tfBuscar.setBounds(100, 170, 200, 24);

        // Botones
        btnGuardar.setBounds(20, 210, 120, 30);
        btnBuscar.setBounds(150, 210, 120, 30);
        btnListar.setBounds(280, 210, 120, 30);
        btnMostrar.setBounds(310, 70, 90, 24); // mostrar último encontrado

        // Resultado / mensajes
        resultadoLabel.setBounds(20, 255, 380, 25);
        resultadoLabel.setForeground(Color.DARK_GRAY);

        // Agregar componentes
        add(lCodigo); add(tfCodigo);
        add(lNombre); add(tfNombre);
        add(lCantidad); add(tfCantidad);
        add(lPrecio); add(tfPrecio);
        add(lBuscar); add(tfBuscar);
        add(btnGuardar); add(btnBuscar); add(btnListar); add(btnMostrar);
        add(resultadoLabel);

        // Eventos
        btnGuardar.addActionListener(e -> guardarProducto());
        btnBuscar.addActionListener(e -> buscarProducto());
        btnListar.addActionListener(e -> listarProductos());
        btnMostrar.addActionListener(e -> mostrarUltimo());

        // Enter en campos
        tfCodigo.addActionListener(e -> tfNombre.requestFocusInWindow());
        tfNombre.addActionListener(e -> tfCantidad.requestFocusInWindow());
        tfCantidad.addActionListener(e -> tfPrecio.requestFocusInWindow());
        tfPrecio.addActionListener(e -> btnGuardar.requestFocusInWindow());
        tfBuscar.addActionListener(e -> buscarProducto());

        // Atajos de teclado
        setupKeyBindings();

        // Mensaje inicial
        resultadoLabel.setText("Listo. Usa los botones o atajos.");
    }

    // Guardar producto 
    private void guardarProducto() {
        try {
            String codigo = tfCodigo.getText().trim();
            String nombre = tfNombre.getText().trim();
            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código y Nombre son obligatorios.");
                return;
            }
            int cantidad;
            double precio;
            try {
                cantidad = Integer.parseInt(tfCantidad.getText().trim().isEmpty() ? "0" : tfCantidad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.");
                return;
            }
            try {
                precio = Double.parseDouble(tfPrecio.getText().trim().isEmpty() ? "0" : tfPrecio.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un precio válido.");
                return;
            }

            Producto p = new Producto(codigo, nombre, cantidad, precio);
            repo.save(p);
            resultadoLabel.setText("Producto guardado: " + codigo);
            flashHeader(Color.GREEN.darker());
            clearInputs();
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    // Buscar producto 
    private void buscarProducto() {
        try {
            String q = tfBuscar.getText().trim();
            String q2 = tfCodigo.getText().trim();
            String query = !q.isEmpty() ? q : q2;
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese código o texto para buscar.");
                return;
            }
            List<Producto> all = repo.findAll();
            List<Producto> matches = new ArrayList<>();
            for (Producto p : all) {
                if (fuzzyMatch(query, p)) matches.add(p);
            }
            if (matches.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron coincidencias.");
                resultadoLabel.setText("Sin resultados para: " + query);
                ultimoEncontrado = null;
                return;
            }
            if (matches.size() == 1) {
                ultimoEncontrado = matches.get(0);
                populateFields(ultimoEncontrado);
                resultadoLabel.setText("Encontrado: " + ultimoEncontrado.codigo);
            } else {
                // mostrar opciones en diálogo simple
                String[] opciones = new String[matches.size()];
                for (int i = 0; i < matches.size(); i++) opciones[i] = matches.get(i).toShortString();
                String sel = (String) JOptionPane.showInputDialog(this, "Varias coincidencias, elige una:",
                        "Resultados", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
                if (sel != null) {
                    for (Producto p : matches) {
                        if (p.toShortString().equals(sel)) {
                            ultimoEncontrado = p;
                            populateFields(p);
                            resultadoLabel.setText("Seleccionado: " + p.codigo);
                            break;
                        }
                    }
                }
            }
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }
    }

    private void listarProductos() {
        try {
            List<Producto> all = repo.findAll();
            if (all.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos registrados.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Producto p : all) sb.append(p.toString()).append("\n");
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            ta.setRows(Math.min(20, all.size() + 2));
            ta.setColumns(60);
            JScrollPane sp = new JScrollPane(ta);
            JOptionPane.showMessageDialog(this, sp, "Listado de productos", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al listar: " + ex.getMessage());
        }
    }

    private void mostrarUltimo() {
        if (ultimoEncontrado == null) {
            JOptionPane.showMessageDialog(this, "No hay producto seleccionado.");
        } else {
            JOptionPane.showMessageDialog(this, ultimoEncontrado.toString(), "Producto", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void populateFields(Producto p) {
        tfCodigo.setText(p.codigo);
        tfNombre.setText(p.nombre);
        tfCantidad.setText(String.valueOf(p.cantidad));
        tfPrecio.setText(String.valueOf(p.precio));
    }

    private void clearInputs() {
        tfCodigo.setText("");
        tfNombre.setText("");
        tfCantidad.setText("");
        tfPrecio.setText("");
        tfBuscar.setText("");
    }

    // Atajos de teclado (Ctrl+S, Ctrl+F, Ctrl+L)
    private void setupKeyBindings() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "guardar");
        am.put("guardar", new AbstractAction() {@Override
 public void actionPerformed(ActionEvent e) { guardarProducto(); } });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK), "buscar");
        am.put("buscar", new AbstractAction() {@Override
 public void actionPerformed(ActionEvent e) { buscarProducto(); } });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK), "listar");
        am.put("listar", new AbstractAction() {@Override
 public void actionPerformed(ActionEvent e) { listarProductos(); } });
    }

    // Cabecera animada simple 
    private void startHeaderAnimation() {
        final float[] hue = {0f};
        javax.swing.Timer timer = new javax.swing.Timer(40, e -> {
            hue[0] += 0.005f;
            if (hue[0] > 1f) hue[0] = 0f;
            header.setBackground(Color.getHSBColor(hue[0], 0.6f, 0.9f));
        });
        timer.start();
    }

    private void flashHeader(Color c) {
        Color orig = header.getBackground();
        header.setBackground(c);
        // usar javax.swing.Timer también aquí
        new javax.swing.Timer(300, e -> header.setBackground(orig)).start();
    }

    // -------------------------
    // Modelo Producto (serializable)
    // -------------------------
    private static class Producto implements Serializable {
        private static final long serialVersionUID = 1L;
        String codigo;
        String nombre;
        int cantidad;
        double precio;

        Producto(String codigo, String nombre, int cantidad, double precio) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precio = precio;
        }

        @Override
        public String toString() {
            return String.format("Código: %s | Nombre: %s | Cantidad: %d | Precio: %.2f",
                    codigo, nombre, cantidad, precio);
        }

        String toShortString() {
            return codigo + " - " + nombre;
        }
    }

    // -------------------------
    // Repositorio binario con backup
    // -------------------------
    private interface Repository {
        void save(Producto p) throws IOException;
        Producto findByCodigo(String codigo) throws IOException;
        List<Producto> findAll() throws IOException;
    }

    private static class BinaryRepository implements Repository {
        private final Path path;
        private final Path backup;

        BinaryRepository(String filename) {
            this.path = Paths.get(filename);
            this.backup = Paths.get(filename + ".bak");
            try {
                if (!Files.exists(path)) saveAll(new ArrayList<>());
            } catch (IOException e) {
                throw new RuntimeException("No se pudo inicializar repositorio", e);
            }
        }

        private synchronized void saveAll(List<Producto> list) throws IOException {
            if (Files.exists(path)) Files.copy(path, backup, StandardCopyOption.REPLACE_EXISTING);
            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
                oos.writeObject(list);
            }
        }

        @SuppressWarnings("unchecked")
        private synchronized List<Producto> loadAll() throws IOException {
            if (!Files.exists(path)) return new ArrayList<>();
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
                Object obj = ois.readObject();
                if (obj instanceof List) return (List<Producto>) obj;
                return new ArrayList<>();
            } catch (ClassNotFoundException | EOFException ex) {
                // intentar restaurar backup si existe
                if (Files.exists(backup)) {
                    Files.copy(backup, path, StandardCopyOption.REPLACE_EXISTING);
                    return loadAll();
                }
                return new ArrayList<>();
            }
        }

        @Override
        public synchronized void save(Producto p) throws IOException {
            List<Producto> all = loadAll();
            boolean replaced = false;
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).codigo.equalsIgnoreCase(p.codigo)) {
                    all.set(i, p);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) all.add(p);
            saveAll(all);
        }

        @Override
        public synchronized Producto findByCodigo(String codigo) throws IOException {
            List<Producto> all = loadAll();
            for (Producto p : all) if (p.codigo.equalsIgnoreCase(codigo)) return p;
            return null;
        }

        @Override
        public synchronized List<Producto> findAll() throws IOException {
            return loadAll();
        }
    }

    // -------------------------
    // Utilidad: Levenshtein y match difuso
    // -------------------------
    private static int levenshtein(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";
        a = a.toLowerCase();
        b = b.toLowerCase();
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    private static boolean fuzzyMatch(String query, Producto p) {
        if (query == null || query.trim().isEmpty()) return false;
        query = query.trim().toLowerCase();
        if (p.codigo.toLowerCase().contains(query) || p.nombre.toLowerCase().contains(query)) return true;
        int d1 = levenshtein(query, p.codigo);
        int d2 = levenshtein(query, p.nombre);
        int threshold = Math.max(1, Math.min(3, query.length() / 3));
        return d1 <= threshold || d2 <= threshold;
    }

    // -------------------------
    // Main
    // -------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventarioUnicoCorregido().setVisible(true));
    }
}
