package clinica;
import utilidades.Conexion;
import entidades.*;
import java.sql.*;
import java.util.HashMap;
import java.time.*;


public class SistemaPaciente {
	private Paciente paciente;
	private FichaMedica ficha;
	private HashMap<Integer,Turno> historial,turnosReservados;

	private final String USER,PASS,BDD;
	
	public SistemaPaciente(){
		limpiarFicha();
		limpiarPaciente();
		limpiarHistorial();
		limpiarTurnosReservados();
		USER="root";
		PASS="Ssap4toor0t0!";
		BDD="dr_muelas";
		}
	

//	metodos solicitados
	public void registrarse(String vUsuario,String vContrasenia,long vDNI,String vFechaNacimiento,String vNombre,String vApellido,Double vPeso,Double vTalla,String vAlergias,String vTratamientos){
		try{
			Conexion conn;
			String sql,sector,fecha;
			Statement stmt;
			ResultSet rs;
			int id,idFicha,edad;
			FichaMedica ficha;
			
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			
//		    primero solicito su usuario y contraseña
			stmt=conn.getConnection().createStatement();
//			reviso los datos almacenados previamente
			sql="select usuario from paciente where usuario=\'"+vUsuario+"\';";
			rs=stmt.executeQuery(sql);
			if(!rs.next()){//Si no existe tal usuario...
//				Se ingresa nuevo usuario a la BDD
				sql="select id from paciente order by id DESC;";
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
					id=rs.getInt("Id")+1;
				}
				else id=1;
				
				sql="select id from fichaMedica order by id DESC;";
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
					idFicha=rs.getInt("Id")+1;
				}
				else idFicha=1;
				
				ficha=new FichaMedica(idFicha,vDNI,vFechaNacimiento,vNombre, vApellido, vPeso, vTalla, vAlergias, vTratamientos);
				edad=ficha.getEdad();
				sql="insert into fichaMedica values ("+idFicha+","+vDNI+",\'"+vFechaNacimiento+"\',\'"+vNombre+"\',\'"+vApellido+"\',"+edad+","+vPeso+","+vTalla+",\'"+vAlergias+"\',\'"+vTratamientos+"\',"+true+");";
				stmt.executeUpdate(sql);
			
				if(edad>=3 && edad<=12) {
					sector="Primero";
				}
				else if (edad>12) {
					sector="Segundo";
				}
				else {
					sector="Vacio";
					System.out.println("Error al asignar sector");
				}
				
				fecha=LocalDate.now().toString();    	
//				Creamos e  insertamos el objeto paciente
				sql="insert into paciente values ("+id+","+idFicha+",\'"+sector+"\',\'"+vUsuario+"\',\'"+vContrasenia+"\',\'"+fecha+"\',"+true+");";
				stmt.executeUpdate(sql);
			
		    }
		    else{
		    	System.out.println("Ya existe tal usuario ingrese otro");
		    }
		 }
		 catch(Exception e){
			 System.err.print("Error al registrarse\n");
			 e.printStackTrace();
		   }
		}
	public boolean iniciarSesion(String vUsuario,String vContrasenia){
		try{
			Conexion conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			String sql;
			Statement stmt;
			ResultSet rs;
		  
			sql="select id, usuario, contrasenia from paciente where usuario=\'"+vUsuario+"\';";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
		  
			if(rs.next()&& vContrasenia.compareTo(rs.getString("contrasenia"))==0){
				System.out.println("Leyendo paciente");
				cargarPaciente(rs.getString("usuario"));
				System.out.println("Leyendo Ficha");
				cargarFicha(rs.getInt("id"));
				System.out.println("Leyendo Historial");
				cargarHistorial();
				return true;
			}	
			else{
			  System.out.println("Los datos ingresados no son correctos o la cuenta esta inactiva");
			  return false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		  
		}
	public void solicitarTurno(int dia) {
		int id,idPaciente,anio,mes;
		String diaSolicitado,estado,sector,sql,diaTurno;
		Turno miTurno;
		Conexion conn;
		Statement stmt;
		ResultSet rs;
		LocalDate fechaActual=LocalDate.now(); 
		anio=fechaActual.getYear();
		mes=fechaActual.getMonthValue();
		if(this.getPaciente()!=null && this.diaDisponible(dia)) {
			try {
				idPaciente=this.getPaciente().getId();
				diaSolicitado=fechaActual.toString();
//				diaTurno=LocalDate.parse(diaTurno).toString();
				diaTurno=LocalDate.of(anio, mes, dia).toString();
				estado="Reservado";
				sector=this.getPaciente().getSector();
				
				conn=new Conexion(BDD, USER, PASS);
				conn.conectar();
				sql="select id from turno order by id DESC;";
				stmt=conn.getConnection().createStatement();
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
					id=rs.getInt("id")+1;
				}
				else {
					id=1;
				}
				miTurno=new Turno(id,idPaciente,diaSolicitado,diaTurno,estado,sector,true);
				this.getTurnosReservados().put(id, miTurno);
				this.getHistorial().put(id,miTurno);
				
				sql="insert into turno values("+id+","+idPaciente+",\'"+diaSolicitado+"\',\'"+diaTurno+"\',\'"+estado+"\',\'"+sector+"\',"+true+");";
				stmt=conn.getConnection().createStatement();
				stmt.executeUpdate(sql);
				System.out.println("Se solicito turno nro: "+id);
			}
			catch(Exception e) {
				System.err.print("Error al solicitar turno\n");
				e.printStackTrace();
			}
		}
		else {
			if(this.getPaciente()!=null) {
				System.out.println("Primero inicie sesion");				
			}
			else {
				System.out.println("Dia no disponible");	
			}
		}
	}
	public void cancelarTurno(int vId) {
		if(this.getPaciente()!=null) {
			if(!this.getTurnosReservados().isEmpty() && this.getTurnosReservados().containsKey(vId)) {
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
						miTurno.setActivo(false);
						this.getTurnosReservados().remove(miTurno.getId());
						
						System.out.println("Se cancelo correctamente");
						
					}
					else {
						System.out.println("No se puede cancelar, faltan menos de 24 hs");
					}
				}
				catch(Exception e){
					System.out.println("Error al Cancelar turno");
					e.printStackTrace();
				}
				
			}
			else {
				if(!this.getTurnosReservados().isEmpty()) {
					System.out.println("Elija turno valido ");
				}
				else {
					System.out.println("No hay turnos reservados");
				}
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
			if(!this.getTurnosReservados().isEmpty()) {
				for(Turno elemento:this.getTurnosReservados().values()) {
					System.out.println("["+elemento.toString()+"]");
				}
			}
			else {
				System.out.println("No hay turnos reservados");
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
			String sql,fecha,sector;
			int id,idFicha;
			sql="select * from paciente where usuario=\'"+vUsuario+"\';";
			rs=stmt.executeQuery(sql);
			rs.next();
		
//			le asigno valor a esas variables
			id=rs.getInt("id");
			idFicha=rs.getInt("idFicha");
			sector=rs.getString("sector");
			fecha=rs.getDate("fechaCreacion").toLocalDate().toString();
//			cargo al Paciente
			Paciente aux=new Paciente(id,idFicha,sector,vUsuario,rs.getString("contrasenia"),fecha,true);
			
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
			  fechaNacimiento=rs.getDate	("fechaNacimiento").toLocalDate().toString();
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
			Turno miTurno;
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
				fechaSolicitado=(rs.getDate("diaSolicitado").toLocalDate().toString());
				fechaTurno=rs.getDate("diaDelTurno").toLocalDate().toString();
				estado=(rs.getString("estado"));
				sector=rs.getString("sector");
				activo=rs.getBoolean("activo");
				miTurno=new Turno(id,idPaciente,fechaSolicitado,fechaTurno,estado,sector,activo);
				this.getHistorial().put(id,miTurno);
				if(estado.compareTo("Reservado")==0 || activo) {
					this.getTurnosReservados().put(id,miTurno);
				}
				
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
	public HashMap<Integer, Turno> getTurnosReservados() {return this.turnosReservados;}
	private void limpiarHistorial() {this.historial=new HashMap<Integer,Turno>();}
	private void limpiarPaciente() {this.paciente=null;}
	private void limpiarFicha() {this.ficha=null;}
	private void limpiarTurnosReservados() {this.turnosReservados=new HashMap<Integer,Turno>();}

	
	
	public String toString() {
		String cad="";
		if(this.getPaciente()!=null) {
			cad+="\nDatos del Paciente: \n"+this.getPaciente().toString();
			cad+="\nFicha del Paciente: \n"+this.getFichaMedica().toString();
			cad+="\nHistorial de turnos del Paciente: \n"+this.getHistorial().toString();
		}
		return cad;
		
	}


	public void salir() {
		escribirFicha();
		escribirPaciente();
		escribirTurnos();
		
	}


	private void escribirTurnos() {
		
		if(this.getPaciente()!=null) {
			Conexion conn;
			String sql,estado,fechaSolicitado,fechaTurno,sector;
			Statement stmt;
			ResultSet rs;
			int id,idPaciente;
			boolean activo;
			LocalDate diaActual,diaTurno;
			
			diaActual=LocalDate.now();
			try {	
				conn = new Conexion(BDD,USER,PASS);
				conn.conectar();
				
				for(Turno turno:this.getHistorial().values()) {
					id=turno.getId();
					idPaciente=turno.getIdPaciente();
					fechaSolicitado=turno.getDiaSolicitado();
					fechaTurno=turno.getDiaDelTurno();
					sector=turno.getSector();
					stmt=conn.getConnection().createStatement();
					diaTurno=LocalDate.parse(fechaTurno);
					if(diaActual.compareTo(diaTurno)>0 && turno.isActivo()) {
						estado="Atendido";
						activo=false;
					}
					else {
						estado=turno.getEstado();
						activo=turno.isActivo();
					}
					
						
					sql="select id from turno where id="+id+";";
					rs=stmt.executeQuery(sql);
					if(!rs.next()) {
						sql="insert into turno values("+id+","+idPaciente+",\'"+fechaSolicitado+"\',\'"+fechaTurno+"\',\'"+estado+"\',\'"+sector+"\',"+activo+");";
						stmt=conn.getConnection().createStatement();
						stmt.executeUpdate(sql);
					}
					else {
//						sql="update turno set id="+activo+" where id="+id+";";
//						stmt.executeUpdate(sql);
						
						sql="update turno set idPaciente="+idPaciente+" where id="+id+";";
						stmt.executeUpdate(sql);
						sql="update turno set diaSolicitado=\'"+fechaSolicitado+"\' where id="+id+";";
						stmt.executeUpdate(sql);
						sql="update turno set diaDelTurno=\'"+fechaTurno+"\' where id="+id+";";
						stmt.executeUpdate(sql);
						sql="update turno set estado=\'"+estado+"\' where id="+id+";";
						stmt.executeUpdate(sql);
						sql="update turno set sector=\'"+sector+"\' where id="+id+";";
						stmt.executeUpdate(sql);
						sql="update turno set activo="+activo+" where id="+id+";";
						stmt.executeUpdate(sql);
					}
				
				}
			}
			catch(Exception e) {
				System.err.print("Error al escribirTurno");
				e.printStackTrace();
			}
			
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
					conn.conectar();
					
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
					sql="update fichaMedica set fechaNacimiento=\'"+fechaNacimiento
							+ "\' where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set nombre=\'"+nombre+"\'"
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set apellido=\'"+apellido+"\'"
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
					sql="update fichaMedica set alergias=\'"+alergias+"\'"
							+ " where id="+id+";";
					stmt.executeUpdate(sql);
					sql="update fichaMedica set tratamientos=\'"+tratamientos+"\'"
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
				conn.conectar();
				
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
				sql="update paciente set usuario=\'"+usuario
						+ "\' where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set contrasenia=\'"+contrasenia
						+ "\' where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set fechaCreacion=\'"+fechaCreacion
						+ "\' where id="+id+";";
				stmt.executeUpdate(sql);
				sql="update paciente set activo="+activo
						+ " where id="+id+";";
				stmt.executeUpdate(sql);
				
				
			}
			catch(Exception e) {
				System.err.print("Error al escribir Paciente");
			}
		}
	}

	public boolean diaDisponible(int dia) {
		int diaTurnoReservado;
		if(this.getPaciente()!=null) {
			if(!this.getTurnosReservados().isEmpty()) {
				for(Turno turnoLeido:this.getTurnosReservados().values()) {
					diaTurnoReservado=LocalDate.parse(turnoLeido.getDiaDelTurno()).getDayOfMonth();
					if(dia==diaTurnoReservado)
						return false;
					
				}
			}
		}
		return true;
	}

//	public void mostrarTurnosDisponibles() {
//	int cantidad;
//	if(this.getPaciente()!=null) {
//		cantidad=this.getTurnosReservados().size();
//		if(cantidad>0) {
//			System.out.println("\nNro\tfecha");
//			for(Turno turnoLeido:this.getTurnosReservados().values()) {
//				System.out.println("\n"+turnoLeido.getId()+"\t"+turnoLeido.getDiaDelTurno());
//			}
//		}
//		else {
//			System.out.println("No hay turnos disponibles");
//		}
//	}
//	else {
//		System.out.println("Primero inicie sesion");
//	}
//}
}
	
	
	
