import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class Usuarios {
	
	public static void login(Connection con, String BDNombre) throws SQLException {
		boolean estado = true;
		
		while(estado) {
			Scanner teclado = new Scanner(System.in);
			String email;
			String passwd;
			
			System.out.println("Bienvenido al programa!\n");
			System.out.println("Acceso al sistema");
			System.out.print("Email: ");
			email = teclado.nextLine();	
						
			System.out.print("contrasenya: ");
			passwd = teclado.nextLine();
			
			String queryEmail = "SELECT email, contrasenya FROM usuarios "
					+ "WHERE email = '" + email + "'"
					+ "AND contrasenya = '" + passwd + "'" ;		
			
			String queryAdmin = "SELECT email, rol FROM usuarios "
					+ "WHERE email = '" + email + "'";
			
			String queryUser = "SELECT email FROM usuarios "
					+ "WHERE email = '" + email + "'";
			
			Statement stmt = null;
			
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(queryEmail);
				int total = 0;
				
				while (rs.next()){
					   total++;
				}

				rs = stmt.executeQuery(queryAdmin);	
				String rol = "";
				while(rs.next()) {
					rol = rs.getString("rol");
				}
				
				rs = stmt.executeQuery(queryUser);
				int existeUser = 0;
				while(rs.next()) {
					existeUser++;
				}
				
				if(existeUser == 1) {
					if(rol.equals("administrador")) {
						if(total == 1) {
							System.out.println();
							estado = false;
							MenuPrincipal.menu();
						} else {
							System.out.println("\nEmpleado o contrasenya incorrecta\n");
						}
						
					} else {
						System.out.println("\nEl usuario debe ser administrador\n");
					}
				} else {
					System.out.println("\nEmpleado o contrasenya incorrecta\n");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				stmt.close();
			}
		}
	}


	public static void MenuUsuario(Connection con, String BDNombre) throws SQLException {
    	
    	// HAGO LA CONEXION A LA BASE DE DATOS 
        Statement stmt = null;
        
        try {
            stmt = con.createStatement();

            boolean estado = true;
            while (estado) {
                // LLAMO A LA CLASE SCANNER
                Scanner teclado = new Scanner(System.in);
                // CREO UN MENU COMO EL MENU PRINCIPAL
                System.out.println("Menú Principal: ElectricSkate\n");
                System.out.println("1. Gestionar Usuarios (Empleados/Clientes)");
                System.out.println("2. Gestionar Patinetes");
                System.out.println("3. Listar (Empleados/Clientes/Patinetes)");
                System.out.println("4. Buscar Por DNI");
                System.out.println("5. Salir\n");

                System.out.println("Elige una opción: ");
                int opcion = teclado.nextInt();
                // SALTO DE LINEA PARA EVITAR ERROR
                teclado.nextLine();

                // HAGO UN MENU CON SWITCH
                switch (opcion) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("\nAdiós\n");
                        estado = false;
                        break;
                    default:
                        System.out.println("\nElige un número entre 1 y 5\n");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // CERRAR CONEXION
           stmt.close();
        }


}
}
