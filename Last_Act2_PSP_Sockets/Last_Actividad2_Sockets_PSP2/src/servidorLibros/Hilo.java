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
		
		// Cada vez que se abra un hilo, se incrementará el número de Cliente
		numeroCliente++;
		
		//Creamos el hilo, al que le añadiremos el número de cliente
		hilo = new Thread(this, "Cliente número: "+ numeroCliente);
		
		// Uno de los atributos del hilo que se crea será un objeto de tipo Socket que será
		// el Socket de conexión entre el servidor y el cliente (conexionAlCliente), que se pasa como parámetro
		// cuando desde el servidor se creó el hilo: new HiloAñadirLibros(conexionAlCliente)
		this.conexionAlCliente = cliente;
		
		this.bibliotecaServer = bibliotecaServer;
		
		// Activamos o iniciamos el hilo
		hilo.start();
	}
	
	// Sobreescribimos el método run del hilo para que ejecute las acciones necesarias 
	//y dé una respuesta a la petición específica del cliente

	@Override
	public void run() {
			
		Scanner lector = new Scanner(System.in);
		InputStreamReader entrada = null;
		PrintStream salida = null;
		BufferedReader entradaBuffer = null;
		
		try {
			// Iniciamos el InputStreamReader y el PrintStream para recibir y enviar información al cliente
			entrada = new InputStreamReader(conexionAlCliente.getInputStream());
			salida = new PrintStream(conexionAlCliente.getOutputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			boolean continuar = true;
			String textoEntrada= "";
			
			while (true) {
				
				// Creamos la biblioteca con los libros preañadidos:
				bibliotecaServer = new Biblioteca();
				
				// Almacenamos en una variable tipo String la cadena de datos del libro que se añadirá, 
				// leída a través del BufferedReader
				String stringDatosRecibidos = entradaBuffer.readLine();
				
				// Sacamos por pantalla el conjunto de datos recibidos:
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringDatosRecibidos);
				
				// Almacenamos en un array de Strings los datos, separados entre sí gracias al método split,
				// que los separa donde hay un guión:
				String [] datosLibro = stringDatosRecibidos.split("-");
				
				// Guardamos en una variable el número de opción escogida en el menú cliente, que siempre será el primer elemento  
				// del array que contiene los datos del string recibido
				String opcionEscogida = datosLibro[0];
				
				// Almacenaremos los datos separados en diferentes variables que indican su contenido:
				String isbnLibro;
				String autorLibro;
				String tituloLibro;
				String precioLibro;
				String datosAñadirLibro;  // contendrá el String enviado por el cliente que contiene todos los datos de un libro a añadir
				
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
					
					// Creamos un nuevo objeto libro con los datos que hemos extraído del String recibido del cliente
					Libro nuevoLibro = new Libro (isbnLibro, tituloLibro, autorLibro, precioLibro);
					
					// Damos de alta el libro en la biblioteca con el método altaLibro(), pasándole como argumento
					// el nuevo libro que hemos creado con los datos que nos ha pasado el cliente
					bibliotecaServer.añadirLibro(nuevoLibro);
					
					salida.println("El libro que se ha añadido es el siguiente:" + nuevoLibro);
				case "FIN":
					salida.println("Fin. Gracias por establecer conexión");
				}
			}
			
		} catch (IOException excepcion) {
			System.out.println(excepcion);
		} finally {
			// Cerramos todas las conexiones de entrada y salida de información (input y output streams) y la conexión con el socket al cliente
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