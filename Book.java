public class Book {
    private String title;
    private Author author; 
    private Publisher publisher;
    private Address address;
    private boolean isBorrowed;
    private Member borrower;

    public Book(String title, Author author, Publisher publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher; 
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public void showBookInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author.getName());
        System.out.println("Publishare Name: " + publisher.getName());
        System.out.println("Publishare Address: " + address.toString());
        System.out.println("Status: " + (isBorrowed ? "Borrowed" : "Available"));
        System.out.println();  
    }

    public Author getAuthor() {
        return author;
    }
    
    public Publisher getPublisher() {
        return publisher;
    }

    // //tmabahan wktu bikin gui
    // public Member getBorrower() {
    //     return borrower;
    // }

    // public void setBorrower(Member borrower) {
    //     this.borrower = borrower;
    // }
}
