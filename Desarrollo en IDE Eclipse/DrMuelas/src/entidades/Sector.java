package entidades;;

public class Sector {
	private int id;
	private String nombre,tipo;
	private int cantPacientesRegistrados;
	private boolean habilitado;
	
	public Sector(int vId,String vNombre,int vCantPacientesRegistrados,String vTipo,boolean vHabilitado) {
		setId(vId);
		setNombre(vNombre);
		setCantPacReg(vCantPacientesRegistrados);
		setTipo(vTipo);
		setHabilitado(vHabilitado);
	}
	//Setters
	private void setHabilitado(boolean vHabilitado) {this.habilitado=vHabilitado;}
	public void setTipo(String vTipo) {this.tipo=vTipo;}
	public void setNombre(String vNombre) {this.nombre=vNombre;}
	public void setCantPacReg(int vCantPacientesRegistrados) {this.cantPacientesRegistrados=vCantPacientesRegistrados;}
	public void setId(int vId) {this.id=vId;}
	
	//Getters
	public boolean getHabilitado() {return this.habilitado;}	
	public String getTipo() {return this.tipo;}
	public String getNombre() {return this.nombre;}
	public int getCantPacReg() {return this.cantPacientesRegistrados;}
	public int getId() {return this.id;}
	
}
