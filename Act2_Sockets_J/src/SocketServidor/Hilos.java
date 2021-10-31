package SocketServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class Hilos implements Runnable{
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;
	private Biblioteca biblioteca;	
	
	// Constructor de la clase Hilos
	public Hilos(Socket socketAlCliente, Biblioteca biblioteca) {
		numCliente++;
		hilo = new Thread(this, "Cliente_"+numCliente);
		this.socketAlCliente = socketAlCliente;
		this.biblioteca = biblioteca;
		hilo.start();
	}
	

	@Override
	public void run() {
		System.out.println("Estableciendo comunicacion con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		String [] datosLibro = null;
		String opcionEscogida = null;
		
		try {
			//Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String texto = null;
			boolean continuar = true;
			
			
			
			//Procesaremos entradas hasta que el texto del cliente sea FIN
			while (continuar) {
				texto = entradaBuffer.readLine();				
			
				datosLibro = texto.split("-");			
				opcionEscogida = datosLibro[0];
				
					
					String isbnLibro;
					String autorLibro;
					String tituloLibro;
					String precioLibro;
					
					
					switch (opcionEscogida) {
					case "1":
						
						isbnLibro = datosLibro[1];
						salida.println(biblioteca.buscarISBN(isbnLibro));
					break;
					case "2": 
						tituloLibro = datosLibro [1];
						salida.println(biblioteca.buscarTitulo(tituloLibro));
					break;
					case "3":
						autorLibro = datosLibro [1];
						salida.println(biblioteca.buscarAutor(autorLibro));
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
						biblioteca.añadirLibro(nuevoLibro);
						
						salida.println("El libro que se ha añadido es el siguiente:" + nuevoLibro);
						break;					
						
					case "FIN":
						salida.println("Fin. Gracias por establecer conexión");
						continuar = false;
						
						break;
					
					}					
					
			 }
			
	
			//Cerramos el socket
			socketAlCliente.close();
			
		} catch (IOException e) {
			System.err.println("Hilos: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Hilos: Error");
			e.printStackTrace();
		}
  }
}
	

