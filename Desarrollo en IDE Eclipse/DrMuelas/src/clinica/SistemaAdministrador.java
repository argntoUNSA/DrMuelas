package clinica;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import entidades.*;
import utilidades.Conexion;;

public class SistemaAdministrador {
private ArrayList<Paciente> listaPacientes;
private ArrayList<Turno> listaTurnos;
private ArrayList<FichaMedica> listaFichasMedicas; 

public SistemaAdministrador() {
	setListaPacientes(new ArrayList<Paciente>());
	setListaFichasMedicas(new ArrayList<FichaMedica>());
	setListaTurnos(new ArrayList<Turno>());
	cargarDatos();
}

//Metodos requeridos
public void cargarPacienteEmergencia(long DNI) {
	Paciente nuevoPaciente,auxiliar;
	int id,indice;
	String usuario;
	
	indice=this.getListaPacientes().size()-1;
	auxiliar=this.getListaPacientes().get(indice);
	id=auxiliar.getId()+1;
	usuario=String.valueOf(DNI);
	nuevoPaciente=new Paciente(id, usuario);
	
	this.getListaPacientes().add(nuevoPaciente);	
}
public void informes(int mesInforme) {
	//Definimos
	Turno turnoLeido;
	int cantidadSector1,cantidadSector2,cantSectorEmergencia,cantidadTotalTurnos,mesTurno;
	LocalDate fecha;

	//Calculamos
	cantidadTotalTurnos=this.getListaPacientes().size();
	for(int i=0;i<cantidadTotalTurnos;i++) {
		turnoLeido=this.getListaTurnos().get(i);
		fecha=LocalDate.parse(turnoLeido.getFechaHora());
		mesTurno=fecha.getMonthValue();
		if(mesInforme==mesTurno) {
			if(turnoLeido.get)
		}
	}
	

	
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

//Metodos Adicionales
public void cargarDatos() {
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
		int id;
		
		sql="select id,usuario from paciente;";
		stmt=conn.getConnection().createStatement();
		rs=stmt.executeQuery(sql);
		
		while(rs.next()) {
			id=rs.getInt("id");
			usuario=rs.getString("usuario");
			pacienteLeido=new Paciente(id,usuario);
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
		String sql,nombre,apellido,fechaCreacion,alergias,tratamientos;
		Statement stmt;
		ResultSet rs;
		int id,edad;
		long DNI;
		double peso,talla;
		FichaMedica fichaLeido;
		
		sql="select * from fichaMedica;";
		stmt=conn.getConnection().createStatement();
		rs=stmt.executeQuery(sql);
		while(rs.next()) {
			id=rs.getInt			("id");
			DNI=rs.getLong			("DNI");
			nombre=rs.getString		("nombre");
			apellido=rs.getString	("apellido");
			edad=rs.getInt			("edad");
			peso=rs.getDouble		("peso");
			talla=rs.getDouble		("talla");
//			fechaCreacion=rs.getDate	("fechaRegistro").toGMTString();
			alergias=(rs.getString	("alergias"));
			tratamientos=rs.getString("tratamientos");
			
			fichaLeido=new FichaMedica(id, DNI, nombre, apellido, edad, peso, talla, alergias, tratamientos);
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
		String sql,fecha,estado;
		Statement stmt;
		ResultSet rs;
		Turno turnoLeido;		
		int id;

		sql="select * from turno;";
		stmt=conn.getConnection().createStatement();
		rs=stmt.executeQuery(sql);
		System.out.println(conn.conectar());
		while(rs.next()){
			id=(rs.getInt("idTurno"));
			fecha=rs.getDate("diaYHora").toString();
			estado=rs.getString("estado");
			turnoLeido=new Turno(id, fecha, estado);
			
			
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}

}

public void cargarUnPaciente(Paciente elemento) {
	this.getListaPacientes().add(elemento);
}
public void cargarUnaFicha(FichaMedica elemento) {
	this.getListaFichasMedicas().add(elemento);
}

public void cargarUnTurno(Turno elemento) {
	this.getListaTurnos().add(elemento);
}

public ArrayList<Paciente> getListaPacientes() {
	return listaPacientes;
}

public void setListaPacientes(ArrayList<Paciente> listaPacientes) {
	this.listaPacientes = listaPacientes;
}

public ArrayList<Turno> getListaTurnos() {
	return listaTurnos;
}

public void setListaTurnos(ArrayList<Turno> listaTurnos) {
	this.listaTurnos = listaTurnos;
}

public ArrayList<FichaMedica> getListaFichasMedicas() {
	return listaFichasMedicas;
}

public void setListaFichasMedicas(ArrayList<FichaMedica> listaFichasMedicas) {
	this.listaFichasMedicas = listaFichasMedicas;
}







}
