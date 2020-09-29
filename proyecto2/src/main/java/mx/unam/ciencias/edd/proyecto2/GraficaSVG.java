package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para hacer una grafica en svg
 */
public class GraficaSVG extends SVGFunciones {

    private Grafica<Integer> grafica = new Grafica<>();
    private int elem;
    private String svg;
    private int ancho;
    private int alto;
    private int[] auxX;
    private int[] auxY;
    private int[] auxE;

    /**
     * Crea una grafica 
     * @param elementos lista de los elementos de la grafica
     * @throws IllegalArgumentException
     */
    public GraficaSVG(Lista<Integer> elementos) throws IllegalArgumentException {
        if (elementos.getLongitud() % 2 != 0) {
            throw new IllegalArgumentException("No hay número par de elementos");
        }
        analizaElementos(elementos);
        elem = grafica.getElementos();
        ancho = alto = (50 * elem) * 2 + 350;
    }

    /**
     * auxiliar para analizar la lista de elementos y determinar vecinos y vertices
     * @param elementos la lista de elementos
     */
    private void analizaElementos(Lista<Integer> elementos) {
        for (int i = 0; i < elementos.getLongitud() - 1; i += 2) {
            for (int j = 1; j < elementos.getLongitud(); j += 2) {
                // CHECAMOS NUMEROS CONSECUTIVOS
                if (j == i + 1) {
                    // CASO DONDE EL PRIMER NUMERO DE LA PAREJA AUN NO ESTA EN LA GRAFICA
                    if (!grafica.contiene(elementos.get(i))) {
                        // AGREGAMOS EL NUMERO
                        grafica.agrega(elementos.get(i));
                        // CHECAMOS SI EL SEGUNDO NUMERO ES DIFERENTE DEL PRIMERO
                        if (elementos.get(i) != elementos.get(j)) {
                            // CHECAMOS SI YA ESTA EL SEGUNDO
                            if (grafica.contiene(elementos.get(j))) {
                                // SI ESTA ENTONCES SOLO CONECTAMOS
                                grafica.conecta(elementos.get(i), elementos.get(j));
                            } else {
                                // SI NO ESTA ENTONCES AGREGAMOS Y CONECTAMOS
                                grafica.agrega(elementos.get(j));
                                grafica.conecta(elementos.get(i), elementos.get(j));
                            }
                        }
                        // CASO DONDE EL PRIMER NUMERO YA ESTA EN LA GRAFICA
                    } else {
                        if (elementos.get(i) != elementos.get(j)) {
                            // CHECAMOS SI EL SEGUNDA YA ESTA
                            if (grafica.contiene(elementos.get(j))) {
                                // SI YA ESTA SOLO CONECTAMOS
                                if (!grafica.sonVecinos(elementos.get(i), elementos.get(j))) {
                                    grafica.conecta(elementos.get(i), elementos.get(j));
                                }
                            } else {
                                // SI NO ESTA ENTONCES AGREGAMOS Y CONECTAMOS
                                grafica.agrega(elementos.get(j));
                                grafica.conecta(elementos.get(i), elementos.get(j));
                            }
                        }
                    }
                } else {
                    // SALTAMOS CASOS DONDE NO SE CHECAN NUMEROS CONSECUTIVOS
                    continue;
                }
            }
        }
    }

    /**
     * inica el svg
     */
    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    /**
     * Dibuja los vertices
     */
    private void baseGrafica() {
        int r = 50 * elem;
        int angulo = 360 / elem;
        int i = 1;
        for (Integer integer : grafica) {
            svg = svg + circulo(coordenadaX(angulo * i, r) + ancho / 2, coordenadaY(angulo * i, r) + alto / 2, 50,
                    "white");
            svg = svg + escribeNum(coordenadaX(angulo * i, r) + ancho / 2, coordenadaY(angulo * i, r) + alto / 2,
                    integer, "black");
            i++;
        }
    }

    /**
     * Estructura la grafica
     */
    private void construyeGrafica() {
        defineCoordenas();
        for (int i = 0; i < grafica.getElementos(); i++) {
            for (int j = 0; j < grafica.getElementos(); j++) {
                if (grafica.sonVecinos(auxE[i], auxE[j])) {
                    svg = svg + pendiente(auxX[i], auxY[i], auxX[j], auxY[j]);
                }
            }
        }
    }

    /**
     * Guarda las coordenadas de los vertices
     */
    private void defineCoordenas() {
        auxE = new int[elem];
        auxX = new int[elem];
        auxY = new int[elem];
        int i = 0;
        int angulo = 360 / elem;
        for (Integer integer : grafica) {
            auxX[i] = coordenadaX(angulo * (i + 1), 50 * elem) + ancho / 2;
            auxY[i] = coordenadaY(angulo * (i + 1), 50 * elem) + ancho / 2;
            auxE[i] = integer;
            i++;
        }
    }

    /**
     * Calcula la coordenada x de un vertice
     * @param angulo el angulo donde debe de estar
     * @param radio el radio de la circunferencia donde esta el vertice
     * @return la coordenada x
     */
    private int coordenadaX(double angulo, int radio) {
        return (int) Math.floor(Math.cos(Math.toRadians(angulo)) * radio);
    }

    /**
     * Calcula la coordenada y de un vertice
     * @param angulo el angulo donde debe de estar
     * @param radio el radio de la circunferencia donde esta el vertice
     * @return la coordenada y
     */
    private int coordenadaY(double angulo, int radio) {
        return (int) Math.floor(Math.sin(Math.toRadians(angulo)) * radio);
    }

    /**
     * determina el tamaño del svg
     */
    private void setSize() {
        svg = svg + "<svg width='" + ancho + "'" + " height='" + alto + "'>" + "\n" + "<g>" + "\n";
    }

    /**
     * termina el svg
     */
    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    /**
     * Crea el codigo svg de la grafica
     * @return el codigo
     */
    public String SVG() {
        iniciaSVG();
        setSize();
        construyeGrafica();
        baseGrafica();
        terminaSVG();
        return svg;
    }
}