package clinica;
import utilidades.Conexion;
import entidades.*;
import java.util.ArrayList;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.time.*;


public class SistemaPaciente {
	private Paciente paciente;
	private FichaMedica ficha;
	private HashMap<String,Turno> historial;
	
	public SistemaPaciente(){
		limpiarFicha();
		limpiarPaciente();
		limpiarHistorial();
		}
	
		
	
//	metodos solicitados
	public void registrarse(String vUsuario,String vContrasenia){
		try{
			Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(conn.conectar());
			String sql;
			Statement stmt;
			ResultSet rs;
			int vId;
//			Paciente nuevoPaciente;
//			LocalDate vFecha;
//		    primero solicito su nombre de usuario y contraseña

			stmt=conn.getConnection().createStatement();

		    System.out.println("Inicio de Registro");
//		    Pido usuario
		    	
			System.out.println("Se ingreso este usuario: "+vUsuario);
//			reviso los datos almacenados previamente
			sql="select usuario from paciente where usuario=\'"+vUsuario+"\';";
			rs=stmt.executeQuery(sql);
			if(!rs.next()){//Si no existe tal usuario...
//				Se ingresa nuevo usuario a la BDD
				sql="select id from paciente order by DESC;";
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
					vId=rs.getInt("Id")+1;
				}
				else vId=1;
//				vFecha=LocalDate.now();    	
//				Creamos e  insertamos el objeto paciente
				sql="insert into paciente values ("+vId+","+vUsuario+","+vContrasenia+");";
				stmt.executeUpdate(sql);
				System.out.println("Registro completado");
			
		    }
		    else{
		    	System.out.println("Ya existe tal usuario ingrese otro");
		    }
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
		    cargarHistorial();
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
	public void solicitarTurno(int vId) {
		Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
		conn.conectar();
		String sql,fecha;
		Statement stmt;
		ResultSet rs;
		int id,idPaciente,idTurno;
		try {
			if(this.getPaciente()!=null) {
				stmt=conn.getConnection().createStatement();
				sql="select diaYHora from turno where id="+vId+";";
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
					System.out.println("nro ingresado del turno que se reservara: "+vId);
					idTurno=vId;
					idPaciente=this.getPaciente().getId();
					fecha=rs.getDate("diaYHora").toString();

					sql="select id from turno_asignado_paciente order by id DESC;";
					rs=stmt.executeQuery(sql);
					if(rs.next()) {
						id=rs.getInt("id")+1;
					}
					else{
						id=1;
					}
					sql="update turno set estado=\'Reservado\' where id="+idTurno+";";
					stmt.executeUpdate(sql);
					
					sql="insert into turno_asignado_paciente values ("+id+","+idPaciente+","+idTurno+",\'"+fecha+"\');";
					stmt.executeUpdate(sql);
					
					System.out.println("reserva finalizada con exito");
					
				}
				else {
					System.out.println("No se encontro turno disponible con el numero ingresado");
				}
			}
			else {
				System.out.println("Primero inicie sesion");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void cancelarTurno(int id) {
		if(this.getPaciente()!=null) {
			if(this.getHistorial().size()>0) {
				try {
					Conexion conn;
					Statement stmt;
					String sql;
					Turno turno;
					LocalDate fechaActual,fechaTurno;
					int diferencia;
					
					
					turno=this.getHistorial().get(String.valueOf(id));
					fechaActual=LocalDate.now();
					fechaTurno=LocalDate.parse(turno.getFechaHora());
					diferencia=calculaDiferencia(fechaActual,fechaTurno);
					conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
					conn.conectar();
					 
					if(diferencia>=1) {
						this.getHistorial().get(String.valueOf(id)).setEstado("Cancelado");
						stmt=conn.getConnection().createStatement();
						sql="update turno set estado=\'Cancelado\' where id="+id+";";
						stmt.executeUpdate(sql);
						System.out.println("Se cancelo correctamente");
					}
					else {
						System.out.println("No se puede cancelar");
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
			else {
				System.out.println("No hay turnos reservados");
			}
		}
		else {
			System.out.println("Primero Iniciar sesion");
		}
	}
	public void mostrarFichaMedica() {
		System.out.println(this.getFichaMedica().toString());
	}
	
//	metodos adicionales
	public int calculaDiferencia(LocalDate fechaA,LocalDate fechaB){
		//se considera fechaA menor que fechaB
		int diferencia=fechaB.compareTo(fechaA);
		System.out.println("Diferencia de fechas: "+diferencia);
		if(diferencia>0) {
			return diferencia;
		}
		else {
			return -1;
		}
	} 
	public void mostrarTurnosReservados(){
		if(this.getPaciente()!=null) {
			if(this.getHistorial().size()>0) {
				System.out.println(this.getHistorial());
				for(Turno elemento:this.getHistorial().values()) {
					if(elemento.getEstado().compareTo("Reservado")==0)
						System.out.println("["+elemento.toString()+"]");
				}
			}
			else {
				System.out.println("Historial vacio");
			}
		}
		else {
			System.out.println("Primero inicie sesion");
		}
	}
	public void mostrarTurnosDisponibles() {
		Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
		conn.conectar();
		String sql;
		Statement stmt;
		ResultSet rs;
		int i;
		LocalDate fecha;
		try {
			sql="select id, diaYHora from turno where estado=\'Disponible\';";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			System.out.println("Nro\tfecha");
			while(rs.next()) {
				i=rs.getInt("id");
				fecha=rs.getDate("diaYHora").toLocalDate();
				System.out.println(i+"\t"+fecha);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			if(conn!=null) conn.desconectar();
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
	public void cargarHistorial(){
		try {
			Conexion conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			conn.conectar();
			Statement stmt,stmtAux;
			ResultSet rs,rsAux;
			String sql,estado,fecha,sector;
			Turno aux;
			int idPaciente,id;
			limpiarHistorial();
			idPaciente=this.getPaciente().getId();
			sql="select * from turno_asignado_paciente where idPaciente="+idPaciente+";";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				id=(rs.getInt("idTurno"));
				sql="select * from turno where id="+id+";";
				stmtAux=conn.getConnection().createStatement();
				rsAux=stmtAux.executeQuery(sql);
				rsAux.next();
				estado=(rsAux.getString("estado"));
				fecha=(rsAux.getDate("diaYHora").toLocalDate().toString());
				sector=rs.getString("sector");
				aux=new Turno(id,fecha,estado,sector);
				this.getHistorial().put(String.valueOf(id),aux);
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void setPaciente(Paciente p) {this.paciente=p;}
	private void setFichaMedica(FichaMedica fm) {this.ficha=fm;}
	private Paciente getPaciente() {return this.paciente;}
	private FichaMedica getFichaMedica() {return this.ficha;}
	private HashMap<String,Turno> getHistorial() {return this.historial;}
	private void limpiarHistorial() {this.historial=new HashMap<String,Turno>();}
	private void limpiarPaciente() {this.paciente=null;}
	private void limpiarFicha() {this.ficha=null;}
	public String toString() {
		String cad="";
		if(this.getPaciente()!=null) {
			cad+="\nDatos del Paciente: \n"+this.getPaciente().toString();
			cad+="\nFicha del Paciente: \n"+this.getFichaMedica().toString();
			cad+="\nHistorial de turnos del Paciente: \n"+this.getHistorial().toString();
		}
		return cad;
		
	}
}
	
	
	
