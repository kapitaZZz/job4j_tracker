package ru.job4j.tracker;

public class StartUI {

    public void init(Input input, Tracker tracker, UserAction[] userAction) {
        boolean run = true;
        while (run) {
            this.showMenu(userAction);
            int select = input.askInt("Select: ");
            UserAction action = userAction[select];
            run = action.execute(tracker, input);
        }
    }

    private void showMenu(UserAction[] userAction) {
        System.out.println("---MENU---");
        for (int i = 0; i < userAction.length; i++) {
            System.out.println(i + ". - " + userAction[i]);
        }

    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Tracker tracker = new Tracker();
        UserAction[] userAction = {
                new CreateAction(),
                new EditAction(),
                new DeleteAction(),
                new FIndByIdAction(),
                new FindByNameAction(),
                new ShowAction(),
                new ExitAction()
        };
        new StartUI().init(input, tracker, userAction);
    }
}
