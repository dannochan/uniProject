package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class DatabaseServiceImpl implements DatabaseService {

	private Database db;
	public DatabaseServiceImpl(Database db) {
		this.db = db;
	}

	@Override
	public void addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
			throws LiteratureDatabaseException {


		List<Author> authors = new ArrayList<>(); //code in andys code rein
		for(String authorID : authorIDs) {
			Author author = Author.getAuthorByID(authorID, this.db);
			if (author != null) {
				authors.add(author);
			} else {
				throw new LiteratureDatabaseException("Author with ID: " + authorID + " does not exist!");
			}
		}

		Publication newPub = new Publication(id, title, yearPublished, type, authors);
		updateAuthorsListWithNewPublication(authors, newPub);
		db.getPublications().add(newPub);
	}

	@Override
	public void removePublicationByID(String id) throws LiteratureDatabaseException {
		Publication pbToBeRemoved = Publication.getPublicationByID(id, this.db);
		System.out.println(pbToBeRemoved);
		if(pbToBeRemoved != null) {
			List<Author> authorsOfPubsToBeRemoved = Author.getAuthorsByPublication(id, this.db);
			db.getPublications().remove(pbToBeRemoved);
			updatePublicationListAfterRemovedPublication(authorsOfPubsToBeRemoved, pbToBeRemoved);
		} else {
			throw new LiteratureDatabaseException("Publication not found");
		}

	}

	@Override
	public void removeAuthorByID(String id) throws LiteratureDatabaseException {
		Author authorToBeRemoved = Author.getAuthorByID(id, this.db);
		if(authorToBeRemoved != null) {
			db.getAuthors().remove(authorToBeRemoved);
		} else {
			if (db.getAuthors().isEmpty()) {
				throw new LiteratureDatabaseException("Database does have not any author(s)");
			} else {
				throw new LiteratureDatabaseException("Author not found");
			}
		}
	}

	@Override
	public void addAuthor(String name, String email, String id) throws LiteratureDatabaseException {
		if(Author.getAuthorByID(id, this.db) == null) {
			Author authorToBeAdded = new Author(id, name, email, new LinkedList<>());
			db.getAuthors().add(authorToBeAdded);
		}
	}

	@Override
	public List<Publication> getPublications() {
		if(db.getPublications() != null) {
			db.getPublications();
		}
		return null;
	}

	@Override
	public List<Author> getAuthors() {
		if(db.getAuthors() != null) {
			db.getAuthors();
		}
		return null;
	}

	@Override
	public void clear() {
		db.getPublications().clear();
		db.getAuthors().clear();
	}

	@Override
	public void printXMLToConsole() throws LiteratureDatabaseException {
		try {
			JAXBContext context = JAXBContext.newInstance(Database.class);
			Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(db, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveXMLToFile(String path) throws LiteratureDatabaseException {
		try {
			JAXBContext context = JAXBContext.newInstance(Database.class);
			Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(db, Path.of(path).toFile());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	private void updateAuthorsListWithNewPublication(List<Author> authors, Publication newPub) {
		for(Author author : authors){
			this.db.getAuthors().stream()
					.filter(a -> a.getId().equals(author.getId()))
					.forEach(a -> a.getPublications().add(newPub));
		}
	}

	private void updatePublicationListAfterRemovedPublication(List<Author> authors, Publication delPub) {
		for(Author author : authors){
			this.db.getAuthors().stream()
					.filter(a -> a.getId().equals(author.getId()))
					.forEach(a -> a.getPublications().remove(delPub));
		}
	}

}

