package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;

import mx.unam.ciencias.edd.AlgoritmoDispersor;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;

/**
 * Clase donde se guarda la información de un archivo
 */
public class Contador {

    private Diccionario<String, Integer> contador;
    private int elementos;
    private String nombre;

    /**
     * Crea un contador. Aquí es donde se hace el proceso de contar las palabras y
     * sus repeticiones, también sirve para ignorar acentos y comas, así como
     * también caracteres especiales ya que podría haber casos como "¿Hola? / Hola!
     * / Hola"
     * 
     * @param archivo el archivo del que se crea el contador
     */
    public Contador(String archivo) {
        File f = new File(archivo);
        nombre = f.getName();
        this.contador = new Diccionario<String, Integer>(
                FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING));
        String linea;
        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(lector);
            while ((linea = bf.readLine()) != null) {
                if (!linea.isBlank()) {
                    String[] arreglo = linea.trim().split(" +");
                    for (String string2 : arreglo) {
                        String nuevo = ignoraAcentos(string2).replaceAll("[^\\dA-Za-z]", "").toLowerCase().trim();
                        if (contador.contiene(nuevo)) {
                            int valor = contador.get(nuevo);
                            contador.agrega(nuevo, valor + 1);
                            elementos++;
                        } else {
                            contador.agrega(nuevo, 1);
                            elementos++;
                        }
                    }
                }
            }
            bf.close();
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo " + archivo
                    + ", intente nuevamente con un archivo existente. \nSaliendo del programa...");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Hay un error con el archivo " + archivo
                    + ", por favor verifique que sea válido.\nSaliendo del programa...");
            System.exit(-1);
        }
    }

    /**
     * Regresa el número total de palabras en el archivo
     * 
     * @return total de palabras
     */
    public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el nombre del archivo
     * 
     * @return nombre del archivo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Regresa el diccionario que contiene las palabras
     * 
     * @return Un diccionario con las palabras
     */
    public Diccionario<String, Integer> palabras() {
        return contador;
    }

    /**
     * Nos regresa una lista de Palabras, con las palabras del archivo
     * 
     * @return una lista de palabras
     */
    public Lista<Palabra> getPalabras() {
        Iterator it = contador.iteradorLlaves();
        Lista<Palabra> palabras = new Lista<>();
        while (it.hasNext()) {
            String n = (String) it.next();
            Palabra nueva = new Palabra(n, contador.get(n));
            palabras.agrega(nueva);
        }
        return Lista.mergeSort(palabras);
    }

    /**
     * Regresa una lista con las 5 palabras más comunes
     * 
     * @return una lista de 5 palabras
     */
    public Lista<Palabra> top5() {
        Lista<Palabra> ordenada = getPalabras();
        Lista<Palabra> top = new Lista<>();
        for (int i = 0; i < 5; i++) {
            if (ordenada.esVacia()) {
                return top;
            } else {
                top.agrega(ordenada.eliminaUltimo());
            }
        }
        return top;
    }

    /**
     * Regresa una lista con las 15 palabras más comunes
     * 
     * @return una lista de 15 palabras
     */
    public Lista<Palabra> top15() {
        Lista<Palabra> ordenada = getPalabras();
        Lista<Palabra> top = new Lista<>();
        for (int i = 0; i < 15; i++) {
            if (ordenada.esVacia()) {
                return top;
            } else {
                top.agrega(ordenada.eliminaUltimo());
            }
        }
        return top;
    }

    /**
     * Nos dice si un archivo tiene en común con otro archivo una palabra de al
     * menos 7 carácteres
     * 
     * @param o Contador del segundo archivo
     * @return
     */
    public boolean sonAdyacentes(Contador o) {
        for (Palabra palabra : o.getPalabras()) {
            for (Palabra palabra2 : getPalabras()) {
                if (palabra.equals(palabra2) && palabra.caracteres() >= 7) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo auxiliar que quita los acentos de una palabra
     * 
     * @param cadena la palabra
     * @return La palabra sin acentos
     */
    private static String ignoraAcentos(String cadena) {
        String nuevo = cadena;
        nuevo = Normalizer.normalize(nuevo, Normalizer.Form.NFD);
        nuevo = nuevo.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return nuevo;
    }

}