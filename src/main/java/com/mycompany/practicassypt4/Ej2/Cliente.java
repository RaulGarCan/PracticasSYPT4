package Ej2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost",5555);
            boolean ejecucion = true;
            do {
                InputStream in = cliente.getInputStream();
                OutputStream out = cliente.getOutputStream();
                ObjectOutputStream escribir = new ObjectOutputStream(out);
                Scanner userInput = new Scanner(System.in);
                System.out.println("1- Enviar Correo\n2- Salir");
                System.out.print("Introduzca la opción deseada: ");
                String opcion = userInput.nextLine();
                escribir.writeObject(opcion);
                switch (opcion){
                    case "1":
                        System.out.print("Introduce el correo origen: ");
                        String origen = userInput.nextLine();
                        System.out.print("Introduce el correo destino: ");
                        String destino = userInput.nextLine();
                        System.out.print("Introduce el asunto del correo: ");
                        String asunto = userInput.nextLine();
                        System.out.print("Introduce el mensaje del correo: ");
                        String mensaje = userInput.nextLine();
                        Correo correo = new Correo(origen,destino,asunto,mensaje);
                        escribir.writeObject(correo);
                        break;
                    case "2":
                        System.out.println("Cerrando el programa de envío de correos...");
                        ejecucion = false;
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            }while (ejecucion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
