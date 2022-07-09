package test;
import utilidades.Conexion;
import java.time.*;
import java.util.Scanner;

import clinica.SistemaPaciente;
import entidades.Paciente;
public class Pruebas {
	public static void main(String[] args) {
		try {
//			Prueba de Conexion
			System.out.println("Se intenta conectar");
			Conexion miConexion=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(miConexion.conectar());
			System.out.println("Se conecto");
			System.out.println("Se intenta desconectar");
			miConexion.desconectar();
			System.out.println("Se desconecto");
//			Prueba Clase Cliente
//			Paciente miPaciente=new Paciente(1, "user1","pass1",LocalDate.now(), false);
//			System.out.println(miPaciente.toSting());
//			Prueba Clase SistemaPaciente
//			Scanner scan=new Scanner(System.in);
//			String u,c,car;
//			int vId;
//			SistemaPaciente miSistema=new SistemaPaciente();
//			System.out.println("Usuario: ");
//		    u=scan.next();
//		    System.out.println("Contraseña: ");
//		    c=scan.next();
//		    miSistema.registrarse(u,c);
//			
//			System.out.println("Prueba inicio sesion...\nUsuario: ");
//			u=scan.next();
//			System.out.println("Contrasenia: ");
//			c=scan.next();
//			miSistema.iniciarSesion(u,c);
//			System.out.println(miSistema.toString());
			
//			miSistema.mostrarTurnosDisponibles();
//			System.out.println("Ingrese numero de turno: ");
//		    vId=scan.nextInt();
//			miSistema.solicitarTurno(vId);
			
//			miSistema.mostrarTurnosReservados();
			
//			System.out.println("Prueba diferencia de fechas");
//			System.out.println(miSistema.calculaDiferencia(LocalDate.now(), LocalDate.of(2022, Month.JULY, 8)));
//			
			
//			System.out.println("Prueba cancelar Turno reservado");
//			int id,opcion;
//			miSistema.mostrarTurnosReservados();
//			System.out.println("Ingrese nro del turno que desea cancelar");
//			opcion=scan.nextInt();
//			miSistema.cancelarTurno(opcion);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
