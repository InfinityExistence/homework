package org.app;

import org.app.console.ConsoleApplication;
import org.app.ioc.Application;
import org.app.repository.DataBaseDefaultInfoInitializer;

public class Main
{
    public static void main( String[] args )
    {
        var context =  Application.run("org.app");
        context.getObject(DataBaseDefaultInfoInitializer.class).initAllData();

        var console = context.getObject(ConsoleApplication.class);
        console.start();
    }
}
