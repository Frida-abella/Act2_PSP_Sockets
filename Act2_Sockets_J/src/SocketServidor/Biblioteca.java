package SocketServidor;

import java.util.ArrayList;
import java.util.List;
import SocketServidor.Libro;



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
	
	public void cargarBiblioteca() {
		
		listaLibros.add(l1);
		listaLibros.add(l2);
		listaLibros.add(l3);
		listaLibros.add(l4);
		listaLibros.add(l5);	
		listaLibros.add(l6);
		listaLibros.add(l7);
		
	}
	
	public Libro buscarTitulo(String titulo) {
		Libro resultado = null;
		for (Libro l : listaLibros) {
			if (l.getTitulo().equals(titulo)) {
				resultado =  l;			
			}
		}
		return resultado;
	}
	
	
	public Libro buscarISBN(String isbn) {
		Libro resultado = null;
		for (Libro l : listaLibros) {
			if (l.getIsbn().equals(isbn)) {
				resultado = l;	
				
			}
							
		}
		return resultado;
	}
	
	public List<Libro> buscarAutor(String autor) {
		List<Libro> resultado = new ArrayList<>();
		
		for (Libro l : listaLibros) {
			if (l.getAutor().equals(autor)) {
				resultado.add(l);					
			}
		}
		return resultado;		
		
	}
	
	public void añadirLibro (Libro libro) {
		
		
		listaLibros.add(libro);
		
		System.out.println("Nuevo libro añadido: " + libro.getTitulo());
		
		System.out.println(listaLibros);

	}
		

}
