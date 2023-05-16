import java.sql.*;
import java.util.Scanner;

public class Patinete {

	public static void MenuGestionarPatinetes(Connection con, String BDNombre) throws SQLException {
		

		int opcionMenuPatinete;
		
		do {
			Scanner teclado = new Scanner(System.in);
			
			System.out.println("\nGESTIONAR PATINETES\n");
			
			System.out.println("1. Añadir Patinete");
			System.out.println("2. Alquiler Patinete");
			System.out.println("3. Devolver Patinete");
			System.out.println("4. Atrás\n");
			
			System.out.print("Elige una opción: ");
			
			opcionMenuPatinete = teclado.nextInt();
			teclado.nextLine();
				
			switch (opcionMenuPatinete) {
			case 1:
				String numSerie, marca, modelo, color;
				
				System.out.println("\nEscriba 'exit' en cualquier momento para salir al menú.\n");

				System.out.print("Introduzca el número de serie: ");
				numSerie = teclado.nextLine();
				if(numSerie.toLowerCase().equals("exit")) { break; }
				
				System.out.print("Introduzca la marca: ");
				marca = teclado.nextLine();
				if(marca.toLowerCase().equals("exit")) { break; }
				
				System.out.print("Introduzca el modelo: ");
				modelo = teclado.nextLine();
				if(modelo.toLowerCase().equals("exit")) { break; }
				
				System.out.print("Introduzca el color: ");
				color = teclado.nextLine();
				if(color.toLowerCase().equals("exit")) { break; }
				
				// Para que el número de serie siempre se ponga en mayúsculas
				numSerie = numSerie.toUpperCase();

				anyadirPatinete(con, BDNombre, numSerie, marca, modelo, color);

				break;
			case 2:
				String numSerieAl, emailAlquiler;
				
				System.out.println("\nEscriba 'exit' en cualquier momento para salir al menú.\n");

				System.out.print("Introduzca el número de serie del patinete: ");
				numSerieAl = teclado.nextLine();
				if(numSerieAl.toLowerCase().equals("exit")) { break; }
				
				System.out.print("Introduzca el email del cliente: ");
				emailAlquiler = teclado.nextLine();
				if(emailAlquiler.toLowerCase().equals("exit")) { break; }
				
				System.out.println();

				// Para que el número de serie siempre se ponga en mayúsculas
				numSerieAl = numSerieAl.toUpperCase();

				alquilerPatinete(con, BDNombre, numSerieAl, emailAlquiler);

				break;
			case 3:				
				String emailDev;
				int kmPatinete;
				System.out.println("\nEscriba 'exit' en cualquier momento para salir al menú.\n");
				
				System.out.print("Introduzca el email del cliente: ");
				emailDev = teclado.nextLine();
				if(emailDev.toLowerCase().equals("exit")) { break; }
				
				System.out.print("Introduzca los kilómetros del patinete: ");
				kmPatinete = teclado.nextInt();
				
				devolucionPatinete(con, BDNombre, emailDev, kmPatinete);
				
				break;
			case 4:
				System.out.println("\nVolviendo atrás...\n");
				break;
			default:
				
				System.out.println("\nOpción inválida, vuelva a intentarlo.");
			}
			
		} while (opcionMenuPatinete != 4);

	}
	
	public static void anyadirPatinete(Connection con, String BDNombre, String numSerie, String marca, String modelo,
			String color) throws SQLException {

		Statement stmt = null;

		try {
			
			String createString = "SELECT numeroSerie FROM patinetes WHERE numeroSerie = '" + numSerie + "'";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(createString);
			int existePatinete = 0;
			
			while(rs.next()) {
				existePatinete++;
			}
			
			if (existePatinete == 1) {
				System.out.println("\nYa existe un patinete con ese Número de Serie");
			} else {
			
				stmt.executeUpdate("INSERT INTO " + BDNombre + ".patinetes VALUES ('" + numSerie + "', '" + marca
						+ "', '" + modelo + "', '" + color + "', " + 0 + ")");
	
				System.out.println("\nSe ha introducido un nuevo patinete en la base de datos.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			stmt.close();
		}

	}
	
	public static void alquilerPatinete(Connection con, String BDNombre, String numSerieAl, String emailAlquiler)
			throws SQLException {
		Scanner teclado = new Scanner(System.in); 
		int validadorNumSerie = 0;
		int validadorEmail = 0;
		int validacionNumSerieAlquiler = 0;
		int validacionEmailAlquiler = 0;

		Statement stmt = null;

		try {

			stmt = con.createStatement();

			// 1. Comprobar que el número de serie ya exista en la tabla patinetes.
			String createString = "SELECT count(*) FROM patinetes WHERE numeroSerie = '" + numSerieAl + "'";
			ResultSet rs = stmt.executeQuery(createString);
			if (rs.next()) {
				validadorNumSerie = rs.getInt(1);
			}

			// 2. Comprobar que el mail exsita en la tabla usuarios.
			String createString2 = "SELECT count(*) FROM usuarios WHERE email = '" + emailAlquiler + "'";
			rs = stmt.executeQuery(createString2);
			if (rs.next()) {
				validadorEmail = rs.getInt(1);
			}

			// 3. Comprobar que no se repita el número de serie en la tabla
			// patinetesusuarios
			String createString3 = "SELECT count(*) FROM patinetesusuarios WHERE numeroSerie = '" + numSerieAl + "'";
			rs = stmt.executeQuery(createString3);
			if (rs.next()) {
				validacionNumSerieAlquiler = rs.getInt(1);
			}

			// 4. Comprobar que no exista el email en la tabla patienetesusuarios
			String createString4 = "SELECT count(*) FROM patinetesusuarios WHERE email = '" + emailAlquiler + "'";
			rs = stmt.executeQuery(createString4);
			if (rs.next()) {
				validacionEmailAlquiler = rs.getInt(1);
			}

			// Si el número de serie está en la tabla patinetes, el email está en la tabla usuarios y
			// el número de serie y el email NO están en la tabla patinetesusuarios INSERTA EL ALQUILER.
			if (validadorNumSerie != 0 && validadorEmail != 0 && validacionNumSerieAlquiler == 0
					&& validacionEmailAlquiler == 0) {

				stmt.executeUpdate("INSERT INTO " + BDNombre + ".patinetesusuarios VALUES " + "('" + numSerieAl + "', '"
						+ emailAlquiler + "')");
				
				System.out.println("\nSe ha introducido un nuevo alquiler en la base de datos.\n");
				System.out.println("Volviendo al menú de Gestión de Patinetes...");

				// Lo hago en un if anidado ya que puede pasar solo una cosa o más de una a la
				// vez y tiene que decir el mensaje correcto según lo que haya fallado
			} else if (validadorNumSerie == 0 || validadorEmail == 0 || validacionNumSerieAlquiler != 0
					|| validacionEmailAlquiler != 0) {

				if (validadorNumSerie == 0) {
					System.out.println("El número de serie " + numSerieAl + " no corresponde a ningún "
							+ "patinete de la base de datos.");
				}
				if (validadorEmail == 0) {
					System.out.println(
							"El email " + emailAlquiler + " no corresponde a ningún cliente " + "de la base de datos.");
				}
				if (validacionNumSerieAlquiler != 0) {
					System.out.println("El patinete con número de serie " + numSerieAl + " ya está alquilado.");
				}
				if (validacionEmailAlquiler != 0) {
					System.out.println("El cliente con email " + emailAlquiler + " ya tiene un alquiler.");
				}

					
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			stmt.close();
		}

	}
	
	public static void devolucionPatinete(Connection con, String BDNombre, String emailDev, int kmPatinete) throws SQLException {
		Scanner teclado = new Scanner(System.in); 
		int existeAlquiler = 0;
		int existeEmail = 0;
		
		String numSerieEquivalente = "";
		
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();	
			
			String createString = "SELECT email FROM usuarios WHERE email = '" + emailDev + "'";
			
			ResultSet rs = stmt.executeQuery(createString);
			
			while (rs.next()) {
				existeEmail++;
			}
			
			if (existeEmail == 0) {
				System.out.println("\nEl email introducido no existe!");
				MenuGestionarPatinetes(con, BDNombre);
			}
			
			// Comprobar que el email tenga un patinete asignado
			// Si el email está en la tabla patinetesusuarios es que tiene un alquiler en este momento
			String createString2 = "SELECT email, numeroSerie FROM patinetesusuarios WHERE email = '" + emailDev + "'";
			
			rs = stmt.executeQuery(createString2);
			
			while (rs.next()) {
				existeAlquiler++;
				numSerieEquivalente = rs.getString("numeroSerie");
			}	
			
			
			if (existeAlquiler == 1) {
				stmt.executeUpdate("UPDATE " + BDNombre + ".patinetes SET km_recorridos = (km_recorridos + " 
									+ kmPatinete + ") WHERE numeroSerie = '" + numSerieEquivalente + "'");				
				
				stmt.executeUpdate("DELETE FROM patinetesusuarios WHERE email = '" + emailDev + "'");
				
				System.out.println("\nEfectuada devolución de patinete.");
						
			} else {				
				System.out.println("\nEl cliente con email " + emailDev + " no tiene un alquiler en este momento.");
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
			
	}
	
	
}









