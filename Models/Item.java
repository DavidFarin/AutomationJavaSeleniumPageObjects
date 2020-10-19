package models.rialto;

public class RialtoItem {

	public RialtoItem(String title, String author, String price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}

	public RialtoItem(String workTitle, String title, String author, String publicationYear, String publisher,
			String binding, String isbn, String price, String platform) {
		this(title, author, price);
		this.workTitle = workTitle;
		this.publicationYear = publicationYear;
		this.publisher = publisher;
		this.binding = binding;
		this.isbn = isbn;
		this.platform = platform;
	}

	private String title, author, publicationYear, publisher, binding, isbn, price, platform, workTitle;

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////// GETTERS /////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public String getWorkTitle() {
		return this.workTitle;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return this.author;
	}
	
	public String getPublicationYear() {
		return this.publicationYear;
	}
	
	public String getPublisher() {
		return this.publisher;
	}
	
	public String getBinding() {
		return this.binding;
	}
	
	public String getIsbn() {
		return this.isbn;
	}
	
	public String getPlatform() {
		return this.platform;
	}
	
	public String getPrice() {
		return this.price;
	}

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////// BOOLEAN METHODS /////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	public boolean deepEquals(RialtoItem item) {
		return (basicEquals(item) && this.publicationYear.equals(item.getPublicationYear()) && this.publisher.equals(item.getPublisher())
				&& this.binding.equals(item.getBinding()) && this.platform.equals(item.getPlatform()) && this.isbn.equals(item.getIsbn()));
	}

	public boolean basicEquals(RialtoItem item) {
		return (this.title.equals(item.getTitle()) && this.author.equals(item.getAuthor()) && this.price.equals(item.getPrice()));
	}
}
