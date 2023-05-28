import java.sql.*;

/**
 * Clase principal del programa. 
 * 
 * @author Javier Navarro <p>
 * Andreu Julià <p>
 * Valentín Silva <p>
 * Pedro Caaveiro <p>
 * Juan Castresana
 * 
 */
public class ElectricSkate {
	
	/**
	 * Crea una conexión a la base de datos, 
	 * además ejecuta el método login para iniciar sesión en nuestro programa.
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
