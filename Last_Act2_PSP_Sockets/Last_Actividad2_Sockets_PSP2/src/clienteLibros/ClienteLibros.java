package clienteLibros;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteLibros {

	// Declaramos las variables que almacenan la dirección del servidor: IP y puerto
	final static int PUERTO = 5001;
	final static String IP_SERVER = "localhost";	
	
	public static void main(String[] args) {
			
		System.out.println(" ----- APLICACIÓN CLIENTE -----");	
		
		// Creamos un scanner para poder introducir datos por consola o pantalla
		Scanner sc = new Scanner (System.in);
		InputStreamReader entrada=null;
		PrintStream salida=null;
		Socket socketClienteLibro = null;

		
		try {
			//Aquí se crea el socket y a continuación la dirección del servidor para que pueda conectar con él
			socketClienteLibro = new Socket();
			InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
			System.out.println("Conectando los canales de entrada y salida...");
			
			//  y los objetos de entrada y salida de datos que el cliente recibirá y enviará. 
			entrada = new InputStreamReader(socketClienteLibro.getInputStream());
			salida = new PrintStream(socketClienteLibro.getOutputStream());
			
			// A continuación conectamos el cliente con el servidor a través del socket y el método connect(), proporcionándole la 
			// dirección (IP y puerto, que serán los mismos que los del servidor).

			System.out.println("Esperando a que conecte con el servidor...");
			socketClienteLibro.connect(direccionServidor);
			
			// En cuanto el servidor acepte la conexión, avisaremos al cliente por pantalla:
			System.out.println("La conexión con el servidor ha sido establecida");
			
			String opcionEscogida = "";
			//boolean fin = false;
			
			String isbnLibro = "";
			String tituloLibro = "";
			String autorLibro = "";
			String precioLibro = "";
			String datosLibro = "";
			
			String mensajeSalida = "";
			
			do {
				
				System.out.println("Seleccione la opción que desea (1, 2, 3, 4 o FIN:");
				System.out.println("1: Buscar un libro por su ISBN");
				System.out.println("2: Buscar un libro por su título");				
				System.out.println("3: Buscar un libro por su autor");
				System.out.println("4: Añadir un libro a la biblioteca");
				System.out.println("FIN: Teclee la palabra FIN para salir de la aplicación");
				
				opcionEscogida = sc.nextLine();
				
				switch (opcionEscogida) {
				case "1": // Se ejecutan las instrucciones y acciones de la opción de buscar por título o libro
					System.out.println("A continuación busque el libro deseado escribiendo su ISBN: ");
					
					// Leemos por consola lo que el usuario escriba y lo guardamos en una variable
					isbnLibro = sc.nextLine();
					
					// Asignamos el número de opción escogida a la variable:
					opcionEscogida = "1";
					
					//Asignamos a la variable que se envía al servidor el número de la opción escogida (primero) y del isbn (a continuación)
					//separados por un guión:
					mensajeSalida = opcionEscogida + "-" + isbnLibro;
				break;
				case "2":
					System.out.println("A continuación busque el libro deseado escribiendo su TÍTULO");
					
					//Leemos por consola lo que el usuario escriba y lo guardamos en la variable
					tituloLibro = sc.nextLine();
					
					opcionEscogida = "2";
					
					// Asignamos a la variable que será enviada al servidor el contenido de los Strings 
					// con la opcion que ha sido escogida (primero) y el título del libro a buscar (después)
					mensajeSalida = opcionEscogida + "-" + tituloLibro;
				break;
				case "3": 
					// Se pide por pantalla que se teclee el nombre del autor por el que buscar
					System.out.println("A continuación busque el libro deseado escribiendo su AUTOR: ");
					
					// Leemos por consola lo que el usuario escriba y lo guardamos en una variable
					autorLibro = sc.nextLine();
					
					// Asignamos el número de opción 3 a la variable
					opcionEscogida = "3";
					
					//Asignamos el contenido de esa variable a la variable que se envía al servidor:
					mensajeSalida = opcionEscogida + "-" + autorLibro;
				break;
				
				case "4":  
					// En caso de que seleccione añadir libro, pediremos por pantalla al cliente los datos de cada libro que se quiera añadir
					System.out.println("A continuación facilite todos los datos del libro.");
					System.out.println("Introduzca el ISBN del libro a añadir:");
					isbnLibro = sc.nextLine();
					System.out.println("Introduzca el AUTOR del libro a añadir:");
					autorLibro = sc.nextLine();
					System.out.println("Introduzca el TÍTULO del libro a añadir:");
					tituloLibro = sc.nextLine();
					System.out.println("Introduzca el PRECIO del libro a añadir:");
					precioLibro = sc.nextLine();
					
					// Separamos los diferentes datos por un guión para que el hilo pueda separarlos:
					datosLibro = isbnLibro + "-" + autorLibro + "" + tituloLibro + "" + precioLibro;
					
					// Asignamos el número 4 a la variable que recoge el número de la opcion escogida
					opcionEscogida = "4";
					
					// Asignamos al mensaje que se enviará al servidor el contenido de la opción escogida
					// y a continuación los datos del libro a añadir, separados por guión
					mensajeSalida = opcionEscogida + "-" + datosLibro;
				break;
				default:
					System.out.println("Error: no existe la opción escogida. Pruebe BUSCAR, AUTOR, AÑADIR o FIN");	
				}

				
				// Enviamos al servidor los datos en cuestión a través del OutputStream
				salida.println(mensajeSalida);
			
				
				//Preparamos el BufferedReader
				BufferedReader entradaBuffer = new BufferedReader(entrada);
				
				// Esperamos a que el servidor nos de una respuesta... 
				System.out.println("Esperando respuesta del servidor...");
				
				// Cuando llegue un mensaje del servidor será leído 
				String mensajeServidor = entradaBuffer.readLine();	
				
				//Sacamos por pantalla el mensaje del servidor
				System.out.println("CLIENTE: El servidor ha respondido: " + mensajeServidor);
				

			// Todo lo anterior se ejecuta mientras el usuario no haya tecleado la palabra FIN, en cuyo caso se acaba el programa	
			} while (!opcionEscogida.equals("FIN"));
			
			// Capturamos todas las excepciones que se pueden producir:
			} catch (UnknownHostException excepcion) {
				System.err.println("No encuentro el servidor en la dirección" + IP_SERVER);
			} catch (IOException excepcion) {
				System.err.println("Error de entrada/salida");
			} catch (Exception e) {
				System.err.println("Error: " + e);
			} finally {
				// Cerramos todas las conexiones de entrada y salida de información, incluido el socket y el scanner:
				try {
					if(salida != null && entrada != null) {
					salida.close();
					entrada.close();
					socketClienteLibro.close();
					sc.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}		
	}
}
