import java.util.Scanner;

public class Listar {
    public static void MenuListar() {
        Scanner scanner = new Scanner(System.in);
        
        
        int opcion = 0;
        do {
            System.out.println("\nLISTAR (Empleados/Clientes/Patinetes)\n");
            System.out.println("1. Listar usuarios empleados");
            System.out.println("2. Listar usuarios clientes");
            System.out.println("3. Listar todos los usuarios");
            System.out.println("4. Listar patinetes alquilados");
            System.out.println("5. Listar patinetes no alquilados");
            System.out.println("6. Atrás\n");

            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcion) {
                case 1:
                    System.out.println("\nListando usuarios empleados...\n");
                    break;
                case 2:
                    System.out.println("\nListando usuarios clientes...\n");
                    break;
                case 3:
                    System.out.println("\nListando todos los usuarios...\n");
                    break;
                case 4:
                    System.out.println("\nListando patinetes alquilados...\n");
                    break;
                case 5:
                    System.out.println("\nListando patinetes no alquilados...\n");
                    break;
                case 6:
                    System.out.println("\nVolviendo atrás...\n");
                    break;
                default:
                    System.out.println("\nOpción inválida, vuelva a intentarlo.\n");
            }
        } while (opcion != 6);
    }
}
