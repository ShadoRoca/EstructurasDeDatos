package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Clase que controla las ordenaciones de los archivos y distingue la entrada
 * estándar de la entrada por parámetros.
 */

public class Ordenador {

    /**
     * Método que ordena el contenido de archivos.
     * 
     * @param archivos Lista que contiene los nombres/rutas de los archivos a
     *                 ordenar.
     * @return Lista de lineas que contiene los renglones ordenados del contenido de
     *         los archivos.
     * @throws IllegalArgumentException si la lista de archivos es vacía.
     */
    public static Lista<Linea> ordenaArchivos(Lista<String> archivos) throws IllegalArgumentException {
        if (archivos.esVacia()) {
            throw new IllegalArgumentException("No hay archivos para ordenar");
        }
        Lista<Linea> regreso = new Lista<>();
        Lista<Linea> nuevaLista = new Lista<>();
        String linea;
        for (String string : archivos) {
            try {
                FileReader lector = new FileReader(string);
                BufferedReader buffer = new BufferedReader(lector);
                while ((linea = buffer.readLine()) != null) {
                    Linea nueva = new Linea(linea);
                    regreso.agrega(nueva);
                }
                nuevaLista = Lista.mergeSort(regreso);
                lector.close();
            } catch (FileNotFoundException e) {
                System.out.println("Arhivo " + string + " no encontrado");
                break;
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error");
            }
        }
        return nuevaLista;
    }

    /**
     * Método que funciona con la bandera -o. Recibe el nombre de un archivo, si
     * existe lo sobreescribe con el contenido de <code>lista</code> si no, lo crea
     * y le agrega el contenido de <code>lista</code>
     * 
     * @param lista         Lista de Linea que contiene el texto a ser escrito en
     *                      <code>identificador</code>
     * @param identificador Ruta o nombre del archivo donde se quiere guardar el
     *                      contenido de <code>lista</code>
     */
    public static void operacionDestructiva(Lista<Linea> lista, String identificador) {
        FileWriter writer = null;
        PrintWriter printer = null;
        try {
            File archivo = new File(identificador);
            if (archivo.exists()) {
                writer = new FileWriter(archivo);
                printer = new PrintWriter(writer);

                for (Linea linea : lista) {
                    printer.println(linea);
                }
            } else {
                if (archivo.createNewFile()) {
                    writer = new FileWriter(archivo);
                    printer = new PrintWriter(writer);

                    for (Linea linea : lista) {
                        printer.println(linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al crear el archivo");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Escritor no fue inicializado");
            }
        }
    }

    /**
     * Este método nos devuelve una lista ordenada de Linea con el texto de archivos
     * captados por entrada estándar
     * 
     * @return Una lista ordenada con el contenido de la entrada estándar
     */
    public static Lista<Linea> entradaEstandar() {
        String linea;
        Lista<Linea> lista = new Lista<>();
        Lista<Linea> regreso = new Lista<>();
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            if (!buffer.ready()) {
                System.out.println("Error, archivo no válido");
                regreso = null;
                return regreso;
            }
            while ((linea = buffer.readLine()) != null) {
                Linea nueva = new Linea(linea);
                lista.agrega(nueva);
            }
            if (lista.esVacia()) {
                System.out.println("No hay qué ordenar");
            }

            regreso = Lista.mergeSort(lista);

            buffer.close();

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error");
        }
        return regreso;
    }

    /**
     * Método que identifica si es entrada estándar o por parámetros de la línea de
     * comandos.
     * 
     * @param pps Arreglo de strings con los parámetros del main.
     * @return <code>true</code> si es entrada estándar. <code>false</code> si no es
     *         entrada estándar.
     * @throws ExcepcionBanderaSinID si se detecta que no hay una bandera válida.
     */
    public static boolean esEstandar(String[] pps) throws ExcepcionBanderaSinID {
        if (pps.length == 1 && BanderaR.hayBanderaR(pps)) {
            return true;
        }
        if (pps.length == 1 && BanderaO.hayBanderaO(pps)) {
            throw new ExcepcionBanderaSinID("La bandera -o necesita un identificador");
        }
        if (pps.length == 0) {
            return true;
        }
        if (pps.length == 2 && BanderaO.hayBanderaO(pps)) {
            return true;
        }
        if (pps.length == 3 && BanderaO.hayBanderaO(pps) && BanderaR.hayBanderaR(pps) && !pps[0].equals("-o") && !pps[1].equals("-o")) {
            return true;
        }
        if (pps.length == 3 && pps[0].equals("-o") && pps[1].equals("-r")) {
            return false;
        }
        if (pps.length == 3 && pps[1].equals("-o") && pps[2].equals("-r")) {
            return false;
        }
        return false;
    }
}