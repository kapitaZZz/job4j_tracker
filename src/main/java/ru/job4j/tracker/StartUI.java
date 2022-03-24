package ru.job4j.tracker;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StartUI {

    public void init(Scanner scanner, Tracker tracker) {
        boolean run = true;
        while (run) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.println("Select: ");
            if (choice != 6) {
                System.out.println("Пользователь выбрал: " + choice);
            } else {
                run = false;
            }
        }
    }

    private void showMenu() {
        String[] menu = {"Add new Item",
                "Show all items",
                "Edit item",
                "Delete item",
                "Find item by id",
                "Find items by name",
                "Exit Program"};
        for (int i = 0; i < menu.length; i++) {
            System.out.println(i + ". - " + menu[i]);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Tracker tracker = new Tracker();
        new StartUI().init(scanner, tracker);
    }
}
