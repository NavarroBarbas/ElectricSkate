import java.sql.*;

public class ElectricSkate {

	public static void main(String[] args) {
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
			
			Usuarios.login(con, "electricskate");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
