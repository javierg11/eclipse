import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

public class Server {

	public static void main(String[] args) {
		Socket s = null;
		ServerSocket ss = null;
		String respuesta = "";
		DataOutputStream dos = null;
		DataInputStream dis = null;

		try {
			ss = new ServerSocket(3010);

			s = ss.accept();

			dis = new DataInputStream(s.getInputStream());

			String men = dis.readUTF();
			men = men.toLowerCase().trim();

			String nombreArchivo = dis.readUTF();

			File file = new File(nombreArchivo);
			String nombre = "servidor/" + file.getName();
			String cliente = "cliente/" + file.getName();
			File archivoServidor = new File(nombre);
			File archivoCliente = new File(cliente);
			System.out.println(archivoServidor.getAbsolutePath());

			switch (men) {
			case "up":
				if (!archivoServidor.exists()) {
					respuesta = "ok";
					Thread thTransferir = new Thread(new HiloTransferencia(s,nombre));
					thTransferir.start();
				}else if(archivoServidor.exists())
					respuesta = "AlreadyExists";
				else
					respuesta = "NotFound";

				break;
			case "down":
				if (archivoServidor.exists()) {
					respuesta = "ok";
					Thread thDescargar = new Thread(new hiloDescargar(s, nombre));
					thDescargar.start();
				} else
					respuesta = "NotFound";
				break;
			case "delete":
				if (archivoServidor.exists()) {
					respuesta = "ok";
					Thread thBorrar = new Thread(new HiloBorrar(file.getName()));
					thBorrar.start();
				} else
					respuesta = "NotFound";
				break;
			default:
				respuesta = "Por favor pon una orden válida";
				break;
			}

			dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(respuesta);
			dos.flush();

		} catch (IOException e) {
			System.out.println("hola");
		} finally {
			
			if (ss != null && ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (s != null && s.isClosed()) {
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