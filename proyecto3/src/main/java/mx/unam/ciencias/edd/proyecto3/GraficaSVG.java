package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para hacer una grafica en svg
 */
public class GraficaSVG extends SVGFunciones {

    private Grafica<Contador> grafica = new Grafica<>();
    private int elem;
    private String svg;
    private int ancho;
    private int alto;
    private int[] auxX;
    private int[] auxY;
    private Contador[] auxE;

    /**
     * Crea una grafica
     * 
     * @param elementos lista de los elementos de la grafica
     * @throws IllegalArgumentException
     */
    public GraficaSVG(Lista<Contador> elementos) {
        for (Contador contador : elementos) {
            grafica.agrega(contador);
        }
        for (Contador contador : elementos) {
            for (Contador contador2 : elementos) {
                try {
                    if (contador.sonAdyacentes(contador2)) {
                        grafica.conecta(contador, contador2);
                    }
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        }
        elem = grafica.getElementos();
        ancho = alto = (50 * elem) * 2 + 350;
    }

    /**
     * inica el svg
     */
    private void iniciaSVG() {
        svg = "<svg class=\"graficaC\" xmlns=\"http://www.w3.org/2000/svg\" width='" + ancho + "'" + " height='" + alto + "'>" + "\n" + "<g>" + "\n";
    }

    /**
     * Dibuja los vertices
     */
    private void baseGrafica() {
        int r = 50 * elem;
        int angulo = 360 / elem;
        int i = 1;
        for (Contador contador : grafica) {
            svg = svg + circulo(coordenadaX(angulo * i, r) + ancho / 2, coordenadaY(angulo * i, r) + alto / 2, 50,
                    "white");
            svg = svg + escribePalabra(coordenadaX(angulo * i, r) + ancho / 2, coordenadaY(angulo * i, r) + alto / 2,
                    contador.getNombre(), "black");
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
        auxE = new Contador[elem];
        auxX = new int[elem];
        auxY = new int[elem];
        int i = 0;
        int angulo = 360 / elem;
        for (Contador contador : grafica) {
            auxX[i] = coordenadaX(angulo * (i + 1), 50 * elem) + ancho / 2;
            auxY[i] = coordenadaY(angulo * (i + 1), 50 * elem) + ancho / 2;
            auxE[i] = contador;
            i++;
        }
    }

    /**
     * Calcula la coordenada x de un vertice
     * 
     * @param angulo el angulo donde debe de estar
     * @param radio  el radio de la circunferencia donde esta el vertice
     * @return la coordenada x
     */
    private int coordenadaX(double angulo, int radio) {
        return (int) Math.floor(Math.cos(Math.toRadians(angulo)) * radio);
    }

    /**
     * Calcula la coordenada y de un vertice
     * 
     * @param angulo el angulo donde debe de estar
     * @param radio  el radio de la circunferencia donde esta el vertice
     * @return la coordenada y
     */
    private int coordenadaY(double angulo, int radio) {
        return (int) Math.floor(Math.sin(Math.toRadians(angulo)) * radio);
    }

    /**
     * termina el svg
     */
    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    /**
     * Crea el codigo svg de la grafica
     * 
     * @return el codigo
     */
    public String SVG() {
        iniciaSVG();
        construyeGrafica();
        baseGrafica();
        terminaSVG();
        return svg;
    }
}