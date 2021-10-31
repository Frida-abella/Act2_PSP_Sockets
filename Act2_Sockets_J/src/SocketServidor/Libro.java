package SocketServidor;

// Creamos una clase LIBRO que sirva para crear objetos de este tipo, con los diferentes datos 

public class Libro {
	
	// Declaramos los atributos que componen el objeto, que permitirán al usuario buscar al objeto o añadir uno nuevo 
	private String isbn;
	private String autor;
	private String titulo;
	private String precio;
	
	// Creamos el método constructor del objeto Libro, al que se le pasarán como parámetros los Strings con el isbn, autor, titulo y precio,
	// Para que pasen a ser los atributos de ese nuevo objeto:
	public Libro(String isbn, String autor, String titulo, String precio) {
		super();
		this.isbn = isbn;
		this.autor = autor;
		this.titulo = titulo;
		this.precio = precio;
		
	}

	public Libro() {
		
	}
	
	// Creamos los Getter & Setter
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	// Sobreescribimos el método toString para que nos imprima el objeto libro con todos sus atributos y su valor
	@Override
	public String toString() {
		return "libro [isbn=" + isbn + ", autor=" + autor + ", titulo=" + titulo + ", precio=" + precio + "]";
	}
	
}	
