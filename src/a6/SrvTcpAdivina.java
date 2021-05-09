package a6;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SrvTcpAdivina {
	/* Servidor TCP que genera un número perquè ClientTcpAdivina.java jugui a encertar-lo
	 * i on la comunicació dels diferents jugador passa per el Thread : ThreadServidorAdivina.java
	 * */

	int port;
	
	public SrvTcpAdivina(int port ) {
		this.port = port;
	}
	
	public void listen() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			while(true) { //esperar connexió del client i llançar thread
				clientSocket = serverSocket.accept();
				System.out.println("Conexió establerta ;)");
				//Llançar Thread per establir la comunicació
				ThreadSevidorAdivina FilServidor = new ThreadSevidorAdivina(clientSocket);
				Thread client = new Thread(FilServidor);
				client.start();
			}
		} catch (IOException ex) {
			Logger.getLogger(SrvTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) {
				/*if (args.length != 1) {
            System.err.println("Usage: java SrvTcpAdivina <port number>");
            System.exit(1);
        }*/


		//int port = Integer.parseInt(args[0]);
        SrvTcpAdivina srv = new SrvTcpAdivina(5558);
        srv.listen();

	}

}
