package cambiarTerminal;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RepintarInterfaz extends main{
	
	static void repintarImagenCentral() {
		ImageIcon imageIcon = new ImageIcon(rutaImagenes + "/1"+terminacionImagen);
		imageIcon.getImage().flush(); // Limpia el caché
		Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
		imagenLabel.setIcon(new ImageIcon(image));
		imagenLabel.revalidate();
		imagenLabel.repaint();
		
	}
	static void repintarScrollPanel() {
		
		imagePanel.removeAll();
		
		String[] archivos = carpeta.list();
		if (archivos != null) {
			for (String archivo : archivos) {
				if (!archivo.equals("1"+terminacionImagen)) {

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
							cambTextoImagen();			
							repintarScrollPanel();
						}
					
					});
				}
			}
		}

		
	}
}
