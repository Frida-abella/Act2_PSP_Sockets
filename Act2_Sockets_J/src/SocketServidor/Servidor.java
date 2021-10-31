package SocketServidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


import SocketServidor.Hilos;


public class Servidor {
	
	
	public static final int PUERTO = 2021;
	
	

	public static void main(String[] args) {	
		
		
		System.out.println("      APLICACIÓN DE SERVIDOR BIBLIOTECA     ");
		System.out.println("-------------------------------------------");		
		
		Biblioteca biblioteca = new Biblioteca();
		biblioteca.cargarBiblioteca();
		int peticion = 0;
				
		try (ServerSocket servidor = new ServerSocket()){			
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);			

			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			
			while (true) {
				//CREA UN SOCKET DIFERENTE POR CADA PETICION DE CLIENTE
				Socket socketAlCliente = servidor.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
				/*ABRIMOS UN HILO NUEVO Y LIBERAMOS EL HILO MAIN PARA QUE ATIENDA
				 *  PETICIONES DE OTROS CLIENTES */				
				new Hilos(socketAlCliente, biblioteca);
			}			
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
		
		

	}

}
