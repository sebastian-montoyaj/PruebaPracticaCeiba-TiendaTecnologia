package dominio;

/**
 * Clase Producto, es la clase encargada de representar los objetos tipo producto en el dominio de la aplicacion
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public class Producto
{
	// Los siguientes son los atributos (Campos) que describen un objeto producto
	private String codigo;
	private String nombre;
	private double precio;
	
	/**
	 * Constructor de la clase, el cual permite crear/definir un objeto producto
	 * @param codigo Codigo del producto
	 * @param nombre Nombre del producto
	 * @param precio Precio del producto
	 */
	public Producto(String codigo, String nombre, double precio)
	{
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	// Metodos gettern para recuperar la informacion de un objeto producto
	
	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPrecio() {
		return precio;
	}
	
}
