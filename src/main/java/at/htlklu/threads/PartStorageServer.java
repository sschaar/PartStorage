package at.htlklu.threads;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import at.htlklu.persistence.Dao;
import at.htlklu.entities.Parts;

public class PartStorageServer {

    private static final int PORT = 12312;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server running on Port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
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

                    Parts updatedPart = null;
                    boolean result;
                    switch (command) {
                        case "ADD":
                            result = Dao.updatePartCount(serialnr, count);
                            if (result) {
                                updatedPart = Dao.searchPart(serialnr); // Fetch updated part details
                            }
                            out.println(result ? updatedPart.toString() : "NOK");
                            break;
                        case "REDUCE":
                            result = Dao.updatePartCount(serialnr, -count);
                            if (result) {
                                updatedPart = Dao.searchPart(serialnr); // Fetch updated part details
                            }
                            out.println(result ? updatedPart.toString() : "NOK");
                            break;
                        case "EXIT":
                            out.println("Connection closed.");
                            socket.close(); // Close socket here to ensure client receives "Connection closed."
                            return;
                        default:
                            out.println("Unknown command!");
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    System.out.println("Client disconnected: " + socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
