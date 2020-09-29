package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;
import java.text.Normalizer;

/**
 * Clase Linea, una linea es un renglón de un texto
 */

public class Linea implements Comparable<Linea> {
    /**
     * El renglón de la línea
     */
    String cadena;

    /**
     * Collator para ordenar lexicograficamente como lo hace unix
     */
    Collator collator = Collator.getInstance();

    /**
     * Constructor con parámetro
     * @param s el renglón 
     */
    public Linea(String s) {
        cadena = s;
    }

    /**
     * Nos devuelve el renglón de la Linea
     * @return el renglón
     */
    public String get() {
        return cadena;
    }

    /**
     * Quita los acentos de una cadena
     * @return una nueva cadena sin acentos
     */
    public String ignoraAcentos() {
        String nuevo = cadena;
        nuevo = Normalizer.normalize(nuevo, Normalizer.Form.NFD);
        nuevo = nuevo.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return nuevo;
    }

    /**
     * Compara Lineas
     */
    @Override
    public int compareTo(Linea o) {
        collator.setStrength(Collator.PRIMARY);
        String primero = this.ignoraAcentos().replaceAll("[^\\dA-Za-z]", " "); //QUITAMOS ACENTOS Y CARACTERES ESPECIALES
        String segundo = o.ignoraAcentos().replaceAll("[^\\dA-Za-z]", " ");

        return collator.compare(primero, segundo);
    }

    /**
     * Regresa la representacion en cadena de una Linea
     */
    public String toString() {
        return cadena;
    }

}