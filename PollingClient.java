	import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PollingClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null); // localhost
            PollingInterface stub = (PollingInterface) registry.lookup("PollingService");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter your option (yes, no, don't care) or type 'count' to see results or 'exit' to quit:");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                } else if (input.equalsIgnoreCase("count")) {
                    String results = stub.getCurrentCounts();
                    System.out.println("Current counts: " + results);
                } else {
                    try {
                        stub.submitVote(input);
                        System.out.println("Vote submitted successfully.");
                    } catch (Exception e) {
                        System.out.println("Invalid input. Valid inputs are yes, no, don't care.");
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
