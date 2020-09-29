package mx.unam.ciencias.edd.proyecto3;

import java.io.File;

import mx.unam.ciencias.edd.Lista;

/**
 * Crea archivos html de archivos de texto en un directorio específicado así como un indice de los mismos
 * Los archivos contienen información en varias presentaciones
 */
public class Proyecto3 {

    public static void main(String[] args) {

        // Si no hay argumentos, cerramos el programa
        if (args.length == 0) {
            System.out.println("No se detectaron argumentos \nSaliendo del programa...");
            System.exit(-1);
        }

        Lista<Contador> contadores = new Lista<>();
        Lista<String> archivos = new Lista<>();
        String directorio = "";

        /**
         * Capta el path del directorio
         */
        try {
            directorio = BanderaO.RegresaIdentificador(args);
        } catch (ExcepcionBanderaSinID e) {
            System.out.println(e);
            System.exit(-1);
        }

        /**
         * Capta los archivos
         */
        for (String string : args) {
            if (!string.equals("-o") && !string.equals(directorio)) {
                archivos.agrega(string);
            }
        }

        // Si no hay archivos cerramos el programa
        if (archivos.esVacia()) {
            System.out.println("No se detectaron archivos \nSaliendo del programa...");
            System.exit(-1);
        }

        // Creamos un File con el path del directorio
        File dir = new File(directorio);

        // Si es un archivo, salimos del programa
        if (dir.isFile()) {
            System.out.println("No se puede usar un archivo como directorio \nSaliendo del programa...");
            System.exit(-1);
        }

        /**
         * Si no existe el directorio, se crea, si no hay permisos o ocurre un error se
         * cierra el programa
         */
        if (!dir.exists()) {
            try {
                if (dir.mkdirs()) {
                    System.out.println("Directorio " + directorio + " creado.");
                } else {
                    System.out.println("Ha ocurrido un error al crear el directorio \nSaliendo del programa...");
                    System.exit(-1);
                }
            } catch (SecurityException e) {
                System.out.println("No hay permisos para crear el directorio \nSaliendo del programa...");
                System.exit(-1);
            }
        }

        /**
         * Creamos los contadores
         */
        for (String string : archivos) {
            Contador nuevo = new Contador(string);
            contadores.agrega(nuevo);
        }

        /**
         * Generamos los HTML de cada archivo en el diretorio
         */
        for (Contador contador : contadores) {
            HTMLGen html = new HTMLGen(contador);
            html.generaHTML(dir.getPath());
        }

        /**
         * Generamos el index
         */
        IndexGen index = new IndexGen(contadores);
        index.generaHTML(dir.getPath());
        /**
         * Generamos el css en el directorio
         */
        index.generaCSS(dir.getPath());
    }
}