package ru.job4j.tracker;

public class FIndByIdAction implements UserAction {
    @Override
    public String name() {
        return "Find by id";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        System.out.println("=== Find item by id ===");
        int id = input.askInt("Enter id: ");
        tracker.findById(id);
        return true;
    }
}
