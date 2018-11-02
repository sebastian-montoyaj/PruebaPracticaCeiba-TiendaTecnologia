package persistencia.repositorio;

//Importes necesarios para que la clase funcione
import javax.persistence.EntityManager;
import javax.persistence.Query;
import dominio.Producto;
import dominio.repositorio.RepositorioProducto;
import persistencia.builder.ProductoBuilder;
import persistencia.entitad.ProductoEntity;
import persistencia.repositorio.jpa.RepositorioProductoJPA;

/**
 * Clase que implementa las operaciones de persistencia/consulta para los productos del sistema
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public class RepositorioProductoPersistente implements RepositorioProducto, RepositorioProductoJPA
{
	private static final String CODIGO = "codigo"; // Constante que almacena el nombre del atributo del codigo (o identificador) del producto
	private static final String PRODUCTO_FIND_BY_CODIGO = "Producto.findByCodigo"; // Constante que almacena el nombre de la operacion para buscar un producto por su codigo (o identificador)
	// NOTA: Si no estoy mal, el sentido de crear constantes con valores para no diseminar valores o numeros a lo largo de un script
	//       es para evitar un antipatron, el cual si no me acuerdo mal se llama: "numeros magicos" o "valores magicos".
	
	// Variable para gestionar/manipular la base de datos
	private EntityManager entityManager;
	
	/**
	 * Constructor de la clase, el cual simplemente establece/iniciliza el administrador de la base de datos
	 * @param entityManager Variable con los datos de conexion a la base de datos
	 */
	public RepositorioProductoPersistente(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	// Implementacion del metodo obtenerPorCodigo, el cual simplemente invoca a su vez la implementación del metodo
	// obtenerProductoEntityPorCodigo para recuperar el producto de la base de datos y retorna lo encontrado.
	// Si el producto no se encuentra en la BD simplemente retorna Nulo.
	@Override
	public Producto obtenerPorCodigo(String codigo)
	{
		ProductoEntity productoEntity = obtenerProductoEntityPorCodigo(codigo);
		return ProductoBuilder.convertirADominio(productoEntity);
	}
	
	// Implementacion del metodo obtenerProductoEntityPorCodigo, en donde se invoca la respectiva consulta etiquetada previamente
	// en la clase ProductoEntity y se le entrega por que atributo y con que valor debe relizar la busqueda del producto.
	// Finalmente, se retorna un objeto del tipo ProductoEntity o Nulo sino encontro nada
	@Override
	public ProductoEntity obtenerProductoEntityPorCodigo(String codigo)
	{
		Query query = entityManager.createNamedQuery(PRODUCTO_FIND_BY_CODIGO);
		query.setParameter(CODIGO, codigo);

		return (ProductoEntity) query.getSingleResult();
	}
	
	// Implementacion del metodo agregar. Aqui se invoca ProductoBuilder para que transforme el objeto producto a una entidad producto
	// que luego sera almacenada/registrada en la BD mediante la operacion persist del administrador de la base de datos
	@Override
	public void agregar(Producto producto)
	{
		entityManager.persist(ProductoBuilder.convertirAEntity(producto));
	}
	
}
