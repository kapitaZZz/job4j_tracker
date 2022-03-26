package ru.job4j.tracker;

public class EditAction implements UserAction {
    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        System.out.println("=== Edit item ===");
        String name = input.askStr("Enter name: ");
        int id = input.askInt("Enter id:");
        tracker.replace(id, new Item(name));
        System.out.println("Заявка изменена успешно.");
        return true;
    }
}
