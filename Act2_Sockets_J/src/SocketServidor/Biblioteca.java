package SocketServidor;

// Importamos las diferentes utilidades que usaremos: arrayList, List
import java.util.ArrayList;
import java.util.List;
import SocketServidor.Libro;


// Creamos la clase biblioteca, que contendrá un Lista de libros predeterminados y una serie de métodos para manejar esa lista

public class Biblioteca {
	
	//LISTA DE LIBROS PREDETERMINADA	
	//SE GUARDAN EN UN ARRAY LIST PARA CONSULTA
	
	Libro l1 = new Libro("3454", "ELOY MORENO", "TIERRA", "30");
	Libro l2 = new Libro("3455", "YUVAL NOAH", "SAPIENS", "40");
	Libro l3 = new Libro("3456", "JKR", "HARRY POTTER Y LA PIEDRA FILOSOFAL", "20");
	Libro l4 = new Libro("3457", "JULIO VERNE", "ESCUELA DE ROBINSONES", "20");
	Libro l5 = new Libro("3458", "JKR", "HARRY POTTER Y LA CAMARA SECRETA", "15");	
	Libro l6 = new Libro("3459", "JULIO VERNE", "VIAJE AL CENTRO DE LA TIERRA", "30");	
	Libro l7 = new Libro("3460", "JULIO VERNE", "LA ISLA MISTERIOSA", "17");
	
	List<Libro> listaLibros = new ArrayList<Libro>();
	
	// Con el método cargarBiblioteca() añadimos los libros que hemos creado previamente a la lista.
	public void cargarBiblioteca() {
		
		listaLibros.add(l1);
		listaLibros.add(l2);
		listaLibros.add(l3);
		listaLibros.add(l4);
		listaLibros.add(l5);	
		listaLibros.add(l6);
		listaLibros.add(l7);
		
	}
	
	// Este método sirve para buscar un objeto libro al pasarle como parámetro su título. Recorrerá la lista de libros, elemento a elemento, 
	// y buscará aquel elemento (Libro l) cuyo título sea igual al que se ha pasado como parámetro al método
	// Devuelve como resultado el objeto libro en cuestión que se haya encontrado
	public Libro buscarTitulo(String titulo) {
		Libro resultado = null;
		for (Libro l : listaLibros) {
			if (l.getTitulo().equals(titulo)) {
				resultado =  l;			
			}
		}
		return resultado;
	}
	
	// Este método sirve para buscar libros por su ISBN. Su funcionamiento es igual al de buscarTitulo
	public Libro buscarISBN(String isbn) {
		Libro resultado = null;
		for (Libro l : listaLibros) {
			if (l.getIsbn().equals(isbn)) {
				resultado = l;	
				
			}
							
		}
		return resultado;
	}
	
	// Este método busca dentro de la lista de libros de la biblioteca a todos aquellos que tengan como autor al mismo nombre o string que se le ha pasado al método como parámetro.
	// Devuelve otra Lista de libros con todos aquellos libros que tengan a ese autor 
	public List<Libro> buscarAutor(String autor) {
		List<Libro> resultado = new ArrayList<>();
		
		for (Libro l : listaLibros) {
			if (l.getAutor().equals(autor)) {
				resultado.add(l);					
			}
		}
		return resultado;		
		
	}
	
	// El método añadirLibro, recibe como parámetro un objeto de tipo libro y lo añade a la lista de libros mediante .add
	// Después informa por consola del título del libro que se ha añadido y muestra también la lista entera de libros a la que se ha añadido para comprobar que figura en ella
	public void añadirLibro (Libro libro) {
		
		
		listaLibros.add(libro);
		
		System.out.println("Nuevo libro añadido: " + libro.getTitulo());
		
		System.out.println(listaLibros);

	}
		

}
