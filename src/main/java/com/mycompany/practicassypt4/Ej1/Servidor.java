package com.mycompany.practicassypt4.Ej1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor implements Runnable {
    private Socket conexion;
    private static ArrayList<Mensaje> mensajes;
    public Servidor(Socket conexion){
        this.conexion = conexion;
    }
    public static void main(String[] args) {
        try {
            mensajes = new ArrayList<>();
            ServerSocket server = new ServerSocket(5555);
            while(true){
                Socket socket = server.accept();
                Thread hilo = new Thread(new Servidor(socket));
                hilo.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            InputStream in = conexion.getInputStream();
            OutputStream out = conexion.getOutputStream();
            ObjectInputStream leer = new ObjectInputStream(in);
            String nombreCliente = (String)leer.readObject();
            System.out.println("Identificado cliente: "+nombreCliente);
            ObjectOutputStream escribir = new ObjectOutputStream(out);
            boolean ejecucion = true;
            do {
                String opcion = (String)leer.readObject();
                switch (opcion){
                    case "1": // Consultar mensajes
                        ArrayList<Mensaje> mensajesPorLeer = getMensajesDestinatario(nombreCliente);
                        borrarMensajesLeidos(mensajesPorLeer);
                        escribir.writeObject(mensajesPorLeer);
                        break;
                    case "2": // Dejar mensaje
                        Mensaje mensajeNuevo = (Mensaje) leer.readObject();
                        if(mensajeNuevo!=null){
                            addMensaje(mensajeNuevo);
                        }
                        break;
                    case "3":
                        String avisoCierre = "¡Nos vemos "+nombreCliente+"!";
                        escribir.writeObject(avisoCierre);
                        System.out.println("Cerrando conexion con cliente: "+nombreCliente);
                        ejecucion = false;
                        conexion.close();
                        break;
                    default:
                        break;
                }
            }while (ejecucion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getConexion() {
        return conexion;
    }

    public void setConexion(Socket conexion) {
        this.conexion = conexion;
    }
    public static ArrayList<Mensaje> getMensajesDestinatario(String destinatario){
        ArrayList<Mensaje> mensajesDestinatario = new ArrayList<>();
        for(Mensaje m : mensajes){
            if(m.getReceptor().equalsIgnoreCase(destinatario)){
                mensajesDestinatario.add(m);
            }
        }
        return mensajesDestinatario;
    }
    public static void borrarMensajesLeidos(ArrayList<Mensaje> mensajesLeidos){
        for(Mensaje m : mensajesLeidos){
            mensajes.remove(m);
        }
    }
    public static boolean addMensaje(Mensaje mensaje){
        return mensajes.add(mensaje);
    }
}
