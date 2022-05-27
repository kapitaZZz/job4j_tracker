package ru.job4j.tracker;

public class CreateAction implements UserAction {
    private final Output out;

    public CreateAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Create item";
    }

    @Override
    public boolean execute(Store store, Input input) {
        out.println("=====Create new item=====");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        store.add(item);
        out.println("Добавленная заявка: " + item);
        return true;
    }
}
