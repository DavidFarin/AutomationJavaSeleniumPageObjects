package models.rialto;

public class RialtoBib {

	public RialtoBib(String title, String author, String isbn, String[] locations, String[] availabilities) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.locations = locations;
		this.availabilities = availabilities;
	}

	private String title, author, isbn;
	private String[] locations, availabilities;

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getIsbn() {
		return this.isbn;
	}
	
	public String[] getLocations() {
		return this.locations;
	}

	public String[] getAvailabilities() {
		return this.availabilities;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public boolean equals(RialtoBib bib) {		
		if(!this.title.equals(bib.getTitle()) || !this.author.equals(bib.getAuthor()))
			return false;
		int length = getLocations().length;
		for(int i=0; i < length; i++) {
			if(!this.getLocations()[i].equals(bib.getLocations()[i]))
				return false;
		}
		length = getAvailabilities().length;
		for(int i=0; i < length; i++) {
			if(!this.getAvailabilities()[i].equals(bib.getAvailabilities()[i]))
				return false;
		}
		return true;
	}
}
