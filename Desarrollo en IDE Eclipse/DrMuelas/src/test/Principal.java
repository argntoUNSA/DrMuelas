package test;
import clinica.*;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		int opcion1,opcion2,mesInforme,turno;
		SistemaAdministrador miSistemaAdministrador;
		SistemaPaciente miSistemaPaciente;
		Scanner scan=new Scanner(System.in);
		String usuario,contrasenia;
		long DNI;
		
		
		
		do {
			opcion1=menuPrincipal();
			if(opcion1==1) {
				if (validar() ){
					System.out.println("Iniciando Sistema Administrador");
					miSistemaAdministrador=new SistemaAdministrador();
					System.out.println("Sistema Administrador Iniciado");
					do {
						opcion2=menuAdministrador();
						switch(opcion2) {
						case 1:{
							System.out.print("Ingrese DNI: ");
							DNI=scan.nextLong();
							miSistemaAdministrador.cargarPacienteEmergencia(DNI);
							
							break;
						}
						case 2:{
							miSistemaAdministrador.ListarFichasMedicas();
							break;
						}
						case 3:{
							System.out.print("Ingrese Mes: ");
							mesInforme=menuMes();
							miSistemaAdministrador.informes(mesInforme);
							break;
						}
						case 0:{
							System.out.println("Cerrando sistema Administrador");
							miSistemaAdministrador.escribirDatos();
							System.out.println("Sistema Administrador cerrado");
							
							break;
						}
						
						}
					}
					while(opcion2!=0);
				}
			}
			else if(opcion1==2){
				
				System.out.println("Iniciando Sistema Paciente");
				miSistemaPaciente=new SistemaPaciente();
				System.out.println("Sistema Paciente Iniciado");
				
				opcion2=menuPaciente();
				switch(opcion2) {
				case 1:{
					System.out.print("Ingrese nuevo usuario: ");
					usuario=scan.next();
					
					System.out.print("Ingrese nueva contrasenia: ");
					contrasenia=scan.next();
					
					miSistemaPaciente.registrarse(usuario, contrasenia);
					
					break;
				}
				case 2:{
					System.out.print("Ingrese nuevo usuario: ");
					usuario=scan.next();
					
					System.out.print("Ingrese nueva contrasenia: ");
					contrasenia=scan.next();
					
					miSistemaPaciente.iniciarSesion(usuario, contrasenia);
					break;
				}
				case 3:{
					System.out.print("Ingrese el numero del turno: ");
					miSistemaPaciente.mostrarTurnosDisponibles();
					turno=scan.nextInt();
					miSistemaPaciente.solicitarTurno(turno);
					break;
				}
				case 4:{
					System.out.print("Ingrese el numero del turno: ");
					miSistemaPaciente.mostrarTurnosReservados();
					turno=scan.nextInt();
					miSistemaPaciente.cancelarTurno(turno);
					break;
				}
				case 0:{
					System.out.println("Cerrando sistema Paciente");
					miSistemaPaciente.escribirDatos();
					System.out.println("Sistema Paciente cerrado ");
					break;
				}
				
				}
			}
			else {
				System.out.println("Sistema finalizado");
				
			}
		}
		while(opcion1!=0);
	}
public static int menuPrincipal() {
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n1- Menu Administrador";
	cad+="\n2- Menu Paciente";
	cad+="\n0- Salir";
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("Ingrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>2);
	return opcion;
}

public static int menuPaciente(){
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n1- Registrarse";
	cad+="\n2- Iniciar sesion";
	cad+="\n3- Solicitar turno";
	cad+="\n4- Cancelar turno";
	cad+="\n0- Salir";
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("Ingrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>4);
 
    return opcion;
  }

public static int menuAdministrador(){
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n1- Cargar paciente sector emergencia";
	cad+="\n2- Listar fichas medicas";
	cad+="\n3- Mostrar informes";
	cad+="\n0- Salir";
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("Ingrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>4);
 
    return opcion;
  }

public static boolean validar() {
	return true;
}
public static int menuMes() {
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n1-	Enero";
	cad+="\n2-	Febrero";
	cad+="\n3-	Marzo";
	cad+="\n4-	Abril";
	cad+="\n5-	Mayo";
	cad+="\n6-	Junio";
	cad+="\n7-	Julio";
	cad+="\n8-	Agosto";
	cad+="\n9-	Septiembre";
	cad+="\n10-	Octubre";
	cad+="\n11- Noviembre";
	cad+="\n12- Diciembre";
	cad+="\n0- Salir";
	
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("Ingrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>12);
 
    return opcion;
}
}
