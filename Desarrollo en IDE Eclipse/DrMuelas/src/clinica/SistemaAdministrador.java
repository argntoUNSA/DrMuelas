package clinica;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import entidades.*;
import utilidades.Conexion;;

public class SistemaAdministrador {
private ArrayList<Paciente> listaPacientes;
private ArrayList<Turno> listaTurnos,ListaTurnosEmergencia;
private ArrayList<FichaMedica> listaFichasMedicas; 

public SistemaAdministrador() {
	setListaPacientes(new ArrayList<Paciente>());
	setListaFichasMedicas(new ArrayList<FichaMedica>());
	setListaTurnos(new ArrayList<Turno>());
	sestlistaEmergencia(new ArrayList<Turno>());
	leerDatos();
}

//Metodos requeridos
public void cargarPacienteEmergencia(long DNI) {
	Paciente nuevoPaciente,ultimoPaciente;
	FichaMedica nuevaFicha,ultimaFicha;
	Turno nuevoTurno,ultimoTurno;
	int idPaciente,idFicha,idTurno,indice;
	String usuario,fecha;
	
	indice=this.getListaPacientes().size()-1;
	ultimoPaciente=this.getListaPacientes().get(indice);
	idPaciente=ultimoPaciente.getId()+1;
	
	usuario=String.valueOf(DNI);
	
	indice=this.getListaFichasMedicas().size()-1;
	ultimaFicha=this.getListaFichasMedicas().get(indice);
	idFicha=ultimaFicha.getId()+1;
	nuevaFicha=new FichaMedica(idFicha,DNI);
	this.getListaFichasMedicas().add(nuevaFicha);
	
	nuevoPaciente=new Paciente(idPaciente,idFicha,usuario);
	this.getListaPacientes().add(nuevoPaciente);
	
	indice=this.getListaTurnosEmergencia().size()-1;
	ultimoTurno=this.getListaTurnosEmergencia().get(indice);
	idTurno=ultimoTurno.getId()+1;
	idPaciente=nuevoPaciente.getId();
	fecha=LocalDate.now().toString();
	
	nuevoTurno=new Turno(idTurno,idPaciente,fecha,fecha,"Reservado","Emergencia",true);
	this.getListaTurnosEmergencia().add(nuevoTurno);
	
	indice=this.getListaTurnos().size()-1;
	ultimoTurno=this.getListaTurnos().get(indice);
	idTurno=ultimoTurno.getId()+1;
	nuevoTurno=new Turno(idTurno,idPaciente,fecha,fecha,"Reservado","Emergencia",true);
	this.getListaTurnos().add(nuevoTurno);
	
}
public void informes(int mesInforme) {
	//Definimos
	Turno turnoLeido;
	int cantidadSector1,cantidadSector2,cantidadSector3,cantidadTotalTurnos,mesTurno;
	LocalDate fecha;

	//Calculamos
	cantidadTotalTurnos=this.getListaPacientes().size();
	cantidadSector1=cantidadSector2=cantidadSector3=0;
	for(int i=0;i<cantidadTotalTurnos;i++) {
		turnoLeido=this.getListaTurnos().get(i);
		fecha=LocalDate.parse(turnoLeido.getDiaYHoraDelTurno());
		mesTurno=fecha.getMonthValue();
		if(mesInforme==mesTurno && !turnoLeido.isActivo()) {
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
public void ListarFichasMedicas() {
	int cantidad;
	FichaMedica ficha;
	cantidad=this.getListaFichasMedicas().size();
	for(int i=0;i<cantidad;i++) {
		ficha=this.getListaFichasMedicas().get(i);
		System.out.println("["+ficha+"]");
	}
}

//Metodos adicionales
//Metodos Adicionales
public void leerDatos() {
	cargarPacientes();
	cargarFichas();
	cargarTurnos();
}
public void cargarPacientes() {
	try {
		Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
		System.out.println(conn.conectar());
		String sql,usuario;
		Statement stmt;
		ResultSet rs;
		Paciente pacienteLeido=null;		
		int id,idFicha;
		
		sql="select id,usuario from paciente;";
		stmt=conn.getConnection().createStatement();
		rs=stmt.executeQuery(sql);
		
		while(rs.next()) {
			id=rs.getInt("id");
			idFicha=rs.getInt("idFicha");
			usuario=rs.getString("usuario");
			pacienteLeido=new Paciente(id,idFicha,usuario);
			cargarUnPaciente(pacienteLeido);
		}
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
public void cargarFichas() {
	try {
		Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
		System.out.println(conn.conectar());
		String sql,fechaNacimiento,nombre,apellido,alergias,tratamientos;
		Statement stmt;
		ResultSet rs;
		int id,edad;
		long DNI;
		double peso,talla;
		FichaMedica fichaLeido;
		boolean activo;
		
		sql="select * from fichaMedica;";
		stmt=conn.getConnection().createStatement();
		rs=stmt.executeQuery(sql);
		while(rs.next()) {
			id=rs.getInt			("id");
			DNI=rs.getLong			("DNI");
			fechaNacimiento=rs.getTime("fechaNacimiento").toString();
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
		e.printStackTrace();
	}
}
public void cargarTurnos() {
	try {
		Conexion conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
		System.out.println(conn.conectar());
		String sql,fechaSolicitado,fechaTurno,estado,sector;
		Statement stmt;
		ResultSet rs;
		Turno turnoLeido;		
		int id,idPaciente;
		boolean activo;

		sql="select * from turno;";
		stmt=conn.getConnection().createStatement();
		rs=stmt.executeQuery(sql);
		System.out.println(conn.conectar());
		while(rs.next()){
			id=(rs.getInt("id"));
			idPaciente=(rs.getInt("idPaciente"));
			fechaSolicitado=rs.getDate("diayHoraSolicitado").toString();
			fechaTurno=rs.getDate("diaYHoraDelTurno").toString();
			estado=rs.getString("estado");
			sector=rs.getString("sector");
			activo=rs.getBoolean("activo");
			turnoLeido=new Turno(id, idPaciente , fechaSolicitado, fechaTurno, estado, sector, activo);
			this.cargarUnTurno(turnoLeido);
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}

}
public void cargarUnPaciente(Paciente elemento) {this.getListaPacientes().add(elemento);}
public void cargarUnaFicha(FichaMedica elemento) {this.getListaFichasMedicas().add(elemento);}
public void cargarUnTurno(Turno elemento) {this.getListaTurnos().add(elemento);}
public void escribirDatos() {
	volcarPacientes();
	volcarFichas();
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
			
			conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(conn.conectar());	
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
			
			conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(conn.conectar());
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
			
			Turno turnoLeido=null;		
			int id;
			
			conn = new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(conn.conectar());
			stmt=conn.getConnection().createStatement();
			for(int i=0;i<total;i++) {
				turnoLeido=this.getListaTurnos().get(i);
				id=turnoLeido.getId();
				
				sql="select * from  paciente where id="+id+";";
				rs=stmt.executeQuery(sql);
				if(rs.next()) {
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
private void escribirPaciente(Paciente pacienteLeido) {
	try {
		Conexion conn;
		Statement stmt;
		String sql,usuario,contrasenia;
		int id,idFicha;
		boolean activo;
		LocalDateTime fecha;
		
		id=pacienteLeido.getId();
		idFicha=pacienteLeido.getIdFicha();
		usuario=pacienteLeido.getUsuario();
		contrasenia=pacienteLeido.getContrasenia();
		fecha=LocalDateTime.parse(pacienteLeido.getFechaCreacion());
		activo=pacienteLeido.isActivo();
		
		conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
		System.out.println(conn.conectar());
		stmt=conn.getConnection().createStatement();
		sql="insert into paciente values("+id+","+idFicha+","+usuario+","+contrasenia+","+fecha+","+activo+");";
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
		String sql,nombre,apellido,alergias,tratamientos;
		int id,edad;
		long DNI;
		double peso,talla;
		boolean activo;
		LocalDateTime fechaNacimiento;
		
		id=fichaLeida.getId();
		DNI=fichaLeida.getDNI();
		nombre=fichaLeida.getNombre();
		apellido=fichaLeida.getApellido();
		fechaNacimiento=LocalDateTime.parse(fichaLeida.getFechaNacimiento());
		edad=fichaLeida.getEdad();
		peso=fichaLeida.getPeso();
		talla=fichaLeida.getTalla();
		alergias=fichaLeida.getAlergias();
		tratamientos=fichaLeida.getTratamientos();
		activo=fichaLeida.isActivo();
		
		conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
		System.out.println(conn.conectar());
		stmt=conn.getConnection().createStatement();
		sql="insert into fichaaMedica values("+id+","+DNI+","+fechaNacimiento+","+nombre+","+apellido+","+edad+","+peso+","+talla+","+alergias+","+tratamientos+","+activo+");";
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
		LocalDateTime fechaSolicitado,fechaTurno;
		
		id=turnoLeido.getId();
		idPaciente=turnoLeido.getIdPaciente();
		fechaSolicitado=LocalDateTime.parse(turnoLeido.getDiayHoraSolicitado());
		fechaTurno=LocalDateTime.parse(turnoLeido.getDiaYHoraDelTurno());
		estado=turnoLeido.getEstado();
		sector=turnoLeido.getSector();
		activo=turnoLeido.isActivo();
		
		conn=new Conexion("dr_muelas","root","Ssap4toor0t0!");
		System.out.println(conn.conectar());
		stmt=conn.getConnection().createStatement();
		sql="insert into turno values("+id+","+idPaciente+","+fechaSolicitado+","+fechaTurno+","+estado+","+sector+","+activo+");";
		stmt.executeUpdate(sql);
	}
	catch(Exception e) {
		System.err.print("Error en escribirPaciente");
		e.printStackTrace();
	}
}
	
//Gets
public ArrayList<Paciente> getListaPacientes() {return listaPacientes;}
public ArrayList<Turno> getListaTurnos() {return listaTurnos;}
private ArrayList<Turno> getListaTurnosEmergencia() {return this.ListaTurnosEmergencia;}
public ArrayList<FichaMedica> getListaFichasMedicas() {return listaFichasMedicas;}

//Sets
public void setListaPacientes(ArrayList<Paciente> listaPacientes) {this.listaPacientes = listaPacientes;}
public void setListaTurnos(ArrayList<Turno> listaTurnos) {this.listaTurnos = listaTurnos;}
public void setListaFichasMedicas(ArrayList<FichaMedica> listaFichasMedicas) {this.listaFichasMedicas = listaFichasMedicas;}
public void sestlistaEmergencia(ArrayList<Turno> listaTurnos) {this.ListaTurnosEmergencia=listaTurnos;}





}
