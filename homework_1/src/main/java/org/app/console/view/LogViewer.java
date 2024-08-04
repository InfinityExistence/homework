package org.app.console.view;

import org.app.console.view.input.Reader;
import org.app.console.view.output.EntityPrinter;
import org.app.controller.LogController;
import org.app.entity.Log;
import org.app.entity.UserCredentials;
import org.app.ioc.annotation.AutoInjected;

import java.util.List;

public class LogViewer {

    @AutoInjected
    Reader reader;
    @AutoInjected
    LogController logController;
    @AutoInjected
    EntityPrinter entityPrinter;


    void viewALlLogs(UserCredentials userCredentials)

    {
        List<Log> allLogs = logController.getAllLogs(userCredentials);
        entityPrinter.printLogsWithHeader(allLogs);
        reader.waitToEnterInput();
    }

}
