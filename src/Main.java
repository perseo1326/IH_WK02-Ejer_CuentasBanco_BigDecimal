import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {

    private static int contador = 1;
    private static final ArrayList<Cuenta> cuentas = new ArrayList<>();

    // Creacion de una cuenta
    public static void crearCuenta(String DNI, String name, String lastName) {

        String nuevoIBAN = generarIBAN();

        Cuenta unaCuenta = new Cuenta(nuevoIBAN, DNI, name, lastName);
        cuentas.add(unaCuenta);
        System.out.println("Anadir cuenta " + unaCuenta);

    }

    // Encontrar una cuenta segun el IBAN
    public static Cuenta obtenerCuentaIBAN(String IBAN) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getIBAN().equals(IBAN)) {
                return cuenta;
            }
        }
        System.out.println("Cuenta con IBAN " + IBAN + " NO encontrada.");
        return null;
    }

    // Encontrar cuentas de un mismo DNI
    public static ArrayList<Cuenta> encontrarCuentasDNI(String DNI) {
        ArrayList<Cuenta> cuentasDNI = new ArrayList<>();
        for ( Cuenta cuenta : cuentas ) {
            if ( cuenta.getDNI().equals(DNI)) {
                cuentasDNI.add(cuenta);
            }
        }
        return cuentasDNI;
    }

    // Rango de saldos Min y Max
    public static ArrayList<Cuenta> encontrarCuentasRango(BigDecimal rangoMin, BigDecimal rangoMax) {
        ArrayList<Cuenta> cuentasRango = new ArrayList<>();
        for ( Cuenta cuenta : cuentas ) {
            if ( rangoMin.compareTo(cuenta.getSaldoBigDecimal()) <= 0 && rangoMax.compareTo(cuenta.getSaldoBigDecimal()) >= 0 ) {
                cuentasRango.add(cuenta);
            }
        }
        return cuentasRango;
    }

    // Deposito bancario
    public static void ingresarDineroCuenta(String iban, BigDecimal cantidad) {
        Cuenta cuenta = obtenerCuentaIBAN(iban);
        if (cuenta != null) {
            cuenta.addSaldo(cantidad);
        }
    }

    // Retiro bancario
    public static boolean retirarDineroCuenta(String iban, BigDecimal cantidad) {
        boolean status = false;
        Cuenta cuenta = obtenerCuentaIBAN(iban);
        if (cuenta == null) {
            System.out.println("La cuenta con IBAN: " + iban + " NO encontrada!");
            return false;
        } else {
            if (cuenta.restaSaldo(cantidad)) {
                System.out.println("Retiro de " + cantidad + "€ efectuado correctamente.");
                status = true;
            } else {
                System.out.println("Fondos insuficientes");
            }
        }
        return status;
    }

    // transferencia entre cuentas
    public static boolean transferenciaCuentas(String ibanOrigen, String ibanDestino, BigDecimal cantidad) {
        Cuenta cuentaOrigen = obtenerCuentaIBAN(ibanOrigen);
        Cuenta cuentaDestino = obtenerCuentaIBAN(ibanDestino);

        if (cuentaOrigen != null && cuentaDestino != null) {
            if (retirarDineroCuenta(ibanOrigen, cantidad)) {
                ingresarDineroCuenta(ibanDestino, cantidad);
                return true;
            }
        }
        return false;
    }

    public static String generarIBAN() {
/*
        int DESDE = 3;
        int HASTA = 100;
        // Generar un aleatorio en un rango
        Integer nuevoIBAN = (int)(Math.random() * (HASTA - DESDE + 1) + DESDE);
        return nuevoIBAN.toString();
 */
        return ("IBAN" + contador++);
    }

    public static void mostrarCuentas() {
        System.out.println("\nValores de las cuentas: ");
        for (Cuenta c : cuentas ) {
            System.out.println(c.toString());
        }
    }

    //////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {

        String buscarIban = "IBAN3";
        String buscarDNI = "123K";
        Cuenta cuentaTemp ;

        // 1. Añadir cuenta bancaria "crearCuenta"
        System.out.println("\n1. Añadir cuentas bancarias:");
        crearCuenta("456L", "Jose", "Osorio");
        crearCuenta("789P", "Maria", "Lopez");
        crearCuenta("123K", "Juan", "Perez");
        crearCuenta("123K", "Juan", "Perez");
        crearCuenta("147A", "Ana", "Ruiz");
        crearCuenta("147A", "Ana", "Ruiz");

        // 2. Buscar cuenta por IBAN "obtenerCuentaIBAN"
        System.out.println("\n2. Buscar cuenta por IBAN: ");
        System.out.println("Cuenta con IBAN: " + buscarIban + " => " + obtenerCuentaIBAN(buscarIban));

        // 3. buscar cuentas con DNI -> encontrarCuentasDNI
        System.out.println("\n3. Buscar cuentas de un mismo DNI: ");
        System.out.println("Cuentas que pertencen al DNI: " + buscarDNI);
        for (Cuenta c : encontrarCuentasDNI(buscarDNI) ) {
            System.out.println(c);
        }

        // 4. Ingreso de dinero en cuenta segun IBAN "ingresarDineroCuenta"
        System.out.println("\n4. Realizar un deposito en cuenta: ");
        cuentaTemp = obtenerCuentaIBAN(buscarIban);
        if (cuentaTemp != null) {
            System.out.println("Saldo actual cuenta IBAN: " + buscarIban + " => " + cuentaTemp.getSaldo() + "€");
            ingresarDineroCuenta(buscarIban, new BigDecimal(1200.0d));
            System.out.println("Saldo actual cuenta IBAN: " + buscarIban + " => " + cuentaTemp.getSaldo() + "€");
        }

        // 5. Retiro de cuenta segun IBAN "retirarDineroCuenta"
        System.out.println("\n5. Realizar un retiro de una cuenta:");
        cuentaTemp = obtenerCuentaIBAN(buscarIban);
        if (cuentaTemp != null) {
            System.out.println("Saldo actual cuenta IBAN: " + buscarIban + " => " + cuentaTemp.getSaldo() + "€");
            retirarDineroCuenta(buscarIban, new BigDecimal(110d));
            System.out.println("Saldo actual cuenta IBAN: " + buscarIban + " => " + cuentaTemp.getSaldo() + "€");
        }

        // 6. transferencia
        System.out.println("\n6. Realizar una transferencia entre cuentas: ");
        String ibanDestino = "IBAN5";
        cuentaTemp = obtenerCuentaIBAN(buscarIban);
        Cuenta cuentaDestino = obtenerCuentaIBAN(ibanDestino);

        if (cuentaTemp != null && cuentaDestino != null) {
            if (transferenciaCuentas(buscarIban, ibanDestino, new BigDecimal(110d))) {
                System.out.println("Transferencia realizada correctamente!");
            } else {
                System.out.println("Fallo al procesar la transferencia.");
            }
        }

        // 7. encontrar cuentas con saldo dentro de un rango "encontrarCuentasRango"
        System.out.println("\n7. Buscar cuentas por saldo dentro de un determinado rango: ");

        // Modificar los saldos de las cuentas con valores para hacer la prueba
        BigDecimal temp = new BigDecimal(0);
        BigDecimal incremento = new BigDecimal(30);
        for ( Cuenta c : cuentas ) {
            c.addSaldo(temp);
            temp = temp.add(incremento);
        }

        mostrarCuentas();

        BigDecimal rangoMinimo = new BigDecimal(10);
        BigDecimal rangoMaximo = new BigDecimal(150);
        System.out.println("\nCuentas que pertencen al Rango (" + rangoMinimo + ", " + rangoMaximo + ") ");
        for (Cuenta c : encontrarCuentasRango(rangoMinimo, rangoMaximo) ) {
            System.out.println(c);
        }

    }



}