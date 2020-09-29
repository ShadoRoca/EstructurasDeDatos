package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase que genera el código y los archivos .html de los reportes de los
 * archivos de texto
 */
public class HTMLGen {

    Contador contador;
    BarChartSVG barras;
    PieChartSVG pie;
    RojinegroSVG rojinegro;
    AvlSVG avl;
    String html;
    Lista<Palabra> todas = new Lista<>();

    /**
     * Genera el código html a partir de un contador
     * 
     * @param c El contador del archivo
     */
    public HTMLGen(Contador c) {
        contador = c;
        barras = new BarChartSVG(c);
        pie = new PieChartSVG(c);
        rojinegro = new RojinegroSVG(c.top15());
        avl = new AvlSVG(c.top15());
        todas = c.getPalabras();
        iniciaHTML();
        bodyStart();
        agregaListaPalabras();
        anexaGraficas();
        anexaArboles();
        finalizaHTML();
    }

    /**
     * auxiliar para el código
     */
    private void iniciaHTML() {
        html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\">" + "\n" + "<html>" + "\n" + "<head>" + "\n"
                + "    <title>" + contador.getNombre() + "</title>" + "\n"
                + "    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" + "\n" + "</head>";
    }

    /**
     * Auxiliar para el código
     */
    private void finalizaHTML() {
        html = html + "\n" + "</body>" + "\n" + "</html>";
    }

    /**
     * Auxilair para el código
     */
    private void anexaGraficas() {
        html = html + "\n" + "<h4>GRÁFICA DE PASTEL</h4>" + pie.toString() + "\n" + "<h4>GRÁFICA DE BARRAS</h4>" + "\n"
                + barras.toString();
    }

    /**
     * auxiliar para el código
     */
    private void anexaArboles() {
        html = html + "\n" + "<h4>ÁRBOL AVL</h4>" + "\n" + avl.SVG() + "\n" + "<h4>ÁRBOL ROJINEGRO</h4>" + "\n"
                + rojinegro.SVG();
    }

    /**
     * auxiliar para el código
     */
    private void bodyStart() {
        html = html + "\n" + "<body>" + "\n" + "   <h1 class=\"Encabezado\">" + "\n" + "       Reporte de archivo"
                + "\n" + "   </h1>" + "\n" + "   <h2>" + "\n" + "       " + contador.getNombre() + "\n" + "   </h2>"
                + "\n" + "   <h3>" + "\n" + "       Número de palabras en el archivo: " + contador.getElementos() + "\n"
                + "   </h3>";
    }

    /**
     * auxiliar para el código
     */
    private void agregaListaPalabras() {
        html = html + "\n" + "  <div>" + "\n" + "       <h4>" + "\n" + "            Lista de las palabras en el archivo"
                + "\n" + "          <h4>" + "\n" + "        <ul>" + "\n";
        for (Palabra palabra : todas) {
            html = html + "            <li type=\"square\">" + palabra.toString() + " - " + palabra.valor() + "</li>"
                    + "\n";
        }
        html = html + "        </ul>" + "\n" + "    </div>";
    }

    /**
     * Regresa el código html del reporte del archivo
     */
    public String toString() {
        return html;
    }

    /**
     * Crea el archivo .html en la ruta específicada
     * @param ruta el directorio donde se crea el archivo
     */
    public void generaHTML(String ruta) {
        File archivo = new File(ruta, contador.getNombre().replaceAll(".txt", "") + ".html");
        try {
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(html);
            bf.close();
            System.out.println("Archivo " + contador.getNombre().replaceAll(".txt", "") + ".html creado con éxito.");
        } catch (IOException e) {
            System.out.println("Hubo un error al crear el archivo " + contador.getNombre() + ".html");
            System.exit(-1);
        }
    }

}