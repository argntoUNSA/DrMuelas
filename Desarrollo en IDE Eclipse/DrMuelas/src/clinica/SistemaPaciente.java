package clinica;
import utilidades.Conexion;
import entidades.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.util.Date;
import java.time.*;


public class SistemaPaciente {
	private Paciente paciente;
	private FichaMedica ficha;
	private ArrayList<Turno> historial;
	
	public SistemaPaciente(){
		limpiarFicha();
		limpiarPaciente();
		limpiarHistorial();
		}
	
	private void setPaciente(Paciente p) {this.paciente=p;}
	private void setFichaMedica(FichaMedica fm) {this.ficha=fm;}
	
	private Paciente getPaciente() {return this.paciente;}
	private FichaMedica getFichaMedica() {return this.ficha;}
	private ArrayList<Turno> getHistorial() {return this.historial;}
	
	private void limpiarHistorial() {
		this.historial=new ArrayList<Turno>();
	}
	private void limpiarPaciente() {
		this.paciente=null;
	}
	private void limpiarFicha() {
		this.ficha=null;
	}
	
	
	public void registrarse(String vUsuario,String vContrasenia){
		try{
			Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(conn.conectar());
			String sql;
			Statement stmt;
			ResultSet rs;
			int vId,bandera;
//			Paciente nuevoPaciente;
//			LocalDate vFecha;
//		    primero solicito su nombre de usuario y contraseña

		     stmt=conn.getConnection().createStatement();
		    
		    do {
		    	System.out.println("Inicio de Registro");
		    	//Pido usuario
		    	
			    System.out.println("Se ingreso este usuario: "+vUsuario);
			    //reviso los datos almacenados previamente
			    sql="select usuario from paciente where usuario="+vUsuario+";";
			    rs=stmt.executeQuery(sql);
			    if(!rs.next()){//Si no existe tal usuario...
			    	//Se ingresa nuevo usuario a la BDD
			    	sql="select id from paciente order by DESC;";
				    rs=stmt.executeQuery(sql);
				    if(rs.next()) {
				    	vId=rs.getInt("Id")+1;
				    }
				    else vId=1;
//				    vFecha=LocalDate.now();    	
//			    	Creamos e  insertamos el objeto paciente
			    	
			    	sql="insert into paciente values ("+vId+","+vUsuario+","+vContrasenia+");";
			    	stmt.executeUpdate(sql);
			    	System.out.println("Registro completado");
			    	bandera=1;
			    }
			    else{
			    	System.out.println("Ya existe tal usuario ingrese otro");
			    	bandera=0;
			    }
		    }
		    while(bandera==0);
		    System.out.println("Fin de Registro");
		 }
		 catch(Exception e){
		   e.printStackTrace();
		   }
		}

	public void iniciarSesion(String vUsuario,String vContrasenia){
		try{
		  Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
		  conn.conectar();
		  String sql;
		  Statement stmt;
		  ResultSet rs;
		  
		  sql="select id, usuario, contraseña from paciente where usuario=\'"+vUsuario+"\';";
		  stmt=conn.getConnection().createStatement();
		  rs=stmt.executeQuery(sql);
		  
		  if(rs.next()&& vContrasenia.compareTo(rs.getString("contraseña"))==0){
		    cargarPaciente(rs.getString("usuario"));
		    cargarFicha(rs.getInt("id"));
		    cargarHistorial(rs.getInt("id"));
		    System.out.println("Inicio  de Sesion completado");
		  }
		  else{
			  System.out.println("Los datos ingresados no son correctos o la cuenta esta inactiva");
		  }
		  conn.desconectar();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		  
		}
	public void cargarPaciente(String vUsuario){
		try {
			Conexion conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			conn.conectar();
			Statement stmt=conn.getConnection().createStatement();
			ResultSet rs;
			String sql;
	  
			sql="select * from paciente where usuario=\'"+vUsuario+"\';";
			rs=stmt.executeQuery(sql);
			rs.next();
			//creo variables utiles
			int id;
//			LocalDate fecha;
			//le asigno valor a esas variables
			id=rs.getInt("id");
//			fecha=rs.getDate("fechaRegistro").toLocalDate();
//			cargo al Paciente
//			Paciente aux=new Paciente(id,this.getUsuario(),"",fecha,true);
			Paciente aux=new Paciente(id,vUsuario,"",LocalDate.now(),true);
			
			this.setPaciente(aux);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	public void cargarFicha(int vId){
		try {
			Conexion conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			conn.conectar();
			Statement stmt=conn.getConnection().createStatement();
			ResultSet rs;
			String sql;
			sql="select * from fichaMedica where id="+vId+";";
			rs=stmt.executeQuery(sql);
			int id,edad;
			long DNI;
			String nombre,apellido,fechaCreacion,alergias,tratamientos;
			double peso,talla;
			FichaMedica aux;
			if( rs.next()) {
			  id=rs.getInt				("id");
			  DNI=rs.getLong			("DNI");
			  nombre=rs.getString		("nombre");
			  apellido=rs.getString		("apellido");
			  edad=rs.getInt			("edad");
			  peso=rs.getDouble			("peso");
			  talla=rs.getDouble		("talla");
//			  fechaCreacion=rs.getDate	("fechaRegistro").toGMTString();
			  alergias=(rs.getString	("alergias"));
			  tratamientos=rs.getString	("tratamientos");
//			  aux=new FichaMedica(id,DNI,nombre,apellido,edad,fechaCreacion,peso,talla,alergias,tratamientos);
			  aux=new FichaMedica(id,DNI,nombre,apellido,edad,peso,talla,alergias,tratamientos);
			  
			  setFichaMedica(aux);
		  }
		  else {
			  System.out.println("El Paciente no tiene ficha cargada\nSe le asignara una ficha vacia");
			  sql="select id from fichaMedica order by id DESC;";
			  rs=stmt.executeQuery(sql);
			 if(rs.next())
				 id=rs.getInt("id")+1;
			 else
				 id=0;
//			 aux=new FichaMedica(id,-1,"vacio","vacio",0,LocalDate.now().toString(),0.0,0.0,"vacio","vacio");
			 aux=new FichaMedica(id,-1,"vacio","vacio",0,0.0,0.0,"vacio","vacio");
			 setFichaMedica(aux);
			 
		  }
		}
		catch(Exception e) {e.printStackTrace();}
	}
	public void cargarHistorial(int vId){
		try {
			Conexion conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			conn.conectar();
			Statement stmt=conn.getConnection().createStatement();
			ResultSet rs;
			String sql;
			Turno aux;
			limpiarHistorial();
			sql="select * from turno where id="+vId+";";
			
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				int id=(rs.getInt("id"));
				String estado=(rs.getString("estado"));
				Date fechaHora=(rs.getDate("diaYHora"));
				aux=new Turno(id,fechaHora,estado);
				this.getHistorial().add(aux);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		String cad="";
		if(this.getPaciente()!=null) {
			cad+="\nDatos del Paciente: "+this.getPaciente().toString();
			cad+="\nFicha del Paciente: "+this.getFichaMedica().toString();
			cad+="\nHistorial de turnos del Paciente: "+this.getHistorial().toString();
		}
		return cad;
		
	}
}
	
	
	
