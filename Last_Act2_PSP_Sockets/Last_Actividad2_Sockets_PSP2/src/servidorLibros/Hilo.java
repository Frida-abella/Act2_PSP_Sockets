package servidorLibros;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import servidorLibros.Libro;


public class Hilo implements Runnable {
	
	private Thread hilo;
	public static int numeroCliente = 0;
	private Socket conexionAlCliente;
	public Biblioteca bibliotecaServer;
	

	
	public Hilo (Socket cliente, Biblioteca bibliotecaServer) {
		
		// Cada vez que se abra un hilo, se incrementar� el n�mero de Cliente
		numeroCliente++;
		
		//Creamos el hilo, al que le a�adiremos el n�mero de cliente
		hilo = new Thread(this, "Cliente n�mero: "+ numeroCliente);
		
		// Uno de los atributos del hilo que se crea ser� un objeto de tipo Socket que ser�
		// el Socket de conexi�n entre el servidor y el cliente (conexionAlCliente), que se pasa como par�metro
		// cuando desde el servidor se cre� el hilo: new HiloA�adirLibros(conexionAlCliente)
		this.conexionAlCliente = cliente;
		
		this.bibliotecaServer = bibliotecaServer;
		
		// Activamos o iniciamos el hilo
		hilo.start();
	}
	
	// Sobreescribimos el m�todo run del hilo para que ejecute las acciones necesarias 
	//y d� una respuesta a la petici�n espec�fica del cliente

	@Override
	public void run() {
			
		Scanner lector = new Scanner(System.in);
		InputStreamReader entrada = null;
		PrintStream salida = null;
		BufferedReader entradaBuffer = null;
		
		try {
			// Iniciamos el InputStreamReader y el PrintStream para recibir y enviar informaci�n al cliente
			entrada = new InputStreamReader(conexionAlCliente.getInputStream());
			salida = new PrintStream(conexionAlCliente.getOutputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			boolean continuar = true;
			String textoEntrada= "";
			
			while (true) {
				
				// Creamos la biblioteca con los libros prea�adidos:
				bibliotecaServer = new Biblioteca();
				
				// Almacenamos en una variable tipo String la cadena de datos del libro que se a�adir�, 
				// le�da a trav�s del BufferedReader
				String stringDatosRecibidos = entradaBuffer.readLine();
				
				// Sacamos por pantalla el conjunto de datos recibidos:
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringDatosRecibidos);
				
				// Almacenamos en un array de Strings los datos, separados entre s� gracias al m�todo split,
				// que los separa donde hay un gui�n:
				String [] datosLibro = stringDatosRecibidos.split("-");
				
				// Guardamos en una variable el n�mero de opci�n escogida en el men� cliente, que siempre ser� el primer elemento  
				// del array que contiene los datos del string recibido
				String opcionEscogida = datosLibro[0];
				
				// Almacenaremos los datos separados en diferentes variables que indican su contenido:
				String isbnLibro;
				String autorLibro;
				String tituloLibro;
				String precioLibro;
				String datosA�adirLibro;  // contendr� el String enviado por el cliente que contiene todos los datos de un libro a a�adir
				
				switch (opcionEscogida) {
				case "1":
					isbnLibro = datosLibro[1];
					salida.println(bibliotecaServer.buscarPorIsbn(isbnLibro));
				break;
				case "2": 
					tituloLibro = datosLibro [1];
					salida.println(bibliotecaServer.buscarPorTitulo(tituloLibro));
				break;
				case "3":
					autorLibro = datosLibro [1];
					salida.println(bibliotecaServer.buscarPorAutor(autorLibro));
				break;
				case "4":
					isbnLibro = datosLibro[1];
					autorLibro = datosLibro[2];
					tituloLibro = datosLibro[3];
					precioLibro = datosLibro[4];
					
					// Creamos un nuevo objeto libro con los datos que hemos extra�do del String recibido del cliente
					Libro nuevoLibro = new Libro (isbnLibro, tituloLibro, autorLibro, precioLibro);
					
					// Damos de alta el libro en la biblioteca con el m�todo altaLibro(), pas�ndole como argumento
					// el nuevo libro que hemos creado con los datos que nos ha pasado el cliente
					bibliotecaServer.a�adirLibro(nuevoLibro);
					
					salida.println("El libro que se ha a�adido es el siguiente:" + nuevoLibro);
				case "FIN":
					salida.println("Fin. Gracias por establecer conexi�n");
				}
			}
			
		} catch (IOException excepcion) {
			System.out.println(excepcion);
		} finally {
			// Cerramos todas las conexiones de entrada y salida de informaci�n (input y output streams) y la conexi�n con el socket al cliente
			try {
				if(salida != null && entrada != null){
					salida.close();
					entrada.close();
					conexionAlCliente.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
					
		
	}
}