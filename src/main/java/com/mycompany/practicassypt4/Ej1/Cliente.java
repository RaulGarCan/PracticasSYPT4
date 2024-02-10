package Ej1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            do {
                Socket cliente = new Socket("localhost", 5555);
                boolean ejecucion = true;
                Scanner userInput = new Scanner(System.in);
                InputStream in = cliente.getInputStream();
                OutputStream out = cliente.getOutputStream();
                System.out.print("Introduce tu nombre: ");
                String nombreCliente = userInput.nextLine();
                ObjectOutputStream escribir = new ObjectOutputStream(out);
                escribir.writeObject(nombreCliente);
                ObjectInputStream leer = new ObjectInputStream(in);
                do {
                    System.out.println("\n1- Consultar mensajes\n2- Dejar un mensaje\n3- Salir");
                    System.out.print("\nSelecciona una opción: ");
                    String opcion = userInput.nextLine();
                    escribir.writeObject(opcion);
                    switch (opcion) {
                        case "1":
                            ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) leer.readObject();
                            if (!mensajes.isEmpty()) {
                                System.out.println("\nLista de mensajes: ");
                                for (Mensaje m : mensajes) {
                                    System.out.println(m.toString());
                                }
                            } else {
                                System.out.println("\nEsto es un poco violento pero... no te ha llegado ningún mensaje nuevo :(");
                            }
                            break;
                        case "2":
                            System.out.print("Introduce el nombre del destinatario: ");
                            String destinatario = userInput.nextLine();
                            System.out.print("Introduce el contenido del mensaje: ");
                            String contenido = userInput.nextLine();
                            if (!nombreCliente.equalsIgnoreCase(destinatario)) {
                                Mensaje mensajeNuevo = new Mensaje(nombreCliente, destinatario, contenido);
                                escribir.writeObject(mensajeNuevo);
                                System.out.println("Mensaje enviado correctamente");
                            } else {
                                System.out.println("\n¿Acabas de intentar mandarte un mensaje a ti mismo...?");
                                escribir.writeObject(null);
                            }
                            break;
                        case "3":
                            System.out.println("\n"+(String) leer.readObject()+"\n");
                            ejecucion = false;
                            break;
                        default:
                            break;
                    }
                } while (ejecucion);
            } while (true);
        } catch (IOException e) {
            System.out.println("\n¿Has probado a encender el servidor?\n");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
