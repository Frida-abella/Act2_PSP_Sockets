package SocketServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;


public class Hilos implements Runnable{
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;
	private Biblioteca biblioteca;	
		
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
		
		try {
			//Salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String texto = "";
			boolean continuar = true;
			String [] textosplit;
			
			//Procesaremos entradas hasta que el texto del cliente sea FIN
			while (continuar) {
				texto = entradaBuffer.readLine();
				textosplit = texto.split("-");
				
				if (texto.equalsIgnoreCase("FIN")) {
					
					salida.println("FIN");
					System.out.println(hilo.getName() + " ha cerrado la comunicacion");
					continuar = false;
				} else {
										
					for (Libro l : biblioteca.listaLibros) {
						if (l.getAutor().contains(texto)) {
							List<Libro> respuesta = null;
							respuesta = biblioteca.buscarAutor(texto);
							//Le mandamos la respuesta al cliente
							salida.println(respuesta);
							
						}else if (l.getIsbn().contains(texto)) {
							Libro respuesta = null;
							respuesta = biblioteca.buscarISBN(texto);
							salida.println(respuesta);
							
						}else if (l.getTitulo().contains(texto)) {
							Libro respuesta = null;
							respuesta = biblioteca.buscarTitulo(texto);
							salida.println(respuesta);							
						} else if (textosplit[0] == "AÑADIR"){
							String isbnLibro = textosplit[1];
							String tituloLibro = textosplit[2];
							String autorLibro = textosplit[3];
							String precioLibro = textosplit[4];
							Libro nuevoLibro = new Libro(isbnLibro, tituloLibro, autorLibro, precioLibro);
							biblioteca.añadirLibro(nuevoLibro);
							salida.println(nuevoLibro);	
						}
					}
						
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
	

