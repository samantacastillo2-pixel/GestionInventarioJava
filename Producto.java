// clase que representa un producto dentro del sistema
public class Producto {

    // atributos que describen al producto
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidadStock;

    // constructor que asigna valores iniciales al crear el objeto
    public Producto(String codigo, String nombre, double precio, int cantidadStock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    // metodo que obtiene el codigo del producto
    public String getCodigo() { return codigo; }

    // metodo que actualiza el codigo del producto
    public void setCodigo(String codigo) { this.codigo = codigo; }

    // metodo que devuelve el nombre del producto
    public String getNombre() { return nombre; }

    // metodo que permite cambiar el nombre del producto
    public void setNombre(String nombre) { this.nombre = nombre; }

    // metodo que retorna el precio actual del producto
    public double getPrecio() { return precio; }

    // metodo que modifica el precio del producto
    public void setPrecio(double precio) { this.precio = precio; }

    // metodo que muestra la cantidad disponible en stock
    public int getCantidadStock() { return cantidadStock; }
    
    // metodo que actualiza la cantidad en inventario
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }

    // metodo que define como se mostrara el producto en texto
    @Override
    public String toString() {
        return codigo + " | " + nombre + " | Precio: " + precio + " | Stock: " + cantidadStock;
    }
}

