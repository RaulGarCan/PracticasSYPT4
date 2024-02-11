package com.mycompany.practicassypt4.Ej2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;

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
                        System.out.println("Enviando correo de "+correo.getOrigen()+" a "+correo.getDestinatario());
                        enviarCorreo(correo);
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
    public static void enviarCorreo(Correo correo){
        String smtp = "alt1.gmail-smtp-in.l.google.com";
        Properties propiedades = System.getProperties();
        propiedades.put("mail.smtp.host",smtp);
        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo.getOrigen(), correo.getPasswd());
            }
        });
        MimeMessage mensaje = new MimeMessage(sesion);

        try {
            mensaje.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mensaje.addHeader("format", "flowed");
            mensaje.addHeader("Content-Transfer-Encoding", "8bit");

            mensaje.setFrom(new InternetAddress(correo.getOrigen(), "NoReply-JD"));
            mensaje.setReplyTo(InternetAddress.parse(correo.getDestinatario(), false));

            mensaje.setSubject(correo.getAsunto(), "UTF-8");
            mensaje.setText(correo.getMensaje(), "UTF-8");
            mensaje.setSentDate(new Date());

            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo.getDestinatario(), false));
            Transport.send(mensaje);
            System.out.println("Correo enviado");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
