package persistencia.sistema;

// Importes necesarios para que la clase funcione
import javax.persistence.EntityManager;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import persistencia.conexion.ConexionJPA;
import persistencia.repositorio.RepositorioProductoPersistente;
import persistencia.repositorio.RepositorioGarantiaPersistente;

/**
 * Clase encargada de las operaciones basicas de la base de datos
 * @author Ceiba
 * @version 1.0.0
 * @since 29/10/2018
 */
public class SistemaDePersistencia
{
	// Variable para gestionar/manipular la base de datos
	private EntityManager entityManager;
	
	/**
	 * Constructor de la clase, el cual simplemente se encarga de iniciar la conexion con la base de datos e inicilizar el administrador de la BD
	 */
	public SistemaDePersistencia()
	{
		this.entityManager = new ConexionJPA().createEntityManager();
	}
	
	/**
	 * Metodo para inicializar el controlador de la tabla (entidad) Producto de la BD
	 * @return Un objeto administrador de los productos del sistema
	 */
	public RepositorioProducto obtenerRepositorioProductos()
	{
		return new RepositorioProductoPersistente(entityManager);
	}
	
	/**
	 * Metodo para inicializar el controlador de la tabla (entidad) GarantiaExtendida de la BD
	 * @return Un objeto administrador de las garantias del sistema
	 */
	public RepositorioGarantiaExtendida obtenerRepositorioGarantia()
	{
		return new RepositorioGarantiaPersistente(entityManager, this.obtenerRepositorioProductos());
	}
	
	/**
	 * Metodo para dar inicio a las transacciones que se van a efectuar en la base de datos
	 */
	public void iniciar()
	{
		entityManager.getTransaction().begin();
	}
	
	/**
	 * Metodo para ejecutar todas las transacciones formuladas a la BD hasta la ejecucion de este metodo
	 */
	public void terminar()
	{
		entityManager.getTransaction().commit();
	}
	
}
