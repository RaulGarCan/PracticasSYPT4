package Ej3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Servidor implements Runnable {
    private Socket conexion;
    private int idClienteHilo;
    private static ArrayList<Profesor> profesores;
    private static int idCliente = 1;
    public Servidor(Socket conexion, int idClienteHilo){
        this.conexion = conexion;
        this.idClienteHilo = idClienteHilo;
    }
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5555);
            crearDatosBD();
            while (true){
                Socket conexion = server.accept();
                Thread hilo = new Thread(new Servidor(conexion, idCliente));
                hilo.start();
                incrementarId();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        ArrayList<Profesor> profesoresDevueltos = new ArrayList<>();
        ArrayList<Integer> idsPeticionados = new ArrayList<>();
        try {
            LocalDateTime fechaHoraConexion = LocalDateTime.now();
            InputStream in = conexion.getInputStream();
            OutputStream out = conexion.getOutputStream();
            ObjectInputStream leer = new ObjectInputStream(in);
            ObjectOutputStream escribir = new ObjectOutputStream(out);
            escribir.writeObject(String.valueOf(idClienteHilo));
            boolean ejecucion = true;
            do {
                String idProfesor = (String) leer.readObject();
                if(idProfesor.equalsIgnoreCase("***")){
                    System.out.println("Cerrando conexion con cliente nº"+this.idClienteHilo);
                    ejecucion = false;
                } else {
                    try {
                        idsPeticionados.add(Integer.parseInt(idProfesor));
                        Profesor profesor = buscarProfesor(Integer.parseInt(idProfesor));
                        escribir.writeObject(profesor);
                        if(profesor!=null){
                            profesoresDevueltos.add(profesor);
                        }
                    }catch (NumberFormatException e){
                        escribir.writeObject(null);
                        System.out.println("No se puede convertir texto a entero");
                    }
                }
            }while (ejecucion);
            LocalDateTime fechaHoraDesconexion = LocalDateTime.now();
            ClienteConectado clienteConectado = new ClienteConectado(this.idClienteHilo,fechaHoraConexion,fechaHoraDesconexion);
            clienteConectado.setIdsPeticionados(idsPeticionados);
            clienteConectado.setProfesoresEnviados(profesoresDevueltos);
            guardarDatosCliente(clienteConectado);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void incrementarId(){
        idCliente++;
    }
    private synchronized static void guardarDatosCliente(ClienteConectado datosCliente){
        HashMap<String, Object> mapa = datosCliente.toMap();
        try {
            FileWriter escribir = new FileWriter("./src/Ej3/Log/FichLog.txt",true);
            escribir.write(mapa.toString());
            escribir.write("\n");
            escribir.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Profesor buscarProfesor(int idProfesor){
        for(Profesor p : profesores){
            if(p.getId()==idProfesor){
                return p;
            }
        }
        return null;
    }
    private static void crearDatosBD(){
        Asignatura as1 = new Asignatura(1, "Asignatura1");
        Asignatura as2 = new Asignatura(2, "Asignatura2");
        Asignatura as3 = new Asignatura(3, "Asignatura3");
        Asignatura as4 = new Asignatura(4, "Asignatura4");
        Asignatura as5 = new Asignatura(5, "Asignatura5");

        Especialidad es1 = new Especialidad(1,"Especialidad1");
        Especialidad es2 = new Especialidad(2,"Especialidad2");
        Especialidad es3 = new Especialidad(3,"Especialidad3");
        Especialidad es4 = new Especialidad(4,"Especialidad4");
        Especialidad es5 = new Especialidad(5,"Especialidad5");

        Profesor p1 = new Profesor(1,"Profesor1");
        Profesor p2 = new Profesor(2,"Profesor2");
        Profesor p3 = new Profesor(3,"Profesor3");
        Profesor p4 = new Profesor(4,"Profesor4");
        Profesor p5 = new Profesor(5,"Profesor5");

        p1.addAsignatura(as1);
        p1.addEspecialidad(es1);

        p2.addAsignatura(as2);
        p2.addEspecialidad(es2);

        p3.addAsignatura(as3);
        p3.addEspecialidad(es3);

        p4.addAsignatura(as4);
        p4.addEspecialidad(es4);

        p5.addAsignatura(as5);
        p5.addEspecialidad(es5);

        profesores = new ArrayList<>();

        profesores.add(p1);
        profesores.add(p2);
        profesores.add(p3);
        profesores.add(p4);
        profesores.add(p5);
    }
}
