package persistencia.conexion;

// Importes necesarios para que la clase funcione
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase encargada de crear la conexion e inicializar la BD
 * @author Ceiba
 * @version 1.0.0
 * @since 29/10/2018
 */
public class ConexionJPA
{
	// Primero, se define la constante con el nombre especifico de la BD a utilizar
	// OJO: El nombre que se deposita aqui debe coincidir con el que esta en el archivo persistence.xml
	private static final String TIENDA = "tienda";
	
	// Luego, se crea un objeto EntityManagerFactory el cual permitira abrir la conexion a la base de datos y crear objetos EntityManager, los cuales son necesarios para manipular la BD
	private static EntityManagerFactory entityManagerFactory;
	
	/**
	 * Metodo para inicilizar la base de datos
	 */
	public ConexionJPA()
	{
		entityManagerFactory = Persistence.createEntityManagerFactory(TIENDA);
	}
	
	/**
	 * Metodo para crear un objeto EntityManager.
	 * Este tipo de objetos son los que nos permitiran gestionar las entidades de la BD, es decir, son los encargados de insertar, eliminar, actualizar o buscar datos en la BD.
	 * @return Objeto EntityManager encargado de administrar la BD
	 */
	public EntityManager createEntityManager()
	{
		return entityManagerFactory.createEntityManager();
	}
}
