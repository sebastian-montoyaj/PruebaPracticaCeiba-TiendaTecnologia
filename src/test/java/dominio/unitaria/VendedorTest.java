package dominio.unitaria;

//Importes necesarios para que la clase funcione
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import dominio.Vendedor;
import dominio.excepcion.GarantiaExtendidaException;
import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import testdatabuilder.ProductoTestDataBuilder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase para realizar pruebas unitarias sobre la clase vendedor
 * @author Sebastian Montoya Jimenez
 * @version 1.0.0
 * @since 31/10/2018
 */
public class VendedorTest
{
	// Constantes con valores dummy para hacer pruebas
	private static final String CODIGO = "CT03-AW7237";
	private static final String NOMBRE_PRODUCTO = "Moto G6 PLUS";
	private static final int PRECIO = 900000;
	private static final String CLIENTE_PRUEBA = "FULANITO";
	private static final String CODIGO_CON_TRES_VOCALES = "AE16-RU4169";
	
	// Prueba para verificar que el metodo tieneGarantia si es capaz de detectar cuando un producto NO TIENE una garantia extendida
	@Test
	public void productoNoTieneGarantiaTest()
	{
		// ----- arrange -----
		// Primero, se prepara y crea un objeto producto
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build(); 
		
		// Despues, se crean objetos "simulados" para el control de las entidades Producto y GarantiaExtendida de la BD
		// Esto se puede hacer mediante el metodo mock( ), el cual permite crear objetos de prueba (que no se conectan a la BD) y que aislan dichas implementaciones de otras pruebas
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		// Posteriormente, se establece que cuando se invoque el metodo obtenerProductoConGarantiaPorCodigo dentro del metodo tieneGarantia
		// se devolvera simplemente nulo. De esta manera, se simula (o se finge) que la base de datos esta retornando nulo (o sea, que no se encontro garantia)
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);
		
		// Ya para terminar la etapa arrange, creamos un objeto Vendedor y lo inicializamos con los controladores "simulados" creados previamente
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		// ----- act -----
		// Despues de la preparacion, se intenta obtener la garantia asociada al codigo del producto creado previamente
		boolean existeProducto = vendedor.tieneGarantia(producto.getCodigo());
		
		// ----- assert -----
		// Finalmente, se revisa que el producto en verdad NO posea ninguna garantia
		assertFalse(existeProducto);
	}
	
	// Prueba para verificar que el metodo tieneGarantia si es capaz de detectar cuando un producto SI TIENE una garantia extendida
	@Test
	public void productoYaTieneGarantiaTest()
	{
		// ----- arrange -----
		// Primero, se prepara y crea un objeto producto y un objeto garantia extendida
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.build();
		GarantiaExtendida garantiaAsociada = new GarantiaExtendida(producto);
		
		// Luego, se crean objetos "simulados" para el control de las entidades Producto y GarantiaExtendida de la BD
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		// Ahora, se crean escenarios de que cuando se intente acceder a producto se devolvera el antes creado
		// Y lo mismo para una garantia extendida. De esta forma y al igual que con la prueba anterior se simulara
		// obtener un producto y una garantia que estan registradas en la BD
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);
		when(repositorioGarantia.obtener(producto.getCodigo())).thenReturn(garantiaAsociada);
		
		// Ya para terminar la etapa arrange, creamos un objeto Vendedor y lo inicializamos con los controladores creados previamente
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		// ----- act ----- 
		// Despues de la preparacion, se intenta obtener la garantia asociada al codigo del producto creado previamente
		boolean existeProducto = vendedor.tieneGarantia(producto.getCodigo());
		
		// ----- assert -----
		// Y finalmente, se revisa que el producto en verdad NO posea ninguna garantia
		assertTrue(existeProducto);
	}
	
	// Prueba para revisar el metodo para contar vocales esta funcionando apropiadamente
	@Test
	public void contarVocalesTest()
	{
		// ----- arrange -----
		// Primero creo algunas cadenas de prueba con diferentes escenarios
		String cadenaDePrueba1 = "Hola mundo!";
		String cadenaDePrueba2 = "Esto es unA prUeba de cOnteO de vocales.";
		String cadenaDePrueba3 = "123456789-.,=SQW&BTY";
		String cadenaDePrueba4 = "øDetectar· la puntuaci”n de las vocales?";
		String cadenaDePrueba5 = "aeiouAEIOU·ÈÌÛ˙¡…Õ”⁄";
		
		// ----- act -----
		// Luego procedo a realizar el conteo de las vocales en cada caso
		int conteoDeVocales1 = Vendedor.contarVocales(cadenaDePrueba1);
		int conteoDeVocales2 = Vendedor.contarVocales(cadenaDePrueba2);
		int conteoDeVocales3 = Vendedor.contarVocales(cadenaDePrueba3);
		int conteoDeVocales4 = Vendedor.contarVocales(cadenaDePrueba4);
		int conteoDeVocales5 = Vendedor.contarVocales(cadenaDePrueba5);
		
		// ----- assert -----
		// Finalmente, compruebo que los conteos si hayan sido los correctos
		assertEquals(conteoDeVocales1, 4);
		assertEquals(conteoDeVocales2, 16);
		assertEquals(conteoDeVocales3, 0);
		assertEquals(conteoDeVocales4, 15);
		assertEquals(conteoDeVocales5, 20);
	}
	
	// Prueba 1 para revisar el metodo existeElProducto (NO existe el producto)
	@Test
	public void noExisteElProductoTest()
	{
		// ----- arrange -----
		// Se crean objetos "simulados" para el control de las entidades Producto y GarantiaExtendida de la BD
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		// Ya para terminar la etapa arrange, creamos un objeto Vendedor y lo inicializamos con los controladores "simulados" creados previamente
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		// ----- act -----
		// Despues de la preparacion, se intenta obtener un producto que simplemente no esta registrado en la BD
		boolean existeProducto = vendedor.existeElProducto(CODIGO);
		
		// ----- assert -----
		// Finalmente, se revisa que el producto en verdad NO exista
		assertFalse(existeProducto);
	}
	
	// Prueba 2 para revisar el metodo existeElProducto (SI existe el producto)
	@Test
	public void existeElProductoTest()
	{
		// ----- arrange -----
		// Primero, se prepara y crea un objeto producto
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conCodigo(CODIGO).conNombre(NOMBRE_PRODUCTO).conPrecio(PRECIO);
		Producto producto = productoestDataBuilder.build();
		
		// Luego, se crean objetos "simulados" para el control de las entidades Producto y GarantiaExtendida de la BD
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		// Ahora, se establece que cuando se invoque el metodo obtenerPorCodigo dentro del metodo existeElProducto
		// se devolvera el producto creado. De esta manera, se simula (o se finge) que la base de datos si tiene dicho producto
		when(repositorioProducto.obtenerPorCodigo(CODIGO)).thenReturn(producto);
		
		// Para terminar la etapa arrange, creamos un objeto Vendedor y lo inicializamos con los controladores "simulados" creados previamente
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		// ----- act -----
		// Despues de la preparacion, se intenta obtener el producto creado con anterioridad
		boolean existeProducto = vendedor.existeElProducto(CODIGO);
		
		// ----- assert -----
		// Finalmente, se revisa que el producto en verdad SI exista
		assertTrue(existeProducto);
	}
	
	// Prueba para revisar que el metodo calcularFechaFinGarantia si este trabajando correctamente
	@Test
	public void calcularFechaFinGarantiaTest()
	{
		// ----- arrange -----
		// Para empezar, se crea una variable Calendar para poder generar fechas con facilidad
		Calendar calendario = Calendar.getInstance();
		
		// Luego, se crea una variable SimpleDateFormat para poder convertir fechas contenidas en variables tipo Date a cadenas de strings (Esto ayudara en la comparacion)
		SimpleDateFormat formateadorFechas = new SimpleDateFormat("yyyy-MM-dd");
		
		// Ahora, creamos algunas fechas de prueba
		calendario.set(2018, Calendar.AUGUST, 16);
		Date fechaInicio1 = calendario.getTime();
		calendario.set(2018, Calendar.OCTOBER, 29);
		Date fechaInicio2 = calendario.getTime();
		calendario.set(2018, Calendar.NOVEMBER, 7);
		Date fechaInicio3 = calendario.getTime();
		calendario.set(2018, Calendar.DECEMBER, 24);
		Date fechaInicio4 = calendario.getTime();
		calendario.set(2019, Calendar.JANUARY, 25);
		Date fechaInicio5 = calendario.getTime();
		calendario.set(2019, Calendar.JANUARY, 29);
		Date fechaInicio6 = calendario.getTime();
		// OJO: La clase Calendar maneja los meses de 0 a 11 por lo que si se van a usar
		//      los meses de 1 a 12, no olvide restar -1 al mes. Sino, usar simplemente
		//      Calendar.{MES EN INGLES} para evitar incovenientes.
		
		
		// ----- act -----
		// Ya con nuestras fechas de prueba preparadas se procede a calcular fechas de terminacion para varios escenarios
		Date fechaFin1 = Vendedor.calcularFechaFinGarantia(fechaInicio1, 200);
		Date fechaFin2 = Vendedor.calcularFechaFinGarantia(fechaInicio2, 3);
		Date fechaFin3 = Vendedor.calcularFechaFinGarantia(fechaInicio3, 30);
		Date fechaFin4 = Vendedor.calcularFechaFinGarantia(fechaInicio4, 20);
		Date fechaFin5 = Vendedor.calcularFechaFinGarantia(fechaInicio5, 10);
		Date fechaFin6 = Vendedor.calcularFechaFinGarantia(fechaInicio6, 12);
		Date fechaFin7 = Vendedor.calcularFechaFinGarantia(fechaInicio6, 11);
		
		// ----- assert -----
		// Finalmente, se valida que las fechas de terminacion si terminen (valga la redundancia) cuando deben
		assertEquals(formateadorFechas.format(fechaFin1), "2019-04-06");
		assertEquals(formateadorFechas.format(fechaFin2), "2018-11-02");
		assertEquals(formateadorFechas.format(fechaFin3), "2018-12-12");
		assertEquals(formateadorFechas.format(fechaFin4), "2019-01-17");
		assertEquals(formateadorFechas.format(fechaFin5), "2019-02-06");
		assertEquals(formateadorFechas.format(fechaFin6), "2019-02-12");
		assertEquals(formateadorFechas.format(fechaFin7), "2019-02-12");
	}

}
