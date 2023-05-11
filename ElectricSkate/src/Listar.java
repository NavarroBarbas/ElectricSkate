import java.util.Scanner;

public class Listar {
    public static void MenuListar() {
        Scanner scanner = new Scanner(System.in);
        /
        
        int opcion = 0;
        do {
            System.out.println("LISTAR (Empleados/Clientes/Patinetes)");
            System.out.println("1. Listar usuarios empleados");
            System.out.println("2. Listar usuarios clientes");
            System.out.println("3. Listar todos los usuarios");
            System.out.println("4. Listar patinetes alquilados");
            System.out.println("5. Listar patinetes no alquilados");
            System.out.println("6. Volver atrás");

            opcion = scanner.nextInt();

            switch(opcion) {
                case 1:
                    System.out.println("Listando usuarios empleados...");
                    break;
                case 2:
                    System.out.println("Listando usuarios clientes...");
                    break;
                case 3:
                    System.out.println("Listando todos los usuarios...");
                    Listar.MenuListar();

                    break;
                case 4:
                    System.out.println("Listando patinetes alquilados...");
                    break;
                case 5:
                    System.out.println("Listando patinetes no alquilados...");
                    break;
                case 6:
                    System.out.println("Volviendo atrás...");
                    break;
                default:
                    System.out.println("Opción inválida, vuelva a intentarlo.");
            }
        } while (opcion != 6);

        scanner.close();
    }
}
