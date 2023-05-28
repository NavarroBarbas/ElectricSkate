import java.sql.*;

/**
 * Clase principal del programa. 
 * 
 * @author Javier Navarro <p>
 * Andreu Juli� <p>
 * Valent�n Silva <p>
 * Pedro Caaveiro <p>
 * Juan Castresana
 * 
 */
public class ElectricSkate {
	
	/**
	 * Crea una conexi�n a la base de datos, 
	 * adem�s ejecuta el m�todo login para iniciar sesi�n en nuestro programa.
	 */
	public static void main(String[] args) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
			
			Usuarios.login(con, "electricskate");
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}
}
