package ru.job4j.tracker;

public class FindByNameAction implements UserAction {
    @Override
    public String name() {
        return "Find by name";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        System.out.println("=== Find items by name ===");
        String name = input.askStr("Enter item name: ");
        tracker.findByName(name);
        return true;
    }
}
