package ru.job4j.tracker;

public class DeleteAction implements UserAction {
    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Tracker tracker, Input input) {
        System.out.println("=== Delete item ===");
        int id = input.askInt("Enter id for delete: ");
        tracker.delete(id);
        System.out.println("Заявка удалена успешно.");
        return true;
    }
}
