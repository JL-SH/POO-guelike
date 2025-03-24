import kotlinx.serialization.Serializable

@Serializable
class Inventory {
    val items = mutableListOf<Item>() // List to hold items
    val resources = mutableListOf<Resource>() // List to hold resources

    // Method to add an item to the inventory and display a message
    fun addItem(item: Item, output: Output) {
        items.add(item)
        output.showMsg("✅ Added ${item.name} to your inventory.\n")
    }

    // Method to use (remove) an item from the inventory
    fun useItem(item: Item) {
        items.remove(item)
    }

    // Method to display the items in the inventory
    fun showItems(output: Output) {
        output.clearConsole()
        output.showMsg("\n===== 🎒 Inventory: Items =====\n")

        if (items.isNotEmpty()) {
            // If there are items in the inventory, display them
            items.forEachIndexed { index, item ->
                output.showMsg("${index + 1} -> ${item.name}")
            }
        } else {
            // If no items are found, show a message
            output.showMsg("You have no items.\n")
        }

        output.pressEnter()
    }

    // Method to add a resource to the inventory and display a message
    fun addResource(resource: Resource, output: Output) {
        resources.add(resource)
        output.showMsg("🔹 Added ${resource.name} to your inventory.\n")
    }

    // Method to display the resources in the inventory
    fun showResources(output: Output) {
        output.clearConsole()
        output.showMsg("\n===== 🔨 Inventory: Resources =====\n")

        if (resources.isNotEmpty()) {
            // If there are resources in the inventory, display them
            resources.forEachIndexed { index, resource ->
                output.showMsg("${index + 1} -> ${resource.name}")
            }
        } else {
            // If no resources are found, show a message
            output.showMsg("You have no resources.\n")
        }

        output.pressEnter()
    }

    // Method to show a summary of resources and their points total
    fun showResourcesAndGetTotal(output: Output) {
        output.clearConsole()
        output.showMsg("\n===== 🔄 Resource Summary =====\n")

        var totalPoints = 0 // Variable to keep track of the total points of resources

        if (resources.isNotEmpty()) {
            // If there are resources, display each resource's name and points
            resources.forEach { resource ->
                output.showMsg("${resource.name} | Points: ${resource.points}")
                totalPoints += resource.points // Add points to the total
            }
        } else {
            // If no resources are found, show a message
            output.showMsg("You have no resources.\n")
        }

        output.showMsg("🌟 Total points: $totalPoints\n") // Display the total points
        output.pressEnter()
    }

    // Method to get all items in the inventory as a list
    fun getAllItems(): List<Item> = items.toList()

    // Method to get all equipment items from the inventory (filtering only Equipment types)
    fun getEquipments(): List<Item.Equipment> = items.filterIsInstance<Item.Equipment>()

    // Method to remove equipment items from the inventory
    fun removeEquipments(equipments: List<Item.Equipment>) {
        items.removeAll(equipments) // Removes the specified equipments from the inventory
    }
}

