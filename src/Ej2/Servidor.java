package Ej2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    private Socket conexion;
    public Servidor(Socket conexion){
        this.conexion = conexion;
    }
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5555);
            while (true){
                Socket conexion = server.accept();
                Thread hilo = new Thread(new Servidor(conexion));
                hilo.start();
                System.out.println("Conexión aceptada");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        boolean ejecucion = true;
        do {
            try {
                InputStream in = conexion.getInputStream();
                OutputStream out = conexion.getOutputStream();
                ObjectInputStream leer = new ObjectInputStream(in);
                String opcion = (String) leer.readObject();
                switch (opcion){
                    case "1":
                        Correo correo = (Correo)leer.readObject();
                        System.out.println(correo);
                        break;
                    case "2":
                        System.out.println("Conexión terminada");
                        ejecucion = false;
                        conexion.close();
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }while (ejecucion);
    }
}
