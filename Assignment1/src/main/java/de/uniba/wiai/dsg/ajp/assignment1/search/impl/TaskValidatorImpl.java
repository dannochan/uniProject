package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;

import java.nio.file.Path;
import java.nio.file.Files;

public class TaskValidatorImpl implements TaskValidator {

    private final Path rootFolder;
    private final Path ignoreFile;
    private final String fileExtension;
    private final String token;
    private final Path outputFile;

    public TaskValidatorImpl(SearchTask task) {
        this.rootFolder = Path.of(task.getRootFolder());
        this.ignoreFile = Path.of(task.getIgnoreFile());
        this.outputFile = Path.of(task.getResultFile());
        this.fileExtension = task.getFileExtension();
        this.token = task.getToken();
    }

    @Override
    public Path getRootFolder() {
        return rootFolder;
    }


    @Override
    public Path getIgnoreFile() {
        return ignoreFile;
    }


    @Override
    public Path getOutputFile() {
        return outputFile;
    }


    @Override
    public String getFileExtension() {
        return fileExtension;
    }


    @Override
    public String getToken() {
        return token;
    }


    @Override
    public boolean validation(){

        return rootFolderValidate() && checkExtensionAndToken() && checkOutput() && checkIgnoreFile();
    }

    /*Allerzuerst wird das Wurzelverzeichnis validiert
    möchliche Fehhler
    1. Der Path ist leer
    2. Das vorgegebene Verzeichniss gibt es nicht!
    3. es ist keine Vezeichnis
    4. es ist nicht lesebar
     */

    @Override
    public boolean rootFolderValidate() {
        if (rootFolder.toString().isEmpty()) {
            System.err.println("The path of root folder cannot be empty!");
            return false;
        }

        if (!rootFolder.toFile().exists()) {
            System.err.println("The folder " + rootFolder + " doesn't exists!");
            return false;
        }

        if (!rootFolder.toFile().isDirectory()) {
            System.err.println("The given folder" + rootFolder + " is not a valid folder.");
            return false;
        }

        return true;

    }

    @Override
    public boolean checkIgnoreFile() {

        if (!Files.exists(ignoreFile)) {
            System.err.println("The file " + ignoreFile + " doesn't exist!");
            return false;
        }

        if (!Files.isRegularFile(ignoreFile)) {
            System.err.println("The file " + ignoreFile + " is not an actual file!");
            return false;
        }

        if (!Files.isReadable(ignoreFile)) {
            System.err.println("The file " + ignoreFile + " is not readable!");
            return false;
        }

        return true;
    }

    @Override
    public boolean checkOutput() {

        Path parent = outputFile.getParent();

        if (!Files.exists(parent)) {
            System.err.println("The File cannot be create because parent folder of " + outputFile + "does not exist!");
            return false;
        }


        if (Files.exists(outputFile) && !Files.isWritable(outputFile)) {
            System.err.println("The File" + outputFile + " exists already and is not writable!");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkExtensionAndToken() {

        if (fileExtension.matches("\\\\0-9\\\\")) {
            System.err.println("The file Extension " + fileExtension + " shall not contain numbers");
            return false;
        }

        if (fileExtension.isEmpty()) {

            System.err.println("The File Extension cannot be empty!");
            return false;
        }

        if (token.isEmpty()) {
            System.err.println("The token cannot be empty! Don't you want to search something?");
            return false;
        }

        return true;

    }


}
