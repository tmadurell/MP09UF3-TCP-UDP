package a6;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadSevidorAdivina implements Runnable {
/* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */
	
	Socket clientSocket = null;
	Llista llistaEntrant;
	Llista llistaSortint;
	boolean acabat;
	
	public ThreadSevidorAdivina(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		acabat = false;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
			while(!acabat) {
				llistaEntrant = (Llista)inFromClient.readObject();
				llistaSortint = generaResposta(llistaEntrant);
				outToClient.writeObject(llistaSortint);


			}
		}catch(IOException | ClassNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}

		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Llista generaResposta(Llista llistaordenada) {
		//Agafa la llista ordenada i elimina els elements repetits que hi ha creat
		Set set = new HashSet(llistaordenada.getNumberList());
		List list = new ArrayList(set);
		//Es crea una altra nova llista i substitueix la llista original amb el contingut modificat per que no hi hagi numeros repetits
		llistaordenada.getNumberList().clear();
		llistaordenada.getNumberList().addAll(list);
		acabat = true;
		return llistaordenada;
	}

}
