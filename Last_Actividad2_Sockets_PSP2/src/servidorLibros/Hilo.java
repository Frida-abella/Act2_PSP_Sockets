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
				
				// CONFIRMAR SI ES CORRECTO
				// Creamos la biblioteca con los libros prea�adidos:
				bibliotecaServer.Biblioteca();
				
				// Almacenamos en una variable tipo String la cadena de datos del libro que se a�adir�, 
				// le�da a trav�s del BufferedReader
				String stringDatosRecibidos = entradaBuffer.readLine();
				
				// Sacamos por pantalla el conjunto de datos recibidos:
				System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringDatosRecibidos);
				
				// Almacenamos en un array de Strings los datos, separados entre s� gracias al m�todo split,
				// que los separa donde hay un gui�n:
				String [] datosLibro = stringDatosRecibidos.split("-");
				
				//***** �C�MO MANDARLE HACER DIFERENTES COSAS SEG�N LO QUE PIDA EL CLIENTE?
				
				// si el array de objetos solo tiene un elemento sabremos que es un titulo, autor o isbn.
				// **** C�MO DISTINGUIR SI NOS HAN MANDADO AUTOR, TITULO O ISBN?
				
				//Llamamos a los m�todos correspondientes:
				if (datosLibro.length == 1) {
					bibliotecaServer.buscarPorAutor(datosLibro[0]);
					bibliotecaServer.buscarPorIsbn(datosLibro[0]);
					bibliotecaServer.buscarPorTitulo(datosLibro[0]);
				}
				
				
				/* 	---------------  REQUERIMIENTO 3:  ----------
				 * En caso de que el array contenga m�s de un elemento, eso significar� que es una lista de elementos
				que corresponden a los diferentes atributos que componen el objeto Libro, para crearlo */
				else if (datosLibro.length>1 ) {
				// Sacamos, uno a uno, el valor de cada uno de los datos dentro del array de datos:
				String isbnLibro = datosLibro[0];//3
				String tituloLibro = datosLibro[1];//4
				String autorLibro = datosLibro[2];
				String precioLibro = datosLibro[3];  

				// Creamos un objeto de la clase Libro al que le pasamos como par�metros,
				//los datos que han sido obtenidos del array
				
				Libro nuevoLibro = new Libro (isbnLibro, tituloLibro, autorLibro, precioLibro);  //HABR�A QUE CAMBIAR EL PAR�METRO DE ENTRADA DE PRECIO A STRING! 
				
				// Damos de alta el libro en la biblioteca con el m�todo altaLibro(), pas�ndole como argumento
				// el nuevo libro que hemos creado con los datos que nos ha pasado el cliente
				bibliotecaServer.a�adirLibro(nuevoLibro);
				
				salida.println("El libro que se ha a�adido es el siguiente:" + nuevoLibro);
				} 	// Aqu� terminan las instrucciones del requerimiento 3
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
