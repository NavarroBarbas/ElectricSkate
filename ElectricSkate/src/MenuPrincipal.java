import java.sql.*;
import java.util.Scanner;

/**
 * Clase Menú Principal, tras hacer correctamente el login en el programa.
 * 
 * @author Javier Navarro <p>
 * Andreu Julià <p>
 * Valentín Silva <p>
 * Pedro Caaveiro <p>
 * Juan Castresana
 *
 */
public class MenuPrincipal {

	/**
	 * Tendrá el Menú Principal del programa, se presentará 
	 * este menú con las diferentes opciones disponibles. <p>
	 * - Gestionar Usuarios <p>
	 * - Gestionar Patinetes <p>
	 * - Listar <p>
	 * - Buscar por DNI <p>
	 * 
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
	public static void menu() throws SQLException {	
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
			
			boolean estado = true;
			
			while(estado) {
				Scanner teclado = new Scanner(System.in);
				
				System.out.println("Menú Principal: ElectricSkate\n");
				System.out.println("1. Gestionar Usuarios (Empleados/Clientes)");
				System.out.println("2. Gestionar Patinetes");
				System.out.println("3. Listar (Empleados/Clientes/Patinetes)");
				System.out.println("4. Buscar Por DNI");
				System.out.println("5. Salir\n");
				
				System.out.print("Elige una opción: ");
				int opcion = teclado.nextInt();
				teclado.nextLine();
				
				switch (opcion) {
				case 1:
					Usuarios.MenuUsuario(con, "electricskate");
					break;
					
				case 2:
					Patinete.MenuGestionarPatinetes(con, "electricskate");
					break;
				
				case 3:
					Listar.MenuListar(con, "electricskate");
					break;
					
				case 4:
					Usuarios.buscarDni(con, "electricskate");
					break;

				case 5: 
					System.out.println("\nAdiós\n");
					Usuarios.login(con, "electricskate");
					break;
			
				default:
					System.out.println("\nElige un número entre 1 y 5\n");
					break;
				}    
			}
		} catch (SQLException e) {
			e.printStackTrace();
				
		}
	}
}
