import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.sql.*;

 	 class Listar {
	 private static Connection conn;

    public static void MenuListar() throws SQLException {
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

            System.out.print("Elige una opci n: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcion) {
                case 1:
                    System.out.println("\nListando usuarios empleados...\n");
                    listarEmpleados();
                    break;
                case 2:
                    System.out.println("\nListando usuarios clientes...\n");
                    listarClientes();
                    break;
                case 3:
                    System.out.println("\nListando todos los usuarios...\n");
                    listarTodosUsuarios();
                    break;
                case 4:
                    System.out.println("\nListando patinetes alquilados...\n");
                    break;
                case 5:
                    System.out.println("\nListando patinetes no alquilados...\n");
                    break;
                case 6:
                    System.out.println("\nVolviendo atr s...\n");
                    break;
                default:
                    System.out.println("\nOpci n inv lida, vuelva a intentarlo.\n");
            }
        } while (opcion != 6);
    }
    
    public static void listarEmpleados() throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE rol = 'administrador'");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                System.out.println("Empleado: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            throw e;
        }
    }
    
    public static void listarClientes() throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE rol = 'cliente'");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                System.out.println("Cliente: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            throw e;
        }
    }
    
    public static void listarTodosUsuarios() throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");
                System.out.println("Usuario: " + nombre + " (" + rol + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            throw e;
        }
    }



    
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
			
			System.out.println("La lista ha sido guardada con  xito!\n");
			
		} catch (Exception e) {
			System.out.println("No se ha podido guardar el listado\n");
		}
	}
}