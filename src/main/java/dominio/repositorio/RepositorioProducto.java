package dominio.repositorio;

import dominio.Producto;

/**
 * Interfaz para definir las operaciones que se pueden realizar sobre objetos Producto
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public interface RepositorioProducto
{
	/**
	 * Metodo que permite obtener un producto dado un codigo
	 * @param codigo Codigo del producto a buscar/obtener
	 * @return Un objeto producto
	 */
	Producto obtenerPorCodigo(String codigo);

	/**
	 * Metodo que permite agregar un producto al repositorio
	 * @param producto Producto a registrar en la base de datos
	 */
	void agregar(Producto producto);

}