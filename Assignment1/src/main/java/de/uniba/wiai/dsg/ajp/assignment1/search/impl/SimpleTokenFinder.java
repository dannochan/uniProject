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
        File ignoreFile = new File(task.getIgnoreFile());

        try {
            List<String> ignoredItems = Files.readAllLines(ignoreFile.toPath(), StandardCharsets.UTF_8);



            if (pathOfRoot==null && pathOfRoot.getFileName()==null) {
                // do something
            }
            List<Path> listOfRoot = findAndFilterDirectory(pathOfRoot, ignoredItems, task.getFileExtension());


        }catch (IOException e){
            e.printStackTrace();
        }



    }

    private static List<Path> findAndFilterDirectory(Path path, List<String> list, String fileExtension) throws TokenFinderException {
        List<Path> result = null;

        if(!Files.isDirectory(path)){
            throw new TokenFinderException("The given path is not an available root folder!");
        }

        try (Stream<Path> treeWalk = Files.walk(path)){
            result = treeWalk.filter(Files::isRegularFile).
                                    filter(path1 -> !checkNameOfFile(path1, list)).
                                        filter(path1 -> path1.getFileName().toString().endsWith(fileExtension)).
                                           collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static boolean checkNameOfFile(Path pathForCheck, List<String> listForCheck){

            boolean result = false;
            for (String elem : listForCheck) {
                if (pathForCheck.toAbsolutePath().toString().contains(elem)){
                    result = true;
                }

            }
            return result;
    }


}
