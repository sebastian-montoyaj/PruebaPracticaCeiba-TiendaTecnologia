package persistencia.entitad;

// Importes necesarios para que la clase funcione
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Clase encargada de definir los atributos de la tabla Producto y asociarse con dicha entidad en la BD
 * @author Ceiba
 * @version 1.0.0
 * @since 29/10/2018
 */
@Entity(name = "Producto") // Anotacion que permite vincular esta clase con la tabla Producto de la BD
@NamedQuery(name = "Producto.findByCodigo", query = "SELECT producto FROM Producto producto WHERE producto.codigo = :codigo") // Anotacion que permite registrar una Query SQL con un nombre o metodo especifico. Esto ayuda a que no se repitan consultas en el aplicativo y a realizar/ejecutar las mismas de una forma más sencilla
public class ProductoEntity
{
	@Id // Anotacion que indica que la siguiente variable corresponde a la llave primaria de la entidad
	@GeneratedValue(strategy = GenerationType.AUTO) // Anotación que establece la estrategia de asignacion de valor para la llave primaria
	private Long id; 

	@Column(nullable = false) // Anotacion para establecer que la variable es un atributo de la tabla
	private String codigo;
	
	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private double precio;
	
	// Los siguientes, son los metodos settern y gettern necesarios para cada uno de los atributos creados
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
