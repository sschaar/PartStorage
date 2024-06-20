package at.htlklu.threads;

import at.htlklu.entities.Parts;
import at.htlklu.persistence.Dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PartStorageThread extends Thread {
    private Socket socket;
    private Dao dao;

    public PartStorageThread(Socket socket) {
        this.socket = socket;
        this.dao = new Dao();
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            out.println("HTL electronic Parts Server");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] tokens = inputLine.split(" ");
                if (tokens.length < 3) {
                    out.println("Unknown command!");
                    continue;
                }

                String command = tokens[0];
                String serialnr = tokens[1];
                int count;

                try {
                    count = Integer.parseInt(tokens[2]);
                } catch (NumberFormatException e) {
                    out.println("NOK");
                    continue;
                }

                boolean result;
                switch (command) {
                    case "ADD":
                        result = dao.updatePartCount(serialnr, count);
                        out.println(result ? "OK" : "NOK");
                        break;
                    case "REDUCE":
                        result = dao.updatePartCount(serialnr, -count);
                        out.println(result ? "OK" : "NOK");
                        break;
                    case "EXIT":
                        out.println("Connection closed.");
                        socket.close();
                        return;
                    default:
                        out.println("Unknown command!");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
