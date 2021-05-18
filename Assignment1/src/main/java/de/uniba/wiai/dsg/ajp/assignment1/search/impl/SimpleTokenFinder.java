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

                // den Inhalt durchlaufen

                for (int lineIndex = 0; lineIndex < contentInList.size(); lineIndex++) {

                    String tmpLine = contentInList.get(lineIndex);
                    int pos = tmpLine.indexOf(keyword);

                    // zählen, wieviel Tokens in jeder Zeile der File zu finden sind. die Funktion gibt das Ergebnis zurück
                    countToken = getCountToken(keyword, contentInList, lineIndex) + countToken;


                    if (tmpLine.contains(keyword)){

                        tokenLeft = contentInList.get(lineIndex).substring(0, pos);
                        tokenRight = contentInList.get(lineIndex).substring(pos+keyword.length());
                        columm_Number = tmpLine.indexOf(keyword, columm_Number++);
                        line_Number = lineIndex+1;
                    }
                }
                count = count + countToken;

                String output1 = String.format(" %s includes **%s** %d times%n", elem, keyword, countToken);
                System.out.println(output1);
                outputTmp.add(output1);

                String output2 = String.format("%s : %d  %d > %s**%s**%s %n",
                        elem, line_Number, columm_Number, tokenLeft, keyword, tokenRight);
                System.out.println(output2);
                outputTmp.add(output2);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Anzahl des Token in Projekt zählen
        outputTmp.add("Number of the token for project " + count);

        //das ergebnis in result Text-Datei reinschreiben
        writeToFileNewLines(resultFile, outputTmp);
    }

    private static int getCountToken(String keyword, List<String> contentOfFile, int lineNumber) {
        int countResult = 0;

        for (int index = 0; (index = contentOfFile.get(lineNumber).indexOf(keyword, index)) >= 0; index++) {   //
            countResult++;
        }
        return countResult;
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

