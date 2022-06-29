package entidades;

import java.util.Date;

public class Paciente {

	private int id;
	private String usuario;
	private String contrase�a;
	private Date fechaRegistro;
	private boolean activo;
	//Constructores
	public Paciente(int vId,String vUsuario,String vContrase�a,Date vFechaRegistro,boolean vActivo) {
		setId(vId);
		setUsuario(vUsuario);
		setContrase�a(vContrase�a);
		setFechaRegistro(vFechaRegistro);
		setActivo(vActivo);
	}
	//Setters
	public void setActivo(boolean vActivo) {this.activo=vActivo;}
	private void setFechaRegistro(Date vFechaRegistro) {this.fechaRegistro=vFechaRegistro;}
	public void setContrase�a(String vContrase�a) {this.contrase�a=vContrase�a;}
	public void setUsuario(String vUsuario) {this.usuario=vUsuario;}
	public void setId(int vId) {this.id=vId;}
	//Getters
	public boolean getActivo() {return this.activo;}
	public Date getFechaRegistro() {return this.fechaRegistro;}
	public String getContrase�a() {return this.contrase�a;}
	public String getUsuario() {return this.usuario;}
	public int getId() {return this.id;}
	
	
	public String toSting() {
		String cadena="";
		
		cadena+="ID\tUsuario\tContrase�a\tFechaRegistro\tActivo";
		cadena+=this.getId();
		cadena+='\t'+this.getUsuario();
		cadena+='\t'+this.getContrase�a();
		cadena+='\t'+this.getFechaRegistro().toString();
		cadena+='\t'+String.valueOf(this.getActivo());
		
		return cadena;
		
	}
}
