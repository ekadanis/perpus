import java.util.ArrayList;
import java.util.List;

public class Member {
    private String id;
    private String name;
    private Address address;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();

    public Member(String id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("Tidak ada buku yang dipinjam.");
        } else {
            for (Book book : borrowedBooks) {
                System.out.println("- " + book.getTitle());
            }
        }
    }

    public Book findBorrowedBookByTitle(String title) {
        for (Book book : borrowedBooks) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void displayDetailMember(){
        System.out.println("ID : " + id);
        System.out.println("Nama : " + name);
        System.out.println("Alamat : " + address.getStreet() +" "+ address.getCity());
    }

    
    private void displayAllMembers() {
        for (Member member : members) {
            System.out.println(member.toString());
        }
    }

    public Address getAddress() {
        return address;
    }
    
    @Override
    public String toString() {
    return "ID: " + id +
           ", Nama: " + name +
           ", Alamat: " +  address.toString();
    }

     public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    
}
