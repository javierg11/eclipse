import java.net.*;
import java.io.*;

public class Emisor {

	public static void main(String[] args) {
		if(args.length!=2)
			System.err.println();
		else try {
			DatagramSocket sSocket=new DatagramSocket();
			InetAddress ip= InetAddress.getByName("172.23.107.127");
			int Puerto=5000;
			byte[] cadena= args[1].getBytes();
			DatagramPacket mensaje= new DatagramPacket(cadena,args[1].length(), ip, Puerto);
			sSocket.send(mensaje);
			sSocket.close();
		}catch(UnknownHostException e) {
			
		}catch(SocketException e) {
			
		}catch(IOException e) {
			
		}

	}

}
