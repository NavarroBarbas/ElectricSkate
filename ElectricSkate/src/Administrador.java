import java.sql.*;
import java.util.Scanner;

public class Administrador extends Usuarios{

	//Atributos
	String contrase�a;
	
	//Constructor
	public Administrador(String nombre, String apellidos, int edad, 
			String dni, String email, String contrase�a) {
		super(nombre, apellidos, edad, dni, email);
		this.contrase�a = contrase�a;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	
	//M�todos
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
						
			System.out.print("Contrase�a: ");
			passwd = teclado.nextLine();
			
			String queryEmail = "SELECT email, contrase�a FROM usuarios "
					+ "WHERE email = '" + email + "'"
					+ "AND contrase�a = '" + passwd + "'" ;		
			
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
							System.out.println("\nEmpleado o contrase�a incorrecta\n");
						}
						
					} else {
						System.out.println("\nEl usuario debe ser administrador\n");
					}
				} else {
					System.out.println("\nEmpleado o contrase�a incorrecta\n");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				stmt.close();
			}
		}
	}
}
