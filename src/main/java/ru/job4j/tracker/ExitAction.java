package ru.job4j.tracker;

public class ExitAction implements UserAction {
    private final Output out;

    public ExitAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "EXIT";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        out.println("EXIT");
        return false;
    }
}
