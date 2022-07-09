package clinica;
import utilidades.Conexion;
import entidades.*;
import java.sql.*;
import java.util.HashMap;
import java.time.*;


public class SistemaPaciente {
	private Paciente paciente;
	private FichaMedica ficha;
	private HashMap<Integer,Turno> historial,turnosDisponibles;
	
	private final String USER,PASS,BDD;
	
	public SistemaPaciente(){
		limpiarFicha();
		limpiarPaciente();
		limpiarHistorial();
		limpiarTurnosDisponibles();
		USER="root";
		PASS="Ssap4toor0t0!";
		BDD="dr_muelas";
		}
	

//	metodos solicitados
	public void registrarse(String vUsuario,String vContrasenia){
		try{
			Conexion conn = new Conexion(BDD,USER,PASS);
			System.out.println(conn.conectar());
			String sql;
			Statement stmt;
			ResultSet rs;
			int vId;
//		    primero solicito su usuario y contraseña
			stmt=conn.getConnection().createStatement();
		    System.out.println("Inicio de Registro");
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
			Conexion conn = new Conexion(BDD,USER,PASS);
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
		int idPaciente;
		Turno miTurno;
		if(this.getPaciente()!=null) {
			if(this.getTurnosDisponibles().size()>0) {
				System.out.println("nro ingresado del turno que se reservara: "+vId);
				idPaciente=this.getPaciente().getId();
				if(this.getTurnosDisponibles().containsKey(vId)) {
					miTurno=this.getTurnosDisponibles().get(vId);
					miTurno.setEstado("Reservado");
					miTurno.setIdPaciente(idPaciente);
					miTurno.setDiaSolicitado(LocalDate.now().toString());
					this.getHistorial().put(miTurno.getId(),miTurno);
					System.out.println("reserva finalizada con exito");
				}
				else {
					System.out.println("No se encontro turno con ese numero");
				}
				
			}
			else {
				System.out.println("No hay turnos disponibles");
			}
		}
		else {
			System.out.println("Primero inicie sesion");
		}
	}
	public void cancelarTurno(int vId) {
		if(this.getPaciente()!=null) {
			if(this.getHistorial().size()>0) {
				try {
					
					Turno miTurno;
					String fechaActual,fechaTurno;
					int diferencia;
					
					
					miTurno=this.getHistorial().get(vId);
					fechaActual=LocalDate.now().toString();
					fechaTurno=miTurno.getDiaDelTurno();
					
					diferencia=calculaDiferencia(LocalDate.parse(fechaActual),LocalDate.parse(fechaTurno));
					if(diferencia>=1) {
						miTurno=this.getHistorial().get(vId);
						miTurno.setEstado("Cancelado");
						
						System.out.println("Se cancelo correctamente");
					}
					else {
						System.out.println("No se puede cancelar, faltan menos de 24 hs");
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
		if(this.getPaciente()!=null) {
			System.out.println(this.getFichaMedica().toString());
		}
		else {
			System.out.println("Primero inicie sesion");
		}
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
		int cantidad;
		if(this.getPaciente()!=null) {
			cantidad=this.getTurnosDisponibles().size();
			if(cantidad>0) {
				System.out.println("\nNro\tfecha");
				for(Turno turnoLeido:this.getTurnosDisponibles().values()) {
					System.out.println("\n"+turnoLeido.getId()+"\t"+turnoLeido.getDiaDelTurno());
				}
			}
			else {
				System.out.println("No hay turnos disponibles");
			}
		}
		else {
			System.out.println("Primero inicie sesion");
		}
	}
	public void cargarPaciente(String vUsuario){
		try {
			Conexion conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			Statement stmt=conn.getConnection().createStatement();
			ResultSet rs;
			String sql,fecha;
			int id,idFicha;
			sql="select * from paciente where usuario=\'"+vUsuario+"\';";
			rs=stmt.executeQuery(sql);
			rs.next();
		
//			le asigno valor a esas variables
			id=rs.getInt("id");
			idFicha=rs.getInt("idFicha");
			fecha=rs.getDate("fechaRegistro").toLocalDate().toString();
//			cargo al Paciente
			Paciente aux=new Paciente(id,idFicha,vUsuario,"",fecha,true);
			
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
			String nombre,apellido,fechaNacimiento,alergias,tratamientos;
			double peso,talla;
			boolean activo;
			FichaMedica aux;
			if( rs.next()) {
			  id=rs.getInt				("id");
			  DNI=rs.getLong			("DNI");
			  fechaNacimiento=rs.getDate	("fechaNacimietno").toLocalDate().toString();
			  nombre=rs.getString		("nombre");
			  apellido=rs.getString		("apellido");
			  edad=rs.getInt			("edad");
			  peso=rs.getDouble			("peso");
			  talla=rs.getDouble		("talla");
			  alergias=(rs.getString	("alergias"));
			  tratamientos=rs.getString	("tratamientos");
			  activo=rs.getBoolean("activo");
			  aux=new FichaMedica(id,DNI,fechaNacimiento,nombre,apellido,edad,peso,talla,alergias,tratamientos,activo);
			  
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
			 aux=new FichaMedica(id,-1,"vacio","vacio","vacio",0,0.0,0.0,"vacio","vacio",true);
			 setFichaMedica(aux);
			 
		  }
		}
		catch(Exception e) {e.printStackTrace();}
	}
	public void cargarHistorial(){
		try {
			Conexion conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			Statement stmt;
			ResultSet rs;
			String sql,estado,fechaSolicitado,fechaTurno,sector;
			Turno aux;
			int idPaciente,id;
			boolean activo;
			
			limpiarHistorial();
			idPaciente=this.getPaciente().getId();
			sql="select * from turno where idPaciente="+idPaciente+";";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				id=(rs.getInt("id"));
				idPaciente=rs.getInt("idPaciente");
				fechaSolicitado=(rs.getDate("diayHoraSolicitado").toLocalDate().toString());
				fechaTurno=rs.getDate("diaYHoraDelTurno").toLocalDate().toString();
				estado=(rs.getString("estado"));
				sector=rs.getString("sector");
				activo=rs.getBoolean("activo");
				aux=new Turno(id,idPaciente,fechaSolicitado,fechaTurno,estado,sector,activo);
				this.getHistorial().put(id,aux);
				
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
	private HashMap<Integer,Turno> getHistorial() {return this.historial;}
	private HashMap<Integer, Turno> getTurnosDisponibles() {return this.turnosDisponibles;}
	private void limpiarHistorial() {this.historial=new HashMap<Integer,Turno>();}
	private void limpiarPaciente() {this.paciente=null;}
	private void limpiarFicha() {this.ficha=null;}
	private void limpiarTurnosDisponibles() {this.turnosDisponibles=new HashMap<Integer,Turno>();}

	
	
	public String toString() {
		String cad="";
		if(this.getPaciente()!=null) {
			cad+="\nDatos del Paciente: \n"+this.getPaciente().toString();
			cad+="\nFicha del Paciente: \n"+this.getFichaMedica().toString();
			cad+="\nHistorial de turnos del Paciente: \n"+this.getHistorial().toString();
		}
		return cad;
		
	}


	public void escribirDatos() {
		escribirPaciente();
		escribirFicha();
		escribirTurnos();
		
	}


	private void escribirTurnos() {
		
		if(this.getPaciente()!=null) {
			Conexion conn;
			String sql,estado;
			Statement stmt;
			int idPaciente;
			LocalDate fechaSolicitado;
			try {	
				conn = new Conexion(BDD,USER,PASS);
				System.out.println(conn.conectar());
				stmt=conn.getConnection().createStatement();
				
				for(Turno turno:this.getHistorial().values()) {
					idPaciente=turno.getIdPaciente();
					fechaSolicitado=LocalDate.parse(turno.getDiaSolicitado());
					estado=turno.getEstado();
					
					sql="update turno"
							+ " set idPaciente="+idPaciente
							+ " where id="+turno.getId()+";";
					stmt.executeUpdate(sql);
					
					sql="update turno"
							+ " set diaSolicitado="+fechaSolicitado
							+ " where id="+turno.getId()+";";
					stmt.executeUpdate(sql);
					
					sql="update turno"
							+ " set estado="+estado
							+ " where id="+turno.getId()+";";
					stmt.executeUpdate(sql);
				}
			}
			catch(Exception e) {
				System.err.print("Error al escribirTurno");
				e.printStackTrace();
			}
			
		}
		else {
			System.out.println("Inicie sesion");
		}
		
	}


	private void escribirFicha() {
		if(this.getPaciente()!=null) {
			if(this.ficha!=null) {
				Conexion conn;
				String sql,nombre,apellido,alergias,tratamientos;
				LocalDate fechaNacimiento;
				Statement stmt;
				int id,edad;
				FichaMedica ficha;
				long DNI;
				double peso,talla;
				
				try {
					conn = new Conexion(BDD,USER,PASS);
					System.out.println(conn.conectar());
					
					ficha=this.getFichaMedica();
					id=ficha.getId();
					DNI=ficha.getDNI();
					fechaNacimiento=LocalDate.parse(ficha.getFechaNacimiento());
					nombre=ficha.getNombre();
					apellido=ficha.getApellido();
					edad=ficha.getEdad();
					peso=ficha.getPeso();
					talla=ficha.getTalla();
					alergias=ficha.getAlergias();
					tratamientos=ficha.getTratamientos();
					
					stmt=conn.getConnection().createStatement();
					
					sql="update fichaMedica set DNI="+DNI
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set fechaNacimiento="+fechaNacimiento
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set nombre="+nombre+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set apellido="+apellido+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set edad="+edad+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set peso="+peso+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set talla="+talla+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set alergias="+alergias+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set tratamientos="+tratamientos+""
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
				}
				catch(Exception e) {
					System.err.print("Error al escribir ficha medica");
					e.printStackTrace();
				}
				
			}
			else {
				System.out.println("No hay ficha para cargar");
			}
			
		}
		else {
			System.out.println("Inicie sesion");
		}
		
	}


	private void escribirPaciente() {
		if(this.getPaciente()!=null) {
			Conexion conn;
			String sql,usuario,contrasenia;
			Statement stmt;
			int id,idFicha;
			LocalDate fechaCreacion;
			Paciente paciente;
			boolean activo;
			try {
				paciente=this.getPaciente();
				conn=new Conexion(BDD, USER, PASS);
				System.out.println(conn.conectar());
				
				id=paciente.getId();
				idFicha=paciente.getIdFicha();
				usuario=paciente.getUsuario();
				contrasenia=paciente.getContrasenia();
				fechaCreacion=LocalDate.parse(paciente.getFechaCreacion());
				
				activo=paciente.isActivo();
				
				stmt=conn.getConnection().createStatement();
				
				sql="update paciente set idFicha="+idFicha
						+ " where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set usuario="+usuario
						+ " where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set contrasenia="+contrasenia
						+ " where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set fechaCreacion="+fechaCreacion
						+ " where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set activo="+activo
						+ " where id="+id+";";
				stmt.executeUpdate(sql);
				
				
			}
			catch(Exception e) {
				System.err.print("Error al escribir Paciente");
			}
		}
		else {
			System.out.println("Inicie sesion");
		}
	}
}
	
	
	
