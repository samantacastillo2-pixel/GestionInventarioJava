public class Producto {

    private String codigo;
    private String nombre;
    private double precio;
    private int cantidadStock;

    public Producto(String codigo, String nombre, double precio, int cantidadStock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | Precio: " + precio + " | Stock: " + cantidadStock;
    }
}

