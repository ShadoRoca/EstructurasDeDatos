package mx.unam.ciencias.edd.proyecto3;

/**
 * Clase para guardar palabras con su valor y que se puedan comparar entre ellas 
 */
public class Palabra implements Comparable<Palabra> {

    private String palabra;
    private int valor;

    /**
     * Crea una Palabra
     * @param palabra el string 
     * @param valor su valor
     */
    public Palabra(String palabra, int valor) {
        this.palabra = palabra;
        this.valor = valor;
    }

    /**
     * Compara dos palabras de acuerdo a su valor
     */
    @Override
    public int compareTo(Palabra o) {
        return this.valor - o.valor;
    }

    /**
     * imprime la palabra
     */
    public String toString() {
        return palabra;
    }

    /**
     * Nos dice si una palabra es igual a otra sin importar sus valores
     * @param o la otra Palabra
     * @return True si son iguales, False si no
     */
    public boolean equals(Palabra o) {
        return this.palabra.equals(o.palabra);
    }

    /**
     * Modifica el valor de una palabra
     * @param valor
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Regresa cuántos carácteres tiene una palabra
     * @return Número de letras en una palabra
     */
    public int caracteres() {
        return palabra.length();
    }

    /**
     * Nos devuelve el valor de una palabra
     * @return valor 
     */
    public int valor() {
        return valor;
    }

}