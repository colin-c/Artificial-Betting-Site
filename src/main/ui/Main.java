package ui;

import model.Event;
import model.EventLog;

import java.io.FileNotFoundException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        new BetGUI();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Iterator<Event> eventLog = EventLog.getInstance().iterator();
            while (eventLog.hasNext()) {
                Event e = eventLog.next();
                System.out.println(e.toString());
                System.out.println();
            }
        }));
//        try {
//            new BettingApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
    }
}


