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
	
	// Declaramos las variables que contienen el puerto e IP de la dirección del servidor
	public static final int PUERTO = 2021;
	public static final String IP_SERVER = "localhost";

	public static void main(String[] args) {
		
		System.out.println("    APLICACIÓN CLIENTE BIBLIOTECA     ");
		System.out.println("--------------------------------------");
	
		// Creamos la dirección del Servidor y le pasamos como argumentos el IP y puerto, que deben ser los mismos que los del servidor
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
		
		try (Scanner sc = new Scanner(System.in)){
			
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
			
			// Creamos el objeto de la clase socket del cliente, que es la que nos permite conectar con el servidor:
			Socket socketAlServidor = new Socket();
			
			//Conectamos el socket del cliente con el servidor a través de la dirección 
			socketAlServidor.connect(direccionServidor);
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + 
					" por el puerto " + PUERTO);
			
			// Creamos el canal de entrada de datos mandados por el servidor
			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);
			
			// Creamos el canal de salida de información al servidor
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());		
			
			
			//Variable que controla las opciones del switch del menu
			String opcion;
			
			// Inicializamos a String vacío las variables que contendrán los datos del libro que introduzca el usuario
			String isbn = "";
			String titulo = "";
			String autor = "";
			String datosLibro = "";			
			
			//Variable no inicializada que mandamos al servidor, que tendrá el contenido de la opción e información a buscar			
			String buscar;
						
			
			do {
				//Llamamos al método menú para desplegar opciones
				menu();				
				//Leemos a través del scanner la opción seleccionada por el usuario			
				opcion = sc.nextLine().toUpperCase();
				
				// Creamos un menú en el que, según la opción elegida se ejecuten unas u otras acciones
				switch (opcion) {
					// Si la opción elegida es 1, se pedirá el ISBN del libro que se quiere buscar:	
					case "1":
						System.out.println("Escribe el IBSN del libro: ");
						// Leemos el isbn 
						isbn = sc.nextLine();
						opcion = "1";
						
						// La variable buscar, que enviaremos al servidor, contendrá en un string la concatenación de:
						// el número de opción y el isbn buscado, separados por guión (-) para que el servidor pueda hacer split
						// y separarlos
						buscar  = opcion + "-" + isbn;
						
						// Enviamos al servidor el string con toda la información
						salida.println(buscar);
						System.out.println("CLIENTE: Esperando respuesta ...... ");		
						
						// Cuando el servidor envíe una respuesta, la leeremos a través del bufferedReader
						String respuesta1 = entradaBuffer.readLine();	
						
						//Mostramos por pantalla la respuesta del servidor 
						System.out.println("CLIENTE: Servidor responde, " + respuesta1);
											
						break;
						
					case "2":
						// La opción 2 se corresponde con buscar el libro por título. Se repite el mismo esquema de arriba:
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
						// La opción 4 es para añadir un libro. El esquema es el mismo de arriba.
						// En esta ocasión, dado que se piden varios datos, se le pide al usuario que los separe
						// también con guión (-) para que el servidor pueda separarlos y usarlos convenientemente
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
						// Cuando el usuario teclea la palabra FIN, se informa de que el programa está finalizado
						System.out.println("Finalizando programa");
						
						// Previamente mandamos al hilo de que la opción elegida es "FIN"
						salida.println(opcion);
						System.out.println("CLIENTE: Esperando respuesta ...... ");	
						
						// El hilo nos manda una respuesta de despedida, ya que interpreta que queremos finalizar el programa
						String despedida = entradaBuffer.readLine();
						System.out.println(despedida + " ADIOS");
						
						break;
						
						//En caso de que no se teclee ninguna de las opciones previas, se mostrará un mensaje por pantalla:
					default:
						System.out.println("Opción incorrecta");
				
				}
					
				
			// Este programa NO finalizará mientras no se escriba FIN.
			// Si se escribe FIN, en cuanto el while se cerrará el socket o conexión al servidor
			//Previamente habrá ejecutado la opción case:FIN, en la que el hilo se habrá despedido
			} while (!opcion.equals("FIN"));
			//CERRAMOS EL SOCKET
			socketAlServidor.close();
			
			// Capturamos todas las excepciones
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


