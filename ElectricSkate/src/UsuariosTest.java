import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UsuariosTest {

	private static Connection con = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
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
	public void testExisteUsuario() throws SQLException {		
		Statement stmt = null;

		try {
			stmt = con.createStatement();
		} catch(SQLException e) {
			
		} finally {
			stmt.close();
		}
	}

}
