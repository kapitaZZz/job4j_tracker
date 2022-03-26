package ru.job4j.tracker;

public class ShowAction implements UserAction {
    @Override
    public String name() {
        return "Show all items";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        System.out.println("=== Show all items ===");
        tracker.findAll();
        return true;
    }
}
