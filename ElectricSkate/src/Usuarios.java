import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class Usuarios {

	// ATRIBUTOS
	String nombre;
	String apellidos;
	int edad;
	String dni;
	String email;

	// CONSTRUCTOR
	public Usuarios(String nombre, String apellidos, int edad, String dni, String email) {
		super();

	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	 // METODO PARA LISTAR USUARIOS
    public void listarUsuarios() {
    	
    	// HAGO LA CONEXION A LA BASE DE DATOS 
        Connection con = null;
        Statement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
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
                //CIERRO TECLADO
                teclado.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // CERRAR CLASES STATAMENT Y CONEXION
            try {
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


}
}
