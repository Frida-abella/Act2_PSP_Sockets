package SocketCliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;




public class Cliente {
	
	public static final int PUERTO = 2021;
	public static final String IP_SERVER = "localhost";

	public static void main(String[] args) {
		
		System.out.println("    APLICACIÓN CLIENTE BIBLIOTECA     ");
		System.out.println("--------------------------------------");

		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		
		try (Scanner sc = new Scanner(System.in)){
						
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
			Socket socketAlServidor = new Socket();
			socketAlServidor.connect(direccionServidor);
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + 
					" por el puerto " + PUERTO);

			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);
			
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());		
			
			
			//Variable que controla las opciones del switch del menu
			String opcion;
			
			String isbn = "";
			String titulo = "";
			String autor = "";
			String datosLibro = "";
			String fin = "";
			
			//Variable que mandamos al servidor a buscar			
			String buscar;
						
			
			do {
				//LLAMAMOS AL METODO MENU PARA DESPLEGAR OPCIONES
				menu();				
				//frase que vamos a mandar para contar				
				opcion = sc.nextLine().toUpperCase();
				switch (opcion) {
					case "1":
						System.out.println("Escribe el IBSN del libro: ");
						isbn = sc.nextLine();
						opcion = "1";
						
						buscar  = opcion + "-" + isbn;
						
						salida.println(buscar);
						System.out.println("CLIENTE: Esperando respuesta ...... ");		
						
						String respuesta1 = entradaBuffer.readLine();						
						System.out.println("CLIENTE: Servidor responde, " + respuesta1);
											
						break;
						
					case "2":
						System.out.println("Escribe el título del libro: ");						
						titulo = sc.nextLine();
						opcion = "2";
						
						buscar = opcion + "-" + titulo;
						
						salida.println(buscar);
						System.out.println("CLIENTE: Esperando respuesta ...... ");		
						
						String respuesta2 = entradaBuffer.readLine();
						System.out.println("CLIENTE: Servidor responde, " + respuesta2);
											
						break;						
						
					case "3":
						System.out.println("Escribe el nombre del autor: ");
						autor = sc.nextLine();
						opcion = "3";
						
						buscar = opcion + "-" + autor;
						
						salida.println(buscar);
						System.out.println("CLIENTE: Esperando respuesta ...... ");		
						
						String respuesta3 = entradaBuffer.readLine();
						System.out.println("CLIENTE: Servidor responde, " + respuesta3);
											
						break;
						
					case "4":
						System.out.println("Escribe los datos de la siguiente forma"
								+ " ----> ISBN-AUTOR-TITULO-PRECIO <----  "
								+ "del libro que quieres añadir a la biblioteca separado por - ");
						datosLibro = sc.nextLine();
						opcion = "4";
							
						buscar = opcion + "-" + datosLibro;
						
						salida.println(buscar);
						System.out.println("CLIENTE: Esperando respuesta ...... ");		
						
						String respuesta4 = entradaBuffer.readLine();
						System.out.println("CLIENTE: Servidor responde, " + respuesta4);
											
						break;
												
					
					case "FIN":						
						System.out.println("Programa finalizado");
						
					
						salida.println(opcion);
						System.out.println("CLIENTE: Esperando respuesta ...... ");	
						
						String despedida = entradaBuffer.readLine();
						System.out.println(despedida + " ADIOS");
						
						break;
						
					default:
						System.out.println("Opción incorrecta");
				
				}
					
				
				
			} while (!opcion.equals("FIN"));
			//CERRAMOS EL SOCKET
			socketAlServidor.close();
			
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");
}

			
	// METODO MENU PARA DESPLEGAR OPCIONES
	
	private static void menu() {
		System.out.println();
		System.out.println("--------------------------");
		System.out.println("Bienvenido a la Biblioteca");
		System.out.println("--------------------------");
		System.out.println("Escriba el numero de la opcion que desee o FIN para salir");
		System.out.println("1 = Buscar libro por ISBN");
		System.out.println("2 = Buscar libro por TITULO");
		System.out.println("3 = Buscar libros por AUTOR");
		System.out.println("4 = Añadir libro a la biblioteca");
		System.out.println("FIN = Salir del programa");		
		System.out.println("--------------------------");		
	}

}


