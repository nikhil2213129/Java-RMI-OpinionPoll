import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PollingInterface extends Remote {
    void submitVote(String vote) throws RemoteException;
    String getCurrentCounts() throws RemoteException;
}
