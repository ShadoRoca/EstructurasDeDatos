package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import mx.unam.ciencias.edd.Lista;

/**
 * Clase que crea el código y el archivo html del índice
 */
public class IndexGen {

    Lista<Contador> contadores = new Lista<>();
    String html;
    GraficaSVG grafica;

    ClassLoader cl = this.getClass().getClassLoader();
    InputStream input = cl.getResourceAsStream("css/style.css");

    /**
     * Crea el código a partir de una lista de Contadores de archivos
     * @param lista la lista de los contadores
     */
    public IndexGen(Lista<Contador> lista) {
        this.contadores = lista;
        grafica = new GraficaSVG(lista);
        iniciaHTML();
        bodyStart();
        links();
        anexaGrafica();
        finalizaHTML();
    }

    /**
     * Genera el archivo css para los estilos de los archivos .html
     * @param ruta directorio donde se genera
     */
    public void generaCSS(String ruta) {
        File archivo = new File(ruta, "style.css");
        try {
            OutputStream out = new FileOutputStream(archivo);
            FileWriter fw = new FileWriter(archivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            int read;
            byte[] bytes = new byte[1024];
            while ((read = input.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            bw.close();
            System.out.println("Archivo style.css creado con éxito.");
        } catch (IOException e) {
            System.out.println("Hubo un error al crear el archivo style.css");
            System.exit(-1);
        }
    }

    private void iniciaHTML() {
        html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\">" + "\n" + "<html>" + "\n" + "<head>" + "\n"
                + "    <title>Indice</title>" + "\n"
                + "    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" + "\n" + "</head>";
    }

    private void bodyStart() {
        html = html + "\n" + "<body>" + "\n" + "   <h1 class=\"Encabezado\">" + "\n" + "       Indice de archivos"
                + "\n" + "   </h1>";
    }

    private void links() {
        html = html + "\n" + "  <div>" + "\n" + "       <h4>" + "\n" + "            Links de los reportes de archivos"
                + "\n" + "          <h4>" + "\n" + "        <ul>" + "\n";
        for (Contador contador : contadores) {
            html = html + "            <li type=\"square\">" + "<a href=\""
                    + contador.getNombre().replaceAll(".txt", "") + ".html" + "\">" + contador.getNombre() + " - "
                    + contador.getElementos() + " palabras." + "</a>" + "</li>" + "\n";
        }
        html = html + "        </ul>" + "\n" + "    </div>";
    }

    private void anexaGrafica() {
        html = html + "\n" + "<h4>GRÁFICA DE LOS ARCHIVOS</h4>" + "\n" + grafica.SVG() + "\n"
                + "<h3>La gráfica muestra los archivos como vértices y dos archivos son adyacentes si y sólo si comparten una palabra de al menos 7 carácteres en común</h3>";
    }

    private void finalizaHTML() {
        html = html + "\n" + "</body>" + "\n" + "</html>";
    }

    /**
     * Regresa el código html del indice
     */
    public String toString() {
        return html;
    }

    /**
     * Genera el archivo index.html
     * @param ruta donde se genera el archivo
     */
    public void generaHTML(String ruta) {
        File archivo = new File(ruta, "index.html");
        try {
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(html);
            bf.close();
            System.out.println("Archivo index.html creado con éxito.");
        } catch (IOException e) {
            System.out.println("Hubo un error al crear el archivo index.html");
            System.exit(-1);
        }
    }

}