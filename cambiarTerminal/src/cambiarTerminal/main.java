package cambiarTerminal;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main extends JFrame {
	private final static String rutaImagenes = "/home/alumno/Descargas/imagenes";
	private final static String rutaBanner = "/home/alumno/Descargas/banner";
	private static final File carpeta = new File(rutaImagenes);
	static List<Integer> numerosUsados;
	static int nuevoNumero = 0;
	static int numeroSeleccionado = 0;
	static Thread th;

	private JButton btnCerrar, btnEjecutar, btnEjecutarBucle;
	private JLabel imagenLabel, imagenLabel2;
	private main mainApp;
	private boolean bucle = true;
	private JPanel imagePanel;;
	private int NumeroManual = 0;
	private ImageIcon imageIcon;
	private ImageIcon imageIcon2;
	private JScrollPane scrollPane;

	public main() {
		super("Titulo");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imageIcon = new ImageIcon(rutaImagenes + "/1.jpg");
		Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
		imagenLabel = new JLabel(new ImageIcon(image));

		add(imagenLabel, BorderLayout.NORTH);
		imagePanel = new JPanel();

		scrollPane = new JScrollPane(imagePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		repintarScrollPanel();

		add(scrollPane, BorderLayout.CENTER);
		pack();
		setVisible(true);

		JPanel panelBotones = new JPanel();
		btnCerrar = new JButton("Cerrar");
		btnEjecutar = new JButton("Ejecutar Aleatorio Uno");
		btnEjecutarBucle = new JButton("Ejecutar Aleatorio Bucle");
		btnCerrar.addActionListener(new OyenteBoton());
		btnEjecutar.addActionListener(new OyenteBoton());
		btnEjecutarBucle.addActionListener(new OyenteBoton());
		panelBotones.add(btnCerrar);
		panelBotones.add(btnEjecutar);
		panelBotones.add(btnEjecutarBucle);
		add(panelBotones, BorderLayout.SOUTH);

		setSize(800, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

		setVisible(true);

	}

	public static void main(String[] args) {

		main aplicacion = new main();

	}

	class OyenteBoton implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			String s = (String) ae.getActionCommand();
			if (s.equals("Cerrar")) {
				System.exit(0);
			} else if (s.equals("Ejecutar Aleatorio Uno")) {

				cambImagen();
			} else if (s.equals("Ejecutar Aleatorio Bucle")) {
				if (!bucle) {

				} else {

				}

			}
		}
	}

	static class secundario implements Runnable {
		@Override
		public void run() {
			renombrarArchivo(rutaImagenes, "1.jpg", nuevoNumero + ".jpg");
			renombrarArchivo(rutaImagenes, numeroSeleccionado + ".jpg", "1.jpg");

		}
	}

	private void repintarScrollPanel() {
		
		imagePanel.removeAll();
		
		String[] archivos = carpeta.list();
		if (archivos != null) {
			for (String archivo : archivos) {
				if (!archivo.equals("1.jpg")) {

					imageIcon2 = new ImageIcon(rutaImagenes + "/" + archivo);
					//System.out.println(rutaImagenes + "/" + archivo);
					Image image2 = imageIcon2.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);

					imagenLabel2 = new JLabel(new ImageIcon(image2));
					imagePanel.add(imagenLabel2);
					// Si tocas una imagem coje el nombre de la imagen y la comvierte en la 1
					imagenLabel2.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							NumeroManual = Integer.parseInt(archivo.split("\\.")[0]);
							cambImagen();
							
							
							
							repintarScrollPanel();
						}
					
					});
				}
			}
		}

		
	}

	private void cambImagen() {

		th = new Thread(new secundario());

		numerosUsados = obtenerNumerosUsados(rutaImagenes, ".jpg");

		if (numerosUsados.isEmpty()) {
			System.out.println("No hay archivos suficientes para renombrar.");

		}

		nuevoNumero = generarNuevoNumero(numerosUsados);
		numeroSeleccionado = seleccionarNumeroAleatorio(numerosUsados, NumeroManual);

		// Renombrar archivos de banner y imagen
		th.start();
		renombrarArchivo(rutaBanner, "1.txt", nuevoNumero + ".txt");

		// Si quieres una cambia numeroSeleccionado
		renombrarArchivo(rutaBanner, numeroSeleccionado + ".txt", "1.txt");
		NumeroManual = 0;
		ImageIcon imageIcon = new ImageIcon(rutaImagenes + "/1.jpg");
		imageIcon.getImage().flush(); // Limpia el caché
		Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
		imagenLabel.setIcon(new ImageIcon(image));
		imagenLabel.revalidate();
		imagenLabel.repaint();
		repintarScrollPanel();

	}

	private static List<Integer> obtenerNumerosUsados(String ruta, String extension) {
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

	private static int generarNuevoNumero(List<Integer> numerosUsados) {
		int nuevoNumero;
		do {
			nuevoNumero = (int) (Math.random() * 99) + 1;
		} while (numerosUsados.contains(nuevoNumero));
		return nuevoNumero;
	}

	private static int seleccionarNumeroAleatorio(List<Integer> numeros, int NumeroManual) {
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

	private static void renombrarArchivo(String ruta, String nombreViejo, String nombreNuevo) {
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
