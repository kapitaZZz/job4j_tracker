package ru.job4j.tracker;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemAscByNameTest {

    @Test
    public void testSortAsc() {
        List<Item> items = Arrays.asList(
                new Item("Item1"),
                new Item("Item2"),
                new Item("Item")
        );
        Collections.sort(items, new ItemAscByName());
        List<Item> expected = Arrays.asList(
                new Item("Item"),
                new Item("Item1"),
                new Item("Item2")
        );
        Assert.assertEquals(items, expected);
    }

    @Test
    public void testSortDesc() {
        List<Item> items = Arrays.asList(
                new Item("Item1"),
                new Item("Item2"),
                new Item("Item")
        );
        Collections.sort(items, new ItemDescByName());
        List<Item> expected = Arrays.asList(
                new Item("Item2"),
                new Item("Item1"),
                new Item("Item")
        );
        Assert.assertEquals(items, expected);
    }

}