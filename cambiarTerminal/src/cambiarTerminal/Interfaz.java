package cambiarTerminal;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class Interfaz extends JFrame{
	

	private JButton btnCerrar, btnEjecutar, btnEjecutarBucle;
	protected static JLabel imagenLabel;
	protected static JLabel imagenLabel2;
	
	protected static JPanel imagePanel;;
	
	private ImageIcon imageIcon;
	static ImageIcon imageIcon2;
	private JScrollPane scrollPane;
    
	public Interfaz() {
	super("Visor de Imágenes");

	setLayout(new BorderLayout(10, 10));

	// Panel principal con BorderLayout
	JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
	mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	mainPanel.setBackground(new Color(240, 240, 245));

	// Panel para la imagen grande (a la izquierda)
	imagePanel = new JPanel(new BorderLayout());
	imageIcon = new ImageIcon(main.rutaImagenes + "/1" + main.terminacionImagen);
	Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
	imagenLabel = new JLabel(new ImageIcon(image));
	imagenLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
	imagePanel.add(imagenLabel, BorderLayout.CENTER);
	mainPanel.add(imagePanel, BorderLayout.CENTER);

	// Panel para el scrollPane (a la derecha)
	JPanel rightPanel = new JPanel(new BorderLayout());
	rightPanel.setPreferredSize(new Dimension(250, 0));
	imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	imagePanel.setBackground(new Color(250, 250, 255));
	imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
	scrollPane = new JScrollPane(imagePanel);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Scrollbar vertical
	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Sin scrollbar horizontal
	scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Para la velocidad de la ruleta del raton
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

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(800, 600);
	setLocationRelativeTo(null);
	setVisible(true);
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

				Funciones.cambTextoImagen();
			} else if (s.equals("Ejecutar Aleatorio Bucle")) {
		                // Detener el bucle
		            	 if (!main.bucle) {
		                     // Iniciar un nuevo hilo
		            		 main.bucle = true;
		                     Hilos.bucleThread = new Thread(new Hilos.Infinito());
		                     Hilos.bucleThread.start(); // Inicia el hilo
		                     btnEjecutar.setEnabled(false);
		                 } else {
		                     // Pausar el bucle
		                     main.bucle = false; // Cambia la variable para detener el bucle
		                     synchronized (main.lock) {
		                    	 main.lock.notify(); // Notifica al hilo que se debe pausar
		                     }
		                     btnEjecutar.setEnabled(true);
		                 }
		}
	}
	}
	public void showWindow() {
	    setVisible(true); // Esto hace que la ventana sea visible
	}
}
