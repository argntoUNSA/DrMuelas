package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class Conexion {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private String baseDato;
	private String USER;
	private String PASS;
	private String URL;
	
	Connection conexion;
	
	public Conexion(String baseDatos,String USER,String PASS) 
	{
		this.baseDato = baseDatos;
		this.USER = USER;
		this.PASS = PASS;
		this.URL = "jdbc:mysql://localhost:3306/"+baseDatos;
	}
	
	public String conectar() 
	{
		String respuesta;
		
		try 
		{
			Class.forName(JDBC_DRIVER);
			this.conexion = DriverManager.getConnection(URL, USER, PASS);
			if(conexion != null) 
			{
				respuesta = "CONECTADO";
			}
			else {
				respuesta = "NO CONECTADO";
			}
		}catch (ClassNotFoundException e) {
			respuesta="ocurre una ClassNotFoundException : "+e.getMessage();
		}
		catch (SQLSyntaxErrorException e) {
			respuesta="ocurre una SQLSyntaxErrorException: "+e.getMessage()+"\n";
			respuesta+="Verifique que se este usando la base de datos y tablas correctas...";
		}
		catch (CommunicationsException e) {
			respuesta="ocurre una CommunicationsException: "+e.getMessage()+"\n";
			respuesta+="Verifique que la base de datos fue iniciada...";
		}
		catch (SQLException e) {
			respuesta="ocurre una SQLException: "+e.getMessage()+"\n";
			respuesta+="Este es un problema general de SQL, verifique con el administrador";
		}
		return respuesta;
	}
	
	public Connection getConnection(){
		return this.conexion;
	}
	public void desconectar(){
		this.conexion = null;
	}
}
