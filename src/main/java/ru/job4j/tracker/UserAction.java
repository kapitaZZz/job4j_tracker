package ru.job4j.tracker;

public interface UserAction {
    String name();

    boolean execute(Store store, Input input);
}
