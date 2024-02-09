package Ej3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Scanner userInput = new Scanner(System.in);
            Socket cliente = new Socket("localhost",5555);
            InputStream in = cliente.getInputStream();
            OutputStream out = cliente.getOutputStream();
            ObjectOutputStream escribir = new ObjectOutputStream(out);
            ObjectInputStream leer = new ObjectInputStream(in);
            boolean ejecucion = true;
            String idCliente = (String)leer.readObject();
            System.out.println("Identificador de cliente: "+idCliente);
            do {
                System.out.print("Introduce el id del profesor buscado: ");
                String idProfesor = userInput.nextLine();
                escribir.writeObject(idProfesor);
                Profesor profesor = (Profesor) leer.readObject();
                if(profesor!=null){
                    System.out.println(profesor.toString());
                } else {
                    System.out.println("Profesor no encontrado");
                }
                if(Integer.parseInt(idProfesor)<0){
                    System.out.println("Cerrando conexion con el servidor...");
                    ejecucion = false;
                }
            }while (ejecucion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
