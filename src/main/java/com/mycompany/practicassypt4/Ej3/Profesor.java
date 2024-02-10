package com.mycompany.practicassypt4.Ej3;

import java.io.Serializable;
import java.util.ArrayList;

public class Profesor implements Serializable {
    private int id;
    private String nombre;
    private ArrayList<Asignatura> asignaturas;
    private ArrayList<Especialidad> especialidades;

    public Profesor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.asignaturas = new ArrayList<>();
        this.especialidades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public boolean addAsignatura(Asignatura asignatura) {
        if(asignaturas.size()<5){
            this.asignaturas.add(asignatura);
            return true;
        }
        return false;
    }

    public ArrayList<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void addEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", asignaturas=" + asignaturas +
                ", especialidades=" + especialidades +
                '}';
    }
}
