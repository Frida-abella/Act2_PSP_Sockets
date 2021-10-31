package servidorLibros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import servidorLibros.Libro;


/* Creamos una biblioteca donde est�n guardados todos los libros del servidor (una clase Biblioteca que contiene la lista de libros)
Esta clase tiene m�todos propios para a�adir libros, mostrar la lista de libros,
buscar los libros por isbn, por t�tulo y por autor: */

public class Biblioteca {

	private List<Libro> listaLibros;
	
	public void Biblioteca () {
		
		listaLibros =  new ArrayList<>();
		
		
		listaLibros.add(new Libro("3454", "ELOY MORENO", "TIERRA", "30"));
		listaLibros.add(new Libro("3455", "YUVAL NOAH", "SAPIENS", "40"));
		listaLibros.add(new Libro("3456", "JKR", "HARRY POTTER Y LA PIEDRA FILOSOFAL", "20"));
		listaLibros.add(new Libro("3457", "JULIO VERNE", "ESCUELA DE ROBINSONES", "20"));
		listaLibros.add(new Libro("3458", "ROSA MONTERO", "AMANTES Y ENEMIGOS", "15"));
		listaLibros.add(new Libro("3459", "ROSA MONTERO", "EL REY TRANSPARENTE", "25"));
		listaLibros.add(new Libro("3460", "JULIO VERNE", "VIAJE AL CENTRO DE LA TIERRA", "30"));
		listaLibros.add(new Libro("3458", "JULIO VERNE", "LA ISLA MISTERIOSA", "17"));
	}
	
	//El m�todo a�adirLibro, pas�ndole un objeto de tipo libro, a�ade ese libro a la biblioteca (listaLibros)
	public void a�adirLibro(Libro libro){
		listaLibros.add(libro);
		System.out.println("El libro " + libro.getTitulo() + " ha sido a�adido a la biblioteca");
	}
	
	
	/* Creamos un m�todo (buscarPorIsbn) al que, pas�ndole el String que contiene el ISBN, recorra la lista de libros
	para encontrar el que coincida con ese n�mero de ISBN
	El return devuelve el objeto libro en cuesti�n */
	
	public Libro buscarPorIsbn (String isbn) {
		Libro libro = null;
		for (Libro l : listaLibros) {
			if (l.getIsbn().equals(isbn));
		}
		return libro;
	}
	
	// Creamos un m�todo al que se le pasa el String del t�tulo, para que busque ese libro dentro de la lista
	// El return devuelve el libro
	public Libro buscarPorTitulo(String titulo){
		Libro libro = null;
		for(Libro l : listaLibros){
			if(l.getTitulo().equals(titulo)){
				libro = l;
			}
		}
		return libro;
	}
	
	// El siguiente m�todo, al pasarle el autor que buscamos(un String), devolver�
	// buscar� en la biblitoeca (listaLibros) y a�adir� cada libro que pertenezca a ese autor a una lista
	// El return devolver� esa nueva lista con todos los libros que pertenezcan al mismo autor (listaLibrosEncontrados):
	public List<Libro> buscarPorAutor(String autor){
		List<Libro> listaLibrosEncontrados = new ArrayList<>();
		
		for(Libro l : listaLibros){
			if(l.getAutor().equals(autor)){
				listaLibrosEncontrados.add(l);
			}
		}
		
		return listaLibrosEncontrados;
	}
	
	// Este m�todo servir�a para obtener la lista completa de los libros dados de alta:
	public List<Libro> getListaLibros(){
		return listaLibros;
	}
}
