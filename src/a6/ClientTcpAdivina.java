package a6;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTcpAdivina extends Thread {
	
	String hostname;
	int port;
	boolean ordenat;

	int intents;

	public ClientTcpAdivina(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		ordenat = true;

		intents=0;
	}

	public void run() {
		Socket socket;
		List<Integer> numerosdellista = new ArrayList<>();
		//Fa un parells de numeros aleatoris
		for (int i = 0; i < 5; i++) {
			numerosdellista.add((int)(Math.random() * 10));
		}
		//Creem una llista
		Llista llista = new Llista("Llista: ", numerosdellista);
		//Mirem si la llista esta creada
		System.out.println("Llista del client enviada al SERVIDOR: \n" + llista.getNom());
		for (Integer i:llista.getNumberList()) {
			System.out.println(i);
		}


		try {
			socket = new Socket(InetAddress.getByName(hostname), port);
			ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
			//Envia la llista amb els numeros aleatoris
			outToServer.writeObject(llista);
			//Reben una resposta
			Llista llistaFromServer = (Llista) inFromServer.readObject();
			//Mostra una resposta
			System.out.println("Resposta retornada del SERVIDOR:\n" + llistaFromServer.getNom());
			for (Integer i:llistaFromServer.getNumberList()) {
				System.out.println(i);
			}
		 	close(socket);

		} catch (UnknownHostException ex) {
			System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("Error de connexió indefinit: " + ex.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void close(Socket socket){
		//si falla el tancament no podem fer gaire cosa, només enregistrar
		//el problema
		try {
			//tancament de tots els recursos
			if(socket!=null && !socket.isClosed()){
				if(!socket.isInputShutdown()){
					socket.shutdownInput();
				}
				if(!socket.isOutputShutdown()){
					socket.shutdownOutput();
				}
				socket.close();
			}
		} catch (IOException ex) {
			//enregistrem l'error amb un objecte Logger
			Logger.getLogger(ClientTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	public static void main(String[] args) {
				/*if (args.length != 2) {
            System.err.println(
                "Usage: java ClientTcpAdivina <host name> <port number>");
            System.exit(1);
        }*/

		// String hostName = args[0];
		// int portNumber = Integer.parseInt(args[1]);
        ClientTcpAdivina clientTcp = new ClientTcpAdivina("localhost",5558);
        clientTcp.start();
	}
}
