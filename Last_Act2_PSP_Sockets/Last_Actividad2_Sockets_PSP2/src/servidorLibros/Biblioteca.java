package servidorLibros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import servidorLibros.Libro;


/* Creamos una biblioteca donde están guardados todos los libros del servidor (una clase Biblioteca que contiene la lista de libros)
Esta clase tiene métodos propios para añadir libros, mostrar la lista de libros,
buscar los libros por isbn, por título y por autor: */

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
	
	//El método añadirLibro, pasándole un objeto de tipo libro, añade ese libro a la biblioteca (listaLibros)
	public void añadirLibro(Libro libro){
		listaLibros.add(libro);
		System.out.println("El libro " + libro.getTitulo() + " ha sido añadido a la biblioteca");
	}
	
	
	/* Creamos un método (buscarPorIsbn) al que, pasándole el String que contiene el ISBN, recorra la lista de libros
	para encontrar el que coincida con ese número de ISBN
	El return devuelve el objeto libro en cuestión */
	
	public Libro buscarPorIsbn (String isbn) {
		Libro libro = null;
		for (Libro l : listaLibros) {
			if (l.getIsbn().equals(isbn));
		}
		return libro;
	}
	
	// Creamos un método al que se le pasa el String del título, para que busque ese libro dentro de la lista
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
	
	// El siguiente método, al pasarle el autor que buscamos(un String), devolverá
	// buscará en la biblitoeca (listaLibros) y añadirá cada libro que pertenezca a ese autor a una lista
	// El return devolverá esa nueva lista con todos los libros que pertenezcan al mismo autor (listaLibrosEncontrados):
	public List<Libro> buscarPorAutor(String autor){
		List<Libro> listaLibrosEncontrados = new ArrayList<>();
		
		for(Libro l : listaLibros){
			if(l.getAutor().equals(autor)){
				listaLibrosEncontrados.add(l);
			}
		}
		
		return listaLibrosEncontrados;
	}
	
	// Este método serviría para obtener la lista completa de los libros dados de alta:
	public List<Libro> getListaLibros(){
		return listaLibros;
	}
}
