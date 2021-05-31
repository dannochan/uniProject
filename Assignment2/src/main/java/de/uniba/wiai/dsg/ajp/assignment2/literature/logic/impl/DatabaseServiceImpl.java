package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import java.util.List;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

public class DatabaseServiceImpl implements DatabaseService {

	@Override
	public void addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
			throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePublicationByID(String id) throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAuthorByID(String id) throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAuthor(String name, String email, String id) throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Publication> getPublications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> getAuthors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printXMLToConsole() throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveXMLToFile(String path) throws LiteratureDatabaseException {
		// TODO Auto-generated method stub

	}

}
