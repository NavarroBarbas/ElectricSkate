import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.*;

public class ListarTest {
	//Creamos la conexiï¿½n global a la BBDD
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
		//Despuï¿½s de la clase cerramos la conexiï¿½n a la BBDD
		con.close();
	}

	@Test
	public void testListarEmpleados() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testListarClientes() throws SQLException {
		//Guardamos la lista de clientes
		//Si no hay ninguno simplemente devuelve la línea "\nListando usuarios clientes...\n"
		//Ya que la variable se inicializa así.
		String clientes = Listar.listarClientes(con, "electricskate");
		
		//En este caso como tenemos clientes no nos devolvería solo esa frase
		Assert.assertNotEquals("\nListando usuarios clientes...\n", clientes);
		
		System.out.println(clientes);		
	}

	@Test
	public void testListarTodosUsuarios()throws SQLException {
		
		// GUARDO LA LISTA DE USUARIOS
		String listadoUsuarios=Listar.listarTodosUsuarios(con, "electricskate");
		// VERIFICO SI  LA CADENA LISTADO USUARIO NO ES IGUAL AL TEXTO
		Assert.assertNotEquals("Listado usuario", listadoUsuarios);
		System.out.println(listadoUsuarios);
		
	}
	

	@Test
	public void testListarPatinetesAlquilados() throws SQLException {		
		//Guardamos la lista de Patinetes Alquilados
		//Si no hay ninguno simplemente devuelve la lï¿½nea "\nListando patinetes alquilados...\n"
		//Ya que la variable se inicializa asï¿½.
		String patinetesAlquilados = Listar.listarPatinetesAlquilados(con, "electricskate");
		
		//En este caso como tenemos patinetes no nos devolverï¿½ solo esa frase
		Assert.assertNotEquals("\nListando patinetes alquilados...\n", patinetesAlquilados);
		
		System.out.println(patinetesAlquilados);
	}

	@Test
	public void testListarPatinetesNoAlquilados() throws SQLException {
		//Guardamos la lista de Patinetes no Alquilados
		//Si no hay ninguno simplemente devuelve la lï¿½nea "\nListando patinetes NO alquilados...\n"
		//Ya que la variable se inicializa asï¿½.
		String patinetesNoAlquilados = Listar.listarPatinetesNoAlquilados(con, "electricskate");
		
		//En este caso como tenemos patinetes no nos devolverï¿½ solo esa frase
		Assert.assertNotEquals("\nListando patinetes NO alquilados...\n", patinetesNoAlquilados);
	
		System.out.println(patinetesNoAlquilados);
	}
	
}
