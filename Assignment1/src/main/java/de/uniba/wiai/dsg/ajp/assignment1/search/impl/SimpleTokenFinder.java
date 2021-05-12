package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinder;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.io.BufferedReader;
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
import java.util.stream.Stream;

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

        Path pathOfRoot = Path.of(task.getRootFolder()); // variable for iteration
        Path pathOfIgnoreFile = Path.of(task.getIgnoreFile());

        //eine Arraylist um die PATHS abzuspeichen
        List<Path> pathsOfFiles;

        if (Files.isDirectory(pathOfRoot)) {

            try {
                Stream<Path> filesWalk = Files.walk(pathOfRoot);
                List<String> itemsToIgnore = readFile(pathOfIgnoreFile);
                pathsOfFiles = filesWalk.filter(Files::isDirectory).
                                    filter(x-> checkIgnoreItem(x, itemsToIgnore)).
                                            collect(Collectors.toList());

                pathsOfFiles.forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static List<Path> directoryWalk(Path path) throws IOException {
        List<Path> paths = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream
            ) {
                if (Files.isDirectory(entry)) {
                    directoryWalk(entry);
                }
                paths.add(entry);
            }
            return paths;
        }
    }
        // a method to traverse directories and give back boolean value as result
    public boolean checkIgnoreItem(Path path, List<String> items){
        boolean result = false;

        for (String element:
             items) {
            if (path.toString().contains(element)){
                //print to be deleted
                System.out.println("Directory should be ignored!");
                result =true;
            }
        }
        return result;
    }
        // a Method to read file and give back a list
    public List<String> readFile(Path filePath){
        List<String> tmpList = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);

            String line = reader.readLine();
            while (line!=null){
                tmpList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpList;
    }

}
