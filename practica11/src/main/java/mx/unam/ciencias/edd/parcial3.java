package mx.unam.ciencias.edd;

import java.io.*;
import java.text.*;
import java.text.Normalizer.Form;

public class parcial3 {

    private static class X implements Comparable<X> {
        public String s, l;

        public X(String l) {
            this.l = l;
            s = Normalizer.normalize(l, Form.NFKD);
            s = s.replaceAll("[^\\p{IsAlphabetic}\\s]", "");
            s = s.toLowerCase();
            s = s.trim();
        }

        @Override
        public int compareTo(X o) {
            return s.compareTo(o.s);
        }

        public String toString() {
            return s;
        }
    }

    private static void intercambia(X[] xs, int a, int b) {
        X t = xs[a];
        xs[a] = xs[b];
        xs[b] = t;
    }

    private static X buscaN(X[] xs, int N, int a, int b) {
        if (b < a) {
            throw new IllegalArgumentException();
        }
        if (a == b) {
            return xs[a];
        }
        int i = a + 1, j = b;
        while (i < j) {
            if ((xs[i].compareTo(xs[a]) > 0) && xs[j].compareTo(xs[a]) <= 0) {
                intercambia(xs, i++, j--);
            } else if (xs[i].compareTo(xs[a]) <= 0) {
                i++;
            } else {
                j--;
            }
        }
        if (xs[i].compareTo(xs[a]) > 0) {
            i--;
        }
        intercambia(xs, a, i);
        if (i == N) {
            return xs[i];
        }
        return (i > N) ? buscaN(xs, N, a, i - 1) : buscaN(xs, N, i + 1, b);
    }

    public static void main(String[] args) {
        Lista<X> l = new Lista<>();
        String contenido = "";
        int N = -1;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
            String linea = null;
            while((linea = in.readLine()) != null) {
                l.agrega(new X(linea));
            }
            in.close();
            N = Integer.parseInt(args[1]);
        } catch (IOException e) {
            System.exit(-1);
        }

        int c = 0;
        X[] xs = new X[l.getElementos()];
        for (X x : l) {
            xs[c++] = x;
        }
        X x = buscaN(xs, N, 0, xs.length-1);
        System.out.println(l);
        System.out.println(x.l);
        System.out.println(Lista.mergeSort(l));
    }
}