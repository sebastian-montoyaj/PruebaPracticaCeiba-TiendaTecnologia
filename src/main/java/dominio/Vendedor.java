package dominio;

// Importes necesarios para que la clase funcione
import dominio.repositorio.RepositorioProducto;
import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioGarantiaExtendida;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase que implementa la logica de negocio para el vendedor de la tienda de tecnologia
 * @author Sebastian Montoya Jimenez
 * @version 1.0.0
 * @since 31/10/2018
 */
public class Vendedor
{
	// Constantes del sistema
	private static final String VOCALES = "aeiouAEIOUáéíóúÁÉÍÓÚ";
	public static final String EL_PRODUCTO_NO_EXISTE = "El producto no existe!";
	public static final String EL_PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantia extendida";
	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final int VALOR_REFERENCIA_PARA_GARANTIAS = 500000;
	public static final int PORCENTAJE_PARA_GARANTIAS_TIPO_A = 20;
	public static final int PORCENTAJE_PARA_GARANTIAS_TIPO_B  = 10;
	public static final int DIAS_PARA_GARANTIAS_TIPO_A = 200;
	public static final int DIAS_PARA_GARANTIAS_TIPO_B = 100;
	// Aclaracion: De manera arbitraria he decidido clasificar las garantias en dos clases:
	//             Las tipo A que son aquellas que cubren productos con precio mayor a 500K
	//             Y las tipo B para aquellas com precio menor o igual a 500K
	//             De esta forma, si mas adelante se decide cambiar el limite, los porcentajes o dias
	//             para estos tipos de garantias es simplemente cambiar los valores aqui depositados
	
	// Variables para tomar control de las entidades/tablas Producto y GarantiaExtendida de la BD
    private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;
    
    /**
     * Constructor de la clase, en el cual se inicializan los administradores de las entidades de la BD
     * @param repositorioProducto Objeto controlador/administrador de la tabla Producto
     * @param repositorioGarantia Objeto controlador/administrador de la tabla GarantiaExtendida
     */
    public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia)
    {
        this.repositorioProducto = repositorioProducto;
        this.repositorioGarantia = repositorioGarantia;
    }
    
    /**
     * Metodo para generar una garantia al producto y cliente especificados
     * @param codigo Codigo del producto a generarle una garantia
     * @param nombreCliente Nombre del cliente a vincularle la garantia
     * @throws GarantiaExtendidaException Este metodo presenta excepciones cuando el producto ya tiene una garantia o el producto como tal no existe
     */
    public void generarGarantia(String codigo, String nombreCliente) throws GarantiaExtendidaException
    {
    	// Antes de pasar a generar la garantia al producto indicado, se revisa:
    	// 1. Si el producto a generarle la garantia no esta en la base de datos
    	// 2. Si el producto tiene 3 vocales en su codigo
    	// y 3. Si el producto ya tiene una garantia
    	// En cualquiera de estos tres casos se interrumpe la ejecucion de este metodo
    	// y se lanza una excepcion con un mensaje adecuado para cada situacion
    	
    	if (!existeElProducto(codigo))
    	{
    		throw new GarantiaExtendidaException(EL_PRODUCTO_NO_EXISTE);
    	}
    	
    	if (Vendedor.contarVocales(codigo) == 3)
    	{
    		throw new GarantiaExtendidaException(EL_PRODUCTO_NO_CUENTA_CON_GARANTIA);
    	}
    	
    	if (tieneGarantia(codigo))
    	{
    		throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
    	}
    	
    	// Si se pasa los filtros anteriores entonces se procede a recuperar el producto y sus datos
    	Producto producto = repositorioProducto.obtenerPorCodigo(codigo);
    	
    	// Luego, se crean algunas variables en donde se calcularan los atributos (datos) de la garantia
    	double costoProducto = producto.getPrecio();
    	double precioGarantia = 0.0;
    	int diasGarantia = 0;
    	
    	// Ahora, Si el precio del producto es mayor al valor de referencia entonces
    	if (costoProducto > VALOR_REFERENCIA_PARA_GARANTIAS)
    	{
    		// Se procede a configurar las variables anteriores para una garantia tipo A
    		precioGarantia = costoProducto * (PORCENTAJE_PARA_GARANTIAS_TIPO_A/100);
    		diasGarantia = DIAS_PARA_GARANTIAS_TIPO_A;
    	}
    	else // Sino entonces
    	{
    		// Se procede a configurar las variables anteriores para una garantia tipo B
    		precioGarantia = costoProducto * (PORCENTAJE_PARA_GARANTIAS_TIPO_B/100);
    		diasGarantia = DIAS_PARA_GARANTIAS_TIPO_B;
    	}
    	
    	// Tambien, se toma la fecha de actual del sistema como fecha de inicio de la garantia
    	Date fechaInicioGarantia = Calendar.getInstance().getTime();
    	
    	// Y se calcula la fecha de terminacion de la misma
    	Date fechaFinGarantia = calcularFechaFinGarantia(fechaInicioGarantia, diasGarantia);
    	
    	// Ya con todos los datos necesarios, se prosigue con la creacion de un objeto garantia
    	GarantiaExtendida nuevaGarantia = new GarantiaExtendida(producto, fechaInicioGarantia, fechaFinGarantia, precioGarantia, nombreCliente);
    	
    	// Para finalmente, registrar esta garantia en la BD
    	repositorioGarantia.agregar(nuevaGarantia);
    }
    
    /**
     * Metodo para determinar si un producto tiene una garantia extendida asociada 
     * @param codigo Codigo del producto a revisar
     * @return Verdadero si el producto existe y tiene una garantia asociada, Falso de lo contrario
     */
    public boolean tieneGarantia(String codigo)
    {
    	// Para saber si un producto tiene una garantia, lo 1ero que se debe hacer es mirar SI el producto en primer lugar EXISTE
    	// Para ello tratamos de obtener el producto por su codigo
    	Producto product = repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
    	
    	// Ahora, si el producto es diferente de nulo (o sea, existe) entonces
    	if (product != null)
    	{
    		// Consultamos si dicho producto tiene alguna garantia asociada
    		GarantiaExtendida garantiaAsociada = repositorioGarantia.obtener(codigo);
    		
    		// Si el resultado de la consulta fue diferente de nulo entonces
    		if (garantiaAsociada != null)
    		{
    			// Retorno verdadero, porque si hay una garantia para el producto que esta registrado en la BD
    			return true;
    		}
    	}
    	
    	// En caso que el producto no exista o no tenga garantia alguna asociada simplemente retorno falso
        return false;
    }
    
    /**
     * Metodo para contar el numero de vocales existentes en una cadena de caracteres
     * @param cadena Cadena de caracteres a estudiar
     * @return Numero entero de vocales encontradas en la cadena
     */
    public static int contarVocales(String cadena)
    {
    	int contadorVocales = 0; // Primero, inicializo el contador de vocales
    	
    	for (int i = 0; i < cadena.length(); i++) // Luego, por cada caracter de la cadena a estudiar
    	{
    		for (int j = 0; j < VOCALES.length(); j++) // y por cada vocal (Acentuada o no) haga
    		{
    			if (cadena.charAt(i) == VOCALES.charAt(j)) // Compare si el caracter es una vocal, si lo es entonces
    			{
    				contadorVocales++; // se cuenta la vocal
    			}
    		}
    	}
    	
    	return contadorVocales; // Una vez se termina de recorrer la cadena de estudio, se retorna el conteo
    }
    
    /**
     * Metodo para calcular la fecha de terminacion de una garantia
     * @param fechaInicio Fecha de comienzo de una garantia
     * @param diasGarantia Dias efectivos para hacer valida la garantia
     * @return Un objeto tipo Date con la fecha de conclusion de la garantia
     */
    public static Date calcularFechaFinGarantia(Date fechaInicio, int diasGarantia)
    {
    	// En primer, creo algunas variables para ir recorriendo las fechas del calendario e ir contando los dias validos de la garantia
    	int contador = 0;
    	Calendar calendario = Calendar.getInstance();
    	calendario.setTime(fechaInicio);
    	
    	// Ahora, mientras no se llegue al maximo de dias validos para la terminacion de la garantia haga
    	while (contador < diasGarantia)
    	{
    		// Si el dia del calendario es diferente del lunes entonces
    		if (calendario.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
    		{
    			contador++; // Cuente este dia como un dia valido para hacer efectiva la garantia
    		}
    		
    		// Sin importar el condicional anterior, paso al dia siguiente del calendario
    		calendario.add(Calendar.DAY_OF_MONTH, 1);
    	}
    	
    	// Si despues de contar los dias validos, la fecha de finalizacion de la garantia cae un lunes entonces
    	if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
    	{
    		calendario.add(Calendar.DAY_OF_MONTH, 1); // Se permite que la garantia termine el martes
    	}
    	
    	// De forma similar, si la fecha de finalizacion de la garantia cae un domingo entonces
    	if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
    	{
    		calendario.add(Calendar.DAY_OF_MONTH, 2); // Se permite tambien que la garantia termine el martes
    	}
    	
    	// Por ultimo, se retorna la fecha de finalizacion estimada para la garantia
    	return calendario.getTime();
    }
    
    /**
     * Metodo para determinar si existe el producto con el codigo especificado
     * @param codigoProducto Codigo del producto con el que se realizara la busqueda
     * @return Verdadero si hay un producto que coincide con el codigo dado, Falso de lo contrario
     */
    public boolean existeElProducto(String codigoProducto)
    {
    	return repositorioProducto.obtenerPorCodigo(codigoProducto) != null;
    }
    
}
