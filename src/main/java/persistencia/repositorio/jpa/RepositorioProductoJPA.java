package persistencia.repositorio.jpa;

import persistencia.entitad.ProductoEntity;

/**
 * Interfaz para definir las operaciones que se pueden realizar sobre entidades producto
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public interface RepositorioProductoJPA
{
	/**
	 * Metodo que permite obtener un producto entity por un codigo
	 * @param codigo Codigo del producto a buscar/obtener en base de datos
	 * @return Una entidad (registro) del tipo producto
	 */
	ProductoEntity obtenerProductoEntityPorCodigo(String codigo);
}
