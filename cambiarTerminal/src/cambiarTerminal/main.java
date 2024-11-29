package cambiarTerminal;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main extends JFrame {
	protected final static String rutaImagenes = "/home/alumno/Descargas/imagenes";
	private final static String rutaBanner = "/home/alumno/Descargas/banner";
	protected final static String terminacionImagen=".jpg";
	protected static final File carpeta = new File(rutaImagenes);
	static List<Integer> numerosUsados;
	static int nuevoNumero = 0;
	static int numeroSeleccionado = 0;
	static Thread th;

	private JButton btnCerrar, btnEjecutar, btnEjecutarBucle;
	protected static JLabel imagenLabel;
	protected static JLabel imagenLabel2;
	private main mainApp;
	private boolean bucle = true;
	protected static JPanel imagePanel;;
	static int NumeroManual = 0;
	private ImageIcon imageIcon;
	static ImageIcon imageIcon2;
	private JScrollPane scrollPane;

	public main() {
		super("Visor de Imágenes");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		// Panel principal con BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainPanel.setBackground(new Color(240, 240, 245));

		// Panel para la imagen grande (a la izquierda)
		imagePanel = new JPanel(new BorderLayout());
		imageIcon = new ImageIcon(rutaImagenes + "/1" + terminacionImagen);
		Image image = imageIcon.getImage().getScaledInstance(500,400, Image.SCALE_SMOOTH);
		imagenLabel = new JLabel(new ImageIcon(image));
		imagenLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
		imagePanel.add(imagenLabel, BorderLayout.CENTER);
		mainPanel.add(imagePanel, BorderLayout.CENTER);

		// Panel para el scrollPane (a la derecha)
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(250, 0));
		imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		imagePanel.setBackground(new Color(250, 250, 255));
		scrollPane = new JScrollPane(imagePanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Scrollbar vertical
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Sin scrollbar horizontal
		RepintarInterfaz.repintarScrollPanel();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		rightPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		

		// Panel de botones (abajo)
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBotones.setOpaque(false);
		btnCerrar = createStyledButton("Cerrar", new Color(211, 47, 47));
		btnEjecutar = createStyledButton("Ejecutar Aleatorio Uno", new Color(0, 120, 215));
		btnEjecutarBucle = createStyledButton("Ejecutar Aleatorio Bucle", new Color(0, 150, 136));
		panelBotones.add(btnCerrar);
		panelBotones.add(btnEjecutar);
		panelBotones.add(btnEjecutarBucle);
		mainPanel.add(panelBotones, BorderLayout.SOUTH);

		add(mainPanel);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public static void main(String[] args) {

		main aplicacion = new main();

	}
	private JButton createStyledButton(String text, Color color) {
	    JButton button = new JButton(text);
	    button.setBackground(color);
	    button.setForeground(Color.WHITE);
	    button.setFocusPainted(false);
	    button.setFont(new Font("Arial", Font.BOLD, 14));
	    button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
	    button.addMouseListener(new MouseAdapter() {
	        public void mouseEntered(MouseEvent evt) {
	            button.setBackground(color.brighter());
	        }
	        public void mouseExited(MouseEvent evt) {
	            button.setBackground(color);
	        }
	    });
	    button.addActionListener(new OyenteBoton());
	    return button;
	}
	class OyenteBoton implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			String s = (String) ae.getActionCommand();
			if (s.equals("Cerrar")) {
				System.exit(0);
			} else if (s.equals("Ejecutar Aleatorio Uno")) {

				cambTextoImagen();
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
			//Este hilo cambia la imagen 1.jpg por numAleatorio.jpg y
			//un numeroAleatorio pasa a la posicion 1.jpg
			FunsionesNumeros.renombrarArchivo(rutaImagenes, "1"+terminacionImagen, nuevoNumero + ""+terminacionImagen);
			FunsionesNumeros.renombrarArchivo(rutaImagenes, numeroSeleccionado + ""+terminacionImagen, "1"+terminacionImagen);

		}
	}

	

	static void cambTextoImagen() {

		th = new Thread(new secundario());

		numerosUsados = FunsionesNumeros.obtenerNumerosUsados(rutaImagenes, terminacionImagen);

		if (numerosUsados.isEmpty()) {
			System.out.println("No hay archivos suficientes para renombrar.");

		}

		nuevoNumero = FunsionesNumeros.generarNuevoNumero(numerosUsados);
		numeroSeleccionado = FunsionesNumeros.seleccionarNumeroAleatorio(numerosUsados, NumeroManual);

		// Renombrar archivos de banner y imagen
		th.start();
		FunsionesNumeros.renombrarArchivo(rutaBanner, "1.txt", nuevoNumero + ".txt");

		// Si quieres una cambia numeroSeleccionado
		FunsionesNumeros.renombrarArchivo(rutaBanner, numeroSeleccionado + ".txt", "1.txt");
		NumeroManual = 0;
		
		
		//Repintar la interfaz despues de los cambios en los nombres de la imagenes
		RepintarInterfaz.repintarImagenCentral();
		RepintarInterfaz.repintarScrollPanel();

	}

	
}
