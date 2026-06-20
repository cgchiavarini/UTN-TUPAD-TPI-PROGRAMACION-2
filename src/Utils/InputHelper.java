/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Utils;
import java.util.Scanner;

/**
 *
 * @author Carlos Chiavarini
 */

public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String leerTexto(String mensaje) {
        String texto;

        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Error: el valor no puede estar vacío.");
            }

        } while (texto.isEmpty());

        return texto;
    }

    public String leerTextoOpcional(String mensaje, String valorActual) {
        System.out.print(mensaje + " [" + valorActual + "]: ");
        String texto = scanner.nextLine().trim();

        if (texto.isEmpty()) {
            return valorActual;
        }

        return texto;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero.");
            }
        }
    }

    public int leerEnteroOpcional(String mensaje, int valorActual) {
        while (true) {
            try {
                System.out.print(mensaje + " [" + valorActual + "]: ");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    return valorActual;
                }

                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero.");
            }
        }
    }

    public Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido.");
            }
        }
    }

    public Double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }

    public Double leerDoubleOpcional(String mensaje, Double valorActual) {
        while (true) {
            try {
                System.out.print(mensaje + " [" + valorActual + "]: ");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    return valorActual;
                }

                return Double.parseDouble(entrada);

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }

    public boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("S")) {
                return true;
            }

            if (entrada.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Error: debe ingresar S o N.");
        }
    }

    public boolean leerBooleanOpcional(String mensaje, boolean valorActual) {
        String actual = valorActual ? "S" : "N";

        while (true) {
            System.out.print(mensaje + " (S/N) [" + actual + "]: ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                return valorActual;
            }

            if (entrada.equalsIgnoreCase("S")) {
                return true;
            }

            if (entrada.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Error: debe ingresar S o N.");
        }
    }

    public void pausar() {
        System.out.println();
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
    }
}