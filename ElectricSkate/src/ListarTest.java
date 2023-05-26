import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ListarTest {

	private static Connection con = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
			System.out.println("Conexion realizada");
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		con.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListarEmpleados() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testListarClientes() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testListarTodosUsuarios() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testListarPatinetesAlquilados() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testListarPatinetesNoAlquilados() {
		throw new RuntimeException("not yet implemented");
	}

}
