package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinder;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTokenFinder implements TokenFinder {

    public SimpleTokenFinder() {
        /*
         * DO NOT REMOVE
         *
         * REQUIRED FOR GRADING
         */
    }

    @Override
    public void search(final SearchTask task) throws TokenFinderException {

        // TODO: Implement here!

        Path pathOfRoot = Paths.get(task.getRootFolder()); // variable for iteration
        InputStream is = getClass().getClassLoader().getResourceAsStream(task.getIgnoreFile());

        if (Files.isDirectory(pathOfRoot)) {
            try {
                DirectoryStream<Path> rootStream = Files.newDirectoryStream(pathOfRoot);

                rootStream.forEach(path -> {
                    System.out.println(path);
                });
                for (Path element :
                        rootStream) {

                    System.out.println(element.toAbsolutePath());
                    //iterieren alle die Verzeichniss und Datei
                    Files.walk(pathOfRoot).forEach(path -> {
                        System.out.println(path);
                    });

                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        }


    }

}
