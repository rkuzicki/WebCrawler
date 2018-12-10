package pl.edu.agh.student.oop.webcrawler.frontend.util;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HyperlinkOpener {
    private final Desktop desktop;
    private Thread opener;
    private BlockingQueue<URI> linksToOpen = new ArrayBlockingQueue<>(5);

    {
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        } else {
            desktop = null;
        }
    }

    public boolean isSupported() {
        return desktop != null && desktop.isSupported(Desktop.Action.BROWSE);
    }

    public void open(URI uri) {
        try {
            desktop.browse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAsync(URI uri) {
        startOpenerThread();
        linksToOpen.add(uri);
    }

    private void startOpenerThread() {
        if (opener != null) {
            return;
        }

        opener = new Thread(() -> {
            try {
                open(linksToOpen.take());
            } catch (InterruptedException e) {
                return;
            }
        });
        opener.setName("Asynchronous hyperlink opener");
        opener.start();
    }
}
