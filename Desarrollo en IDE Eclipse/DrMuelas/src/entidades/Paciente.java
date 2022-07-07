package entidades;

import java.time.*;

public class Paciente {

	private int id;
	private String usuario,contrasenia;
	private LocalDate fechaRegistro;
	private boolean activo;
	//Constructores
	public Paciente(int vId,String vUsuario,String vContrasenia,LocalDate vFechaRegistro,boolean vActivo) {
		setId(vId);
		setUsuario(vUsuario);
		this.contrasenia=vContrasenia;
		setFechaRegistro(vFechaRegistro);
		setActivo(vActivo);
	}
	public Paciente(int vId,String vUsuario) {
		this(vId,vUsuario,"",null,true);
	}
	//Setters
	public void setActivo(boolean vActivo) {this.activo=vActivo;}
	private void setFechaRegistro(LocalDate vFechaRegistro) {this.fechaRegistro=vFechaRegistro;}
	public void setUsuario(String vUsuario) {this.usuario=vUsuario;}
	public void setId(int vId) {this.id=vId;}
	
	//Getters
	public boolean getActivo() {return this.activo;}
	public LocalDate getFechaRegistro() {return this.fechaRegistro;}
	public String getUsuario() {return this.usuario;}
	public int getId() {return this.id;}
	
	public String toString() {
		String cadena="";
		
		cadena+="ID\tUsuario\tFechaRegistro\tActivo\n";
		cadena+=this.getId();
		cadena+='\t'+this.getUsuario();
		cadena+='\t'+this.getFechaRegistro().toString();
		cadena+='\t'+String.valueOf(this.getActivo());
		
		return cadena;
		
	}
}
