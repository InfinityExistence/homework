package org.app.console.view.output.element;

public class Delimiter {
    private final String delimiter = "-".repeat(50);
    public void print() {
        System.out.println(delimiter);
    }

}
