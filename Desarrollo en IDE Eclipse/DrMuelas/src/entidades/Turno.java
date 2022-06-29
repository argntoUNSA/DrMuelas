package entidades;

import java.util.Date;

public class Turno {
	private int id;
	private Date fechaHora;
	private String estado;
	
	public Turno(int vId,Date vFechaHora,String vEstado) {
		setId(vId);
		setFechaHora(vFechaHora);
		setEstado(vEstado);
	}
	//Setters
	public void setEstado(String vEstado) {this.estado=vEstado;}
	public void setFechaHora(Date vFechaHora) {this.fechaHora=vFechaHora;}
	public void setId(int vId) {this.id=vId;}
	//Getters
	public String getEstado() {return this.estado;}
	public Date getFechaHora() {return this.fechaHora;}
	public int getId() {return this.id;}
	
}
