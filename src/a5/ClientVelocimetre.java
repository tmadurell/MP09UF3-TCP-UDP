package a5;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

public class ClientVelocimetre {
    private MulticastSocket multisocket;
    private InetAddress multicastIP;
    private InetSocketAddress groupMulticast;
    private NetworkInterface netIf;

    List<Integer> velocitats = new ArrayList<>();
    boolean continueRunning = true;


    public ClientVelocimetre(String ip, int port) throws IOException {

        this.multisocket = new MulticastSocket(port);
        this.multicastIP = InetAddress.getByName(ip);
        this.groupMulticast = new InetSocketAddress(multicastIP,port);
        this.netIf = NetworkInterface.getByName("wlp0s20f3");
    }


    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        //Bucle de les velocitats y mitjana

        while(true) {
            multisocket.joinGroup(groupMulticast,netIf);
            DatagramPacket packet;

            while (continueRunning) {

                packet = new DatagramPacket(receivedData, 1024);
                multisocket.setSoTimeout(5000);
                multisocket.receive(packet);

                int resultat = ByteBuffer.wrap(packet.getData()).getInt();
                velocitats.add(resultat);

                System.out.println("La velocitat es: "+resultat);

                if (velocitats.size() %5 == 0){
                    int total = 0;
                    for (Integer integer:velocitats){
                        total = total+integer;
                    }
                    int mitjana = total/velocitats.size();
                    velocitats.clear();
                    System.out.println("La velocitat mitja: "+mitjana);
                }
            }
            multisocket.leaveGroup(groupMulticast,netIf);
            multisocket.close();
        }
    }

    public static void main(String[] args) {
        try{
            ClientVelocimetre clientVelocimetre = new ClientVelocimetre("224.0.X.X",5557);
            clientVelocimetre.runClient();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}

