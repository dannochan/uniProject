package de.uniba.wiai.dsg.ajp.assignment1.search.impl;


import de.uniba.wiai.dsg.ajp.assignment1.search.TokenFinderException;

import java.nio.file.Path;

public interface TaskValidator {
    Path getRootFolder();

    Path getIgnoreFile();

    Path getOutputFile();

    String getFileExtension();

    String getToken();

    boolean validation();

    boolean rootFolderValidate();

    boolean checkIgnoreFile();

    boolean checkOutput();

    boolean checkExtensionAndToken();
}
