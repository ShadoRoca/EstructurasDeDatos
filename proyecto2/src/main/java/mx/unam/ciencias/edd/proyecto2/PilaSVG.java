package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;

public class PilaSVG extends SVGFunciones {

    private Lista<Integer> lista = new Lista<>();
    private String svg;
    private int elem = 0;

    public PilaSVG() {
    }

    public void meteElementos(Lista<Integer> elementos) {
        elem = elementos.getLongitud();
        while (!elementos.esVacia()) {
            lista.agrega(elementos.eliminaPrimero());
        }
    }

    private void iniciaSVG() {
        svg = "<?xml version='1.0' encoding='UTF-8' ?>" + "\n";
    }

    private void setSize() {
        int altura = elem * 100;
        svg = svg + "<svg width='300'" + " height='" + altura + "'>" + "\n" + "<g>" + "\n";
    }

    private void estructuraSVG() {
        int x = 100;
        int y = 0;
        while (!lista.esVacia()) {
            int i = lista.eliminaUltimo();
            svg = svg + rectuangulo(x, y, 100, 100);
            svg = svg + escribeNum(140, y + 53, i, "black");
            y += 100;
        }
    }

    private void terminaSVG() {
        svg = svg + "\n" + "</g>" + "\n" + "</svg>";
    }

    public String SVG() {
        iniciaSVG();
        setSize();
        estructuraSVG();
        terminaSVG();
        return svg;
    }

}