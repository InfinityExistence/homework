package org.app.console.view.output.element;

import org.app.ioc.annotation.AutoInjected;

public class Message {
    @AutoInjected
    private Delimiter delimiter;

    public void printMessage(String message) {
        delimiter.print();
        System.out.println(message);
        delimiter.print();
    }
}
