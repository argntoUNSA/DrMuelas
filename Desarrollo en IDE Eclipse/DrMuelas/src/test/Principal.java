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
					System.out.println("Iniciando Sistema Administrador");
					miSistemaAdministrador=new SistemaAdministrador();
					System.out.println("Sistema Administrador Iniciado");
					do {
						opcion2=menuAdministrador();
						switch(opcion2) {
						case 1:{
							String nombre,apellido,alergias,descripcion,fecha;
							Double peso,talla;
							System.out.print("Ingrese DNI: ");
							DNI=scan.nextLong();
							System.out.print("Ingrese Nombre: ");
							nombre=scan.next();
							System.out.print("Ingrese Apellido: ");
							apellido=scan.next();
							System.out.print("Ingrese Fecha Nacimiento: ");
							fecha=scan.next();
							System.out.print("Ingrese Peso: ");
							peso=scan.nextDouble();
							System.out.print("Ingrese Talla: ");
							talla=scan.nextDouble();
							System.out.print("Ingrese Alergias: ");
							alergias=scan.next();
							System.out.print("Ingrese descripcion: ");
							descripcion=scan.next();
							miSistemaAdministrador.cargarPacienteEmergencia(DNI,nombre,apellido,fecha,peso,talla,alergias,descripcion);
							
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
							miSistemaAdministrador.salir();
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
				do {
					opcion2=menu1Paciente();
					switch(opcion2) {
					case 1:{
						String nombre,apellido,alergias,tratamientos,fecha;
						Double peso,talla;
						System.out.println("Ingrese sus datos:");
						System.out.print("Usuario: ");
						usuario=scan.next();
						System.out.print("Contrasenia: ");
						contrasenia=scan.next();
						System.out.print("Nombre: ");
						nombre=scan.next();
						System.out.print("Apellido: ");
						apellido=scan.next();
						System.out.print("DNI: ");
						DNI=scan.nextLong();
						System.out.print("Fecha Nac (aaaa-mm-dd): ");
						fecha=scan.next();
						System.out.print("Peso: ");
						peso=scan.nextDouble();
						System.out.print("Talla: ");
						talla=scan.nextDouble();
						System.out.print("Alergias: ");
						alergias=scan.next();
						System.out.print("Tratamientos: ");
						tratamientos=scan.next();
						
						System.out.println("Se esta registrando, espere por favor");
						miSistemaPaciente.registrarse(usuario, contrasenia,DNI,fecha,nombre,apellido,peso,talla,alergias,tratamientos);
						System.out.println("Registro completado");
						break;
					}
					case 2:{
						System.out.print("Ingrese usuario: ");
						usuario=scan.next();
						System.out.print("Ingrese contrasenia: ");
						contrasenia=scan.next();
						
						System.out.println("Iniciando Sesion");
						if(miSistemaPaciente.iniciarSesion(usuario, contrasenia)){
							System.out.println("Sesion iniciada");
							do {
								opcion3=menu2Paciente();
								switch(opcion3) {
								case 1:{
									int diaTurno;
									
									System.out.println("Solicitando turno para este mes");
									diaTurno=menuFecha();
									miSistemaPaciente.solicitarTurno(diaTurno);
									System.out.println("Turno reservado");
									
									break;
								}
								case 2:{
									if(miSistemaPaciente.getTurnosReservados().size()>0) {
										miSistemaPaciente.mostrarTurnosReservados();
										System.out.print("Cancelar turno nro: ");
										turno=scan.nextInt();
										miSistemaPaciente.cancelarTurno(turno);
										System.out.println("Turno Canelado");
									}
									else {
										System.out.println("No hay turnos reservados");
									}
									break;
								}
								case 3:{
									System.out.println("Se muestra ficha cargada: ");
									miSistemaPaciente.mostrarFichaMedica();
									System.out.println("Fin de la muestra");
									break;
								}
								case 0:{
									System.out.println("Cerrando Sesion");
									miSistemaPaciente.salir();
									System.out.println("Sesion cerrada ");
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
						System.out.println("Cerrando sistema Paciente");
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
	   
	cad="\n1- Registrarse";
	cad+="\n2- Iniciar sesion";
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
public static int menu2Paciente(){
	int opcion;
	String cad;
	Scanner scan;
	   
	cad="\n1-	Solicitar Turno";
	cad+="\n2-	Cancelar Turno";
	cad+="\n3-	Mostrar Ficha Medica";
	cad+="\n0-	Salir";
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
	cad+="\n11-	Noviembre";
	cad+="\n12-	Diciembre";
	cad+="\n0-	Salir";
	
	scan=new Scanner(System.in);
	do{
		System.out.println(cad);
		System.out.print("Ingrese opcion valida: ");
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
	cad="\n1-	Lunes";
	cad+="\n2-	Martes";
	cad+="\n3-	Miercoles";
	cad+="\n4-	Jueves";
	cad+="\n5-	Viernes";
	
	cad="Ingrese dia ("+dia+"...30):";
	scan=new Scanner(System.in);
		
	
//		do{
//			System.out.println(cad);
//			System.out.print("Ingrese opcion valida: ");
//			opcion=scan.nextInt();
//		}
//		while(opcion<1 || opcion>5);
//	 
	do {
		System.out.println(cad);
		opcion=scan.nextInt();
	}while(opcion<dia && opcion>30);
    return opcion;
}
}
