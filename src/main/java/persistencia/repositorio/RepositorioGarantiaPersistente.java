package persistencia.repositorio;

//Importes necesarios para que la clase funcione
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import dominio.Producto;
import dominio.GarantiaExtendida;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import persistencia.builder.ProductoBuilder;
import persistencia.entitad.ProductoEntity;
import persistencia.entitad.GarantiaExtendidaEntity;
import persistencia.repositorio.jpa.RepositorioProductoJPA;

/**
 * Clase que implementa las operaciones de persistencia/consulta para las Garantias del sistema
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public class RepositorioGarantiaPersistente implements RepositorioGarantiaExtendida
{
	// Constantes para almacenar el nombre del atributo que se refiere al codigo (o identificador) del producto
	// y la consulta etiquetada para realizar las busquedas de productos por su codigo.
	private static final String CODIGO = "codigo";
	private static final String GARANTIA_EXTENDIDA_FIND_BY_CODIGO = "GarantiaExtendida.findByCodigo";
	
	// Variable para gestionar/manipular la base de datos
	private EntityManager entityManager;
	
	// Variable para registrar/obtener productos de la BD
	private RepositorioProductoJPA repositorioProductoJPA;
	
	/**
	 * Constructor de la clase, el cual establece/iniciliza el administrador de la base de datos y el adminstrador de los productos del sistema
	 * @param entityManager Variable con los datos de conexion a la base de datos
	 * @param repositorioProducto Variable con el gestor de los productos del sistema
	 */
	public RepositorioGarantiaPersistente(EntityManager entityManager, RepositorioProducto repositorioProducto)
	{
		this.entityManager = entityManager;
		this.repositorioProductoJPA = (RepositorioProductoJPA) repositorioProducto;
	}
	
	// Implementacion del metodo agregar, el cual toma la garantia dada en los parametros, la transforma a una entidad y despues la registra en la BD
	@Override
	public void agregar(GarantiaExtendida garantia)
	{
		GarantiaExtendidaEntity garantiaEntity = buildGarantiaExtendidaEntity(garantia);
		entityManager.persist(garantiaEntity);
	}
	
	// Implementacion del metodo obtenerProductoConGarantiaPorCodigo, el cual a partir del codigo entregado en los parametros
	// hace una busqueda de que garantia tiene dicho codigo y si se obtuvo alguna garantia entonces se retorna el objeto
	// asociada a la misma, en caso contrario se retorna nulo
	@Override
	public Producto obtenerProductoConGarantiaPorCodigo(String codigo)
	{
		GarantiaExtendidaEntity garantiaEntity = obtenerGarantiaEntityPorCodigo(codigo);
		return ProductoBuilder.convertirADominio(garantiaEntity != null ? garantiaEntity.getProducto() : null);
	}
	
	/**
	 * Metodo para buscar y obtener la garantia extendida asociada al codigo de producto especificado
	 * @param codigo Codigo del producto que tiene una garantia extendida
	 * @return La garantia (o la primera de ellas) asociada al codigo de producto, en caso de no encontrar nada retorna nada
	 */
	@SuppressWarnings("rawtypes") // Anotacion para suprimir avisos o advertencias.
	private GarantiaExtendidaEntity obtenerGarantiaEntityPorCodigo(String codigo)
	{
		Query query = entityManager.createNamedQuery(GARANTIA_EXTENDIDA_FIND_BY_CODIGO);
		query.setParameter(CODIGO, codigo);

		List resultList = query.getResultList();

		return !resultList.isEmpty() ? (GarantiaExtendidaEntity) resultList.get(0) : null;
	}
	
	/**
	 * Metodo para tranformar un objeto GarantiaExtendida a una entidad o registro perteneciente a la tabla GarantiaExtendida
	 * @param garantia Objeto GarantiaExtendida a transformar
	 * @return Una entidad GarantiaExtendida
	 */
	private GarantiaExtendidaEntity buildGarantiaExtendidaEntity(GarantiaExtendida garantia)
	{
		ProductoEntity productoEntity = repositorioProductoJPA.obtenerProductoEntityPorCodigo(garantia.getProducto().getCodigo());

		GarantiaExtendidaEntity garantiaEntity = new GarantiaExtendidaEntity();
		garantiaEntity.setProducto(productoEntity);
		garantiaEntity.setFechaSolicitudGarantia(garantia.getFechaSolicitudGarantia());

		return garantiaEntity;
	}
	
	// Implementacion del metodo obtener, el cual dado un codigo de producto retorna un objeto garantia extendida
	@Override
	public GarantiaExtendida obtener(String codigo)
	{
		GarantiaExtendidaEntity garantiaEntity = obtenerGarantiaEntityPorCodigo(codigo);

		return new GarantiaExtendida(ProductoBuilder.convertirADominio(garantiaEntity.getProducto()),
				garantiaEntity.getFechaSolicitudGarantia(),garantiaEntity.getFechaFinGarantia(),garantiaEntity.getPrecio(),
				garantiaEntity.getNombreCliente()
				);
	}
	
}
