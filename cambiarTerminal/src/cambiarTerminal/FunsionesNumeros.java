package cambiarTerminal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FunsionesNumeros {
	
	//Mira en la carpeta los archivos que hay (todos son unnumero.jpg, ej:11.jpg)
	static List<Integer> obtenerNumerosUsados(String ruta, String extension) {
		File carpeta = new File(ruta);
		File[] archivos = carpeta.listFiles();
		List<Integer> numerosUsados = new ArrayList<>();

		if (archivos != null) {
			for (File archivo : archivos) {
				String nombre = archivo.getName();
				if (nombre.matches("\\d+" + extension)) {
					int numero = Integer.parseInt(nombre.split("\\.")[0]);
					numerosUsados.add(numero);
				}
			}
		}
		return numerosUsados;
	}

	static int generarNuevoNumero(List<Integer> numerosUsados) {
		int nuevoNumero;
		do {
			nuevoNumero = (int) (Math.random() * 99) + 1;
		} while (numerosUsados.contains(nuevoNumero));
		return nuevoNumero;
	}

	static int seleccionarNumeroAleatorio(List<Integer> numeros, int NumeroManual) {
		if (numeros.size() == 1 && numeros.get(0) == 1) {
			return 1; // Si solo hay un archivo y es el 1, devolvemos 1
		}
		int indice;
		int seleccionado;
		do {
			indice = (int) (Math.random() * numeros.size());
			seleccionado = numeros.get(indice);
		} while (seleccionado == 1);
		if (NumeroManual == 0)
			return seleccionado;
		else {

			return NumeroManual;

		}
	}

	static void renombrarArchivo(String ruta, String nombreViejo, String nombreNuevo) {
		File archivoViejo = new File(ruta, nombreViejo);
		File archivoNuevo = new File(ruta, nombreNuevo);

		if (archivoViejo.exists()) {
			if (archivoViejo.renameTo(archivoNuevo)) {
				System.out.println("Archivo renombrado exitosamente: " + nombreViejo + " -> " + nombreNuevo);
			} else {
				System.out.println("No se pudo renombrar el archivo: " + nombreViejo);
			}
		} else {
			System.out.println("El archivo no existe: " + nombreViejo);
		}
	}
}
