package testdatabuilder;

import dominio.Producto;

/**
 * Clase para realizar/crear objetos producto de prueba
 * @author Ceiba
 * @version 1.0.0
 * @since 31/10/2018
 */
public class ProductoTestDataBuilder
{
	// Constantes con valores dummy
	private static final String CODIGO= "F01TSA0150";
	private static final String NOMBRE = "Computador Lenovo";
	private static final double PRECIO = 780000;
	
	// Atributos basicos de un producto
	private String codigo;
	private String nombre;
	private double precio;
	
	/**
	 * Constructor de la clase, el cual iniciliza la clase o producto con los valores dummy
	 */
	public ProductoTestDataBuilder() {
		this.codigo = CODIGO;
		this.nombre = NOMBRE;
		this.precio = PRECIO;
	}
	
	// Los siguientes tres metodos permiten la modificacion de los atributos de un producto
	
	public ProductoTestDataBuilder conCodigo(String cedula) {
		this.codigo=cedula;
		return this;
	}

	public ProductoTestDataBuilder conNombre(String nombre) {
		this.nombre=nombre;
		return this;
	}

	public ProductoTestDataBuilder conPrecio(double costo) {
		this.precio = costo;
		return this;
	}
	
	// Y este ultimo metodo permite recuperar el producto de prueba
	public Producto build() {
		return new Producto(this.codigo, this.nombre, this.precio);
	}
}
