import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<Book> books;
    private List<Member> members;

    
    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void showBooks() {
        System.out.println("Books in " + name + " Library:");
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                book.showBookInfo(); 
            }
        }
    }

    public void showMembers() {
        System.out.println("Members of " + name + " Library:");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            for (Member member : members) {
                System.out.println(member); // Pastikan kelas Member sudah punya toString() atau ditangani dengan baik
            }
        }
    }

    public ArrayList<String> getBookTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Book book : books) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    public ArrayList<String> getMemberNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Member member : members) {
            names.add(member.getName());
        }
        return names;
    }
}