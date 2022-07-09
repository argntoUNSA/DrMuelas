package entidades;

public class Turno {
	private int id,idPaciente;
	private String diaSolicitado,diaDelTurno,estado,sector;
	private boolean activo;
	
	
	public Turno(int vId,int vIdPaciente,String vDiaSolicitado,String vDiaTurno,String vEstado,String vSector,boolean vActivo) {
		setId(vId);
		setIdPaciente(vIdPaciente);
		setDiaSolicitado(vDiaSolicitado);
		setDiaDelTurno(vDiaTurno);
		setEstado(vEstado);
		setSector(vSector);
		setActivo(vActivo);
	}
	

	//Setters
	public void setId(int vId) {this.id=vId;}
	public void setIdPaciente(int vIdPaciente) {this.idPaciente=vIdPaciente;}
	public void setEstado(String vEstado) {this.estado=vEstado;}
	public void setSector(String vSector) {this.sector=vSector;}
	public void setDiaSolicitado(String diayHoraSolicitado) {this.diaSolicitado = diayHoraSolicitado;}
	public void setDiaDelTurno(String diaYHoraDelTurno) {this.diaDelTurno = diaYHoraDelTurno;}
	public void setActivo(boolean activo) {this.activo = activo;}

	
	//Getters
	public int getId() {return this.id;}
	public int getIdPaciente() {return idPaciente;}
	public String getDiaSolicitado() {return diaSolicitado;}
	public String getDiaDelTurno() {return diaDelTurno;}
	public String getEstado() {return this.estado;}
	public String getSector() {return this.sector;}
	public boolean isActivo() {return activo;}

	public String toString() {
		String cad="\nNro\tIdPaciente\tFechaSolicitado\tFechaTurno\tEstado\tSector\tActivo\t\n";
		cad+=this.getId()+"\t";
		cad+=this.getIdPaciente()+"\t";
		cad+=this.getDiaSolicitado()+"\t";
		cad+=this.getDiaDelTurno()+"\t";
		cad+=this.getEstado()+"\t";
		cad+=this.getSector()+"\t";
		cad+=this.isActivo()+"\t\n";
		return cad;
	}





	
	
}
