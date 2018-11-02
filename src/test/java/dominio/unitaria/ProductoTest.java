package dominio.unitaria;

//Importes necesarios para que la clase funcione
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import dominio.Producto;
import testdatabuilder.ProductoTestDataBuilder;

/**
 * Clase para realizar pruebas unitarias sobre la clase producto
 * @author Sebastian Montoya Jimenez
 * @version 1.0.0
 * @since 31/10/2018
 */
public class ProductoTest
{
	// Constante con valores dummy (de prueba) para un producto
	private static final String CODIGO = "S01H1AT51";
	private static final String NOMBRE_PRODUCTO = "Impresora";
	private static final int PRECIO = 550000;
	
	// Prueba para revisar que se crea/instancia correctamente un objeto Producto
	@Test
	public void crearProductoTest()
	{
		// ----- arrange -----
		// En primer lugar, se prepara un objeto Producto de prueba con los valores dummy anteriormente creados
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder().
				conNombre(NOMBRE_PRODUCTO).
				conCodigo(CODIGO).
				conPrecio(PRECIO);
		
		// ----- act -----
		// Segundo, se genera/crea el objeto producto
		Producto producto = productoTestDataBuilder.build();
		
		// ----- assert -----
		// Y tercero, se valida que se haya creado dicho objeto producto con los datos especificados
		assertEquals(NOMBRE_PRODUCTO, producto.getNombre());
		assertEquals(CODIGO, producto.getCodigo());
		assertEquals(PRECIO, producto.getPrecio(), 0);
	}

}
