package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

/**
 * Clase auxiliar que nos ayuda a identificar la estructura de datos y analizar el archivo
 */
public class Identificador {

    public boolean esCola = false;
    public boolean esPila = false;
    public boolean esLista = false;
    public boolean esCompleto = false;
    public boolean esOrdenado = false;
    public boolean esRojinegro = false;
    public boolean esAvl = false;
    public boolean esGrafica = false;
    public boolean esMonticulo = false;
    public boolean hayID = false;
    public String nombre;
    public Lista<Integer> elementos = new Lista<>();

    public Identificador() {
    }

    /**
     * Compara el identificador para ver si es válido
     * @param nombre
     */
    public void checaID(String nombre) {
        if (nombre.equals("Pila")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esPila = true;
                this.nombre = "Pila";
            }
        }
        if (nombre.equals("Cola")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esCola = true;
                this.nombre = "Cola";
            }
        }
        if (nombre.equals("Lista")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esLista = true;
                this.nombre = "Lista";
            }
        }
        if (nombre.equals("ArbolBinarioCompleto")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esCompleto = true;
                this.nombre = "ArbolBinarioCompleto";
            }
        }
        if (nombre.equals("ArbolBinarioOrdenado")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esOrdenado = true;
                this.nombre = "ArbolBinarioOrdenado";
            }
        }
        if (nombre.equals("ArbolRojinegro")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esRojinegro = true;
                this.nombre = "ArbolRojinegro";
            }
        }
        if (nombre.equals("ArbolAVL")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esAvl = true;
                this.nombre = "ArbolAVL";
            }
        }
        if (nombre.equals("Grafica")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esGrafica = true;
                this.nombre = "Grafica";
            }
        }
        if (nombre.equals("MonticuloMinimo")) {
            if (hayID) {
                throw new IllegalArgumentException();
            } else {
                hayID = true;
                esMonticulo = true;
                this.nombre = "MonticuloMinimo";
            }
        }
    }

    /**
     * Procesa la lista de arreglos de strings con el contenido de archivo
     * @param lista la lista de arreglos
     * @return  lista de elementos
     */
    public Lista<String> depura(Lista<String[]> lista) {
        Lista<String> regreso = new Lista<>();

        for (String[] strings : lista) {
            if (strings[0].equals("#") || strings[0].startsWith("#")) {
                continue;
            } else {
                for (int i = 0; i < strings.length; i++) {
                    if (strings[i].equals("#") || strings[i].startsWith("#")) {
                        break;
                    }
                    checaID(strings[i]);
                    if (!strings[i].equals(nombre)) {
                        regreso.agrega(strings[i]);
                        //System.out.println("agregando " + strings[i] + " indice " + i);
                    }
                }
            }
        }
        return regreso;
    }

}