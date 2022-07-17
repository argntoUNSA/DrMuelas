package test;
import clinica.*;

import java.time.LocalDate;
import java.util.Scanner;

import javax.sql.rowset.spi.TransactionalWriter;

public class Principal {

	public static void main(String[] args) {
		int opcion1,opcion2,opcion3,mesInforme,turno;
		SistemaAdministrador miSistemaAdministrador;
		SistemaPaciente miSistemaPaciente;
		Scanner scan=new Scanner(System.in);
		String usuario,contrasenia;
		long DNI;
		
		
		
		do {
			opcion1=menuPrincipal();
			if(opcion1==1) {
				if (validar() ){
					System.out.println("\nIniciando Sistema Administrador");
					miSistemaAdministrador=new SistemaAdministrador();
					System.out.println("Sistema Administrador Iniciado");
					do {
						opcion2=menuAdministrador();
						switch(opcion2) {
						case 1:{
							System.out.println("\n\tInicia proceso de registro");
							String nombre,apellido,alergias,descripcion,fecha;
							Double peso,talla;
							System.out.print("\tIngrese sus datos: ");
							System.out.print("\tDNI: ");
							DNI=scan.nextLong();
							System.out.print("\tNombre: ");
							nombre=scan.next();
							System.out.print("\tApellido: ");
							apellido=scan.next();
							System.out.print("\tFecha Nacimiento (aaaa-mm-dd): ");
							fecha=scan.next();
							System.out.print("\tPeso: ");
							peso=scan.nextDouble();
							System.out.print("\tTalla: ");
							talla=scan.nextDouble();
							System.out.print("\tAlergias: ");
							alergias=scan.next();
							System.out.print("\tTratamientos: ");
							descripcion=scan.next();
							System.out.println("\tIngreso de datos finalizado");
							miSistemaAdministrador.cargarPacienteEmergencia(DNI,nombre,apellido,fecha,peso,talla,alergias,descripcion);
							System.out.println("\tFin de proceso de registro");
							
							break;
						}
						case 2:{
							System.out.println("\n\tInicia muestra de Fichas Medicas");
							miSistemaAdministrador.ListarFichasMedicas();
							System.out.println("\tFinaliza muestra de Fichas Medicas");
							
							break;
						}
						case 3:{
							System.out.println("\n\tInicia muestra de Informe");
							mesInforme=menuMes();
							if(mesInforme!=0) {
								miSistemaAdministrador.informes(mesInforme);
								System.out.println("\tFinaliza muestra de Informes");
							}
							else{
								System.out.println("Operacion cancelada");
							}
						
							break;
						}
						case 0:{
							System.out.println("\n\tCerrando sistema Administrador");
							miSistemaAdministrador.salir();
							System.out.println("\tSistema Administrador cerrado");
							
							break;
						}
						
						}
					}
					while(opcion2!=0);
				}
			}
			else if(opcion1==2){
				
				System.out.println("\nInician Sistema Paciente");
				miSistemaPaciente=new SistemaPaciente();
				System.out.println("Sistema Paciente Iniciado");
				do {
					opcion2=menu1Paciente();
					switch(opcion2) {
					case 1:{
						System.out.println("\nInicia proceso de Registro");
						String nombre,apellido,alergias,tratamientos,fecha;
						Double peso,talla;
						System.out.println("Ingrese sus datos:");
						System.out.print("\tUsuario: ");
						usuario=scan.next();
						System.out.print("\tContrasenia: ");
						contrasenia=scan.next();
						System.out.print("\tNombre: ");
						nombre=scan.next();
						System.out.print("\tApellido: ");
						apellido=scan.next();
						System.out.print("\tDNI: ");
						DNI=scan.nextLong();
						System.out.print("\tFecha Nac (aaaa-mm-dd): ");
						fecha=scan.next();
						System.out.print("\tPeso: ");
						peso=scan.nextDouble();
						System.out.print("\tTalla: ");
						talla=scan.nextDouble();
						System.out.print("\tAlergias: ");
						alergias=scan.nextLine();
						System.out.print("\tTratamientos: ");
						tratamientos=scan.nextLine();
						
						System.out.println("\nSe esta registrando, espere por favor");
						miSistemaPaciente.registrarse(usuario, contrasenia,DNI,fecha,nombre,apellido,peso,talla,alergias,tratamientos);
						System.out.println("Finaliza proceso de Registro");
						break;
					}
					case 2:{
						System.out.print("\nIngrese usuario: ");
						usuario=scan.next();
						System.out.print("Ingrese contrasenia: ");
						contrasenia=scan.next();
						
						System.out.println("\nIniciando Sesion");
						if(miSistemaPaciente.iniciarSesion(usuario, contrasenia)){
							System.out.println("Sesion iniciada");
							do {
								opcion3=menu2Paciente();
								switch(opcion3) {
								case 1:{
									int diaTurno;
									
									System.out.println("\n\t\tInicia Proceso de Solicitud");
									diaTurno=menuFecha();
									if(diaTurno!=0) {
										miSistemaPaciente.solicitarTurno(diaTurno);
										System.out.println("\t\tFinaliza proceso de Solicitud");
										
									}
									else{
										System.out.println("Operacion cancelada");
									}
									break;
								}
								case 2:{
									System.out.println("\n\t\tInicia proceso de Cancelar");									
									if(miSistemaPaciente.getTurnosReservados().size()>0) {
										miSistemaPaciente.mostrarTurnosReservados();
										System.out.print("\t\tIngrese el nro de turno\n\t\t o 0(cero) para termianr el proceso ");
										turno=scan.nextInt();
										miSistemaPaciente.cancelarTurno(turno);
									}
									else {
										System.out.println("\t\tNo hay turnos reservados");
									}
									System.out.println("\t\tFinaliza proceso de Cancelar");
									break;
								}
								case 3:{
									System.out.println("\n\t\tInicia proceso Mostrar Ficha: ");
									miSistemaPaciente.mostrarFichaMedica();
									System.out.println("\t\tFinaliza proceso Mostrar Ficha");
									break;
								}
								case 0:{
									System.out.println("\n\t\tCerrando Sesion");
									miSistemaPaciente.salir();
									System.out.println("\t\tSesion cerrada ");
									break;
								}
								}
							}while(opcion3!=0);
							
						}
						else{
							System.out.println("No se pudo iniciar sesion");
						}
						break;
					}
					case 0:{
						System.out.println("\nCerrando sistema Paciente");
						miSistemaPaciente.salir();
						System.out.println("Sistema Paciente cerrado");
						break;
					}
					}
				
				}while(opcion2!=0);
			}
			else {
				System.out.println("Sistema finalizado");
				
			}
		}
		while(opcion1!=0);
		scan.close();
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

public static int menu1Paciente(){
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n\t1- Registrarse";
	cad+="\n\t2- Iniciar sesion";
	cad+="\n\t0- Salir";
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("\tIngrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>4);
 
    return opcion;
  }
public static int menu2Paciente(){
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n\t1-	Solicitar Turno";
	cad+="\n\t2-	Cancelar Turno";
	cad+="\n\t3-	Mostrar Ficha Medica";
	cad+="\n\t0-	Salir";
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("Ingrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>3);
 
    return opcion;
  }

public static int menuAdministrador(){
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n\t1-	Cargar paciente sector emergencia";
	cad+="\n\t2-	Listar fichas medicas";
	cad+="\n\t3-	Mostrar informes";
	cad+="\n\t0-	Salir";
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("\tIngrese opcion valida: ");
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
	
	cad="\n\t\tSe considera como entrada el numero del mes (1:Enero ...12:Diciembre):";
	cad+="\n\t\tm- nro mes:";
	cad+="\n\t\t0-	Salir";
	
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("\t\tIngrese opcion valida: ");
		opcion=scan.nextInt();
	}
	while(opcion<0 || opcion>12);
 
    return opcion;
}
public static int menuFecha() {
	int opcion;
	String cad;
	Scanner scan;
	int dia=LocalDate.now().getDayOfMonth();
	LocalDate fechaActual;
	
	cad="\t\tIngrese dia("+dia+"...30)\n\t\t o 0 (cero) para salir: ";
	scan=new Scanner(System.in);
 
	do {
		System.out.print(cad);
		opcion=scan.nextInt();
	}while((opcion<dia && opcion!=0) || opcion>30);
    return opcion;
}
}
