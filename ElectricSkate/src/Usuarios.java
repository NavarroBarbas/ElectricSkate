import java.sql.*;
import java.util.Scanner;

/**
 * En esta clase tendremos todo lo relacionado con la gestión de los usuarios. 
 * Incluido el submenú para la gestión de los usuarios.
 * 
 * @author Javier Navarro <p>
 * Andreu Julià <p>
 * Valentín Silva <p>
 * Pedro Caaveiro <p>
 * Juan Castresana
 *
 */
public abstract class Usuarios {

	/**
	 * Este método nos permitirá registrarnos correctamente en nuestro programa. 
	 * Comprobará si el usuario es administrador o cliente, en caso de ser el primero 
	 * nos llevará al menú principal.
	 * 
	 * @param con Conexión a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
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

			System.out.print("contraseña: ");
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
							System.out.println("\nEmpleado o contraseña incorrecta\n");
						}

					} else {
						System.out.println("\nEl usuario debe ser administrador\n");
					}
				} else {
					System.out.println("\nEmpleado o contraseña incorrecta\n");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				stmt.close();
			}
		}
	}

	/**
	 * Este método será el menú para la gestión del usuario, tendrá dos opciones posibles 
	 * para gestionarlos: <p>
	 * - Añadir un usuario empleado <p>
	 * - Añadir un usuario cliente <p>
	 * 
	 * @param con Conexión a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
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
				System.out.println("\nGESTIONAR USUARIOS (EMPLEADOS/CLIENTES)\n");
				System.out.println("1. Añadir Usuario Cliente");
				System.out.println("2. Añadir Usuario Empleado");
				System.out.println("3. Atrás\n");

				System.out.print("Elige una opción: ");
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
					System.out.println("\nVolviendo al menú principal\n");
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

	/**
	 * El método crearCliente nos permitirá añadir un usuario cliente 
	 * nuevo a nuestra base de datos, comprobando si existe previamente o no.
	 * 
	 * @param con Conexión a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
	public static void crearCliente(Connection con, String BDNombre) throws SQLException {

		Statement stmt = null;
		Scanner teclado = new Scanner(System.in);

		try {
			stmt = con.createStatement();
			
			System.out.println("\nAÑ‘ADIR USUARIO CLIENTE (Escriba exit para volver atrás)");
			// PIDO LOS DATOS AL USUARIO
			System.out.print("\nNombre: ");
			String nombre = teclado.nextLine();
			if (nombre.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			System.out.print("Apellidos: ");
			String apellido = teclado.nextLine();
			if (apellido.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			
			System.out.print("Edad: ");
			int edad = teclado.nextInt();
			teclado.nextLine();
			
			System.out.print("DNI(8 numeros seguidos de una letra): ");
			String dni = teclado.nextLine();
			if (dni.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			
			// HAGO UN WHILE PARA RECORRER EL DNI Y COMPROBAR SI SE HAN INTRODUCIDO CORRECTAMENTE LOS DIGITOS 
			while (dni.length() != 9) {

				System.out.println("\nLa secuencia de números introducida no es correcta");
				System.out.print("Por favor vuelva a introducir su DNI:");
				dni = teclado.nextLine();
			}			

			System.out.print("E-mail:");
			String email = teclado.nextLine();
			
			System.out.print("\nDESEA GUARDAR EL USUARIO? SI/NO: ");
			String opcion=teclado.nextLine();
			
			// ASI PUEDE SER EN MAYUSCULA Y MINUSCULA
			opcion = opcion.toUpperCase();
			
			//HAGO EL INSERT PARA INTRODUCIR LOS DATOS EN LA TABLA
			if (opcion.equals("SI") || opcion.equals("S")) {
				
				int existeUser = existeUsuario(con, "electricskate", dni);
				
				if(existeUser == 1) {
					System.out.println("\nEl usuario ya existe, no se puede añadir");
					return;
				}
					String consulta = "INSERT INTO usuarios (apellidos, dni, edad, email, nombre, rol)"
							+ " VALUES ('" + apellido + "', '" + dni + "', '"+ edad +"',"
							+ " '" + email +"', '" + nombre + "', 'cliente')";
	
					stmt.executeUpdate(consulta);
					
					System.out.println("\nEl usuario " + nombre + " " + apellido + " ha sido guardado!");	
					
			} else {
				System.out.println("\nSe ha cancelado añadir el usuario");
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}

	}

	/**
	 * El método crearCliente nos permitirá añadir un usuario cliente 
	 * nuevo a nuestra base de datos, comprobando si existe previamente o no.
	 * 
	 * @param con Conexión a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
	public static void crearEmpleado(Connection con, String BDNombre) throws SQLException {

		Statement stmt= null;
		
		Scanner teclado= new Scanner(System.in);
		
		try {
			stmt = con.createStatement();
			
			System.out.println("\nAÑADIR USUARIO EMPLEADO (Escriba exit para volver atrás)\n");
			
			// PIDO LOS DATOS AL USUARIO
			System.out.print("Nombre: ");
			String nombre = teclado.nextLine();
			if (nombre.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			
			System.out.print("Apellidos: ");
			String apellido = teclado.nextLine();
			if (apellido.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			
			System.out.print("Edad: ");
			String edad = teclado.nextLine();
			if (edad.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			
			System.out.print("DNI (8 numeros seguidos de una letra Mayúscula: ");
			String dni = teclado.nextLine();
			if (dni.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			// HAGO UN WHILE PARA RECORRER EL DNI Y COMPROBAR SI SE HAN INTRODUCIDO CORRECTAMENTE LOS DIGITOS 
			while (dni.length() != 9) {

				System.out.println("\nEl DNI no es válido");
				System.out.print("Por favor vuelva a introducir su DNI:");
				dni = teclado.nextLine();
			}
			
			System.out.print("E-mail:");
			String email = teclado.nextLine();
			
			System.out.print("Contraseña (3 MAYUSCULAS, 3 MINUSCULAS, 2 números): ");
			String contrasenya = teclado.nextLine();

			// CREO TRES VARIABLES PARA CONTAR
			int mayuscula = 0;
			int minuscula = 0;
			int numero = 0;
			
			// VERIFICO SI LOS REQUISITOS DE LA CONTRASEÑ‘A SON CORRECTOS 
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

			//TODO HAGO UN WHILE PARA VERIFICAR LA CONTRASEÑ‘A
			while (contrasenya.length() < 8 || mayuscula < 3 || minuscula < 3 || numero < 2) {
			    // REINICIO LAS VARIABLES DE CONTADOR
			    mayuscula = 0;
			    minuscula = 0;
			    numero = 0;

			    System.out.println("\nLa contraseña introducida no cumple los parámetros solicitados.");
			    System.out.println("Por favor vuelva a introducir su contraseña: ");
			    contrasenya = teclado.nextLine(); 
			    
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

			System.out.print("\nDESEA GUARDAR EL USUARIO? SI/NO ");
			String opcion=teclado.nextLine();
			// ELEGIR OPCION EN MAYUSCULA O MINUSCULA 
			opcion=opcion.toUpperCase();
			
			//HAGO EL INSERT PARA INTRODUCIR LOS DATOS EN LA TABLA
			if (opcion.equals("SI") || opcion.equals("S")) {
				
				int existeUser = existeUsuario(con, "electricskate", dni);
				
				if(existeUser == 1) {
					System.out.println("\nEl usuario ya existe, no se puede añadir");
					return;
				}
								
				String consulta = "INSERT INTO usuarios (apellidos, contrasenya, dni, edad, email, nombre, rol)"
					+ " VALUES ('" + apellido + "', '" + contrasenya + "', '" + dni + "', '"+ edad +"',"
					+ " '" + email +"', '" + nombre + "', 'administrador')";
					
				// EjECUTO EL INSERT 
				stmt.executeUpdate(consulta);
				
				System.out.println("\nEl usuario " + nombre + " " + apellido + " ha sido guardado!");
		            	
			} else {
				System.out.println("\nSe ha cancelado añadir el empleado");	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			stmt.close();
		}
	}
	
	/**
	 * El método existeUsuario nos permitirá comprobar si existe un usuario o no 
	 * pasándole como parámetro el DNI del usuario que queremos comprobar, 
	 * nos devolverá un int, si existe será un 1 y si no, un 0.
	 * 
	 * @param con Conexión a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @param dni DNI del usuario que queremos comprobar que exista
	 * @return Nos devolverá un int, con valor 0 o 1, si es 1 el usuario existe, si es 0 no.
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
	public static int existeUsuario(Connection con, String BDNombre, String dni) 
			throws SQLException {
		Statement stmt = null;
		int user = 0;
		
		try {
			stmt = con.createStatement();
			
			String consulta = "SELECT dni FROM usuarios WHERE dni = '" + dni +"'";
			
			ResultSet rs = stmt.executeQuery(consulta);
			
			while(rs.next()) {
				user++;
			}
			
		} catch (SQLException e) {
			stmt.close();
		}
		
		return user;
	}
	
	/**
	 * El método buscarDNI nos permitirá buscar por el DNI cualquier usuario, 
	 * nos mostrará toda la información necesaria sobre ese usuario, incluido su rol.
	 * 
	 * @param con Conexión a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrará las excepciones provocadas por la base de datos.
	 */
	public static void buscarDni (Connection con, String BDNombre) throws SQLException {
		Scanner teclado = new Scanner(System.in);
		System.out.print("Introduzca el DNI/NIE a buscar (sin guiones): ");
		String dni = teclado.nextLine();
		
		if (existeUsuario(con, BDNombre, dni) == 1) { // el usuario existe
			Statement stmt = null;
	    	String lista = "";
	    	
	        try {
	            stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE dni = '" + dni + "'");
	            
	            
	            	if(rs.next()) {
	            	// sacamos datos de la BBDD
	            	String email = rs.getString("email");
	                String nombre = rs.getString("nombre");
	                String apellidos = rs.getString("apellidos");
	                int edad = rs.getInt("edad");
	                String contrasenya = rs.getString("contrasenya");
	                String rol = rs.getString("rol");
	            	
	                
	                // listamos el usuario solicitado
	                lista += "\n*************************\n" +
	                		"\nEmail: " + email + "\n" +
	                		"Nombre: " + nombre + "\n" +
	                		"Apellidos: " + apellidos + "\n" +
	                		"Edad: " + edad + " años" + "\n" +
	                		"DNI: " + dni + "\n";
	                
	                if(contrasenya != null) {
	                	lista +=  "Contraseña: " + contrasenya + "\n";
	                }
	               
	                lista += "Rol: " + rol + "\n" + 
	                		"\n*************************\n";
	            	}
	                
	                System.out.println(lista);
	            
	        } catch (SQLException e) {
	            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
	            e.printStackTrace();
	            
	        } finally {
	        	stmt.close();
	        }

		} else { // el usuario no existe
			System.out.println("\nEl DNI/NIE introducido no coincide con ningún usuario.\n");
		}
		
	}

}
