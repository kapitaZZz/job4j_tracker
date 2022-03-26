package ru.job4j.tracker;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        System.out.println("=====EXIT=====");
        return false;
    }
}
