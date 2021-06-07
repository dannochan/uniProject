package de.uniba.wiai.dsg.ajp.assignment2.literature;


import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.DatabaseServiceImpl;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;
import org.w3c.dom.ls.LSOutput;

import javax.management.modelmbean.XMLParseException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		Database db = new Database();
		DatabaseService dbService = new DatabaseServiceImpl(db);

		Author a = new Author("10", "decheng", "decheng@gmail.com", new LinkedList<>());
		//System.out.println(a);
		db.getAuthors().add(a);

		List<String> authorsID = db.getAuthors().stream().map(author -> author.getId()).collect(Collectors.toList());
		//System.out.println(db.getAuthors());

		Publication pubs = new Publication("1", "guido gave us 5.0", 2020, PublicationType.BOOK, null);

		try {
			dbService.addPublication(pubs.getTitle(), pubs.getYearPublished(), pubs.getType(), authorsID, pubs.getId());
			dbService.removeAuthorByID("10"); // wirft exception aus, nur zum testen gedacht
		} catch (LiteratureDatabaseException e) {
			e.printStackTrace();
		}
		//System.out.println(db.getPublications());
		//System.out.println(db.getAuthors());
		try {
			dbService.removePublicationByID(pubs.getId());
		} catch (LiteratureDatabaseException e) {
			e.printStackTrace();
		}
		System.out.println(db.getPublications() + "finish");
	}
}
