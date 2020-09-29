package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto2.*;

/**
 * Programa que grafica estructuras de datos, las cuales son: Colas, Pilas,
 * Listas, los diferentes tipos de arboles, gráficas y montículos minimos.
 * Recibe un archivo ya sea por la entrada estándar o por parámetro. El archivo
 * debe constar de un identificador que debe ser lo primero del archivo y los
 * elementos separados por espacios. Sólo se admiten Integers. También es
 * posible hacer comentarios usando la almohadilla #. En el caso de las
 * gráficas, cada par de elementos es una arista y si un par es el mismo número,
 * representa un vertice sin aristas.
 * 
 * @author Liprandi Cortes Rodrigo. 317275605
 */
public class Proyecto2 {

    /**
     * Nos dice si la entrada es estándar
     * 
     * @param pps el arreglo de parametros del main
     * @return True si la entrada es estandar, false si no.
     */
    public static boolean esEstandar(String[] pps) {
        return pps.length == 0 ? true : false;
    }

    /**
     * Guarda el contenido del archivo pasado como parametro en una lista de
     * arreglos de strings, cada arreglo es una linea.
     * 
     * @param archivo el archivo
     * @return La lista de arreglos de strings con el contenido
     */
    public static Lista<String[]> analizaParametros(String archivo) {
        String linea;
        Lista<String[]> regreso = new Lista<>();
        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(lector);
            while ((linea = buffer.readLine()) != null) {
                if (!linea.isBlank()) {
                    String[] arreglo = linea.trim().split(" +");
                    regreso.agrega(arreglo);
                } else {
                    continue;
                }
            }
            if (regreso.esVacia()) {
                System.out.println("ARCHIVO VACÍO");
                System.exit(-1);
            }
            buffer.close();
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("No existe ese archivo, intente nuevamente con un archivo existente");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Hay un error con el archivo, por favor verifique que sea válido");
            System.exit(-1);
        }
        return regreso;
    }

    public static void main(String[] pps) {

        Identificador i = new Identificador();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        if (esEstandar(pps)) {
            // ES ESTANDAR
            String linea;
            Lista<String[]> regreso = new Lista<>();
            // LEEMOS EL ARCHIVO, PROCESO SIMILAR A LA ENTRDA POR PARAMETROS
            try {
                if (!br.ready()) {
                    System.out.println("Error, archivo no válido");
                }
                while ((linea = br.readLine()) != null) {
                    if (!linea.isBlank()) {
                        String[] arreglo = linea.trim().split(" +");
                        regreso.agrega(arreglo);
                    } else {
                        continue;
                    }
                }
                if (regreso.esVacia()) {
                    System.out.println("ARCHIVO VACÍO");
                    System.exit(-1);
                }
                br.close();
            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado, intente nuevamente");
                System.exit(-1);
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error");
                System.exit(-1);
            }
            if (regreso.esVacia()) {
                System.out.println("El archivo esta vacio");
                System.exit(-1);
            } else {
                // PROCESAMOS
                Lista<String> depurada = i.depura(regreso);
                Lista<Integer> elementos = new Lista<>();
                for (String string : depurada) {
                    try {
                        // CONVERTIMOS A ENTEROS
                        elementos.agrega(Integer.parseInt(string));
                    } catch (NumberFormatException e) {
                        System.out.println("Sólo se admiten enteros en los elementos");
                    }
                }
                if (i.nombre == null) {
                    System.out.println(
                            "NO HAY NOMBRE VALIDO PARA LA ESTRCUTURA, LOS VALIDOS SON: Cola, Pila, Lista, ArbolBinarioCompleto, ArbolBinarioOrdenado, ArbolRojinegro, ArbolAVL, Grafica, MonticuloMinimo");
                }
                if (!i.hayID) {
                    System.out.println("NO HAY IDENTIFICADOR PARA LA ESTRUCTURA, ADIOS");
                }
                // COLA
                if (i.esCola) {
                    ColaSVG cola = new ColaSVG();
                    cola.meteElementos(elementos);
                    System.out.println(cola.SVG());
                }
                // PILA
                if (i.esPila) {
                    PilaSVG pila = new PilaSVG();
                    pila.meteElementos(elementos);
                    System.out.println(pila.SVG());
                }
                // LISTA
                if (i.esLista) {
                    ListaSVG lista = new ListaSVG();
                    lista.agregaElementos(elementos);
                    System.out.println(lista.SVG());
                }
                // A. COMPLETO
                if (i.esCompleto) {
                    CompletoSVG completo = new CompletoSVG(elementos);
                    System.out.println(completo.SVG());
                }
                // A. ORDENADO
                if (i.esOrdenado) {
                    OrdenadoSVG ordenado = new OrdenadoSVG(elementos);
                    System.out.println(ordenado.SVG());
                }
                // A. ROJONEGRO
                if (i.esRojinegro) {
                    RojinegroSVG rojonegro = new RojinegroSVG(elementos);
                    System.out.println(rojonegro.SVG());
                }
                // AVL
                if (i.esAvl) {
                    AvlSVG avl = new AvlSVG(elementos);
                    System.out.println(avl.SVG());
                }
                // GRAFICA
                if (i.esGrafica) {
                    try {
                        GraficaSVG grafica = new GraficaSVG(elementos);
                        System.out.println(grafica.SVG());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Las graficas necesitan numero par de elementos");
                        System.exit(-1);
                    }
                }
                // MONTICULOMINIMO
                if (i.esMonticulo) {
                    MonticuloSVG monti = new MonticuloSVG(elementos);
                    System.out.println(monti.SVG());
                }
            }
        } else {

            // NO ES ESTANDAR
            if (analizaParametros(pps[0]) == null) {
                System.out.println("El archivo esta vacio");
                System.exit(0);
            } else {
                Lista<String> depurada = i.depura(analizaParametros(pps[0]));
                Lista<Integer> elementos = new Lista<>();
                for (String string : depurada) {
                    try {
                        // CONVERTIMOS A INTEGERS
                        elementos.agrega(Integer.parseInt(string));
                    } catch (NumberFormatException e) {
                        System.out.println("Sólo se admiten enteros en los elementos");

                    }
                }
                if (i.nombre == null) {
                    System.out.println(
                            "NO HAY NOMBRE VALIDO PARA LA ESTRCUTURA, LOS VALIDOS SON: Cola, Pila, Lista, ArbolBinarioCompleto, ArbolBinarioOrdenado, ArbolRojinegro, ArbolAVL, Grafica, MonticuloMinimo");
                }
                if (!i.hayID) {
                    System.out.println("NO HAY IDENTIFICADOR PARA LA ESTRUCTURA, ADIOS");
                }
                // COLA
                if (i.esCola) {
                    ColaSVG cola = new ColaSVG();
                    cola.meteElementos(elementos);
                    System.out.println(cola.SVG());
                }
                // PILA
                if (i.esPila) {
                    PilaSVG pila = new PilaSVG();
                    pila.meteElementos(elementos);
                    System.out.println(pila.SVG());
                }
                // LISTA
                if (i.esLista) {
                    ListaSVG lista = new ListaSVG();
                    lista.agregaElementos(elementos);
                    System.out.println(lista.SVG());
                }
                // A. COMPLETO
                if (i.esCompleto) {
                    CompletoSVG completo = new CompletoSVG(elementos);
                    System.out.println(completo.SVG());
                }
                // A. ORDENADO
                if (i.esOrdenado) {
                    OrdenadoSVG ordenado = new OrdenadoSVG(elementos);
                    System.out.println(ordenado.SVG());
                }
                // A. ROJONEGRO
                if (i.esRojinegro) {
                    RojinegroSVG rojonegro = new RojinegroSVG(elementos);
                    System.out.println(rojonegro.SVG());
                }
                // AVL
                if (i.esAvl) {
                    AvlSVG avl = new AvlSVG(elementos);
                    System.out.println(avl.SVG());
                }
                // GRAFICA
                if (i.esGrafica) {
                    try {
                        GraficaSVG grafica = new GraficaSVG(elementos);
                        System.out.println(grafica.SVG());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Las graficas necesitan numero par de elementos");
                        System.exit(-1);
                    }
                }
                // MONTICULO MINIMO
                if (i.esMonticulo) {
                    MonticuloSVG monti = new MonticuloSVG(elementos);
                    System.out.println(monti.SVG());
                }
            }

        }
    }
}
