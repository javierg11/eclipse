package cambiarTerminal;

import java.io.File;


import java.util.List;

public class main  {
	protected final static String rutaImagenes = "/home/alumno/Descargas/imagenes";
	final static String rutaBanner = "/home/alumno/Descargas/banner";
	protected final static String terminacionImagen = ".jpg";
	protected static final File carpeta = new File(main.rutaImagenes);
	protected static List<Integer> numerosUsados;
	protected static int nuevoNumero = 0;
	protected static int numeroSeleccionado = 0;
	protected static Thread th, bucleThread;
	protected final static Object lock = new Object(); // Objeto para sincronización
	protected static int NumeroManual = 0;
	protected static boolean bucle = false;

	public main() {
		
	}

	public static void main(String[] args) {

		main aplicacion = new main();
		Interfaz viewer = new Interfaz();
	    viewer.showWindow();

	}

	

	
	

}
