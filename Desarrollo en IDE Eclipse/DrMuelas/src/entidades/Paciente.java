package entidades;;

public class Paciente {

	private int id,idFicha;
	private String usuario,contrasenia,sector,fechaCreacion;
	private boolean activo;
	
	//Constructores
	public Paciente(int vId,int vIdFicha,String vSector,String vUsuario,String vContrasenia,String vFechaCreacion,boolean vActivo) {
		setId(vId);
		setIdFicha(vIdFicha);
		setSector(vSector);
		setUsuario(vUsuario);
		this.contrasenia=vContrasenia;
		setFechacreacion(vFechaCreacion);
		setActivo(vActivo);
	}
	public Paciente(int vId,int idFicha,String vSector,String vUsuario,String fecha) {
		this(vId,idFicha,vSector,vUsuario,vUsuario,fecha.toString(),true);
	}
	
	//Gets y Sets
	public void setId(int vId) {this.id=vId;}
	public void setIdFicha(int idFicha) {this.idFicha = idFicha;}
	public void setSector(String sector) {this.sector = sector;}
	public void setUsuario(String vUsuario) {this.usuario=vUsuario;}
	public void setFechacreacion(String vFechaCreacion) {this.fechaCreacion=vFechaCreacion;}
	public void setActivo(boolean vActivo) {this.activo=vActivo;}
	
	public int getId() {return this.id;}
	public int getIdFicha() {return idFicha;}
	public String getSector() {return sector;}
	public String getUsuario() {return this.usuario;}
	public String getContrasenia() {return this.contrasenia;}
	public String getFechaCreacion() {return this.fechaCreacion;}
	public boolean isActivo() {return this.activo;}
	
	public String toString() {
		String cadena="";
		
		cadena+="\nID\tIdficha\tUsuario\tFechaCreacion\tActivo\n";
		cadena+=this.getId();
		cadena+="\t"+this.getIdFicha();
		cadena+="\t"+this.getUsuario();
		cadena+="\t"+this.getFechaCreacion().toString();
		cadena+="\t"+String.valueOf(this.isActivo());
		
		return cadena;
		
	}
	
	
	
	
}
