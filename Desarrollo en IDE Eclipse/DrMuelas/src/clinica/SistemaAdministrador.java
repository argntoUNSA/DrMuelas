package clinica;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import entidades.*;
import utilidades.Conexion;;

public class SistemaAdministrador {
	private ArrayList<Paciente> listaPacientes;
	private ArrayList<Turno> listaTurnos,ListaTurnosEmergencia;
	private ArrayList<FichaMedica> listaFichasMedicas; 
	private final String USER="root",PASS="Ssap4toor0t0!",BDD="dr_muelas";
	
	public SistemaAdministrador() {
		setListaPacientes(new ArrayList<Paciente>());
		setListaFichasMedicas(new ArrayList<FichaMedica>());
		setListaTurnos(new ArrayList<Turno>());
		sestlistaEmergencia(new ArrayList<Turno>());
		iniciar();
	}
	
//	Metodos requeridos
	public void cargarPacienteEmergencia(long vDNI,String vNombre,String vApellido,String vFechaNaciemiento,Double vPeso,Double vTalla,String vAlergias,String vTratamientos) {
		Paciente nuevoPaciente,ultimoPaciente;
		FichaMedica nuevaFicha,ultimaFicha;
		Turno nuevoTurno,ultimoTurno;
		int idPaciente,idFicha,idTurno,idTurnoEmergencia,indice;
		String usuario,fecha;
		System.out.println("Cargando paciente emergencia: ");
		
		
		if(!this.getListaFichasMedicas().isEmpty()){
			indice=this.getListaFichasMedicas().size()-1;
			ultimaFicha=this.getListaFichasMedicas().get(indice);
			idFicha=ultimaFicha.getId()+1;
		}
		else {
			idFicha=1;
		}
		nuevaFicha=new FichaMedica(idFicha,vDNI,vFechaNaciemiento,vNombre,vApellido,vPeso,vTalla,vAlergias,vTratamientos);
		this.getListaFichasMedicas().add(nuevaFicha);
		
		if(!this.getListaPacientes().isEmpty()) {
			indice=this.getListaPacientes().size()-1;
			ultimoPaciente=this.getListaPacientes().get(indice);
			idPaciente=ultimoPaciente.getId()+1;
		}
		else {
			idPaciente=1;
		}
		usuario=String.valueOf(vDNI);
		nuevoPaciente=new Paciente(idPaciente,idFicha,"Emergencia",usuario,LocalDate.now().toString());
		this.getListaPacientes().add(nuevoPaciente);
		
		if(!this.getListaTurnosEmergencia().isEmpty()) {
			indice=this.getListaTurnosEmergencia().size()-1;
			ultimoTurno=this.getListaTurnosEmergencia().get(indice);
			idTurnoEmergencia=ultimoTurno.getId()+1;
		}
		else {
			idTurnoEmergencia=1;
		}
		idPaciente=nuevoPaciente.getId();
		fecha=LocalDate.now().toString();
		
		nuevoTurno=new Turno(idTurnoEmergencia,idPaciente,fecha,fecha,"Reservado","Emergencia",true);
		this.getListaTurnosEmergencia().add(nuevoTurno);
		System.out.print("Se cargo paciente en sector emergencia, turno nro: "+idTurnoEmergencia);
		
		if(!this.getListaTurnos().isEmpty()) {
			indice=this.getListaTurnos().size()-1;
			ultimoTurno=this.getListaTurnos().get(indice);
			idTurno=ultimoTurno.getId()+1;
		}
		else {
			idTurno=1;
		}
		
		nuevoTurno=new Turno(idTurno,idPaciente,fecha,fecha,"Reservado","Emergencia",true);
		this.getListaTurnos().add(nuevoTurno);
		System.out.println(" con exito");
	}
	public void ListarFichasMedicas() {
		int cantidad;
		FichaMedica ficha;
		cantidad=this.getListaFichasMedicas().size();
		for(int i=0;i<cantidad;i++) {
			ficha=this.getListaFichasMedicas().get(i);
			System.out.println("["+ficha+"]");
		}
	}
	public void informes(int mesInforme) {
//		Definimos
		Turno turnoLeido;
		int cantidadSector1,cantidadSector2,cantidadSector3,cantidadTotalTurnos,mesTurno,anioActual,diaActual;
		LocalDate fecha;
	
//		Calculamos
		cantidadTotalTurnos=this.getListaTurnos().size();
		cantidadSector1=cantidadSector2=cantidadSector3=0;
		for(int i=0;i<cantidadTotalTurnos;i++) {
			turnoLeido=this.getListaTurnos().get(i);
			fecha=LocalDate.parse(turnoLeido.getDiaDelTurno());
			anioActual=LocalDate.now().getYear();
			mesTurno=fecha.getMonthValue();
			diaActual=LocalDate.now().getDayOfMonth();
			if(turnoLeido.isActivo() && mesInforme==mesTurno && fecha.getYear()==anioActual && fecha.getDayOfMonth()<diaActual) {
				switch(turnoLeido.getSector()) {
				case "Primero":{
					cantidadSector1++;
					break;
				}
				case "Segundo":{
					cantidadSector2++;
					break;
				}
				case "Emergencia":{
					cantidadSector3++;
					break;
				}		
				default:{
					System.out.println("Turno de sector desconocido");
				}
				}
			}
		}
		cantidadTotalTurnos=cantidadSector1+cantidadSector2+cantidadSector3;
		System.out.println("Total de pacientes atendidos: "+cantidadTotalTurnos);
		System.out.println("Pacientes de 3 a 12 años: "+cantidadSector1);
		System.out.println("Pacientes de +12 años: "+cantidadSector2);
		System.out.println("Pacientes de emergencia: "+cantidadSector3);	
	}


//	Metodos Adicionales
	public void iniciar() {
		System.out.println("Leyendo Pacientes");
		cargarPacientes();
		System.out.println("Leyendo Fichas");
		cargarFichas();
		System.out.println("Leyendo Turnos");
		cargarTurnos();
	}
	public void cargarPacientes() {
		try {
			Conexion conn;
			String sql,usuario,sector,fecha;
			Statement stmt;
			ResultSet rs;
			Paciente pacienteLeido=null;		
			int id,idFicha;
			boolean activo;
			
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			sql="select * from paciente;";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				id=rs.getInt("id");
				idFicha=rs.getInt("idFicha");
				usuario=rs.getString("usuario");
				sector=rs.getString("sector");
				fecha=rs.getDate("fechaCreacion").toLocalDate().toString();
				activo=rs.getBoolean("activo");
				pacienteLeido=new Paciente(id,idFicha,sector,usuario,rs.getString("contrasenia"),fecha,activo);
				cargarUnPaciente(pacienteLeido);
			}
			
			
		}
		catch(Exception e) {
			System.err.print("Error en cargarPacientes");
			e.printStackTrace();
		}
	}
	public void cargarFichas() {
		try {
			Conexion conn;
			String sql,fechaNacimiento,nombre,apellido,alergias,tratamientos;
			Statement stmt;
			ResultSet rs;
			int id,edad;
			long DNI;
			double peso,talla;
			FichaMedica fichaLeido;
			boolean activo;
			
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			sql="select * from fichaMedica;";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				id=rs.getInt			("id");
				DNI=rs.getLong			("DNI");
				fechaNacimiento=rs.getDate("fechaNacimiento").toString();
				nombre=rs.getString		("nombre");
				apellido=rs.getString	("apellido");
				edad=rs.getInt			("edad");
				peso=rs.getDouble		("peso");
				talla=rs.getDouble		("talla");
				alergias=(rs.getString	("alergias"));
				tratamientos=rs.getString("tratamientos");
				activo=rs.getBoolean("activo");
				
				fichaLeido=new FichaMedica(id, DNI, fechaNacimiento, nombre, apellido, edad, peso, talla, alergias, tratamientos,activo);
				this.cargarUnaFicha(fichaLeido);
			}
		}
		catch(Exception e) {
			System.err.print("Error al cargarFichas\n");
			e.printStackTrace();
		}
	}
	public void cargarTurnos() {
		try {
			Conexion conn;
			String sql,fechaSolicitado,fechaTurno,estado,sector;
			Statement stmt;
			ResultSet rs;
			Turno turnoLeido;		
			int id,idPaciente;
			boolean activo;
	
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			
			sql="select * from turno;";
			stmt=conn.getConnection().createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				activo=rs.getBoolean("activo");
					
				if(activo) {
					id=(rs.getInt("id"));
					idPaciente=(rs.getInt("idPaciente"));
					fechaSolicitado=rs.getDate("diaSolicitado").toString();
					fechaTurno=rs.getDate("diaDelTurno").toString();
					estado=rs.getString("estado");
					sector=rs.getString("sector");
					turnoLeido=new Turno(id, idPaciente , fechaSolicitado, fechaTurno, estado, sector, activo);
					this.cargarUnTurno(turnoLeido);
					if(sector.compareTo("Emergencia")==0) {
						this.getListaTurnosEmergencia().add(turnoLeido);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	public void cargarUnPaciente(Paciente elemento) {this.getListaPacientes().add(elemento);}
	public void cargarUnaFicha(FichaMedica elemento) {this.getListaFichasMedicas().add(elemento);}
	public void cargarUnTurno(Turno elemento) {this.getListaTurnos().add(elemento);}
	public void salir() {
		System.out.println("Escribiendo Fichas");
		volcarFichas();
		System.out.println("Escribiendo Pacientes");
		volcarPacientes();
		System.out.println("Escribiendo Turnos");
		volcarTurnos();
		
	}
	private void volcarPacientes(){
		int total;
		total=this.getListaPacientes().size();
		if(total>0) {
			try {
				Conexion conn;
				Statement stmt;
				ResultSet rs;
				String sql;
				
				Paciente pacienteLeido=null;		
				int id;
				
				conn = new Conexion(BDD,USER,PASS);
				conn.conectar();
				for(int i=0;i<total;i++) {
					pacienteLeido=this.getListaPacientes().get(i);
					id=pacienteLeido.getId();
					
					stmt=conn.getConnection().createStatement();
					sql="select usuario from  paciente where id="+id+";";
					rs=stmt.executeQuery(sql);
					
					if(!rs.next()) {
						escribirPaciente(pacienteLeido);
					}
				}
			}
			catch(Exception e) {
				System.err.print("Error de ejecucion: volcar paciente");
				e.printStackTrace();
			}
		}
	}
	private void volcarFichas(){
		int total;
		total=this.getListaFichasMedicas().size();
		if(total>0) {
			try {
				Conexion conn;Statement stmt;
				ResultSet rs;
				String sql;
				FichaMedica fichaLeida;		
				int id;
				
				conn = new Conexion(BDD,USER,PASS);
				conn.conectar();
				stmt=conn.getConnection().createStatement();
				for(int i=0;i<total;i++) {
					fichaLeida=this.getListaFichasMedicas().get(i);
					id=fichaLeida.getId();
					sql="select * from  fichaMedica where id="+id+";";
					rs=stmt.executeQuery(sql);
					if(!rs.next()) {
						escribirFichaMedica(fichaLeida);
					}
				}
			}
			catch(Exception e) {
				System.err.print("Error de ejecucion: volcar ficha");
				e.printStackTrace();
			}
			
		}
	}
	private void volcarTurnos(){
		int total;
		total=this.getListaTurnos().size();
		if(total>0) {
			try {
				Conexion conn;
				Statement stmt;
				ResultSet rs;
				String sql;
				LocalDate fechaActual,fechaTurno;
				Turno turnoLeido=null;		
				int id;
				
				conn = new Conexion(BDD,USER,PASS);
				conn.conectar();
				stmt=conn.getConnection().createStatement();
				fechaActual=LocalDate.now();
				for(int i=0;i<total;i++) {
					turnoLeido=this.getListaTurnos().get(i);
					id=turnoLeido.getId();
					
					sql="select * from  turno where id="+id+";";
					rs=stmt.executeQuery(sql);
					if(!rs.next()) {
						fechaTurno=LocalDate.parse(turnoLeido.getDiaDelTurno());
						if(fechaActual.compareTo(fechaTurno)>0) {
							atenderTurno(i);
//							Supuestamente atiende como para no considerar aun los ausentes
//							Se podria refinar controlando el horario actuarl si es que la 
//							fecha es la misma. como para determinar si es o no ausencia
						}
						escribirTurno(turnoLeido);
					}
				}
			}
			catch(Exception e) {
				System.err.print("Error de ejecucion: volcar turno");
				e.printStackTrace();
			}
			
		}
	}
	private void atenderTurno(int i) {
		this.getListaTurnos().get(i).setActivo(false);
		this.getListaTurnos().get(i).setEstado("Atendido");
	}
	private void escribirPaciente(Paciente pacienteLeido) {
		try {
			Conexion conn;
			Statement stmt;
			String sql,usuario,contrasenia,sector;
			int id,idFicha;
			boolean activo;
			LocalDate fechaCreacion;
			
			id=pacienteLeido.getId();
			idFicha=pacienteLeido.getIdFicha();
			sector=pacienteLeido.getSector();
			usuario=pacienteLeido.getUsuario();
			contrasenia=pacienteLeido.getContrasenia();
			fechaCreacion=LocalDate.parse(pacienteLeido.getFechaCreacion());
			activo=pacienteLeido.isActivo();
			
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			stmt=conn.getConnection().createStatement();
			sql="insert into paciente values("+id+","+idFicha+",\'"+sector+"\',\'"+usuario+"\',\'"+contrasenia+"\',\'"+fechaCreacion+"\',"+activo+");";
			stmt.executeUpdate(sql);
		}
		catch(Exception e) {
			System.err.print("Error en escribirPaciente");
			e.printStackTrace();
		}
	}
	private void escribirFichaMedica(FichaMedica fichaLeida) {
		try {
			Conexion conn;
			Statement stmt;
			String sql,nombre,apellido,alergias,tratamientos,fechaNacimiento;
			int id,edad;
			long DNI;
			double peso,talla;
			boolean activo;
			 
			
			id=fichaLeida.getId();
			DNI=fichaLeida.getDNI();
			nombre=fichaLeida.getNombre();
			apellido=fichaLeida.getApellido();
			fechaNacimiento=fichaLeida.getFechaNacimiento();
			edad=fichaLeida.getEdad();
			peso=fichaLeida.getPeso();
			talla=fichaLeida.getTalla();
			alergias=fichaLeida.getAlergias();
			tratamientos=fichaLeida.getTratamientos();
			activo=fichaLeida.isActivo();
			
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			stmt=conn.getConnection().createStatement();
			sql="insert into fichaMedica values("+id+","+DNI+",\'"+fechaNacimiento+"\',\'"+nombre+"\',\'"+apellido+"\',"+edad+","+peso+","+talla+",\'"+alergias+"\',\'"+tratamientos+"\',"+activo+");";
			stmt.executeUpdate(sql);
		}
		catch(Exception e) {
			System.err.print("Error en escribirPaciente");
			e.printStackTrace();
		}
	}
	private void escribirTurno(Turno turnoLeido) {
		try {
			Conexion conn;
			Statement stmt;
			String sql,estado,sector;
			int id,idPaciente;
			boolean activo;
			LocalDate fechaSolicitado,fechaTurno;
			
			id=turnoLeido.getId();
			idPaciente=turnoLeido.getIdPaciente();
			fechaSolicitado=LocalDate.parse(turnoLeido.getDiaSolicitado());
			fechaTurno=LocalDate.parse(turnoLeido.getDiaDelTurno());
			estado=turnoLeido.getEstado();
			sector=turnoLeido.getSector();
			activo=turnoLeido.isActivo();
			
			conn = new Conexion(BDD,USER,PASS);
			conn.conectar();
			stmt=conn.getConnection().createStatement();
			sql="insert into turno values("+id+","+idPaciente+",\'"+fechaSolicitado+"\',\'"+fechaTurno+"\',\'"+estado+"\',\'"+sector+"\',"+activo+");";
			stmt.executeUpdate(sql);
		}
		catch(Exception e) {
			System.err.print("Error en escribirPaciente");
			e.printStackTrace();
		}
	}
	
//	Gets
	public ArrayList<Paciente> getListaPacientes() {return listaPacientes;}
	public ArrayList<Turno> getListaTurnos() {return listaTurnos;}
	private ArrayList<Turno> getListaTurnosEmergencia() {return this.ListaTurnosEmergencia;}
	public ArrayList<FichaMedica> getListaFichasMedicas() {return listaFichasMedicas;}

//	Sets
	public void setListaPacientes(ArrayList<Paciente> listaPacientes) {this.listaPacientes = listaPacientes;}
	public void setListaTurnos(ArrayList<Turno> listaTurnos) {this.listaTurnos = listaTurnos;}
	public void setListaFichasMedicas(ArrayList<FichaMedica> listaFichasMedicas) {this.listaFichasMedicas = listaFichasMedicas;}
	public void sestlistaEmergencia(ArrayList<Turno> listaTurnos) {this.ListaTurnosEmergencia=listaTurnos;}





}
