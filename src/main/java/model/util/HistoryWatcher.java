package model.util;

import model.game.DiamondCircleApplication;
import model.game.Game;

import java.io.File;
import java.nio.file.*;
import java.util.Objects;

public class HistoryWatcher extends Thread {

    private static final Path directory = Paths.get("./history/");

    @Override
    public void run() {
        Game.numberOfGames = Objects.requireNonNull(directory.toFile().listFiles()).length;

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            while(true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }

                for(WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    if(fileName.toString().trim().endsWith(".txt") && fileName.toString().trim().startsWith("IGRA_")) {
                        Game.numberOfGames = Objects.requireNonNull(directory.toFile().listFiles()).length;
                        DiamondCircleApplication.mainController.updateNumberOfGames();
                    }
                }

                boolean valid = key.reset();
                if(!valid) {
                    break;
                }
            }

        } catch (Exception e) {
            LoggerUtil.log(HistoryWatcher.class, e);
        }
    }

}
