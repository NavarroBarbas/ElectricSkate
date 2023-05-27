import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.*;

public class UsuariosTest {

	//Creamos la conexión global a la BBDD
	private static Connection con = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		try {
			//Nos conectamos a la BBDD antes de ejecutar la clase
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//Después de la clase cerramos la conexión a la BBDD
		con.close();
	}
	
	@Test
	public void testExisteUsuario() throws SQLException {		
		//Llamamos al método existeUsuario con un usuario que existe
		//Si no existe el usuario devuelve un 0, si existe, deuelve un 1
		int existe = Usuarios.existeUsuario(con, "electricskate", "11221122D");
			
		//Ese usuario si existe, por lo que sería correcto
		Assert.assertEquals(1, existe);
		Assert.assertNotEquals(0, existe);
		
		//Este usuario no existe
		existe = Usuarios.existeUsuario(con, "electricskate", "22222222F");
		
		//Assert correcto, esperamos 0 y devuelve 0
		Assert.assertEquals(0, existe);
	}

}
