import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class hiloDescargar implements Runnable {
	private Socket s;
	private String nombreArchivo;

	public hiloDescargar(Socket s, String nombreArchivo) {
		this.s = s;
		this.nombreArchivo = nombreArchivo;
	}

	@Override
	public void run() {
		FileInputStream fis = null;
		DataOutputStream dos = null;
		try {
			
			dos = new DataOutputStream(s.getOutputStream());
			File f = new File(nombreArchivo);
			fis = new FileInputStream(f);
			System.out.println(f.getAbsolutePath());
			transferirArchivo.transfer(fis, s.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
