import java.sql.*;
import java.util.Scanner;

/**
 * En esta clase tendremos todo lo relacionado con la gesti�n de los usuarios. 
 * Incluido el submen� para la gesti�n de los usuarios.
 * 
 * @author Javier Navarro <p>
 * Andreu Juli� <p>
 * Valent�n Silva <p>
 * Pedro Caaveiro <p>
 * Juan Castresana
 *
 */
public abstract class Usuarios {

	/**
	 * Este m�todo nos permitir� registrarnos correctamente en nuestro programa. 
	 * Comprobar� si el usuario es administrador o cliente, en caso de ser el primero 
	 * nos llevar� al men� principal.
	 * 
	 * @param con Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
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

			System.out.print("contrase�a: ");
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

	/**
	 * Este m�todo ser� el men� para la gesti�n del usuario, tendr� dos opciones posibles 
	 * para gestionarlos: <p>
	 * - A�adir un usuario empleado <p>
	 * - A�adir un usuario cliente <p>
	 * 
	 * @param con Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
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
				System.out.println("1. A�adir Usuario Cliente");
				System.out.println("2. A�adir Usuario Empleado");
				System.out.println("3. Atr�s\n");

				System.out.print("Elige una opci�n: ");
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
					System.out.println("\nVolviendo al men� principal\n");
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
	 * El m�todo crearCliente nos permitir� a�adir un usuario cliente 
	 * nuevo a nuestra base de datos, comprobando si existe previamente o no.
	 * 
	 * @param con Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
	 */
	public static void crearCliente(Connection con, String BDNombre) throws SQLException {

		Statement stmt = null;
		Scanner teclado = new Scanner(System.in);

		try {
			stmt = con.createStatement();
			
			System.out.println("\nAёADIR USUARIO CLIENTE (Escriba exit para volver atr�s)");
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

				System.out.println("\nLa secuencia de n�meros introducida no es correcta");
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
					System.out.println("\nEl usuario ya existe, no se puede a�adir");
					return;
				}
					String consulta = "INSERT INTO usuarios (apellidos, dni, edad, email, nombre, rol)"
							+ " VALUES ('" + apellido + "', '" + dni + "', '"+ edad +"',"
							+ " '" + email +"', '" + nombre + "', 'cliente')";
	
					stmt.executeUpdate(consulta);
					
					System.out.println("\nEl usuario " + nombre + " " + apellido + " ha sido guardado!");	
					
			} else {
				System.out.println("\nSe ha cancelado a�adir el usuario");
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}

	}

	/**
	 * El m�todo crearCliente nos permitir� a�adir un usuario cliente 
	 * nuevo a nuestra base de datos, comprobando si existe previamente o no.
	 * 
	 * @param con Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
	 */
	public static void crearEmpleado(Connection con, String BDNombre) throws SQLException {

		Statement stmt= null;
		
		Scanner teclado= new Scanner(System.in);
		
		try {
			stmt = con.createStatement();
			
			System.out.println("\nA�ADIR USUARIO EMPLEADO (Escriba exit para volver atr�s)\n");
			
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
			
			System.out.print("DNI (8 numeros seguidos de una letra May�scula: ");
			String dni = teclado.nextLine();
			if (dni.equalsIgnoreCase("exit")) {
			    return; // SALIR DEL METODO Y VOLVER AL PUNTO DESDE DONDE SE LLAMO 
			}
			// HAGO UN WHILE PARA RECORRER EL DNI Y COMPROBAR SI SE HAN INTRODUCIDO CORRECTAMENTE LOS DIGITOS 
			while (dni.length() != 9) {

				System.out.println("\nEl DNI no es v�lido");
				System.out.print("Por favor vuelva a introducir su DNI:");
				dni = teclado.nextLine();
			}
			
			System.out.print("E-mail:");
			String email = teclado.nextLine();
			
			System.out.print("Contrase�a (3 MAYUSCULAS, 3 MINUSCULAS, 2 n�meros): ");
			String contrasenya = teclado.nextLine();

			// CREO TRES VARIABLES PARA CONTAR
			int mayuscula = 0;
			int minuscula = 0;
			int numero = 0;
			
			// VERIFICO SI LOS REQUISITOS DE LA CONTRASEёA SON CORRECTOS 
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

			//TODO HAGO UN WHILE PARA VERIFICAR LA CONTRASEёA
			while (contrasenya.length() < 8 || mayuscula < 3 || minuscula < 3 || numero < 2) {
			    // REINICIO LAS VARIABLES DE CONTADOR
			    mayuscula = 0;
			    minuscula = 0;
			    numero = 0;

			    System.out.println("\nLa contrase�a introducida no cumple los par�metros solicitados.");
			    System.out.println("Por favor vuelva a introducir su contrase�a: ");
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
					System.out.println("\nEl usuario ya existe, no se puede a�adir");
					return;
				}
								
				String consulta = "INSERT INTO usuarios (apellidos, contrasenya, dni, edad, email, nombre, rol)"
					+ " VALUES ('" + apellido + "', '" + contrasenya + "', '" + dni + "', '"+ edad +"',"
					+ " '" + email +"', '" + nombre + "', 'administrador')";
					
				// EjECUTO EL INSERT 
				stmt.executeUpdate(consulta);
				
				System.out.println("\nEl usuario " + nombre + " " + apellido + " ha sido guardado!");
		            	
			} else {
				System.out.println("\nSe ha cancelado a�adir el empleado");	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			stmt.close();
		}
	}
	
	/**
	 * El m�todo existeUsuario nos permitir� comprobar si existe un usuario o no 
	 * pas�ndole como par�metro el DNI del usuario que queremos comprobar, 
	 * nos devolver� un int, si existe ser� un 1 y si no, un 0.
	 * 
	 * @param con Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @param dni DNI del usuario que queremos comprobar que exista
	 * @return Nos devolver� un int, con valor 0 o 1, si es 1 el usuario existe, si es 0 no.
	 * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
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
	 * El m�todo buscarDNI nos permitir� buscar por el DNI cualquier usuario, 
	 * nos mostrar� toda la informaci�n necesaria sobre ese usuario, incluido su rol.
	 * 
	 * @param con Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
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
	                		"Edad: " + edad + " a�os" + "\n" +
	                		"DNI: " + dni + "\n";
	                
	                if(contrasenya != null) {
	                	lista +=  "Contrase�a: " + contrasenya + "\n";
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
			System.out.println("\nEl DNI/NIE introducido no coincide con ning�n usuario.\n");
		}
		
	}

}
