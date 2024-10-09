//package com.example.tidskapslar.uppgift2;
//
//import com.example.tidskapslar.uppgift2.service.AuthService;
//import com.example.tidskapslar.uppgift2.service.MessageService;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//public class ConsoleApp {
//
//    private static String jwtToken = null;  //sparar JWT-token efter inloggning
//    private static Long userId = null;       //sparar användarens ID efter inloggning
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("\n--- Tidskapsel Meny ---");
//            System.out.println("1. Registrera dig");
//            System.out.println("2. Logga in");
//            System.out.println("3. Skapa ett meddelande");
//            System.out.println("4. Visa dina meddelanden");
//            System.out.println("5. Avsluta");
//            System.out.print("Välj ett alternativ: ");
//            choice = scanner.nextInt();
//            scanner.nextLine();  // Konsumera newline
//
//            switch (choice) {
//                case 1:
//                    registerUser(scanner);
//                    break;
//                case 2:
//                    login(scanner);
//                    break;
//                case 3:
//                    createMessage(scanner);
//                    break;
//                case 4:
//                    viewMessages();
//                    break;
//                case 5:
//                    System.out.println("Avslutar...");
//                    break;
//                default:
//                    System.out.println("Ogiltigt alternativ. Försök igen.");
//            }
//        } while (choice != 5);
//
//        scanner.close();
//    }
//
//    //metod för användarregistrering
//    private static void registerUser(Scanner scanner) {
//        System.out.print("Ange e-post: ");
//        String email = scanner.nextLine();
//        System.out.print("Ange lösenord: ");
//        String password = scanner.nextLine();
//        boolean success = AuthService.register(email, password);
//        if (success) {
//            System.out.println("Användare registrerad framgångsrikt!");
//        } else {
//            System.out.println("Registreringen misslyckades. E-post kan redan vara upptagen.");
//        }
//    }
//
//    //iinloggning
//    private static void login(Scanner scanner) {
//        System.out.print("Ange e-post: ");
//        String email = scanner.nextLine();
//        System.out.print("Ange lösenord: ");
//        String password = scanner.nextLine();
//        var authResponse = AuthService.authenticate(email, password);
//        if (authResponse != null && authResponse.containsKey("token")) {
//            jwtToken = authResponse.get("token");
//            userId = Long.parseLong(authResponse.get("userId"));
//            System.out.println("Inloggning lyckades! Token sparad.");
//        } else {
//            System.out.println("Inloggning misslyckades. Kontrollera dina uppgifter.");
//        }
//    }
//
//    //skapa ett meddelande
//    private static void createMessage(Scanner scanner) {
//        if (jwtToken == null) {
//            System.out.println("Du måste logga in först!");
//            return;
//        }
//        System.out.print("Ange meddelande att spara: ");
//        String messageContent = scanner.nextLine();
//        boolean success = MessageService.createMessage(userId, messageContent, jwtToken);
//        if (success) {
//            System.out.println("Meddelandet har sparats och krypterats!");
//        } else {
//            System.out.println("Misslyckades med att spara meddelandet.");
//        }
//    }
//
//    //visa all meddelanden
//    private static void viewMessages() {
//        if (jwtToken == null) {
//            System.out.println("Du måste logga in först!");
//            return;
//        }
//        List<String> messages = MessageService.getUserMessages(userId, jwtToken);
//        if (messages != null && !messages.isEmpty()) {
//            System.out.println("Dina meddelanden:");
//            for (String message : messages) {
//                System.out.println(message);
//            }
//        } else {
//            System.out.println("Inga meddelanden hittades.");
//        }
//    }
//}