import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.sql.*;

 	 class Listar {

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
            System.out.println("6. Atr s\n");

            System.out.print("Elige una opci�n: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcion) {
                case 1:
                    System.out.println(listarEmpleados(conn, "electricskate"));
                    
                    System.out.print("Dese guardar la lista? (S/N): ");
                    String guardar = scanner.nextLine();
                    
                    if(guardar.equalsIgnoreCase("s") || guardar.equalsIgnoreCase("si")) {
                    	guardarListado(listarEmpleados(conn, BDNombre), "empleados");
                    } else {
                    	System.out.println("La lista no ha sido guardada!");
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
                         System.out.println("La lista no ha sido guardada!");
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
                          System.out.println("La lista no ha sido guardada!");
                      }
                      break;
                case 4: // patinetes alquilados
                    System.out.println(listarPatinetesAlquilados(conn, "electricskate"));
                    System.out.print("Dese guardar la lista? (S/N): ");
                    guardar = scanner.nextLine();

                    if (guardar.equalsIgnoreCase("s") || guardar.equalsIgnoreCase("si")) {
                        guardarListado(listarPatinetesAlquilados(conn, BDNombre), "patinetes_alquilados");
                    } else {
                        System.out.println("La lista no ha sido guardada!");
                    }
                    break;
                case 5: // patinetes no alquilados
                    System.out.println(listarPatinetesNoAlquilados(conn, "electricskate"));
                    System.out.print("Dese guardar la lista? (S/N): ");
                    guardar = scanner.nextLine();
                    
                    if (guardar.equalsIgnoreCase("s") || guardar.equalsIgnoreCase("si")) {
                        guardarListado(listarPatinetesNoAlquilados(conn, BDNombre), "patinetes_no_alquilados");
                    } else {
                        System.out.println("La lista no ha sido guardada!");
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
                "Contrasenya: " + contrasenya + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
        	stmt.close();
        }
        
        return lista;
    }
    
    public static String listarClientes(Connection conn, String BDNombre) throws SQLException {
        Statement stmt = null;
        String lista = "\nListando usuarios clientes...\n";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE rol = 'cliente'");

            while (rs.next()) {
                lista += ("\n*************************\n");

                String email = rs.getString("email");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int edad = rs.getInt("edad");
                String dni = rs.getString("dni");
                

                lista += "Email: " + email + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Apellidos: " + apellidos + "\n" +
                        "Edad: " + edad + " años" + "\n" +
                        "DNI: " + dni + "\n";
                        
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stmt.close();
        }

        return lista;
    }
        
    public static String listarTodosUsuarios(Connection conn, String BDNombre) throws SQLException {
        Statement stmt = null;
        String lista = "\nListando todos los usuarios...\n";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

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
                        "Edad: " + edad + " años" + "\n" +
                        "DNI: " + dni + "\n" +
                        "Contrasenya: " + contrasenya + "\n";
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stmt.close();
        }

        return lista;
    }

    
    //Listar Patinetes Alquilados
    public static String listarPatinetesAlquilados(Connection conn, String BDNombre) throws SQLException {
    	Statement stmt = null;
    	String lista = "\nListando patinetes alquilados...\n";
    	
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patinetes WHERE numeroSerie IN (SELECT numeroSerie FROM patinetesUsuarios)");

            while (rs.next()) {
            	lista += ("\n*************************\n");
            	
            	String numSerie = rs.getString("numeroSerie");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                double km = rs.getInt("km_recorridos");
                
                lista += "Número de Serie: " + numSerie + "\n" +
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
        
        return lista;
    }   
    
    //Listar Patinetes No Alquilados
    public static String listarPatinetesNoAlquilados(Connection conn, String BDNombre) throws SQLException {
    	Statement stmt = null;
    	String lista = "\nListando patinetes NO alquilados...\n";
    	
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patinetes WHERE numeroSerie NOT IN (SELECT numeroSerie FROM patinetesUsuarios)");

            while (rs.next()) {
            	lista += ("\n*************************\n");
            	
            	String numSerie = rs.getString("numeroSerie");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                double km = rs.getInt("km_recorridos");
                
                lista += "Número de Serie: " + numSerie + "\n" +
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
        
        return lista;
    }   
    
    
    //Guardar Listados
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