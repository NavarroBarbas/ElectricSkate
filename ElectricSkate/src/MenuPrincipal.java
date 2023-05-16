import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MenuPrincipal {

	public static void menu() throws SQLException {
		Statement stmt = null;
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
			stmt = con.createStatement();
			
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
					Listar.MenuListar();
					break;
					
				case 4:
					//Método Usuarios.buscardni
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
				
		} finally {
			stmt.close();
		}	
	}
}
