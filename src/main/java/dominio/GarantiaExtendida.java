package dominio;

//Importe necesario para que la clase funcione
import java.util.Date;

/**
 * Clase GarantiaExtendida, es la clase encargada de representar los objetos tipo garantia extendida en el dominio de la aplicacion
 * @author Ceiba
 * @version 1.0.0
 * @since 30/10/2018
 */
public class GarantiaExtendida
{
	// Los siguientes son los atributos (Campos) que describen un objeto GarantiaExtendida
    private Producto producto;
    private Date fechaSolicitudGarantia;
    private Date fechaFinGarantia;
    private double precioGarantia;
    private String nombreCliente;
    
    /**
     * Primer constructor de la clase, el cual crea e inicializa una garantia a partir de la fecha actual y con el producto dado en los parametros
     * @param producto Objeto producto a asociar con esta garantia
     */
    public GarantiaExtendida(Producto producto)
    {
        this.fechaSolicitudGarantia = new Date();
        this.producto = producto;
    }
    
    /**
     * Segundo contructor, el cual crea/llena una garantia nueva a partir de los datos especificados en los parametros
     * @param producto Objeto producto a asociar con esta garantia
     * @param fechaSolicitudGarantia Fecha de inicio de la garantia
     * @param fechaFinGarantia Fecha de terminacion de la garantia
     * @param precioGarantia Precio/valor de la garantia
     * @param nombreCliente Nombre del comprador/cliente que adquiere la garantia
     */
    public GarantiaExtendida(Producto producto, Date fechaSolicitudGarantia, Date fechaFinGarantia, double precioGarantia, String nombreCliente)
    {
        this.producto = producto;
        this.fechaSolicitudGarantia = fechaSolicitudGarantia;
        this.fechaFinGarantia = fechaFinGarantia;
        this.precioGarantia = precioGarantia;
        this.nombreCliente = nombreCliente;
    }
    
    // Metodos gettern para recuperar la informacion de un objeto GarantiaExtendida
    
    public Producto getProducto() {
        return producto;
    }

    public Date getFechaSolicitudGarantia() {
        return fechaSolicitudGarantia;
    }

    public Date getFechaFinGarantia() {
        return fechaFinGarantia;
    }

    public double getPrecioGarantia() {
        return precioGarantia;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    
}
