package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

/**
 * Proyecto 1: Ordenador Lexicográfico estilo sort de Unix
 */

public class Proyecto1 {

    public static void main(String[] pps) throws ExcepcionBanderaSinID {

        // CASO DONDE SE PASA LA BANDERA -o SOLA Y SIN ID
        if (pps.length == 1 && BanderaO.hayBanderaO(pps)) {
            System.out.println("La bandera -o necesita de un identificador");
            return;
        }

        // CHECAR SI ES ESTÁNDAR
        if (Ordenador.esEstandar(pps)) {
            // CUANDO ES ESTÁNDAR Y NO HAY BANDERAS
            if (pps.length == 0) {
                Lista<Linea> texto = Ordenador.entradaEstandar();
                if (texto == null) {
                    System.out.println("No se detectó ningún archivo");// si no hay archivos terminamos
                    return;
                }
                while (!texto.esVacia()) {
                    System.out.println(texto.eliminaPrimero());// imprime en consola el texto ordenado
                }
                return;
            }
            // CUANDO ESTÁNDAR Y HAY BANDERA -r
            if (pps.length == 1 && BanderaR.hayBanderaR(pps)) {
                Lista<Linea> texto = Ordenador.entradaEstandar();
                if (texto == null) {
                    System.out.println("No se detectó ningún archivo");
                    return;
                }
                while (!texto.esVacia()) {
                    System.out.println(texto.eliminaUltimo());// Se imprime de ultimo a primero (reversa)
                }
                return;
            }
            // CUANDO ES ESTÁNDAR Y HAY BANDERA -o
            if (pps.length == 2 && BanderaO.hayBanderaO(pps)) {
                if (BanderaO.hayID(pps)) { // checamos si hay id
                    Lista<Linea> texto = Ordenador.entradaEstandar();
                    if (texto == null) {
                        System.out.println("No se detectó ningún archivo");// si no hay archivos terminamos
                        return;
                    }
                    String ruta = BanderaO.RegresaIdentificador(pps);// recuperamos el id

                    Ordenador.operacionDestructiva(texto, ruta);
                } else {
                    System.out.println("El identificador debe ir después de la bandera -o");// si no hay id notificamos
                                                                                            // y terminamos
                    return;
                }
                return;
            }
            // CUANDO ES ESTÁNDAR, HAY BANDERA -r, HAY BANDERA -o Y HAY IDENTIFICADOR
            if (pps.length == 3 && BanderaO.hayBanderaO(pps) && BanderaR.hayBanderaR(pps) && pps[0].equals("-r")) {
                if (BanderaO.hayID(pps)) {
                    Lista<Linea> texto = Ordenador.entradaEstandar();
                    if (texto == null) {
                        System.out.println("No se detectó ningún archivo");
                        return;
                    }
                    texto = texto.reversa();
                    String ruta = BanderaO.RegresaIdentificador(pps);

                    Ordenador.operacionDestructiva(texto, ruta);
                } else {
                    System.out.println("El identificador debe ir después de la bandera -o");
                }
                return;
            }

        } else { // NO ES ENTRADA ESTÁNDAR

            // CASO DONDE SOLO PASAN -r Y NINGUN ARCHIVO
            if (pps.length == 1 && BanderaR.hayBanderaR(pps)) {
                System.out.println("No ingresaste ningún archivo");
                return;
            }
            // CASO DONDE NO PASAN NADA
            if (pps.length == 0) {
                System.out.println("No ingresaste ningún archivo para ordenar");
                return;
            }
            // CASO DONDE HAY BANDERA -o Y NO HAY BANDERA -r
            if (BanderaO.hayBanderaO(pps) && !BanderaR.hayBanderaR(pps)) {
                if (BanderaO.hayID(pps)) {// checo si hay id
                    String ruta = BanderaO.RegresaIdentificador(pps);
                    Lista<String> archivos = new Lista<>(); // lista de los archivos
                    for (String string : pps) {
                        if (!string.equals("-o") && !string.equals(ruta)) { // Agregamos los archivos a una lista
                            archivos.agrega(string);
                        }
                    }
                    try {
                        Lista<Linea> regreso = Ordenador.ordenaArchivos(archivos);
                        Ordenador.operacionDestructiva(regreso, ruta);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                } else {
                    System.out.println("La bandera -o necesita un identificador");
                }
                return;
            }
            // CASO DONDE HAY BANERA -r Y NO HAY BANDERA -o
            if (BanderaR.hayBanderaR(pps) && !BanderaO.hayBanderaO(pps)) {
                Lista<String> archivos = new Lista<>();
                for (String string : pps) {
                    if (!string.equals("-r")) { // agregamos los archivos a una lista
                        archivos.agrega(string);
                    }
                }
                try {
                    Lista<Linea> regreso = Ordenador.ordenaArchivos(archivos);
                    while (!regreso.esVacia()) {
                        System.out.println(regreso.eliminaUltimo()); // imprimimos en reversa
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                }
                return;
            }
            // CASO DONDE HAY BANDERA -O Y SU IDENTIFICADOR ES IDENTICO A LA BANDERA -r
            if (BanderaO.hayBanderaO(pps) && BanderaO.RegresaIdentificador(pps).equals("-r")) {
                if (BanderaO.hayID(pps)) {
                    Lista<String> archivos = new Lista<>();
                    for (String string : pps) {
                        if (!string.equals("-o") && !string.equals("-r")) {
                            archivos.agrega(string);
                        }
                    }
                    Lista<Linea> regreso = Ordenador.ordenaArchivos(archivos);
                    Ordenador.operacionDestructiva(regreso, "-r");
                    return;
                }
            }
            // CASO DONDE ESTÁN AMBAS BANDERAS
            if (BanderaO.hayBanderaO(pps) && BanderaR.hayBanderaR(pps)) {
                if (BanderaO.hayID(pps)) {
                    String ruta = BanderaO.RegresaIdentificador(pps);
                    Lista<String> archivos = new Lista<>();
                    for (String string : pps) {
                        if (!string.equals("-o") && !string.equals(ruta) && !string.equals("-r")) {
                            archivos.agrega(string);
                        }
                    }
                    Lista<Linea> regreso = Ordenador.ordenaArchivos(archivos).reversa();
                    Ordenador.operacionDestructiva(regreso, ruta);
                } else {
                    System.out.println("La bandera -o necesita un identificador");
                    return;
                }
            }
            // CASO DONDE NO HAY NINGUNA BANDERA
            if (!BanderaO.hayBanderaO(pps) && !BanderaR.hayBanderaR(pps)) {
                Lista<String> archivos = new Lista<>();
                for (String string : pps) {
                    archivos.agrega(string);
                }
                Lista<Linea> regreso = Ordenador.ordenaArchivos(archivos);
                for (Linea linea : regreso) {
                    System.out.println(linea);
                }
                return;
            }
            // CASO DONDE EL IDENTIFICADOR DE -o ES IDENTICO A LA BANDERA -o
            if (BanderaO.hayBanderaO(pps) && BanderaO.RegresaIdentificador(pps).equals("-o")) {
                if (BanderaO.hayID(pps)) {
                    Lista<String> archivos = new Lista<>();
                    for (String string : pps) {
                        if (!string.equals("-o")) {
                            archivos.agrega(string);
                        }
                    }
                    Lista<Linea> regreso = Ordenador.ordenaArchivos(archivos);
                    Ordenador.operacionDestructiva(regreso, "-o");
                    return;
                }
            }

        }

    }
}