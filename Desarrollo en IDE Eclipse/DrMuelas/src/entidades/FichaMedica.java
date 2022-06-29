package entidades;

import java.util.Date;

public class FichaMedica {
	private int id,edad;
	private long DNI;
	private String nombre,apellido,alergias,tratamientos;
	private Date fechaNacimiento;
	private double peso,talla;
	
	public FichaMedica(int vId,long vDNI,String vNombre,String vApellido,int vEdad,Date vFechaNacimiento,double vPeso,double vTalla,String vAlergias,String vTratamientos) {
		setId(vId);
		setDNI(vDNI);
		setNombre(vNombre);
		setApellido(vApellido);
		setEdad(vEdad);
		setFechaNacimiento(vFechaNacimiento);
		setPeso(vPeso);
		setTalla(vTalla);
		setAlergias(vAlergias);
		setTratamientos(vTratamientos);
	}
	//Setters
	public void setId(int id) {this.id = id;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public void setEdad(int edad) {this.edad = edad;}
	public void setDNI(long dNI) {DNI = dNI;}
	public void setApellido(String apellido) {this.apellido = apellido;}
	public void setAlergias(String alergias) {this.alergias = alergias;}
	public void setTratamientos(String tratamientos) {this.tratamientos = tratamientos;}
	public void setFechaNacimiento(Date fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}
	public void setPeso(double peso) {this.peso = peso;}
	public void setTalla(double talla) {this.talla = talla;}
	//Getters
	public int getId() {return this.id;}
	public String getNombre() {return nombre;}
	public int getEdad() {return edad;}
	public long getDNI() {return DNI;}	
	public String getApellido() {return apellido;}
	public String getAlergias() {return alergias;}
	public String getTratamientos() {return tratamientos;}
	public Date getFechaNacimiento() {return fechaNacimiento;}
	public double getPeso() {return peso;}
	public double getTalla() {return talla;}
	
}
