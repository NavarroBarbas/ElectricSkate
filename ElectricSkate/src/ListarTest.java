import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.*;

public class ListarTest {
	//Creamos la conexi�n global a la BBDD
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
		//Despu�s de la clase cerramos la conexi�n a la BBDD
		con.close();
	}

	@Test
	public void testListarEmpleados() throws SQLException {
		//Se guarda lista de empleados.
	    String empleados = Listar.listarEmpleados(con, "electricskate");
	    
	    
	    //Creamos un String con lo que deber�a salir por pantalla 
	    //de usuarios que existen
	    String listaEsperada = "\nListando usuarios empleados...\n"
	    		+ "\n*************************\n"
	    		+ "Email: antofer@gmail.com\n"
	    		+ "Nombre: Antonio\n"
	    		+ "Apellidos: Fernandez\n"
	    		+ "Edad: 25 a�os\n"
	    		+ "DNI: 87654321A\n"
	    		+ "Contrase�a: Der11FeR32\n"
	    		+ "\n*************************\n"
	    		+ "Email: javiernavarro@gmail.com\n"
	    		+ "Nombre: javier\n"
	    		+ "Apellidos: navarro\n"
	    		+ "Edad: 26 a�os\n"
	    		+ "DNI: 25123423G\n"
	    		+ "Contrase�a: agua\n"
	    		+ "\n*************************\n"
	    		+ "Email: jonnieve@gmail.com\n"
	    		+ "Nombre: Jon\n"
	    		+ "Apellidos: Nieve\n"
	    		+ "Edad: 33 a�os\n"
	    		+ "DNI: 11223344D\n"
	    		+ "Contrase�a: JJonNieve333\n"
	    		+ "\n*************************\n"
	    		+ "Email: juan@gmail.com\n"
	    		+ "Nombre: Juan\n"
	    		+ "Apellidos: Pastor\n"
	    		+ "Edad: 28 a�os\n"
	    		+ "DNI: 11221122R\n"
	    		+ "Contrase�a: fresno\n";
	    
	    
	    //Se verifica que sea igual a la lista de empleados obtenida. 
	    //E imprimimos por pantalla la lista de empleados.
	    Assert.assertEquals(listaEsperada, empleados);
	    System.out.println(empleados);
	}


	@Test
	public void testListarClientes() throws SQLException {
		//Guardamos la lista de clientes
		//Si no hay ninguno simplemente devuelve la l�nea "\nListando usuarios clientes...\n"
		//Ya que la variable se inicializa as�.
		String clientes = Listar.listarClientes(con, "electricskate");
		
		//Comprobamos que la l�nea inicial deber�a contener
		//ese mensaje, si no lo hace, el m�todo quiere decir que falla
		//y mostrar� un mensaje de error.
		if(!clientes.contains("\nListando usuarios clientes...\n")) {
			Assert.fail("Deber�a mostrar la l�nea inicial del m�todo");
		}
		
		System.out.println(clientes);		
	}

	@Test
	public void testListarTodosUsuarios()throws SQLException {
		
		// GUARDO LA LISTA DE USUARIOS
		String listadoUsuarios = Listar.listarTodosUsuarios(con, "electricskate");
		// VERIFICO SI  LA CADENA LISTADO USUARIO NO ES IGUAL AL TEXTO
		Assert.assertNotEquals("Listado usuario", listadoUsuarios);
		System.out.println(listadoUsuarios);
	}
	

	@Test
	public void testListarPatinetesAlquilados() throws SQLException {		
		//Guardamos la lista de Patinetes Alquilados
		//Si no hay ninguno simplemente devuelve la l�nea "\nListando patinetes alquilados...\n"
		//Ya que la variable se inicializa as�.
		String patinetesAlquilados = Listar.listarPatinetesAlquilados(con, "electricskate");
		
		//En este caso como tenemos patinetes no nos devolver� solo esa frase
		Assert.assertNotEquals("\nListando patinetes alquilados...\n", patinetesAlquilados);
		
		System.out.println(patinetesAlquilados);
	}

	@Test
	public void testListarPatinetesNoAlquilados() throws SQLException {
		//Guardamos la lista de Patinetes no Alquilados
		//Si no hay ninguno simplemente devuelve la l�nea "\nListando patinetes NO alquilados...\n"
		//Ya que la variable se inicializa as�.
		String patinetesNoAlquilados = Listar.listarPatinetesNoAlquilados(con, "electricskate");
		
		//En este caso como tenemos patinetes no nos devolver� solo esa frase
		Assert.assertNotEquals("\nListando patinetes NO alquilados...\n", patinetesNoAlquilados);
	
		System.out.println(patinetesNoAlquilados);
	}
	
}
