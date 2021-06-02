package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainServiceImpl implements MainService {

	/**
	 * Default constructor required for grading.
	 */
	public MainServiceImpl() {
		/*
		 * DO NOT REMOVE - REQUIRED FOR GRADING
		 * 
		 * YOU CAN EXTEND IT BELOW THIS COMMENT
		 */
	}

	@Override
	public void validate(String path) throws LiteratureDatabaseException {

		// Path der Datei deklarieren
		Path pathOfData = Path.of(path);

		// prüfen ob die Datei lesbar ist
		if(!Files.isReadable(pathOfData)){
			LiteratureDatabaseException mxlNotValid = new LiteratureDatabaseException(path + " is not valid!");
			throw mxlNotValid;
		}

		//  Mithilfe der SchemaFactory wird hier die XML Datei validiert
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		try {
			Schema schema = sf.newSchema(pathOfData.toFile());

			Validator validator = schema.newValidator();

			validator.validate(new StreamSource(pathOfData.toFile()));

		} catch (SAXException e) {

			// wenn die Validierung schiefgelaufen ist, was soll man hier tun?
			// Exception für Schema schema = sf.newSchema(pathOfData.toFile());

			e.printStackTrace();
		} catch (IOException e) {

			// wenn die Validierung bei Einlesen der Datei schiefgelaufen ist, was soll man hier tun?
			// Exception für validator.validate(new StreamSource(pathOfData.toFile()));

			e.printStackTrace();
		}


	}

	@Override
	public DatabaseService load(String path) throws LiteratureDatabaseException {

		try {
			JAXBContext XmlContext = JAXBContext.newInstance(Database.class);

			Unmarshaller us = XmlContext.createUnmarshaller();

			Database database = (Database) us.unmarshal(Path.of(path).toFile());

			return new DatabaseServiceImpl(database);

		} catch (JAXBException e) {
			// eine Exception für die Instanzierung einer Klasse

			e.printStackTrace();
		}


		return null;
	}

	@Override
	public DatabaseService create() throws LiteratureDatabaseException {

		// eine neue Datenbank erzeugen und als Parameter in die Klasse DatabaseService übergeben

		Database newDB = new Database();


		return  new DatabaseServiceImpl(newDB);
	}

}
