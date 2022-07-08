package entidades;

public class Turno {
	private int id,idPaciente;
	private String diayHoraSolicitado,diaYHoraDelTurno,estado,sector;
	private boolean activo;
	
	
	public Turno(int vId,int vIdPaciente,String vDiaHoraSolicitado,String vDiaHoraTurno,String vEstado,String vSector,boolean vActivo) {
		setId(vId);
		setIdPaciente(vIdPaciente);
		setDiayHoraSolicitado(vDiaHoraSolicitado);
		setDiaYHoraDelTurno(vDiaHoraTurno);
		setEstado(vEstado);
		setSector(vSector);
		setActivo(vActivo);
	}
	

	//Setters
	public void setId(int vId) {this.id=vId;}
	public void setIdPaciente(int vIdPaciente) {this.idPaciente=vIdPaciente;}
	public void setEstado(String vEstado) {this.estado=vEstado;}
	public void setSector(String vSector) {this.sector=vSector;}
	public void setDiayHoraSolicitado(String diayHoraSolicitado) {this.diayHoraSolicitado = diayHoraSolicitado;}
	public void setDiaYHoraDelTurno(String diaYHoraDelTurno) {this.diaYHoraDelTurno = diaYHoraDelTurno;}
	public void setActivo(boolean activo) {this.activo = activo;}

	
	//Getters
	public int getId() {return this.id;}
	public int getIdPaciente() {return idPaciente;}
	public String getDiayHoraSolicitado() {return diayHoraSolicitado;}
	public String getDiaYHoraDelTurno() {return diaYHoraDelTurno;}
	public String getEstado() {return this.estado;}
	public String getSector() {return this.sector;}
	public boolean isActivo() {return activo;}

	public String toString() {
		String cad="\nid\tidPaciente\tfechaSolicitado\tFechaTurno\testado\tsector\tActivo\t\n";
		cad+=this.getId()+"\t";
		cad+=this.getIdPaciente()+"\t";
		cad+=this.getDiayHoraSolicitado()+"\t";
		cad+=this.getDiaYHoraDelTurno()+"\t";
		cad+=this.getEstado()+"\t";
		cad+=this.getSector()+"\t";
		cad+=this.isActivo()+"\t\n";
		return cad;
	}





	
	
}
