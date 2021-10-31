package servidorLibros;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorLibros {
	
	// Declaramos las variables que contienen la direcci�n del Server, las mismas que le dimos al cliente para conectarse:
	final static int PUERTO = 5001;
	final static String IP_SERVER = "localhost";	
	
	public static void main(String[] args) {
			
		System.out.println(" ----- APLICACI�N DEL SERVIDOR ----- ");
		
		Biblioteca bibliotecaServer = new Biblioteca();
		
		// Declaramos el socket de servidor que permitir� conectar con el Cliente y recibir sus peticiones:
		ServerSocket socketServidorLibros = null;
		
		// Declaramos el socket que har� de conexi�n con el cliente, para intercambiar informaci�n
		Socket conexionAlCliente = null;
		
		try {
			// Creamos el objeto serverSocket de nuestro servidor:
			socketServidorLibros = new ServerSocket();
			
			// Le asignamos su direcci�n:
			InetSocketAddress direccionServer = new InetSocketAddress(IP_SERVER, PUERTO);
			
			// Ponemos al socket en "escucha", para que reciba peticiones del cliente por la direcci�n (IP y puerto)
			// que hemos especificado m�s arriba (direccionServer), la misma que se le ha dado al cliente:
			socketServidorLibros.bind(direccionServer);
			
			// Confirmamos que se ha conectado al IP y puerto especificados. Cuando se conecte, sacar� por pantalla lo siguiente:
			System.out.println("El servidor se ha creado por el puerto: " + PUERTO + " y la IP: " + IP_SERVER);
			
			// Creamos un while cuya condici�n sea true, es decir, que siempre se cumple, para que el servidor repita constatemente el bucle,
			// es decir, que est� siempre escuchando para aceptar permanentemente conexiones,
			//las cuales "enviar�" para ser gestionadas a un hilo secundario, liberando al hilo main o principal:
			
			while (true) {
				System.out.println("Esperando a que se conecte un cliente y haga una petici�n...");
				// Inicializamos el socket de conexi�n con el cliente cuando se acepta la conexi�n con el socketServer:
				conexionAlCliente = socketServidorLibros.accept();
				
				// Una vez haya sido aceptada la petici�n del cliente, lo confirmaremos con mensaje por pantalla:
				System.out.println("Comunicaci�n entrante. Petici�n aceptada.");
				
				// Reenviamos a un hilo secundario esa conexi�n/petici�n para que ejecute las acciones espec�ficas necesarias
				// en paralelo al hilo main, que seguir� activo escuchando y aceptando peticiones, para reenviarlas a los hilos:
				new Hilo (conexionAlCliente, bibliotecaServer);
				
			}
			
		} catch (IOException e) {
			System.err.println("main -> " + e.getMessage());
		} finally {   // Cerramos la conexi�n del socket y capturamos las excepciones
			if(socketServidorLibros != null) {
				try { 
					socketServidorLibros.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
				
	}

}
