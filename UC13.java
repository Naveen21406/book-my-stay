import java.io.*;
import java.util.*;

// Inventory class (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " Available: " + inventory.get(key));
        }
    }
}

// Booking class (Serializable)
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roomType;

    public Booking(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Wrapper class to save both inventory + bookings
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    RoomInventory inventory;
    List<Booking> bookings;

    public SystemState(RoomInventory inventory, List<Booking> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // SAVE
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nData saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    // LOAD
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("Data loaded successfully!\n");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.\n");
        } catch (Exception e) {
            System.out.println("Corrupted data! Starting fresh.\n");
        }
        return null;
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        // Try to load existing data
        SystemState state = PersistenceService.load();

        RoomInventory inventory;
        List<Booking> bookings;

        if (state == null) {
            // Fresh start
            inventory = new RoomInventory();
            bookings = new ArrayList<>();

            // Add sample booking
            bookings.add(new Booking("Single Room"));
        } else {
            // Restore
            inventory = state.inventory;
            bookings = state.bookings;
        }

        // Display current state
        inventory.displayInventory();

        System.out.println("\nBookings:");
        for (Booking b : bookings) {
            System.out.println("Booked: " + b.getRoomType());
        }

        // Save state before exit
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.save(newState);
    }
}
