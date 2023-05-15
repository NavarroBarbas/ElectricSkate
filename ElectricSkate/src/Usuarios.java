<<<<<<< HEAD
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
=======
import java.sql.*;
>>>>>>> branch 'master' of git@github.com:NavarroBarbas/ElectricSkate.git
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
				System.out.println("GESTIONAR USUARIOS (EMPLEADOS/CLIENTES)\n");
				System.out.println("1. Añadir Usuario Cliente");
				System.out.println("2. Añadir Usuario Empleado");
				System.out.println("3. Atras\n");

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
					crearEmpleado(con, "electricskate");
					break;
				case 3:
					MenuPrincipal.menu();
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

		PreparedStatement stmt = null;
		Scanner teclado = new Scanner(System.in);

		try {
			
			System.out.println("AÑADIR USUARIO CLIENTE (Escriba exit para volver atrás)");
			// PIDO LOS DATOS AL USUARIO
			System.out.println("\nNombre: ");
			String nombre = teclado.nextLine();
			if (nombre.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.println("\nApellidos: ");
			String apellido = teclado.nextLine();
			if (apellido.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.println("\nEdad: ");
			String edad = teclado.nextLine();
			if (edad.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.println("\nDNI(8 numeros seguidos de una letra: ");
			String dni = teclado.nextLine();
			if (dni.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			// HAGO UN WHILE PARA RECORRER EL DNI Y COMPROBAR SI SE HAN INTRODUCIDO CORRECTAMENTE LOS DIGITOS 
			while (dni.length() != 9) {

				System.out.println("\nLa secuencia de números introducida no es correcta");
				System.out.println("Por favor vuelva a introducir su DNI");
				dni = teclado.nextLine();
			}
			System.out.println("\nLa secuencia de números introducida es correcta");
			

			System.out.println("\nE-mail:");
			String email = teclado.nextLine();
			
			System.out.println("GUARDAR? SI/NO (no vuelve al menú anterior");
			String opcion=teclado.nextLine();
			
			// ASI PUEDE SER EN MAYUSCULA Y MINUSCULA
			opcion=opcion.toUpperCase();
			//HAGO EL INSERT PARA INTRODUCIR LOS DATOS EN LA TABLA
			if (opcion.equals("SI")) {
				
				try {
					//HAGO LA CONSULTA 
					String consulta = "INSERT INTO usuarios (apellidos, dni, edad, email, nombre, rol) VALUES (?, ?, ?, ?, ?, ?)";
					stmt = con.prepareStatement(consulta);
					// ASIGNO LOS VALORES A LOS PARAMETROS DE LA CONSULTA
					stmt.setString(1, apellido);
					stmt.setString(2, dni);
					stmt.setString(3, edad);
					stmt.setString(4, email);
					stmt.setString(5, nombre);
					stmt.setString(6, "Cliente "); 
					// EjECUTO EL INSERT 
					int filasinsertadas = stmt.executeUpdate();
					

		            // SI LAS FILAS NO SON SUPERIORES A 0 DARA UN ERROR 
		            if (filasinsertadas>0) {
						System.out.println("\nLos datos fueron introducidos correctamente ");
					} else {
						System.out.println("\nLos datos no pudieron ser introducidos");
					}
					

					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					// SI LO QUITO FALLA
					if (stmt !=null) {
						stmt.close();
					}
				}
			}else {
				MenuUsuario(con, "electricskate");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// SI LO QUITO FALLA
			if (stmt != null) {
	            stmt.close();
	        }
		}

	}

	// CREO EL METODO PARA PODER CREAR UN ADMINISTRADOR
	public static void crearEmpleado(Connection con, String BDNombre) throws SQLException {

		PreparedStatement stmt= null;
		
		Scanner teclado= new Scanner(System.in);
		
		try {
			System.out.println("AÑADIR USUARIO EMPLEADO (Escriba exit para volver atrás)");
			// PIDO LOS DATOS AL USUARIO
			System.out.println("\nNombre: ");
			String nombre = teclado.nextLine();
			if (nombre.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.println("\nApellidos: ");
			String apellido = teclado.nextLine();
			if (apellido.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.println("\nEdad: ");
			String edad = teclado.nextLine();
			if (edad.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.println("\nDNI(8 numeros seguidos de una letra Mayúscula: ");
			String dni = teclado.nextLine();
			if (dni.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			// HAGO UN WHILE PARA RECORRER EL DNI Y COMPROBAR SI SE HAN INTRODUCIDO CORRECTAMENTE LOS DIGITOS 
			while (dni.length() != 9) {

				System.out.println("\nLa secuencia de números introducida no es correcta");
				System.out.println("Por favor vuelva a introducir su DNI");
				dni = teclado.nextLine();
			}
			System.out.println("\nLa secuencia de números introducida es correcta");
			System.out.println("\nE-mail:");
			String email = teclado.nextLine();
			System.out.println("Contraseña (3MAYUSCULAS,3MINUSCULAS,2 numeros: ");
			String contrasenya = teclado.nextLine();

			// CREO TRES VARIABLES PARA CONTAR
			int mayuscula = 0;
			int minuscula = 0;
			int numero = 0;

			// HAGO UN WHILE PARA VERIFICAR LA CONTRASEÑA
			while (!(contrasenya.length() >= 8 && contrasenya.length() <= 8 && mayuscula == 3 && minuscula == 3 && numero == 2)) {
			    // REINICIO LAS VARIABLES DE CONTADOR
			    mayuscula = 0;
			    minuscula = 0;
			    numero = 0;

			    System.out.println("\nLa contraseña introducida no cumple los parámetros solicitados.");
			    System.out.println("Por favor vuelva a introducir su contraseña:");

			    contrasenya = teclado.nextLine();

			    // VERIFICO SI LOS REQUISITOS DE LA CONTRASEÑA SON CORRECTOS 
			    for (int i = 0; i < contrasenya.length(); i++) {
			        char caracter = contrasenya.charAt(i);
			        if (Character.isUpperCase(caracter)) {
			            mayuscula++;
			        } else if (Character.isLowerCase(caracter)) {
			            minuscula++;
			        } else if (Character.isDigit(caracter)) {
			            numero++;
			        }
			    }
			}

			System.out.println("\nLa contraseña introducida es correcta.");
			
			System.out.println("GUARDAR? SI/NO (no vuelve al menú anterior");
			String opcion=teclado.nextLine();
			// ELEGIR OPCION EN MAYUSCULA O MINUSCULA 
			opcion=opcion.toUpperCase();
			
			//HAGO EL INSERT PARA INTRODUCIR LOS DATOS EN LA TABLA
			if (opcion.equals("SI")) {
				try {
					
					String consulta = "INSERT INTO usuarios (apellidos, contrasenya, dni, edad, email, nombre, rol) VALUES (?, ?, ?, ?, ?, ?,?)";
					stmt = con.prepareStatement(consulta);
					// ASIGNO LOS VALORES A LOS PARAMETROS DE LA CONSULTA
					stmt.setString(1, apellido);
					stmt.setString(2,contrasenya);
					stmt.setString(3, dni);
					stmt.setString(4, edad);
					stmt.setString(5, email);
					stmt.setString(6, nombre);
					stmt.setString(7, "Administrador ");
					
					
		            // EjECUTO EL INSERT 
		            int filasinsertadas= stmt.executeUpdate();
		            // SI LAS FILAS NO SON SUPERIORES A 0 DARA UN ERROR 
		            if (filasinsertadas>0) {
						System.out.println("Los datos fueron introducidos correctamente ");
					} else {
						System.out.println("Los datos no pudieron ser introducidos");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					// SI LO QUITO FALLA
					if (stmt != null) {
			            stmt.close();
			        }
				}
			} else {
				MenuUsuario(con, "electricskate");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// SI LO QUITO FALLA
			if (stmt!=null) {
				stmt.close();
			}
			
		}
		
	}

}
