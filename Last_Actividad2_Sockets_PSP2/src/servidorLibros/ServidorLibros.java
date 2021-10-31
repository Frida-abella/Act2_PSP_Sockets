package servidorLibros;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorLibros {
	
	// Declaramos las variables que contienen la dirección del Server, las mismas que le dimos al cliente para conectarse:
	final static int PUERTO = 5001;
	final static String IP_SERVER = "localhost";	
	
	public static void main(String[] args) {
			
		System.out.println(" ----- APLICACIÓN DEL SERVIDOR ----- ");
		
		Biblioteca bibliotecaServer = new Biblioteca();
		
		// Declaramos el socket de servidor que permitirá conectar con el Cliente y recibir sus peticiones:
		ServerSocket socketServidorLibros = null;
		
		// Declaramos el socket que hará de conexión con el cliente, para intercambiar información
		Socket conexionAlCliente = null;
		
		try {
			// Creamos el objeto serverSocket de nuestro servidor:
			socketServidorLibros = new ServerSocket();
			
			// Le asignamos su dirección:
			InetSocketAddress direccionServer = new InetSocketAddress(IP_SERVER, PUERTO);
			
			// Ponemos al socket en "escucha", para que reciba peticiones del cliente por la dirección (IP y puerto)
			// que hemos especificado más arriba (direccionServer), la misma que se le ha dado al cliente:
			socketServidorLibros.bind(direccionServer);
			
			// Confirmamos que se ha conectado al IP y puerto especificados. Cuando se conecte, sacará por pantalla lo siguiente:
			System.out.println("El servidor se ha creado por el puerto: " + PUERTO + " y la IP: " + IP_SERVER);
			
			// Creamos un while cuya condición sea true, es decir, que siempre se cumple, para que el servidor repita constatemente el bucle,
			// es decir, que esté siempre escuchando para aceptar permanentemente conexiones,
			//las cuales "enviará" para ser gestionadas a un hilo secundario, liberando al hilo main o principal:
			
			while (true) {
				System.out.println("Esperando a que se conecte un cliente y haga una petición...");
				// Inicializamos el socket de conexión con el cliente cuando se acepta la conexión con el socketServer:
				conexionAlCliente = socketServidorLibros.accept();
				
				// Una vez haya sido aceptada la petición del cliente, lo confirmaremos con mensaje por pantalla:
				System.out.println("Comunicación entrante. Petición aceptada.");
				
				// Reenviamos a un hilo secundario esa conexión/petición para que ejecute las acciones específicas necesarias
				// en paralelo al hilo main, que seguirá activo escuchando y aceptando peticiones, para reenviarlas a los hilos:
				new Hilo (conexionAlCliente, bibliotecaServer);
				
			}
			
		} catch (IOException e) {
			System.err.println("main -> " + e.getMessage());
		} finally {   // Cerramos la conexión del socket y capturamos las excepciones
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
