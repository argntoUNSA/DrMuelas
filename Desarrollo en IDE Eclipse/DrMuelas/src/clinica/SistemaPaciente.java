package clinica;
import utilidades.Conexion;
import entidades.*;
import java.util.ArrayList;
import java.sql.*;

public class SistemaPaciente {
	private Paciente paciente;
	private FichaMedica ficha;
	private ArrayList<Turno> historial;
	private String usuario,contrasenia;
	private Conexion conn;
	
	public SistemaPaciente(){
	  this.usuario="";
	  this.contrasenia="";
	}
	
	private void setPaciente(Paciente p) {this.paciente=p;}
	private void setFichaMedica(FichaMedica fm) {this.ficha=fm;}
	private void setUsuario(String u) {this.usuario=u;}
	private void setContrasenia(String c) {this.contrasenia=c;}
	

	private Paciente getPaciente() {return this.paciente;}
	private FichaMedica getFichaMedica() {return this.ficha;}
	private ArrayList<Turno> getHistorial() {return this.historial;}
	private String getUsuario() {return this.usuario;}
	private Conexion getConexion() {return this.conn;}
	

	
	
	
	
	public void iniciarSesion(String vUsuario,String vContrasenia){
		try{
		  conn = new Conexion("dr_muelas","root","");
		  conn.conectar();
		  String sql;
		  Statement stmt;
		  ResultSet rs;
		  
		  sql="select usuario, contrasenia from paciente where usuario="+vUsuario;
		  stmt=this.getConexion().getConnection().createStatement();
		  rs=stmt.executeQuery(sql);
		  rs.next();
		  if(rs!=null && vContrasenia.compareTo(rs.getString("contrasenia"))==0){
		    this.setUsuario(vUsuario);
		    this.setContrasenia(vContrasenia);
		    cargarPaciente();
		    cargarFicha();
		    cargarHistorial();

		  }
		  else{
			  System.out.println("Los datos ingresados no son correctos");
		  }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		  
		}

	public void cargarPaciente(){
		try {
			Statement stmt=this.getConexion().getConnection().createStatement();
			ResultSet rs;
			String sql;
	  
			sql="select * from paciente where usuario="+this.getUsuario();
			rs=stmt.executeQuery(sql);
			rs.next();
			//creo variables utiles
			int id;
			Date fecha;
			boolean activo;
	 
			//le asigno valor a esas variables
			id=rs.getInt("id");
			fecha=rs.getDate("fechaRegistro");
	  
			Paciente aux=new Paciente(id,this.getUsuario(),fecha,true);
			this.setPaciente(aux);
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
		public void cargarFicha(){
			try {
			  Statement stmt=this.getConexion().getConnection().createStatement();
			  ResultSet rs;
			  String sql;
			  sql="select * from ficha where usuario="+this.getUsuario();
			  
			  rs=stmt.executeQuery(sql);
			  rs.next();
			  FichaMedica aux;
			  int id=rs.getInt("id"),edad=rs.getInt("edad");
			  long DNI=rs.getLong("DNI");
			  String nombre=rs.getString("nombre"),apellido=rs.getString("apellido"),alergias=(rs.getString("alergias")),tratamientos=rs.getString("tratamientos");
			  Date fechaCreacion=rs.getDate("fechaRegistro");
			  double peso=rs.getDouble("peso"),talla=rs.getDouble("talla");
			  aux=new FichaMedica(id,DNI,nombre,apellido,edad,fechaCreacion,peso,talla,alergias,tratamientos);
			  
			  setFichaMedica(aux);
			}
			catch(Exception e) {e.printStackTrace();}
		}
		public void cargarHistorial(){
			try {
			  Statement stmt=this.getConexion().getConnection().createStatement();
			  ResultSet rs1,rs2;
			  String sql1,sql2;
			  Turno aux;
			  sql1="select * from turno where id="+this.getPaciente().getId();
			  
			  rs1=stmt.executeQuery(sql1);
			  while(rs1.next()){
			    int id=(rs1.getInt("id"));
			    String estado=(rs1.getString("estado"));
			    Date fechaHora=(rs1.getDate("fechaRegistro")),fechaTurno=(rs1.getDate("fechaTurno"));
			    aux=new Turno(id,fechaHora,estado);
			    this.getHistorial().add(aux);
			  }
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
}
	
	
	
