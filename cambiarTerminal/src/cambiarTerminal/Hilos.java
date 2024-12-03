package cambiarTerminal;

import javax.swing.SwingUtilities;

public class Hilos extends main{
	static class secundario implements Runnable {
		@Override
		public void run() {
			// Este hilo cambia el txt 1.txt por numAleatorio.txt y
			// un numeroAleatorio pasa a la posicion 1.txt
			
			
			Funciones.renombrarArchivo(rutaBanner, "1.txt", nuevoNumero + ".txt");

			
			Funciones.renombrarArchivo(rutaBanner, numeroSeleccionado + ".txt", "1.txt");

		}
	}

	static class Infinito implements Runnable {
		@Override
		public void run() {

			try {
				 while (true) { // Bucle infinito hasta que se interrumpa
	                    synchronized (lock) {
	                        while (!bucle) { // Espera mientras 'bucle' sea falso
	                            lock.wait(); // El hilo se pausa aquí
	                        }
	                    }

	                    SwingUtilities.invokeLater(() -> Funciones.cambTextoImagen()); // Llama al método en el EDT
	                    Thread.sleep(2000); // Espera antes de la siguiente ejecución
	                }
            } catch (InterruptedException e) {
                // Manejo de la interrupción
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
            }

		}
	}

}
