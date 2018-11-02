package persistencia.entitad;

// Importes necesarios para que la clase funcione
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * Clase encargada de definir los atributos de la tabla GarantiaExtendida y asociarse con dicha entidad en la BD
 * @author Ceiba
 * @version 1.0.0
 * @since 29/10/2018
 */
@Entity(name = "GarantiaExtendida") // Anotacion que permite vincular esta clase con la tabla GarantiaExtendida de la BD
@NamedQuery(name = "GarantiaExtendida.findByCodigo", query = "SELECT garantia from GarantiaExtendida garantia where garantia.producto.codigo = :codigo") // Anotacion que permite registrar una Query SQL con un nombre o metodo especifico. Esto ayuda a que no se repitan consultas en el aplicativo y a realizar/ejecutar las mismas de una forma más sencilla
public class GarantiaExtendidaEntity
{
	@Id // Anotacion que indica que la siguiente variable corresponde a la llave primaria de la entidad
	@GeneratedValue(strategy = GenerationType.AUTO) // Anotación que establece la estrategia de asignacion de valor para la llave primaria
	private Long id;

	@ManyToOne // Anotacion para establecer el tipo de relacion entre este campo y el campo que se referencia en la otra entidad/tabla. Genera una relación de muchos a uno
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id") // Anotación para hacer referencia a la columna que es clave externa en la otra tabla y que se encarga de realizar la relación
	private ProductoEntity producto;

	private Date fechaSolicitudGarantia;

	private Date fechaFinGarantia;

	private String nombreCliente;

	private double precio;
	
	// Los siguientes, son los metodos settern y gettern necesarios para cada uno de los atributos creados
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductoEntity getProducto() {
		return producto;
	}

	public void setProducto(ProductoEntity producto) {
		this.producto = producto;
	}

	public Date getFechaSolicitudGarantia() {
		return fechaSolicitudGarantia;
	}

	public void setFechaSolicitudGarantia(Date fechaSolicitudGarantia) {
		this.fechaSolicitudGarantia = fechaSolicitudGarantia;
	}

	public Date getFechaFinGarantia() {
		return fechaFinGarantia;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public double getPrecio() {
		return precio;
	}

}
