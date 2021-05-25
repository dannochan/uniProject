package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinder;
import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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

        TaskValidator validator = new TaskValidatorImpl(task);   //Die Eingabe in SearchTask wird hier auf Validität/Richtigkeit überprüft

        if(validator.validation()){
            try {
                List<String> ignoredItems = Files.readAllLines(validator.getIgnoreFile(), StandardCharsets.UTF_8);

                List<Path> listOfRoot = findAndFilterDirectory(validator.getRootFolder(), ignoredItems, validator.getFileExtension(), validator.getOutputFile());

                tokenSearchAndCount(listOfRoot, validator.getToken(), validator.getOutputFile());


            } catch (TokenFinderException e) {
                System.err.println("There is something wrong during searching! \n"  + e);
            } catch (IOException e){
                System.err.println("An error occured while the file been read! \n" + e);
            }

        }

    }


    public List<Path> findAndFilterDirectory(Path path, List<String> list, String fileExtension, Path resultFile) throws TokenFinderException {
        List<Path> result = new ArrayList<>();

        try (Stream<Path> treeWalk = Files.walk(path)) {
            result = treeWalk.filter(Files::isReadable).
                    filter(p -> !isIgnoreFile(p, list, resultFile)).                    // Die Files werden bis auf "Ignore" und "lib" gefiltert
                    filter(p -> p.getFileName().toString().endsWith(fileExtension)).
                    collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("An Error occured while iterating through the directories ");
        }
        return result;
    }

    // Ein Method, um die Directories nach den in der angegebenen File bzw. dem Namen zu filtern
    private static boolean isIgnoreFile(Path pathForCheck, List<String> listForCheck, Path resultFile) {
        List<String> outputArray = new ArrayList<>();
        boolean result = false;
        for (String elem : listForCheck) {
            if (pathForCheck.toAbsolutePath().toString().contains(elem)) {

                if (pathForCheck.getFileName().toString().endsWith(elem)) {
                    String outputLine = pathForCheck + " directory was ignored.\n";
                    outputArray.add(outputLine);
                }
                result = true;
            }

        }
        writeToFileNewLines(resultFile, outputArray);
        return result;
    }


    // Ein Method, um die Text-Datei zu lesen und die Token in der Datei zu zählen
    // Parameter von der Method: 1) List der Paths von allen Files, 2) Token als String,  3) Textdatei vom Ergebnis

    public static void tokenSearchAndCount(List<Path> pathList, String keyword, Path resultFile) throws IOException{
        //Eine List, die das Ergebnis von TokenSearch speichert.
        List<String> outputTmp = new ArrayList<>();

        // Eine Variable, um die Summe des Token von dem Projekt zu tracken.
        int count = 0;

        for (Path elem : pathList
        ) {
            // Files lesen mit Stream
            try (Stream<String> lines = Files.lines(elem)) {

                // Der Inhalt der File wird in eine List umgewandelt
                List<String> contentInList = lines.collect(Collectors.toList());

                /*Hier fängt es an, den gewünschten Token in der Text-Datei zu suchen*/
                int countToken = 0;
                int line_Number = 0;
                int columm_Number = 0;
                String tokenLeft = "";
                String tokenRight = "";
                String output1;
                String output2;

                if (!contentInList.isEmpty()) {

                    for (int lineIndex = 0; lineIndex < contentInList.size(); lineIndex++) {    // Hier wird der Inhalt durchlaufen und die Suchergebnisse formatiert


                        for (int index = 0; (index = contentInList.get(lineIndex).indexOf(keyword, index)) >= 0; index++) {   //

                            columm_Number = contentInList.get(lineIndex).indexOf(keyword, index);
                            line_Number = lineIndex + 1;

                            tokenLeft = contentInList.get(lineIndex).substring(0, columm_Number);
                            tokenRight = contentInList.get(lineIndex).substring(columm_Number + keyword.length());


                            output2 = String.format("%s :%d,%d> %s**%s**%s",
                                    elem, line_Number, columm_Number, tokenLeft, keyword, tokenRight);
                            System.out.println(output2);
                            outputTmp.add(output2);
                            countToken++;
                        }

                    }

                }

                output1 = String.format("%s includes **%s** %d times.\n", elem, keyword, countToken);
                System.out.println(output1);
                outputTmp.add(output1);
                count = count + countToken;

            } catch (IOException e) {
                throw new IOException("The File cannot be read correctly", e);
            }

        } // Ende der 1. For-Schleife (PathList). Alle File wurden gelesen.

        // Anzahl des Tokens wird in Projekt gezählt
        outputTmp.add("The project includes " + "**" + keyword + "** " + count + " times.");

        //Das Ergebnis wird in resultFile Text-Datei geschrieben
        writeToFileNewLines(resultFile, outputTmp);
    }


    // Ein Method, um die Ergebniss in einer Text-Datei speichern zu können
    public static void writeToFileNewLines(Path outputFile, List<String> newLines) {

        if (Files.exists(outputFile)) {          // prüfen, ob die Datei schon existiert.

                                    //Falls die Output Datei schon existiert, werden die neuen Line in die Datei geschrieben
            try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardOpenOption.APPEND)) {
                for (String elem : newLines) {
                    writer.write(elem);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("The file cannot be written! something wrong! "+ e);
            }
        } else {                    // Falls die Datei noch nicht existiert, wird zuerst eine Text-File erstellt.
            try {
                Files.write(outputFile, newLines, StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                System.err.println("Error occured when trying to create and write the file. Make sure the file does not exist yet! " + e);
            }
        }
    }
}

