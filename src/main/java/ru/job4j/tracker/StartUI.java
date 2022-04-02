package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public class StartUI {

    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Tracker tracker, List<UserAction> userAction) {
        boolean run = true;
        while (run) {
            this.showMenu(userAction);
            int select = input.askInt("Select: ");
            if (select < 0 || select > userAction.size() - 1) {
                out.println("Wrong input, you can select: 0 .. " + (userAction.size() - 1));
                continue;
            }
            run = userAction.get(select).execute(tracker, input);
        }
    }

    private void showMenu(List<UserAction> userAction) {
        out.println("Menu:");
        for (int i = 0; i < userAction.size(); i++) {
            out.println(i + ". " + userAction.get(i).name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        Tracker tracker = new Tracker();
        List<UserAction> userAction = new ArrayList<>();
        userAction.add(new CreateAction(output));
        userAction.add(new EditAction(output));
        userAction.add(new DeleteAction(output));
        userAction.add(new FindByIdAction(output));
        userAction.add(new FindByNameAction(output));
        userAction.add(new ShowAction(output));
        userAction.add(new ExitAction(output));
        new StartUI(output).init(input, tracker, userAction);
    }
}
