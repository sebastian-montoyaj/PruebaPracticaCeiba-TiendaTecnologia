package dominio.repositorio;

import dominio.Producto;
import dominio.GarantiaExtendida;

/**
 * Interfaz para definir las operaciones que se pueden realizar sobre objetos GarantiaExtendida
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public interface RepositorioGarantiaExtendida
{
	/**
	 * Metodo que permite obtener un producto con garantia extendida dado un codigo
	 * @param codigo Codigo asociado al producto con garantia extendida
	 * @return Un objeto producto
	 */
	Producto obtenerProductoConGarantiaPorCodigo(String codigo);
	
	/**
	 * Metodo que permite agregar una garantia al repositorio de garantia
	 * @param garantia Garantia a registrar en la base de datos
	 */
	void agregar(GarantiaExtendida garantia);
	
	/**
	 * Metodo que permite obtener una garantia extendida por el codigo del producto
	 * @param codigo Codigo del producto
	 */
	GarantiaExtendida obtener(String codigo);

}
