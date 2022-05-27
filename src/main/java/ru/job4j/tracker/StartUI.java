package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public class StartUI {

    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Store tracker, List<UserAction> userAction) {
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
        Input input = new ValidateInput(output,
                new ConsoleInput()
        );
        try (SqlTracker tracker = new SqlTracker()) {
            tracker.init();
            List<UserAction> actions = List.of(
                    new CreateAction(output),
                    new EditAction(output),
                    new DeleteAction(output),
                    new ShowAction(output),
                    new FindByIdAction(output),
                    new FindByNameAction(output),
                    new ExitAction(output)
            );
            new StartUI(output).init(input, tracker, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
