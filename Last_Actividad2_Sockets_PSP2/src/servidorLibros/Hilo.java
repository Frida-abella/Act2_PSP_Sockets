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
				
				// CONFIRMAR SI ES CORRECTO
				// Creamos la biblioteca con los libros preañadidos:
				bibliotecaServer.Biblioteca();
				
				// Almacenamos en una variable tipo String la cadena de datos del libro que se añadirá, 
				// leída a través del BufferedReader
				String stringDatosRecibidos = entradaBuffer.readLine();
				
				// Sacamos por pantalla el conjunto de datos recibidos:
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringDatosRecibidos);
				
				// Almacenamos en un array de Strings los datos, separados entre sí gracias al método split,
				// que los separa donde hay un guión:
				String [] datosLibro = stringDatosRecibidos.split("-");
				
				//***** ¿CÓMO MANDARLE HACER DIFERENTES COSAS SEGÚN LO QUE PIDA EL CLIENTE?
				
				// si el array de objetos solo tiene un elemento sabremos que es un titulo, autor o isbn.
				// **** CÓMO DISTINGUIR SI NOS HAN MANDADO AUTOR, TITULO O ISBN?
				
				//Llamamos a los métodos correspondientes:
				if (datosLibro.length == 1) {
					bibliotecaServer.buscarPorAutor(datosLibro[0]);
					bibliotecaServer.buscarPorIsbn(datosLibro[0]);
					bibliotecaServer.buscarPorTitulo(datosLibro[0]);
				}
				
				
				/* 	---------------  REQUERIMIENTO 3:  ----------
				 * En caso de que el array contenga más de un elemento, eso significará que es una lista de elementos
				que corresponden a los diferentes atributos que componen el objeto Libro, para crearlo */
				else if (datosLibro.length>1 ) {
				// Sacamos, uno a uno, el valor de cada uno de los datos dentro del array de datos:
				String isbnLibro = datosLibro[0];//3
				String tituloLibro = datosLibro[1];//4
				String autorLibro = datosLibro[2];
				String precioLibro = datosLibro[3];  

				// Creamos un objeto de la clase Libro al que le pasamos como parámetros,
				//los datos que han sido obtenidos del array
				
				Libro nuevoLibro = new Libro (isbnLibro, tituloLibro, autorLibro, precioLibro);  //HABRÍA QUE CAMBIAR EL PARÁMETRO DE ENTRADA DE PRECIO A STRING! 
				
				// Damos de alta el libro en la biblioteca con el método altaLibro(), pasándole como argumento
				// el nuevo libro que hemos creado con los datos que nos ha pasado el cliente
				bibliotecaServer.añadirLibro(nuevoLibro);
				
				salida.println("El libro que se ha añadido es el siguiente:" + nuevoLibro);
				} 	// Aquí terminan las instrucciones del requerimiento 3
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
