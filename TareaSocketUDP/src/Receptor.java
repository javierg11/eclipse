import java.net.*;
import java.io.*;

public class Receptor {

	public static void main(String[] args) {
		if(args.length!=2)
			System.err.println();
		else try {
			DatagramSocket sSocket=new DatagramSocket(5000);
			
			int Puerto=5000;
			byte[] cadena= new byte[1024];
			DatagramPacket mensaje= new DatagramPacket(cadena,cadena.length);
			System.out.println("Esperando mensajes...");
			while(true) {
				sSocket.receive(mensaje);
				String datos=new String(mensaje.getData(),0,mensaje.getLength());
				System.out.println(datos);
			
			}
		}catch(SocketException e) {
			
		}catch(IOException e) {
			
		}

	}

}
