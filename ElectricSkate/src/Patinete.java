import java.util.Scanner;

public class Patinete {
	
	public static void MenuGestionarPatinetes() {
        Scanner scanner = new Scanner(System.in);
        
        int opcion = 0;
        do {
        	System.out.println();
            System.out.println("GESTIONAR PATINETES");
            System.out.println();
            System.out.println("1. A�adir Patinete");
            System.out.println("2. Realizar Alquiler");
            System.out.println("3. Realizar Devoluci�n");
            System.out.println("4. Atr�s\n");

            System.out.print("Elige una opci�n: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcion) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    System.out.println("\nVolviendo atr�s...\n");
                    break;
                default:
                    System.out.println("\nOpci�n inv�lida, vuelva a intentarlo.\n");
            }
        } while (opcion != 6);
    }
	
}
