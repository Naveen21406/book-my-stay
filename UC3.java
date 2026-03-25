import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("Room Inventory:\n");

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

public class UseCase3InventorySetup {
    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println("\nUpdating availability...\n");

        // Update values
        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Double Room", 2);

        // Display updated inventory
        inventory.displayInventory();
    }
}
