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
					crearCliente(con, "electricskate");
					break;
				case 2:
					crearAdministrador(con, "electricskate");
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
	public static void crearCliente(Connection con, String BDNombre) throws SQLException {

		Statement stmt = null;
		Scanner teclado = new Scanner(System.in);

		try {
			stmt = con.createStatement();

			// PIDO LOS DATOS AL USUARIO
			System.out.println("Por favor introduzca un nombre: ");
			String nombre = teclado.nextLine();
			System.out.println("\nEl nombre seleccionado es:" + nombre);
			System.out.println("\nPor favor introduzca el primer apellido: ");
			String apellido1 = teclado.nextLine();
			System.out.println("\nEl primer apellido es: " + apellido1);
			System.out.println("\nIntroduzca el segundo apellido: ");
			String apellido2 = teclado.nextLine();
			System.out.println("\nEl segundo apellido es: " + apellido2);
			System.out.println("\nIntroduzca su edad: ");
			String edad = teclado.nextLine();
			System.out.println("\nLa edad introducida es: " + edad);
			System.out.println("\nPor favor introduzca su DNI(8 numeros seguidos de una letra: ");
			String dni = teclado.nextLine();
			do {
				if (dni.length() != 9) {
					System.out.println("\nLa secuencia de numeros introducida es correcta");
					System.out.println("\nEL DNI introducido es: " +  dni);
				}
				System.out.println("Por favor vuelva a introducir su DNI");
				dni = teclado.nextLine();
			} while (dni.length() != 9);
			System.out.println("\nIntroduzca un email:");
			String email=teclado.nextLine();
			System.out.println("\nPor favor introduzca la contraseña(maximo 8 caracteres,3mas,3min,2numeros):");
			String contrasenya = teclado.nextLine();
			// CREO TRES VARIABLES PARA CONTAR
			int mayuscula = 0;
			int minuscula = 0;
			int numero = 0;
			// HAGO UN IF Y DENTRO UN BUCLE PARA RECORRER LA CONTRASEÑA Y DENTRO VOY
			// COMPROBANDO SI LA CONTRASEÑA TIENE
			// 3 MAYUSCULAS, 3 MINUSCULAS Y 2 NUMEROS
			if (contrasenya.length() >= 8 && contrasenya.length() <= 8) {
				for (int i = 0; i < contrasenya.length(); i++) {
					char caracteres = contrasenya.charAt(i);
					if (Character.isUpperCase(caracteres)) {
						mayuscula++;
					} else if (Character.isLowerCase(caracteres)) {
						minuscula++;

					} else if (Character.isDigit(caracteres)) {
						numero++;
					}
				}

				if (mayuscula == 3 && minuscula == 3 && numero == 2) {
					System.out.println("\nLa contraseña introducida es correcta. ");
				} else {
					System.out.println("\nLa contraseña introducida no cumple los parametros solicitados.");
				}
			} else {
				System.out.println("\nLa contraseña debe tener una longitud maxima de 8 caracteres.");
			}
			System.out.println("Por favor introduzca un rol: ");
			String rol=teclado.nextLine();

		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			stmt.close();
		}

	}

	// CREO EL METODO PARA PODER CREAR UN ADMINISTRADOR
	public static void crearAdministrador(Connection con, String BDNombre) throws SQLException {

	}

}
