import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class PollingServer implements PollingInterface {
    private int yesCount = 0;
    private int noCount = 0;
    private int dontCareCount = 0;

    // Lock object for mutual exclusion
    private final Object lock = new Object();

    public void submitVote(String vote) throws RemoteException {
        synchronized(lock) {
            if ("yes".equalsIgnoreCase(vote)) {
                yesCount++;
            } else if ("no".equalsIgnoreCase(vote)) {
                noCount++;
            } else if ("don't care".equalsIgnoreCase(vote) || "dont care".equalsIgnoreCase(vote)) {
                dontCareCount++;
            } else {
                throw new RemoteException("Invalid vote option");
            }
        }
    }

    public String getCurrentCounts() throws RemoteException {
        synchronized(lock) {
            return yesCount + " yes, " + noCount + " no, " + dontCareCount + " don't care";
        }
    }

    public static void main(String[] args) {
        try {
            PollingServer obj = new PollingServer();
            PollingInterface stub = (PollingInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object in the registry
            Registry registry = LocateRegistry.createRegistry(1099); // default port
            registry.bind("PollingService", stub);

            System.out.println("Polling Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
