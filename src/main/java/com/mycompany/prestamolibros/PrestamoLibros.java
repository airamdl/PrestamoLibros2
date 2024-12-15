/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prestamolibros;

/**
 *
 * @author airam
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class PrestamoLibros {

    private static final String[] nombresLibros = {
            "El Camino de los Reyes", "Palabras Radiantes", "Juramentada",
            "El Imperio Final", "El Pozo de la Ascensión", "El Héroe de las Eras",
            "El Ritmo de la Guerra", "Sombras de Identidad", "El Alma del Emperador"
    };

    private static String generarISBN() {
        Random random = new Random();
        return random.nextInt(900000000) + 100000000 + "-" + (random.nextInt(900) + 100) + "-" + (random.nextInt(900) + 100);
    }

    public static void main(String[] args) {
        List<Libros> libros = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            String isbn = generarISBN();
            libros.add(new Libros(i, nombresLibros[i], isbn));
        }

        Thread[] estudiantes = new Thread[4];
        for (int i = 0; i < 4; i++) {
            estudiantes[i] = new Thread(new Estudiante(i, libros));
            estudiantes[i].start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread estudiante : estudiantes) {
            estudiante.interrupt();
        }

        System.out.println("La simulación ha terminado.");
    }
}
