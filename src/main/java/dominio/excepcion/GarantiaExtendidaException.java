package dominio.excepcion;

/**
 * Clase para el manejo personalizado de las excepciones y errores del proyecto
 * @author Ceiba
 * @version 1.0.0
 * @since 29/10/2018
 */
public class GarantiaExtendidaException extends RuntimeException
{
	// Constante que es necesario implementar cuando la clase hereda de la clase Exception. No es algo relevante, no prestarle atención
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo con el cual se tratan las excepciones o eventos inesperados del aplicativo
	 * @param message Mensaje de error o causa por la que se presento la excepcion
	 */
	public GarantiaExtendidaException(String message) {
		super(message);
		// Si se considera conveniente se puede utilizar la libreria Log4j para el registro de errores en algun tipo de registro o bitacora.
	}
}
