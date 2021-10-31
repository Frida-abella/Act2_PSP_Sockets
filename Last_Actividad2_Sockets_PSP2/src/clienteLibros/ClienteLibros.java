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

	// Declaramos las variables que almacenan la direcci�n del servidor: IP y puerto
	final static int PUERTO = 5001;
	final static String IP_SERVER = "localhost";	
	
	public static void main(String[] args) {
			
		System.out.println(" ----- APLICACI�N CLIENTE -----");	
		
		// Creamos un scanner para poder introducir datos por consola o pantalla
		Scanner sc = new Scanner (System.in);
		InputStreamReader entrada=null;
		PrintStream salida=null;
		Socket socketClienteLibro = null;

		
		try {
			//Aqu� se crea el socket y a continuaci�n la direcci�n del servidor para que pueda conectar con �l
			socketClienteLibro = new Socket();
			InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
			System.out.println("Conectando los canales de entrada y salida...");
			
			//  y los objetos de entrada y salida de datos que el cliente recibir� y enviar�. 
			entrada = new InputStreamReader(socketClienteLibro.getInputStream());
			salida = new PrintStream(socketClienteLibro.getOutputStream());
			
			// A continuaci�n conectamos el cliente con el servidor a trav�s del socket y el m�todo connect(), proporcion�ndole la 
			// direcci�n (IP y puerto, que ser�n los mismos que los del servidor).

			System.out.println("Esperando a que conecte con el servidor...");
			socketClienteLibro.connect(direccionServidor);
			
			// En cuanto el servidor acepte la conexi�n, avisaremos al cliente por pantalla:
			System.out.println("La conexi�n con el servidor ha sido establecida");
			
			String opcionEscogida = "";
			//boolean fin = false;
			
			String buscarLibro = "";
			String autorLibro = "";
			String datosLibro = "";
			
			String mensajeSalida = "";
			
			do {
				
				System.out.println("Seleccione la opci�n que desea (BUSCAR, AUTOR o A�ADIR):");
				System.out.println("BUSCAR: Buscar un libro por su ISBN o T�tulo");
				System.out.println("AUTOR: Buscar un libro por su autor");
				System.out.println("A�ADIR: A�adir un libro a la biblioteca");
				System.out.println("Teclee la palabra FIN para salir de la aplicaci�n");
				
				opcionEscogida = sc.nextLine();
				
				switch (opcionEscogida) {
				case "BUSCAR": // Se ejecutan las instrucciones y acciones de la opci�n de buscar por t�tulo o libro
					System.out.println("A continuaci�n busque el libro deseado escribiendo su ISBN o T�TULO: ");
					
					// Leemos por consola lo que el usuario escriba y lo guardamos en una variable
					buscarLibro = sc.nextLine();
					
					//Asignamos el contenido de esa variable a la variable que se env�a al servidor:
					mensajeSalida = buscarLibro;
				break;
				case "AUTOR": 
					// Se pide por pantalla que se teclee el nombre del autor por el que buscar
					System.out.println("A continuaci�n busque el libro deseado escribiendo su AUTOR: ");
					
					// Leemos por consola lo que el usuario escriba y lo guardamos en una variable
					autorLibro = sc.nextLine();
					
					//Asignamos el contenido de esa variable a la variable que se env�a al servidor:
					mensajeSalida = buscarLibro;
				break;
				
				case "A�ADIR":  
					// En caso de que seleccione a�adir libro, pediremos por pantalla al cliente los datos de cada libro que se quiera a�adir
					System.out.println("A continuaci�n introduzca los datos del libro deseado: ISBN, t�tulo, autor y precio"
							+ "separados por gui�n (-). ");
					
					datosLibro = sc.nextLine();
					
					// Hacemos que el mensaje que enviaremos al servidor sean los datos del libro a a�adir:
					mensajeSalida = datosLibro;
				break;
				default:
					System.out.println("Error: no existe la opci�n escogida. Pruebe BUSCAR, AUTOR, A�ADIR o FIN");	
				}

				
				// Enviamos al servidor los datos en cuesti�n a trav�s del OutputStream
				salida.println(mensajeSalida);
			
				
				//Preparamos el BufferedReader
				BufferedReader entradaBuffer = new BufferedReader(entrada);
				
				// Esperamos a que el servidor nos de una respuesta... 
				System.out.println("Esperando respuesta del servidor...");
				
				// Cuando llegue un mensaje del servidor ser� le�do 
				String mensajeServidor = entradaBuffer.readLine();	
				
				//Sacamos por pantalla el mensaje del servidor
				System.out.println("CLIENTE: El servidor ha respondido: " + mensajeServidor);
				

			// Todo lo anterior se ejecuta mientras el usuario no haya tecleado la palabra FIN, en cuyo caso se acaba el programa	
			} while (!opcionEscogida.equals("FIN"));
			
			// Capturamos todas las excepciones que se pueden producir:
			} catch (UnknownHostException excepcion) {
				System.err.println("No encuentro el servidor en la direcci�n" + IP_SERVER);
			} catch (IOException excepcion) {
				System.err.println("Error de entrada/salida");
			} catch (Exception e) {
				System.err.println("Error: " + e);
			} finally {
				// Cerramos todas las conexiones de entrada y salida de informaci�n, incluido el socket y el scanner:
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
