package entidades;

import java.time.LocalDateTime;

public class Turno {
	private int id;
	private String fechaHora;
	private String estado;
	
	public Turno(int vId,String vFechaHora,String vEstado) {
		setId(vId);
		setFechaHora(vFechaHora);
		setEstado(vEstado);
	}
	//Setters
	public void setEstado(String vEstado) {this.estado=vEstado;}
	public void setFechaHora(String vFechaHora) {this.fechaHora=vFechaHora;}
	public void setId(int vId) {this.id=vId;}
	//Getters
	public String getEstado() {return this.estado;}
	public String getFechaHora() {return this.fechaHora;}
	public int getId() {return this.id;}
	
	public String toString() {
		String cad="\nid\tfecha\testado\n";
		cad+=this.getId()+"\t";
		cad+=this.getFechaHora()+"\t";
		cad+=this.getEstado()+"\n";		
		return cad;
	}
	
}
