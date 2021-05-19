package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinder;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleTokenFinder<TODO> implements TokenFinder {

    public SimpleTokenFinder() {
        /*
         * DO NOT REMOVE
         *
         * REQUIRED FOR GRADING
         */
    }


    @Override
    public void search(final SearchTask task) throws TokenFinderException {

        // Variable für die Path von Wurzelverzeichniss und die Ignore-File
        Path pathOfRoot = Path.of(task.getRootFolder());
        File ignoreFile = new File(task.getIgnoreFile());

        // Validieren der Eingabe bzw. Arguments muss auch noch gemacht werden
        // TODO: Implement validation here


        try {
            List<String> ignoredItems = Files.readAllLines(ignoreFile.toPath(), StandardCharsets.UTF_8);

            List<Path> listOfRoot = findAndFilterDirectory(pathOfRoot, ignoredItems, task.getFileExtension(), task.getResultFile());

            tokenSearchAndCount(listOfRoot, task.getToken(), task.getResultFile());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public List<Path> findAndFilterDirectory(Path path, List<String> list, String fileExtension, String resultFile) throws TokenFinderException {
        List<Path> result = new ArrayList<>();

        if (!Files.isDirectory(path)) {
            throw new TokenFinderException("The given path is not an available root folder!");
        }

        try (Stream<Path> treeWalk = Files.walk(path)) {
            result = treeWalk.filter(Files::isReadable).
                    filter(path1 -> !checkNameOfFile(path1, list, resultFile)).  //nur die Files ohne Name "Ignore" und "lib"
                    filter(p -> p.getFileName().toString().endsWith(fileExtension)).
                    collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // ein Method, um die Directories mit den in der angegebenen File steheden Name zu filtern

    private static boolean checkNameOfFile(Path pathForCheck, List<String> listForCheck, String resultFile) {
        List<String> outputArray = new ArrayList<>();
        boolean result = false;
        for (String elem : listForCheck) {
            if (pathForCheck.toAbsolutePath().toString().contains(elem)) {

                if (pathForCheck.getFileName().toString().endsWith(elem)) {
                    String outputLine = pathForCheck + " directory was ignored.";
                    outputArray.add(outputLine);
                }
                result = true;
            }

        }
        writeToFileNewLines(resultFile, outputArray);
        return result;
    }

    // eine Method, um die Text-Datei zu lesen und die Token in der Datei zu zählen.
    // Parameter von der Method: 1. List der Paths von allen Files, 2. Token in String 3.Text Datei von Ergebnis
    public static void tokenSearchAndCount(List<Path> pathList, String keyword, String resultFile) {
        //eine List, die das Ergebnis von dem TokenSearch speichern.
        List<String> outputTmp = new ArrayList<>();

        // eine Variable um die Summe des Token von dem Projekt zu tracken.
        int count = 0;

        for (Path elem : pathList
        ) {
            // Files lesen mit Stream
            try (Stream<String> lines = Files.lines(elem)) {

                // der Inhalt der File wird in eine List umgewandelt
                List<String> contentInList = lines.collect(Collectors.toList());

                /*Hier fängt es an, den gewünschten Token in der Text-Datei zu suchen*/
                int countToken = 0;
                int line_Number = 0;
                int columm_Number = 0;
                String tokenLeft = "";
                String tokenRight = "";
                String output1;
                String output2;

                if (contentInList.isEmpty()) {
                    countToken = 0;
                } else {
                    // den Inhalt durchlaufen

                    for (int lineIndex = 0; lineIndex < contentInList.size(); lineIndex++) {


                        for (int index = 0; (index = contentInList.get(lineIndex).indexOf(keyword, index)) >= 0; index++) {   //

                            columm_Number = contentInList.get(lineIndex).indexOf(keyword, index);
                            line_Number = lineIndex + 1;

                            tokenLeft = contentInList.get(lineIndex).substring(0, columm_Number);
                            tokenRight = contentInList.get(lineIndex).substring(columm_Number + keyword.length());


                            output2 = String.format("%s : %d  %d > %s**%s**%s %n",
                                    elem, line_Number, columm_Number, tokenLeft, keyword, tokenRight);
                            System.out.println(output2);
                            outputTmp.add(output2);
                            countToken++;
                        }

                    }

                }

                output1 = String.format(" %s includes **%s** %d times%n", elem, keyword, countToken);
                System.out.println(output1);
                outputTmp.add(output1);
                count = count + countToken;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } // Ende der 1. For-Schleife (PathList). Alle File werden schon gelesen.

        // Anzahl des Token in Projekt zählen
        outputTmp.add("Number of the token for project " + count);

        //das ergebnis in result Text-Datei reinschreiben
        writeToFileNewLines(resultFile, outputTmp);
    }


    // eine Method, damit die Ergebniss in eine Text-Datei gespeichert werden.

    public static void writeToFileNewLines(String outputFile, List<String> newLines) {

        Path path = Path.of(outputFile); // Die Text-Datei, die zu bearbeiten ist.


        if (Files.exists(path)) {          // prüfen, ob die Datei schon existiert.
            //Falls die Output Datei schon existiert, werden die neuen Line in Datei reingeschrien
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

                for (String elem : newLines) {

                    writer.write(elem);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Falls die Datei noch nicht existiert, wird zuerst eine Text-File erstellt.
        } else {
            try {
                Files.write(path, newLines, StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

