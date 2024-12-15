/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prestamolibros;

/**
 *
 * @author airam
 */
import java.util.List;
import java.util.Random;

public class Estudiante implements Runnable {
    private final int id;
    private final List<Libros> libros;
    private final Random random = new Random();

    public Estudiante(int id, List<Libros> libros) {
        this.id = id;
        this.libros = libros;
    }

    private void seleccionarYLeerLibros() throws InterruptedException {
        int primerLibro, segundoLibro;
        do {
            primerLibro = random.nextInt(libros.size());
            segundoLibro = random.nextInt(libros.size());
        } while (primerLibro == segundoLibro);

        Libros libro1 = libros.get(primerLibro);
        Libros libro2 = libros.get(segundoLibro);

        synchronized (libro1) {
            while (!libro1.intentarObtener()) {
                libro1.wait();
            }

            synchronized (libro2) {
                while (!libro2.intentarObtener()) {
                    libro2.wait();
                }


                System.out.println("El estudiante " + id + " ha obtenido los libros " + libro1.toString() + " y " + libro2.toString());
                int tiempoLectura = 1 + random.nextInt(3);
                Thread.sleep(tiempoLectura);
                System.out.println("El estudiante " + id + " ha terminado de leer los libros " + libro1.toString() + " y " + libro2.toString());

                libro2.liberar();
            }
            libro1.liberar();
        }
    }

    private void descansar() throws InterruptedException {
        int tiempoDescanso = 1 + random.nextInt(2); // entre 1 y 2 horas (minutos en la simulación)
        System.out.println("El estudiante " + id + " estara descansando por " + tiempoDescanso + " horas");
        Thread.sleep(tiempoDescanso * 10); // 1 minuto = 10 ms
    }

    @Override
    public void run() {
        try {
            while (true) {
                seleccionarYLeerLibros();
                descansar();
            }
        } catch (InterruptedException e) {
            System.out.println("El estudiante " + id + " ha terminado.");
        }
    }
}
