import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloTransferencia implements Runnable {

	private Socket s;
	private String nombreArchivo;
	

	public HiloTransferencia(Socket s, String nombreArchivo) {
		this.s = s;
		this.nombreArchivo=nombreArchivo;
	}

	@Override
	public void run() {
		FileOutputStream fos = null;
		

		try {

			

			File f = new File(nombreArchivo);
			if (f.exists()) {
				f.delete();
			}
			fos = new FileOutputStream(f);
			transferirArchivo.transfer(s.getInputStream(), fos);

			s.close();
			s = null;

		}

		catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
