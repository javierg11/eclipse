package cambiarTerminal;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RepintarInterfaz extends main {
	
	static void repintarImagenCentral() {
		ImageIcon imageIcon = new ImageIcon(rutaImagenes + "/1"+terminacionImagen);
		imageIcon.getImage().flush(); // Limpia el caché
		Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
		Interfaz.imagenLabel.setIcon(new ImageIcon(image));
		Interfaz.imagenLabel.revalidate();
		Interfaz.imagenLabel.repaint();
		
	}

	
	static void repintarScrollPanel() {
		
		Interfaz.imagePanel.removeAll();
		
		String[] archivos = carpeta.list();
		if (archivos != null) {
			for (String archivo : archivos) {
				if (!archivo.equals("1"+terminacionImagen)) {

					Interfaz.imageIcon2 = new ImageIcon(rutaImagenes + "/" + archivo);
					//System.out.println(rutaImagenes + "/" + archivo);
					Image image2 = Interfaz.imageIcon2.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
					Interfaz.imagenLabel2 = new JLabel(new ImageIcon(image2));
					Interfaz.imagenLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 50, 50));
				    Interfaz.imagePanel.add(Interfaz.imagenLabel2);
					
					// Si tocas una imagen lee el nombre de la imagen y la comvierte en la 1
					Interfaz.imagenLabel2.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							NumeroManual = Integer.parseInt(archivo.split("\\.")[0]);
							Funciones.cambTextoImagen();			
							repintarScrollPanel();
						}
					
					});
				}
			}
		}

		
	}
}
