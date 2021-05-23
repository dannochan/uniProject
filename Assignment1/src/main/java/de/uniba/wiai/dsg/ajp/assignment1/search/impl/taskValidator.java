package de.uniba.wiai.dsg.ajp.assignment1.search.impl;

import de.uniba.wiai.dsg.ajp.assignment1.search.SearchTask;

import java.nio.file.Path;
import java.nio.file.Files;

public class taskValidator {

    private Path rootFolder;
    private Path ignoreFile;
    private String fileExtension;
    private String token;
    private Path outputFile;

    public taskValidator(SearchTask task) {
        this.rootFolder = Path.of(task.getRootFolder());
        this.ignoreFile = Path.of(task.getIgnoreFile());
        this.outputFile = Path.of(task.getResultFile());
        this.fileExtension = task.getFileExtension();
        this.token = task.getToken();
    }

    public Path getRootFolder() {
        return rootFolder;
    }

    public Path getIgnoreFile() {
        return ignoreFile;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getToken() {
        return token;
    }

    public boolean validation(){

        return rootFolderValidate()&&checkExtensionAndToken()&&checkOutput()&&checkIgnoreFile();
    }

    /*Allerzuerst wird das Wurzelverzeichnis validiert
    möchliche Fehhler
    1. Der Path ist leer
    2. Das vorgegebene Verzeichniss gibt es nicht!
    3. es ist keine Vezeichnis
    4. es ist nicht lesebar
     */

    private boolean rootFolderValidate(){
        if (rootFolder.toString().isEmpty()){
            System.err.println("The path of root folder cannot be empty!");
            return false;
        }

        if (!rootFolder.toFile().exists()){
            System.err.println("The folder " + rootFolder + " doesn't exists!");
            return false;
        }

        if (!rootFolder.toFile().isDirectory()){
            System.err.println("The given folder" + rootFolder + " is not a valid folder ");
            return false;
        }

        return true;

    }

    private boolean checkIgnoreFile (){

        if (!Files.exists(ignoreFile)){
            System.err.println("The file " + ignoreFile + " doesn't exist!");
            return false;
        }

        if (!Files.isRegularFile(ignoreFile)){
            System.err.println("The file " + ignoreFile + " is not an actual file!");
            return false;
        }

        if (!Files.isReadable(ignoreFile)){
            System.err.println("The file " + ignoreFile + " is not readable!");
            return false;
        }

        return true;
    }

    private boolean checkOutput(){

        Path parent = outputFile.getParent();

        if (!Files.exists(parent)){
            System.err.println("The File cannot be create because parent folder of " +outputFile + "does not exist!" );
            return false;
        }


        if (Files.exists(outputFile)&&!Files.isWritable(outputFile)){
            System.err.println("The File" + outputFile + " exists already and is not writable!");
            return false;
        }
        return true;
    }

    private boolean checkExtensionAndToken(){

        if (fileExtension.matches("\\\\0-9\\\\")){
            System.err.println("The file Extension " + fileExtension + " shall not contain numbers");
            return false;
        }

        if (fileExtension.isEmpty()){
            System.err.println("The File Extension cannot be empty!");
            return false;
        }

        if (token.isEmpty()){
            System.err.println("The tpken cannot be empty! Don't you want to search something? ");
            return false;
        }

        return true;

    }


}
