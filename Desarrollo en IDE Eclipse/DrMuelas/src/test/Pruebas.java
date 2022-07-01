package test;
import utilidades.Conexion;;
public class Pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Se intenta conectar");
			Conexion miConexion=new Conexion("dr_muelas","root","Ssap4toor0t0!");
			System.out.println(miConexion.conectar());
			
			System.out.println("Se conecto");
			
			System.out.println("Se intenta desconectar");
			miConexion.desconectar();
			
			System.out.println("Se desconecto");
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
