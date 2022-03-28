package ru.job4j.tracker;

public class StartUI {

    private final Output out;

    public StartUI(Output out) {
        this.out = out;
    }

    public void init(Input input, Tracker tracker, UserAction[] userAction) {
        boolean run = true;
        while (run) {
            this.showMenu(userAction);
            int select = input.askInt("Select: ");
            if (select < 0 || select > 6) {
                out.println("Wrong input, you can select: 0 .. " + (userAction.length - 1));
                continue;
            }
            UserAction action = userAction[select];
            run = action.execute(tracker, input);
        }
    }

    private void showMenu(UserAction[] userAction) {
        out.println("Menu:");
        for (int i = 0; i < userAction.length; i++) {
            System.out.println(i + ". " + userAction[i].name());
        }

    }

    public static void main(String[] args) {
        Input input = new ValidateInput();
        Output output = new ConsoleOutput();
        Tracker tracker = new Tracker();
        UserAction[] userAction = {
                new CreateAction(output),
                new EditAction(output),
                new DeleteAction(output),
                new FindByIdAction(output),
                new FindByNameAction(output),
                new ShowAction(output),
                new ExitAction(output)
        };
        new StartUI(output).init(input, tracker, userAction);
    }
}
