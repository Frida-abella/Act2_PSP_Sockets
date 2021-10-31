package SocketServidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


import SocketServidor.Hilos;


public class Servidor {
	
	// Declaramos la variable que contiene el puerto del servidor, que será el mismo que le hemos dado al cliente 
	public static final int PUERTO = 2021;
	
	

	public static void main(String[] args) {	
		
		
		System.out.println("      APLICACIÓN DE SERVIDOR BIBLIOTECA     ");
		System.out.println("-------------------------------------------");		
		
		// Creamos un objeto biblioteca y lo inicializamos o cargamos con la colección (lista) de libros preestablecidos que tenemos:
		Biblioteca biblioteca = new Biblioteca();
		biblioteca.cargarBiblioteca();
		
		// Inicializamos a 0 la variable con el número de petición para que se vaya aumentando en 1 cada vez que un cliente se conecta o realiza petición
		int peticion = 0;
		
		// Creamos el objeto de tipo ServerSocket, que es el que abre un puerto
		try (ServerSocket servidor = new ServerSocket()){	
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			// Decimos a ese socket que escuche peticiones desde el puerto que hemos especificado al cliente 
			servidor.bind(direccion);			

			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			
			while (true) {
				//CREA UN SOCKET DIFERENTE POR CADA PETICION DE CLIENTE QUE ACEPTA EL OBJETO SERVERSOCKET
				Socket socketAlCliente = servidor.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
				/*ABRIMOS UN HILO NUEVO Y LIBERAMOS EL HILO MAIN PARA QUE ATIENDA
				 *  PETICIONES DE OTROS CLIENTES */	
				// Le pasamos al hilo la conexión(socket) con el cliente y el objeto biblioteca que hemos creado aquí
				new Hilos(socketAlCliente, biblioteca);
			}
			
			// Capturamos todas las excepciones
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
		
		

	}

}
