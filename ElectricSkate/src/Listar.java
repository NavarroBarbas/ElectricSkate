import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.sql.*;

/**
 * Clase Listar, contiene todo lo necesario para crear 
 * y guardar listas, tanto de usuarios como de patinetes.
 * 
 * @author Javier Navarro, 
 * Andreu Juli�, 
 * Valent�n Silva, 
 * Pedro Caaveiro, 
 * Juan Castresana
 *
 */

public class Listar {
	
	/**
	 * El m�todo MenuListar contendr� el men� sobre las opciones disponibles 
	 * para listar Usuarios o Patinetes, adem�s nos pedir� si queremos guardar 
	 * dicho listado o no.
	 * 
	 * @param conn Conexi�n a la base de datos.
	 * @param BDNombre	Nombre de la base de datos.
	 * @throws SQLException	Mostrar� las excepciones provocadas por la base de datos.
	 */
    public static void MenuListar(Connection conn, String BDNombre) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        
        
        int opcion = 0;
        do {
            System.out.println("\nLISTAR (Empleados/Clientes/Patinetes)\n");
            System.out.println("1. Listar usuarios empleados");
            System.out.println("2. Listar usuarios clientes");
            System.out.println("3. Listar todos los usuarios");
            System.out.println("4. Listar patinetes alquilados");
            System.out.println("5. Listar patinetes no alquilados");
            System.out.println("6. Atr�s\n");

            System.out.print("Elige una opci�n: ");
            opcion = 0;
			while (!scanner.hasNextInt()) {
		            System.out.println("\nError: �Ingrese solo n�meros enteros!\n");
		            System.out.print("Ingrese una opci�n: ");
		            scanner.next();
		        }
		        
		    opcion = scanner.nextInt();
		    scanner.nextLine();
            
            switch(opcion) {
                case 1:
                    System.out.println(listarEmpleados(conn, "electricskate"));
                    
                    System.out.print("Desea guardar la lista? (S/N): ");
                    String guardar = scanner.nextLine();
                    
                    if(guardar.equalsIgnoreCase("s") || guardar.equalsIgnoreCase("si")) {
                    	guardarListado(listarEmpleados(conn, BDNombre), "empleados");
                    } else {
                    	System.out.println("\nLa lista no ha sido guardada!");
                    }
                    break;
                case 2:
                	 String listaClientes = listarClientes(conn, "electricskate");
                	 System.out.println(listaClientes);
                     System.out.print("Desea guardar la lista? (S/N): ");
                     String guardarClientes = scanner.nextLine();

                     if (guardarClientes.equalsIgnoreCase("s") || guardarClientes.equalsIgnoreCase("si")) {
                         guardarListado(listaClientes, "clientes");
                     } else {
                         System.out.println("\nLa lista no ha sido guardada!");
                     }
                    break;
                case 3:
                	  String listaUsuarios = listarTodosUsuarios(conn, "electricskate");
                	  System.out.println(listaUsuarios);
                      System.out.print("Desea guardar la lista? (S/N): ");
                      String guardarUsuarios = scanner.nextLine();

                      if (guardarUsuarios.equalsIgnoreCase("s") || guardarUsuarios.equalsIgnoreCase("si")) {
                          guardarListado(listaUsuarios, "todos_usuarios");
                      } else {
                          System.out.println("\nLa lista no ha sido guardada!");
                      }
                      break;
                case 4: // patinetes alquilados
                    System.out.println(listarPatinetesAlquilados(conn, "electricskate"));
                    System.out.print("Dese guardar la lista? (S/N): ");
                    guardar = scanner.nextLine();

                    if (guardar.equalsIgnoreCase("s") || guardar.equalsIgnoreCase("si")) {
                        guardarListado(listarPatinetesAlquilados(conn, BDNombre), "patinetes_alquilados");
                    } else {
                        System.out.println("\nLa lista no ha sido guardada!");
                    }
                    break;
                case 5: // patinetes no alquilados
                    System.out.println(listarPatinetesNoAlquilados(conn, "electricskate"));
                    System.out.print("Dese guardar la lista? (S/N): ");
                    guardar = scanner.nextLine();
                    
                    if (guardar.equalsIgnoreCase("s") || guardar.equalsIgnoreCase("si")) {
                        guardarListado(listarPatinetesNoAlquilados(conn, BDNombre), "patinetes_no_alquilados");
                    } else {
                        System.out.println("\nLa lista no ha sido guardada!");
                    }
                    break;
                case 6:
                    System.out.println("\nVolviendo atr�s...\n");
                    break;
                default:
                    System.out.println("\nOpci�n inv�lida, vuelva a intentarlo.\n");
            }
        } while (opcion != 6);
    }
    
    /**
     * El m�todo listarEmpleados nos devolver� un String con todos los datos de 
     * los empleados (Email, Nombre, Apellidos, Edad, DNI, Contrase�a).
     * 
     * @param conn Conexi�n a la base de datos.
     * @param BDNombre Nombre de la base de datos.
     * @return Devolver� un String con la lista de usuarios Empleados.
     * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
     */
    public static String listarEmpleados(Connection conn, String BDNombre) throws SQLException {
    	Statement stmt = null;
    	String lista = "\nListando usuarios empleados...\n";
    	
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE rol = 'administrador'");

            while (rs.next()) {
            	lista += ("\n*************************\n");
            	
            	String email = rs.getString("email");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int edad = rs.getInt("edad");
                String dni = rs.getString("dni");
                String contrasenya = rs.getString("contrasenya");
                
                lista += "Email: " + email + "\n" +
                "Nombre: " + nombre + "\n" +
               	"Apellidos: " + apellidos + "\n" +
                "Edad: " + edad + " a�os" + "\n" +
                "DNI: " + dni + "\n" +
                "Contrase�a: " + contrasenya + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
        	stmt.close();
        }
        
        return lista;
    }
    
    
    /**
     * El m�todo listarClientes nos devolver� un String con todos los datos de 
     * los clientes (Email, Nombre, Apellidos, Edad, DNI).
     * 
     * @param conn Conexi�n a la base de datos.
     * @param BDNombre Nombre de la base de datos.
     * @return Devolver� un String con la lista de usuarios Clientes.
     * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
     */
    public static String listarClientes(Connection conn, String BDNombre) throws SQLException {
        Statement stmt = null;
        String listaClientes = "\nListando usuarios clientes...\n";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE rol = 'cliente'");

            while (rs.next()) {
                listaClientes += ("\n*************************\n");

                String email = rs.getString("email");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int edad = rs.getInt("edad");
                String dni = rs.getString("dni");
                

                listaClientes += "Email: " + email + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Apellidos: " + apellidos + "\n" +
                        "Edad: " + edad + " a�os" + "\n" +
                        "DNI: " + dni + "\n";          
            }
            
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            stmt.close();
        }

        return listaClientes;
    }
        
    /**
     * El m�todo listarTodosUsuarios nos devolver� un String con todos los datos de 
     * los usuarios registrados en el sistema incluido su rol 
     * (Email, Nombre, Apellidos, Edad, DNI, Contrase�a si tienen, y su Rol). 
     * @param conn Conexi�n a la base de datos.
     * @param BDNombre Nombre de la base de datos.
     * @return Devolver� un String con la lista de usuarios Clientes y usuarios Empleados.
     * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
     */
    public static String listarTodosUsuarios(Connection conn, String BDNombre) throws SQLException {
        Statement stmt = null;
        String listaUsuarios = "\nListando todos los usuarios...\n";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                listaUsuarios += ("\n*************************\n");

                String email = rs.getString("email");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int edad = rs.getInt("edad");
                String dni = rs.getString("dni");
                String contrasenya = rs.getString("contrasenya");
                String rol = rs.getString("rol");

                listaUsuarios += "Email: " + email + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Apellidos: " + apellidos + "\n" +
                        "Edad: " + edad + " a�os" + "\n" +
                        "DNI: " + dni + "\n" +
                        "Contrase�a: " + contrasenya + "\n" +
                        "Rol: " + rol + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stmt.close();
        }

        return listaUsuarios;
    }

    
    /**
     * El m�todo listarPatinetesAlquilados nos mostrar� una lista 
     * con los patinetes alquilados, este mostrar� todos los datos del patinete  
     * (N�mero de Serie, Marca, Modelo, Km Recorridos) 
     * y el nombre del usuario que lo tiene alquilado.
     * 
     * @param conn Conexi�n a la base de datos.
     * @param BDNombre Nombre de la base de datos.
     * @return Devolver� un String con la lista de usuarios de patinetes alquilados.
     * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
     */
    public static String listarPatinetesAlquilados(Connection conn, String BDNombre) throws SQLException {
    	Statement stmt = null;
    	Statement stmt2 = null;
    	String listaAlq = "\nListando patinetes alquilados...\n";
    	
        try {
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patinetes "
            		+ "WHERE numeroSerie IN (SELECT numeroSerie FROM patinetesUsuarios)");
 
            while (rs.next()) {
            	listaAlq += ("\n*************************\n");
            	
            	String numSerie = rs.getString("numeroSerie");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                double km = rs.getInt("km_recorridos");
                               
                listaAlq += "N�mero de Serie: " + numSerie + "\n" +
                "Marca: " + marca + "\n" +
               	"Modelo: " + modelo + "\n" +
                "Km: " + km + " recorridos" + "\n";
                
                ResultSet rs2 = stmt2.executeQuery("SELECT nombre, apellidos FROM usuarios "
                		+ "WHERE email = (SELECT email FROM patinetesUsuarios "
                		+ "WHERE numeroSerie = '" + numSerie + "')");
                
                if(rs2.next()) {
                	String nombreAlq = rs2.getString("nombre");
                	String apellidosAlq = rs2.getString("apellidos");
                	
                	listaAlq += "Alquilado a: " + nombreAlq + " " + apellidosAlq + "\n" ;
                }
               
            }   
            
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
        	stmt.close();
        	stmt2.close();
        }
        
        return listaAlq;
    }   
    
    /**
     * El m�todo listarPatinetesNoAlquilados nos mostrar� una lista 
     * con los patinetes que no est�n alquilados, este mostrar� todos 
     * los datos del patinete (N�mero de Serie, Marca, Modelo, Km Recorridos).
     * 
     * @param conn Conexi�n a la base de datos.
     * @param BDNombre Nombre de la base de datos.
     * @return Devolver� un String con la lista de usuarios de patinetes que no est�n alquilados.
     * @throws SQLException Mostrar� las excepciones provocadas por la base de datos.
     */
    public static String listarPatinetesNoAlquilados(Connection conn, String BDNombre) throws SQLException {
    	Statement stmt = null;
    	String listaNoAlq = "\nListando patinetes NO alquilados...\n";
    	
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patinetes WHERE numeroSerie NOT IN (SELECT numeroSerie FROM patinetesUsuarios)");

            while (rs.next()) {
            	listaNoAlq += ("\n*************************\n");
            	
            	String numSerie = rs.getString("numeroSerie");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                double km = rs.getInt("km_recorridos");
                
                listaNoAlq += "N�mero de Serie: " + numSerie + "\n" +
                "Marca: " + marca + "\n" +
               	"Modelo: " + modelo + "\n" +
                "Km: " + km + " recorridos" + "\n";
            }
            
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
        	stmt.close();
        }
        
        return listaNoAlq;
    }   
    
    
    /**
     * El m�todo guardarListado nos permitir� guardar cualquier listado que hemos creado. 
     * Este m�todo guardar� la lista en la carpeta 'C:/informes', si no existe esa 
     * carpeta la crear� autom�ticamente.
     * 
     * @param lista Deberemos pasarle el String con la lista que queremos guardar
     * @param nomArchivo Deberemos pasarle el nombre de como queremos guardar el archivo
     */
    public static void guardarListado(String lista, String nomArchivo) {
		File fs = null;
		File fd = null;
		
		try {
			fd= new File("C:/informes");
			if(!fd.exists()) {
				fd.mkdirs();
			}
			
			fs = new File("C:/informes/" + nomArchivo + ".txt");
			
			if(!fs.exists()) {
				
				fs.createNewFile();
			}
			
			FileWriter fw = new FileWriter(fs);
			
			fw.write(lista);
			
			if (fw != null) {
				fw.close();
			}
			
			System.out.println("\nLa lista ha sido guardada con �xito!\n");
			
		} catch (Exception e) {
			System.out.println("No se ha podido guardar el listado\n");
		}
	}
}