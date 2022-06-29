import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreacionDeaBDD {
	
	static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost:3306/dr_muelas";
	static final String USER="root";
	static final String PASS="Ssap4toor0t0!";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement stmt=null;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			stmt=conn.createStatement();
			String sql;
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}

}
