package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {
    }

    /**
     * Método que combina 4 bytes en un entero de 32 bits usando el esquema
     * big-endian o little-endian
     * 
     * @param a El primer byte
     * @param b El segundo byte
     * @param c El tercer byte
     * @param d El cuarto byte
     * @return Un entero de 32 bits
     */
    private static int combina(byte a, byte b, byte c, byte d) {
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));
    }

    /**
     * Función de dispersión XOR.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int r = 0;
        int n;
        for (int i = 0; i < llave.length; i += 4) {
            if (i + 1 >= llave.length) {
                n = combina(llave[i], (byte) 00, (byte) 00, (byte) 00);
            } else if (i + 2 >= llave.length) {
                n = combina(llave[i], llave[i + 1], (byte) 00, (byte) 00);
            } else if (i + 3 >= llave.length) {
                n = combina(llave[i], llave[i + 1], llave[i + 2], (byte) 00);
            } else {
                n = combina(llave[i], llave[i + 1], llave[i + 2], llave[i + 3]);
            }
            r = r ^ n;
        }
        return r;
    }

    /**
     * Método privado auxiliar para el hash de Jenkins, mezcla y modifica los
     * valores de 3 integers
     * 
     * @param a el primer int
     * @param b el segundo int
     * @param d el tercer int
     * @return Un arreglo con los nuevos valores de a, b y c
     */
    private static int[] mezcla(int a, int b, int c) {
        int[] arreglo = new int[3];

        a -= b;
        a -= c;
        a ^= (c >>> 13);
        b -= c;
        b -= a;
        b ^= (a << 8);
        c -= a;
        c -= b;
        c ^= (b >>> 13);
        a -= b;
        a -= c;
        a ^= (c >>> 12);
        b -= c;
        b -= a;
        b ^= (a << 16);
        c -= a;
        c -= b;
        c ^= (b >>> 5);
        a -= b;
        a -= c;
        a ^= (c >>> 3);
        b -= c;
        b -= a;
        b ^= (a << 10);
        c -= a;
        c -= b;
        c ^= (b >>> 15);

        arreglo[0] = a;
        arreglo[1] = b;
        arreglo[2] = c;

        return arreglo;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;
        int longitud = llave.length;
        int i = 0;
        int[] arreglo;

        while (longitud >= 12) {
            a += combina(llave[i + 3], llave[i + 2], llave[i + 1], llave[i]);
            b += combina(llave[i + 7], llave[i + 6], llave[i + 5], llave[i + 4]);
            c += combina(llave[i + 11], llave[i + 10], llave[i + 9], llave[i + 8]);
            arreglo = mezcla(a, b, c);
            a = arreglo[0];
            b = arreglo[1];
            c = arreglo[2];
            longitud = longitud - 12;
            i += 12;
        }
        c += llave.length;
        switch (longitud) {
            case 11:
                c += (llave[i + 10] & 0xFF) << 24;
            case 10:
                c += (llave[i + 9] & 0xFF) << 16;
            case 9:
                c += (llave[i + 8] & 0xFF) << 8;
            case 8:
                b += (llave[i + 7] & 0xFF) << 24;
            case 7:
                b += (llave[i + 6] & 0xFF) << 16;
            case 6:
                b += (llave[i + 5] & 0xFF) << 8;
            case 5:
                b += (llave[i + 4] & 0xFF);
            case 4:
                a += (llave[i + 3] & 0xFF) << 24;
            case 3:
                a += (llave[i + 2] & 0xFF) << 16;
            case 2:
                a += (llave[i + 1] & 0xFF) << 8;
            case 1:
                a += (llave[i] & 0xFF);
        }
        arreglo = mezcla(a, b, c);
        c = arreglo[2];
        return c;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * 
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;
        for (int i = 0; i < llave.length; i++) {
            h = h * 33 + (llave[i] & 0xff);
        }
        return h;
    }
}
