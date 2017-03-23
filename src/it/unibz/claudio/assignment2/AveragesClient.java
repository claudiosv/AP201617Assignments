package it.unibz.claudio.assignment2;

import java.io.*;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Created by claudio on 23/03/2017.
 */
public class AveragesClient extends Thread {

    private Socket clientSocket;

    public AveragesClient(int port) throws IOException
    {
        this.clientSocket = new Socket("localhost", port);
        this.clientSocket.setSoTimeout(50000);
    }

    public void run()
    {
        try
        {
            System.out.println("Connected to server on port: " + this.clientSocket.getPort());
            OutputStream outToServer = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            String numbers = "598,29,43,90,32,656";
            System.out.println(numbers);
            out.writeUTF(numbers);

            InputStream inFromServer = clientSocket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            String response = in.readUTF();
            System.out.println("Server says " + response);

            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"));
            writer.write(String.format("%s%n%s", numbers, response));
            writer.close();
            clientSocket.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        int port = Integer.parseInt(args[0]);
        try
        {
            Thread clientThread = new AveragesClient(port);
            clientThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
