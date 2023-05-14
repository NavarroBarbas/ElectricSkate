import java.sql.*;
import java.util.Scanner;

public class Patinete {

	public static void MenuGestionarPatinetes(Connection con, String BDNombre) throws SQLException {
		

		int opcionMenuPatinete;
		
		do {
			Scanner teclado = new Scanner(System.in);
			System.out.println();
			System.out.println("GESTIONAR PATINETES");
			System.out.println();
			System.out.println("1. Añadir Patinete");
			System.out.println("2. Alquiler Patinete");
			System.out.println("3. Devolver Patinete");
			System.out.println("4. Atrás");
			System.out.println();
			
			System.out.print("Elige una opción: ");
			
			opcionMenuPatinete = teclado.nextInt();
			teclado.nextLine();
				
			switch (opcionMenuPatinete) {
			case 1:
				String numSerie, marca, modelo, color;

				System.out.print("Introduzca el número de serie: ");
				numSerie = teclado.nextLine();
				System.out.print("Introduzca la marca: ");
				marca = teclado.nextLine();
				System.out.print("Introduzca el modelo: ");
				modelo = teclado.nextLine();
				System.out.print("Introduzca el color: ");
				color = teclado.nextLine();

				// Para que el número de serie siempre se ponga en mayúsculas
				numSerie = numSerie.toUpperCase();

				anyadirPatinete(con, BDNombre, numSerie, marca, modelo, color);

				break;
			case 2:
				String numSerieAl, emailAlquiler;

				System.out.print("Introduzca el número de serie del patinete que desea alquilar: ");
				numSerieAl = teclado.nextLine();
				System.out.print("Introduzca el email del cliente al que quiere alquilar el patinete: ");
				emailAlquiler = teclado.nextLine();
				System.out.println();

				// Para que el número de serie siempre se ponga en mayúsculas
				numSerieAl = numSerieAl.toUpperCase();

				alquilerPatinete(con, BDNombre, numSerieAl, emailAlquiler);

				break;
			case 3:				
				String emailDev;
				int kmPatinete;
				System.out.print("Introduzca el email del cliente que quiere hacer la devolución: ");
				emailDev = teclado.nextLine();
				System.out.print("Introduzca los kilómetros del patinete: ");
				kmPatinete = teclado.nextInt();
				
				devolucionPatinete(con, BDNombre, emailDev, kmPatinete);
				
				break;
			case 4:
				System.out.println();
				System.out.println("Volviendo atrás...");
				break;
			default:
				System.out.println();
				System.out.println("Opción inválida, vuelva a intentarlo.");
			}
			
		} while (opcionMenuPatinete != 4);

	}
	
	public static void anyadirPatinete(Connection con, String BDNombre, String numSerie, String marca, String modelo,
			String color) throws SQLException {

		Statement stmt = null;

		try {

			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO " + BDNombre + ".patinetes VALUES " + "('" + numSerie + "' , '" + marca
					+ "' , '" + modelo + "' , '" + color + "', " + 0 + ")");

			System.out.println("");
			System.out.println("Se ha introducido un nuevo patinete en la base de datos.");

		} catch (SQLException e) {
			e.printStackTrace();
			metodoVolver();
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
			ResultSet rs1 = stmt.executeQuery(createString);
			if (rs1.next()) {
				validadorNumSerie = rs1.getInt(1);
			}

			// 2. Comprobar que el mail exsita en la tabla usuarios.
			String createString2 = "SELECT count(*) FROM usuarios WHERE email = '" + emailAlquiler + "'";
			ResultSet rs2 = stmt.executeQuery(createString2);
			if (rs2.next()) {
				validadorEmail = rs2.getInt(1);
			}

			// 3. Comprobar que no se repita el número de serie en la tabla
			// patinetesusuarios
			String createString3 = "SELECT count(*) FROM patinetesusuarios WHERE numeroSerie = '" + numSerieAl + "'";
			ResultSet rs3 = stmt.executeQuery(createString3);
			if (rs3.next()) {
				validacionNumSerieAlquiler = rs3.getInt(1);
			}

			// 4. Comprobar que no exista el email en la tabla patienetesusuarios
			String createString4 = "SELECT count(*) FROM patinetesusuarios WHERE email = '" + emailAlquiler + "'";
			ResultSet rs4 = stmt.executeQuery(createString4);
			if (rs4.next()) {
				validacionEmailAlquiler = rs4.getInt(1);
			}

			// Si el número de serie está en la tabla patinetes, el email está en la tabla
			// usuarios y
			// el número de serie y el email NO están en la tabla patinetesusuarios INSERTA
			// EL ALQUILER.
			if (validadorNumSerie != 0 && validadorEmail != 0 && validacionNumSerieAlquiler == 0
					&& validacionEmailAlquiler == 0) {

				stmt.executeUpdate("INSERT INTO " + BDNombre + ".patinetesusuarios VALUES " + "('" + numSerieAl + "', '"
						+ emailAlquiler + "')");
				System.out.println("");
				System.out.println("Se ha introducido un nuevo alquiler en la base de datos.");
				System.out.println();
				System.out.println("Volviendo al menú de Gestión de Patinetes...");

				// Lo hago en un if anidado ya que puede pasar solo una cosa o más de una a la
				// vez y
				// tiene que decir el mensaje correcto según lo que haya fallado
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

				metodoVolver();		
			}

		} catch (SQLException e) {
			e.printStackTrace();
			metodoVolver();
		} finally {
			stmt.close();
		}

	}
	
	public static void devolucionPatinete(Connection con, String BDNombre, String emailDev, int kmPatinete) {
		Scanner teclado = new Scanner(System.in); 
		int validadorEmailDev = 0;
		int validadorEmailDev2 = 0;
		
		String numSerieEquivalente = "";
		
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();	
			
			// Comprobar que el email tenga un patinete asignado
			// Si el email está en la tabla patinetesusuarios es que tiene un alquiler en este momento
			String createString = "SELECT count(*) FROM patinetesusuarios WHERE email = '" + emailDev + "'";
			ResultSet rs = stmt.executeQuery(createString);
			if (rs.next()) {
				validadorEmailDev = rs.getInt(1);
			}
			// Buscamos que número de serie tiene el email introducido en la tabla patienetesusuarios.
			// Lo metemos en "numSerieEquivalente".
			String createString2 = "SELECT numeroSerie FROM patinetesusuarios WHERE email = '" + emailDev + "'";
			ResultSet rs2 = stmt.executeQuery(createString2);			
			if (rs2.next()) {
				// Solo hay una columna seleccionada en la consulta SQL por lo que ponemos (1)
				numSerieEquivalente = rs2.getString(1);
			}
			
			// Comprobar si el email está en la tabla usuarios
			String createString3 = "SELECT count(*) FROM usuarios WHERE email = '" + emailDev + "'";
			ResultSet rs3 = stmt.executeQuery(createString3);
			if (rs3.next()) {
				validadorEmailDev2 = rs3.getInt(1);
			}
			
			if (validadorEmailDev != 0) {
				stmt.executeUpdate("UPDATE " + BDNombre + ".patinetes SET km_recorridos = " 
									+ kmPatinete + " WHERE numeroSerie = '" + numSerieEquivalente + "'");				
				stmt.executeUpdate("DELETE FROM patinetesusuarios WHERE email = '" + emailDev + "'");
				System.out.println("Efectuada devolución de patinete.");
			} else if (validadorEmailDev2 == 0) {
				System.out.println("El email " + emailDev + " no corresponde a ningún cliente.");				
			}else {				
				System.out.println("El cliente con email " + emailDev + " no tiene un alquiler en este momento.");
			}
			
			metodoVolver();
		
		} catch (Exception e) {
			e.printStackTrace();
			metodoVolver();
		}
			
	}
	
	public static void metodoVolver() {
		Scanner teclado = new Scanner(System.in);
		String volver;
		
		System.out.println();
		System.out.println("Escribe la palabra \"exit\" para volver al menú de Gestión de patinetes: ");
		volver = teclado.nextLine();
		
		if (!volver.equals("exit")) {
			do {
				System.out.println();
				System.out.println("Por favor, escriba \"exit\" correctamente para salir");
				volver = teclado.nextLine();
			} while (!volver.equals("exit"));
		}
	}
	
}









