package SocketServidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


// Creamos el hilo implementando la interfaz Runnable. 
public class Hilos implements Runnable{
	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;
	private Biblioteca biblioteca;	
	
	// Constructor de la clase Hilos. Le pasaremos como método, para crear un hilo, un objeto Socket (que nos conecta con el cliente) y una biblioteca (inicializada en el servidor),
	// que contiene todos los libros y funcionalidades(métodos) con los que el hilo tendrá que trabajar para poder dar respuesta al cliente.
	
	public Hilos(Socket socketAlCliente, Biblioteca biblioteca) {
		numCliente++;
		
	// Inicializamos un hilo u objeto Thread cada vez que creamos un objeto "Hilos" y le asignamos un número de cliente (irá aumentando de 1 en 1 cada vez que se acepte una petición de 1 cliente en el servidor)
		hilo = new Thread(this, "Cliente_"+numCliente);
		this.socketAlCliente = socketAlCliente;
		this.biblioteca = biblioteca;
		
		//Arrancamos el hilo con el método start() de la clase Thread, que llamará al método run()
		hilo.start();
	}
	

	// Una vez se haya arrancado el hilo, éste llamará al método run() que debemos sobreescribir para que realice las tareas que deseemos.
	// Sobreescribimos el método, que hará las funciones propias para dar respuesta al cliente
	@Override
	public void run() {
		System.out.println("Estableciendo comunicacion con " + hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		// Creamos el array de Strings donde se almacenarán los diferentes datos que nos lleguen del cliente, una vez los hayamos separado
		String [] datosLibro = null;
		// Creamos la variable que almacena el número de opción elegida, para que el hilo actúe de un modo u otro en función de esto
		String opcionEscogida = null;
		
		try {
			//Inizalicamos la salida del servidor al cliente
			salida = new PrintStream(socketAlCliente.getOutputStream());
			// Inicializamos la entrada del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String texto = null;
			//Ponemos el boolean continuar a true para que la condición del while se cumpla siempre y se ejecute constantemente
			boolean continuar = true;
			
			
			
			//Procesaremos entradas hasta que el texto del cliente sea FIN
			while (continuar) {
				
				// Leemos lo que nos llega desde el cliente a través del canal de entrada y con BufferedReader
				texto = entradaBuffer.readLine();				
				
				// Dado que del cliente nos llegará un String con varios datos separados por guión (-), debemos separar cada dato cortándolos por ese guión
				// con el método split(). Guardamos cada uno de esos datos o elementos en una posición de un array de Strings
				datosLibro = texto.split("-");	
				
				// El número de opción escogida siempre será el primer elemento de ese array de Strings que hemos inicializado (datosLibro),
				// dado que es el primer dato que compone el String enviado desde el cliente.
				opcionEscogida = datosLibro[0];
				
					
					String isbnLibro;
					String autorLibro;
					String tituloLibro;
					String precioLibro;
					
					// En función del número que contenga la variable opcionEscogida, se ejecutarán unas u otras acciones
					switch (opcionEscogida) {
					case "1":
						// Si la opción escogida es 1, hay que buscar un libro por isbn. 
						// El segundo elemento del Array de Strings será el isbn que el cliente introdujo por pantalla
						// Usamos ese dato (isbnLibro) para buscar el libro, pasándoselo al método .buscar(ISBN) del objeto
						// biblioteca que hemos recibido del servidor
						isbnLibro = datosLibro[1];
						salida.println(biblioteca.buscarISBN(isbnLibro));
					break;
					case "2": 
						// Si la opción escogida es 2, se buscará por título, llamando al método correspondiente del objeto biblioteca
						tituloLibro = datosLibro [1];
						salida.println(biblioteca.buscarTitulo(tituloLibro));
					break;
					case "3":
						// Si la opción escogida es 3, se buscarán libros por autor. Mismo funcionamiento que en los case de arriba.
						autorLibro = datosLibro [1];
						salida.println(biblioteca.buscarAutor(autorLibro));
					break;
					case "4":
						// La opción 4 se corresponde con crear un nuevo libro. Para eso, obtenemos los diferentes datos necesarios 
						// para crear el libro (isbn, autor, titulo, precio), que habremos recibido en un string y separado y colocado
						// consecutivamente en el array datosLibro[]. Accedemos a estos elementos gracias a sus posiciones y asignamos este valor
						// a las variables String con las que crearemos el nuevo libro	
						isbnLibro = datosLibro[1];
						autorLibro = datosLibro[2];
						tituloLibro = datosLibro[3];
						precioLibro = datosLibro[4];
						
						// Creamos un nuevo objeto libro con los datos que hemos extraído del String recibido del cliente
						Libro nuevoLibro = new Libro (isbnLibro, tituloLibro, autorLibro, precioLibro);
						
						// Damos de alta el libro en la biblioteca con el método altaLibro(), pasándole como argumento
						// el nuevo libro que hemos creado con los datos que nos ha pasado el cliente
						biblioteca.añadirLibro(nuevoLibro);
						
						// Informamos al cliente de qué libro hemos añadido a la biblioteca:
						salida.println("El libro que se ha añadido es el siguiente:" + nuevoLibro);
						break;					
						
						// En caso de que el cliente haya seleccionado la opción FIN, se mandará un mensaje de despedida
						// y se cambiará el boolean continuar a false para que la condición del while deje de cumplirse y salga del bucle.	
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
	

