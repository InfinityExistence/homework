package org.app.console;

import org.app.console.view.AuthenticationViewer;
import org.app.console.view.MenuViewer;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.AutoInjected;

public class ConsoleApplication {
    @AutoInjected
    AuthenticationViewer authenticationViewer;
    @AutoInjected
    MenuViewer menuViewer;

    public void start() {
        while (true) {

            UserCredentials inited = authenticationViewer.init();
            menuViewer.viewMenu(inited);

        }

    }
}
