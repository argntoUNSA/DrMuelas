package entidades;

import java.util.Date;

public class Paciente {

	private int id;
	private String usuario;
	private Date fechaRegistro;
	private boolean activo;
	//Constructores
	public Paciente(int vId,String vUsuario,Date vFechaRegistro,boolean vActivo) {
		setId(vId);
		setUsuario(vUsuario);
		setFechaRegistro(vFechaRegistro);
		setActivo(vActivo);
	}
	
	//Setters
	public void setActivo(boolean vActivo) {this.activo=vActivo;}
	private void setFechaRegistro(Date vFechaRegistro) {this.fechaRegistro=vFechaRegistro;}
	public void setUsuario(String vUsuario) {this.usuario=vUsuario;}
	public void setId(int vId) {this.id=vId;}
	
	//Getters
	public boolean getActivo() {return this.activo;}
	public Date getFechaRegistro() {return this.fechaRegistro;}
	public String getUsuario() {return this.usuario;}
	public int getId() {return this.id;}
	
	//Metodos del Subsistema solicitado
	public void registrarse() {
		
	}
	public void iniciarSesion() {}
	public void pedirTurno() {}
	public void cancelarTurno() {}
	public void verFichaMedica() {}
	
	public String toSting() {
		String cadena="";
		
		cadena+="ID\tUsuario\tFechaRegistro\tActivo";
		cadena+=this.getId();
		cadena+='\t'+this.getUsuario();
		cadena+='\t'+this.getFechaRegistro().toString();
		cadena+='\t'+String.valueOf(this.getActivo());
		
		return cadena;
		
	}
}
