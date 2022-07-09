package entidades;

import java.time.LocalDate;

public class FichaMedica {
	private int id,edad;
	private long DNI;
	private String nombre,apellido,alergias,tratamientos,fechaNacimiento;
	private double peso,talla;
	private boolean activo;
	
	public FichaMedica(int vId,long vDNI,String vFechaNacimiento,String vNombre,String vApellido,int vEdad,double vPeso,double vTalla,String vAlergias,String vTratamientos,boolean vActivo) {
//	public FichaMedica(int vId,long vDNI,String vNombre,String vApellido,int vEdad,double vPeso,double vTalla,String vAlergias,String vTratamientos) {
		setId(vId);
		setDNI(vDNI);
		setFechaNacimiento(vFechaNacimiento);
		setNombre(vNombre);
		setApellido(vApellido);
		setEdad(vEdad);
		setPeso(vPeso);
		setTalla(vTalla);
		setAlergias(vAlergias);
		setTratamientos(vTratamientos);
		setActivo(vActivo);
	}
	public FichaMedica(int vId,long vDNI) {this(vId,vDNI,LocalDate.now().toString(),"","",0,0.0,0.0,"","",true);}	
	//Setters
	public void setId(int id) {this.id = id;}
	public void setDNI(long vDNI) {this.DNI = vDNI;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public void setApellido(String apellido) {this.apellido = apellido;}
	public void setEdad(int edad) {this.edad = edad;}
	public void setFechaNacimiento(String fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}
	public void setPeso(double peso) {this.peso = peso;}
	public void setTalla(double talla) {this.talla = talla;}
	public void setAlergias(String alergias) {this.alergias = alergias;}
	public void setTratamientos(String tratamientos) {this.tratamientos = tratamientos;}
	public void setActivo(boolean vActivo) {this.activo=vActivo;}
	//Getters
	public int getId() {return this.id;}
	public long 	getDNI()			{return this.DNI;}	
	public String 	getNombre()			{return nombre;}
	public int 		getEdad()			{return edad;}
	public String 	getApellido() 		{return apellido;}
	public String 	getAlergias() 		{return alergias;}
	public String 	getTratamientos() 	{return tratamientos;}
	public String 	getFechaNacimiento(){return fechaNacimiento;}
	public double 	getPeso()			{return peso;}
	public double 	getTalla()			{return talla;}
	public boolean 	isActivo()			{return this.activo;}
	
	public String toString() {
		String cadena="";

		cadena+="ID\tDNI\tfechaNacimiento\t\tnombre\tapellido\tedad\tpeso\ttalla\tfechaNacimiento\talergias\ttratamientos\n";
		cadena+=this.getId();
		cadena+=" \t "+this.getDNI();
		cadena+=" \t "+this.getFechaNacimiento();
		cadena+=" \t\t "+this.getNombre();
		cadena+=" \t "+this.getApellido();
		cadena+=" \t "+this.getEdad();
		cadena+=" \t "+this.getPeso();
		cadena+=" \t "+this.getTalla();
		cadena+=" \t "+this.getFechaNacimiento();
		cadena+=" \t "+this.getAlergias();
		cadena+=" \t "+this.getTratamientos();
		
		return cadena;
		
	}
}
