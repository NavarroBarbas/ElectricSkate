import java.sql.*;
import java.util.Scanner;

public class Patinete {

	public static void MenuGestionarPatinetes(Connection con, String BDNombre) throws SQLException {
		Scanner teclado = new Scanner(System.in);

		int opcion;
		do {
			System.out.println();
			System.out.println("GESTIONAR PATINETES");
			System.out.println();
			System.out.println("1. Añadir Patinete");
			System.out.println("2. Alquiler Patinete");
			System.out.println("3. Devolver Patinete");
			System.out.println("4. Atrás");
			System.out.println();

			System.out.print("Elige una opción: ");
			opcion = teclado.nextInt();
			teclado.nextLine();

			switch (opcion) {
			case 1:				
				String numSerie, marca, modelo, color;
				
				System.out.print("Escriba el número de serie: ");
				numSerie = teclado.nextLine();
				System.out.print("Escriba la marca: ");
				marca = teclado.nextLine();
				System.out.print("Escriba el modelo: ");
				modelo = teclado.nextLine();
				System.out.print("Escriba el color: ");
				color = teclado.nextLine();
				
				// Para que el número de serie siempre se ponga en mayúsculas
				numSerie = numSerie.toUpperCase();
				
				anyadirPatinete(con, BDNombre, numSerie, marca, modelo, color);
				
				break;
			case 2:
				
				String numSerieAlquiler, email;
				System.out.print("Escriba el número de serie del patinete que desea alquilar: ");
				numSerie = teclado.nextLine();
				System.out.print("Escriba el email del usuario a quien quiere alquilar el patinete: ");
				marca = teclado.nextLine();
				
				break;
			case 3:

				break;
			case 4:
				System.out.println("\nVolviendo atrás...\n");
				break;
			default:
				System.out.println("\nOpción inválida, vuelva a intentarlo.\n");
			}
		} while (opcion != 4);

		teclado.close();
	}

	public static void alquilerPatinete(Connection con, String BDNombre, String numSerie, String emailAlquiler)
			throws SQLException {

		Statement stmt = null;

		try {

			stmt = con.createStatement();
	
			int validadorNumSerie = 0;
			int validadorEmail = 0;
			
			String query = "SELECT sum(*) INTO " + validadorNumSerie + " FROM patinetes WHERE numeroSerie = " + numSerie;
			String query2 = "SELECT sum(*) INTO " + validadorEmail + " FROM usuarios WHERE email = " + emailAlquiler;
			
			// 1. Comprobar que el número de serie ya exista en la tabla patinetes.
			// 2. Comprobar que el mail exsita en la tabla usuarios.
			/* 
			 * 3. No hace falta comprobar que el mail o en número de serie existan 
			 * en la tabla ya que son claves primárias y daría un error.
			*/
			
			if (validadorNumSerie == 0 && validadorEmail == 0) {
				
				stmt.executeUpdate("INSERT INTO " + BDNombre + ".patinetesusuarios VALUES "
						+ "('" + numSerie + "' , '" + emailAlquiler + "')");
				
				System.out.println("");
				System.out.println("Se ha introducido un nuevo alquiler en la base de datos.");				
			}
			
			
		} catch (SQLException e) {
			System.out.println("El cliente con este email ya tiene un patienete alquilado o "
					+ "el número de serie no concuerda con el de ningún cliente.");
			System.out.println("Volviendo al menú de Gestión de Patinetes...");
		} finally {
			stmt.close();
		}

	}
	
	
	public static void anyadirPatinete(Connection con, String BDNombre, String numSerie, String marca, 
			String modelo, String color) throws SQLException {

		Statement stmt = null;

		try {

			stmt = con.createStatement();

			stmt.executeUpdate("INSERT INTO " + BDNombre + ".patinetes VALUES "
					+ "('" + numSerie + "' , '" + marca + "' , '" + modelo + "' , '" + color + "', " + 0 + ")");
			
			System.out.println("");
			System.out.println("Se ha introducido un nuevo patinete en la base de datos.");
			
		} catch (SQLException e) {
			System.out.println("El número de serie ya existe.");
			System.out.println("Volviendo al menú de Gestión de Patinetes...");
		} finally {
			stmt.close();
		}

	}
	
	
	
}
