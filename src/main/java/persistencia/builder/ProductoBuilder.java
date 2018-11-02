package persistencia.builder;

// Importes necesarios para que la clase funcione
import dominio.Producto;
import persistencia.entitad.ProductoEntity;

/**
 * Clase que permite la transformación de objetos producto a entidades productos, o viceversa
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public class ProductoBuilder
{
	/**
	 * Construstor de la clase (como tal no cumple ninguna funcion)
	 */
	private ProductoBuilder() {}
	
	/**
	 * Metodo estatico para transformar una entidad o registro perteneciente a la tabla Producto a un objeto Producto de la aplicación
	 * @param productoEntity Entidad producto a transformar
	 * @return un objeto producto
	 */
	public static Producto convertirADominio(ProductoEntity productoEntity)
	{
		Producto producto = null;
		
		if(productoEntity != null)
		{
			producto = new Producto(productoEntity.getCodigo(), productoEntity.getNombre(), productoEntity.getPrecio());
		}
		
		return producto;
	}
	
	/**
	 * Metodo para tranformar un objeto Producto a una entidad o registro perteneciente a la tabla Producto
	 * @param producto Objeto producto a transformar
	 * @return Una entidad producto
	 */
	public static ProductoEntity convertirAEntity(Producto producto)
	{
		ProductoEntity productoEntity = new ProductoEntity();
		
		productoEntity.setCodigo(producto.getCodigo());
		productoEntity.setNombre(producto.getNombre());
		productoEntity.setPrecio(producto.getPrecio());
		
		return productoEntity;
	}
}
