import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class Usuarios {

	public static void login(Connection con, String BDNombre) throws SQLException {
		boolean estado = true;

		while (estado) {
			Scanner teclado = new Scanner(System.in);
			String email;
			String passwd;

			System.out.println("Bienvenido al programa!\n");
			System.out.println("Acceso al sistema");
			System.out.print("Email: ");
			email = teclado.nextLine();

			System.out.print("contrasenya: ");
			passwd = teclado.nextLine();

			String queryEmail = "SELECT email, contrasenya FROM usuarios " + "WHERE email = '" + email + "'"
					+ "AND contrasenya = '" + passwd + "'";

			String queryAdmin = "SELECT email, rol FROM usuarios " + "WHERE email = '" + email + "'";

			String queryUser = "SELECT email FROM usuarios " + "WHERE email = '" + email + "'";

			Statement stmt = null;

			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(queryEmail);
				int total = 0;

				while (rs.next()) {
					total++;
				}

				rs = stmt.executeQuery(queryAdmin);
				String rol = "";
				while (rs.next()) {
					rol = rs.getString("rol");
				}

				rs = stmt.executeQuery(queryUser);
				int existeUser = 0;
				while (rs.next()) {
					existeUser++;
				}

				if (existeUser == 1) {
					if (rol.equals("administrador")) {
						if (total == 1) {
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
				System.out.println("Menu Principal: ElectricSkate\n");
				System.out.println("1. Crear Usuarios (Empleados/Clientes)");
				System.out.println("2. Crear Administrador");
				System.out.println("3. Salir\n");

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
					System.out.println("\nAdios\n");
					estado = false;
					break;

				default:
					System.out.println("\nElige un numero entre 1 y 3\n");
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
	// CREO EL METODO PARA PODER CREAR UN USUARIO
	public static void crearUsuario(Connection con, String BDNombre) throws SQLException {
		
		Statement stmt=null;
		Scanner teclado=new Scanner(System.in);
		
		try {
			stmt=con.createStatement();
			
			System.out.println("Desea crear un Usuario (Empleado/Cliente");
			String opcion=teclado.nextLine();
			switch (opcion.toUpperCase()) {
			case "S":
				// PIDO LOS DATOS AL USUARIO
				System.out.println("Por favor introduzca un nombre: ");
				String nombre=teclado.nextLine();
				System.out.println("El nombre seleccionado es:" + nombre);
				System.out.println("Por favor introduzca el primer apellido: ");
				String apellido1=teclado.nextLine();
				System.out.println("El primer apellido es: " + apellido1);
				System.out.println("Introduzca el segundo apellido: ");
				String apellido2=teclado.nextLine();
				System.out.println("El segundo apellido es: " + apellido2);
				System.out.println("Introduzca su edad: " );
				String edad= teclado.nextLine();
				System.out.println("La edad introducida es: " + edad);
				System.out.println("Por favor introduzca su dni(8 numeros seguidos de una letra: ");
				String dni=teclado.nextLine();
				
				
				
				break;
			case "N":
				MenuUsuario(con, "electricskate");
				break;

			default:
				System.out.println("Por favor eliga S/N");
				break;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	// CREO EL METODO PARA PODER CREAR UN ADMINISTRADOR
	public static void crearAdministrador(Connection con, String BDNombre) throws SQLException {
		
		
		
	}
	
}
