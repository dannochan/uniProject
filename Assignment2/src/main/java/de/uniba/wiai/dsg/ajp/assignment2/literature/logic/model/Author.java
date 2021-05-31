package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class Author {

	private String id;
	private String name;
	private String email;
	private List<Publication> publications = new LinkedList<>();

	public Author() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s (%s) published %d publication(s): %s",
				id, name, email, publications.size(), getPublicationIds());
	}

	private String getPublicationIds() {
		StringJoiner result = new StringJoiner(", ");
		for (int i = 0; i < publications.size(); i++) {
			result.add(publications.get(i).getId());
		}
		return result.toString();
	}

}
