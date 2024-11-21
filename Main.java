import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Tambah Anggota");
            System.out.println("3. Pinjam Buku");
            System.out.println("4. Kembalikan Buku");
            System.out.println("5. Daftar Buku yang Tersedia");
            System.out.println("6. Daftar anggota yang Tersedia");
            System.out.println("7. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer scanner

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    addMember(scanner);
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    displayAvailableBooks();
                    break;
                case 6:
                    displayMembers();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }

        scanner.close();
    }

    // Method untuk menambahkan buku baru
    private static void addBook(Scanner scanner) {
        System.out.print("Masukkan judul buku: ");
        String title = scanner.nextLine();
        System.out.print("Masukkan nama penulis buku: ");
        String authorName = scanner.nextLine();
        System.out.print("Masukkan Bio penulis buku: ");
        String authorBio = scanner.nextLine();
        System.out.print("Masukkan Penerbit buku: ");
        String publisherName = scanner.nextLine();
        System.out.println("Masukkan alamat Penerbit:");
        System.out.print("Jalan: ");
        String street = scanner.nextLine();
        System.out.print("Kota: ");
        String city = scanner.nextLine();
        

        // Membuat objek Address
        Address address = new Address(street, city);
        Publisher publisher = new Publisher(publisherName, address);
        Author author = new Author(authorName, authorBio);
        Book book = new Book(title, author, publisher);
        

        // Menambahkan buku ke daftar buku
        books.add(book);
        System.out.println("Buku " + title + " oleh " + authorName + " berhasil ditambahkan.");
    }

    // Method untuk menambahkan anggota baru
    private static void addMember(Scanner scanner) {
        System.out.print("Masukkan ID anggota: ");
        String memberID = scanner.nextLine();
        System.out.print("Masukkan nama anggota: ");
        String memberName = scanner.nextLine();
        System.out.println("Masukkan alamat anggota:");
        System.out.print("Jalan: ");
        String street = scanner.nextLine();
        System.out.print("Kota: ");
        String city = scanner.nextLine();
        

        // Membuat objek Address
        Address address = new Address(street, city);

        // Membuat objek Member dengan Address
        Member member = new Member(memberID, memberName, address);

        // Menambahkan anggota ke daftar anggota
        members.add(member);
        System.out.println("Anggota berhasil ditambahkan :");
        member.displayDetailMember();
    }

    // Method untuk meminjam buku
    private static void borrowBook(Scanner scanner) {
        System.out.println("Pilih anggota yang akan meminjam buku:");
        displayMembers(); // Menampilkan daftar anggota

        System.out.print("Masukkan ID anggota yang meminjam buku: ");
        String memberID = scanner.nextLine();
        Member member = findMemberByID(memberID); // Mencari anggota berdasarkan ID

        if (member == null) {
            System.out.println("Anggota tidak ditemukan.");
            return;
        }

        System.out.println("Daftar buku yang tersedia untuk dipinjam:");
        displayAvailableBooks();

        System.out.print("Masukkan judul buku yang ingin dipinjam: ");
        String bookTitle = scanner.nextLine();
        Book book = findBookByTitle(bookTitle);

        if (book == null) {
            System.out.println("Buku tidak tersedia.");
            return;
        }

        if (book.isBorrowed()) {
            System.out.println("Buku sudah dipinjam.");
        } else {
            book.setBorrowed(true); // Mengubah status buku menjadi dipinjam
            member.borrowBook(book); // Menambahkan buku ke daftar buku yang dipinjam oleh anggota
            System.out.println("Buku " + book.getTitle() + " berhasil dipinjam oleh " + member.getName());
        }
    }

    // Method untuk mengembalikan buku
    private static void returnBook(Scanner scanner) {
        System.out.println("Pilih anggota yang akan mengembalikan buku:");
        displayMembers(); // Menampilkan daftar anggota

        System.out.print("Masukkan ID anggota yang mengembalikan buku: ");
        String memberID = scanner.nextLine();
        Member member = findMemberByID(memberID); // Mencari anggota berdasarkan ID

        if (member == null) {
            System.out.println("Anggota tidak ditemukan.");
            return;
        }

        // Menampilkan daftar buku yang dipinjam oleh anggota
        System.out.println("Daftar buku yang dipinjam oleh " + member.getName() + ":");
        member.displayBorrowedBooks();

        // Meminta input judul buku yang ingin dikembalikan
        System.out.print("Masukkan judul buku yang akan dikembalikan: ");
        String bookTitle = scanner.nextLine();
        Book book = member.findBorrowedBookByTitle(bookTitle);

        if (book == null) {
            System.out.println("Buku ini tidak ada dalam daftar buku yang dipinjam.");
            return;
        }

        // Mengubah status buku menjadi 'tersedia' dan menghapus buku dari daftar
        // pinjaman anggota
        book.setBorrowed(false); // Mengubah status buku menjadi 'tersedia'
        member.returnBook(book); // Menghapus buku dari daftar buku yang dipinjam

        System.out.println("Buku " + book.getTitle() + " telah dikembalikan oleh " + member.getName());

        // Menampilkan buku yang tersedia setelah pengembalian
        displayAvailableBooks();
    }

    // Menampilkan buku yang tersedia untuk dipinjam
    private static void displayAvailableBooks() {
        System.out.println("Daftar buku yang tersedia:");
        for (Book book : books) {
            if (!book.isBorrowed()) {
                System.out.println("- " + book.getTitle());
            }
        }
    }

    // Menampilkan daftar anggota
    private static void displayMembers() {
        System.out.println("Daftar Anggota:");
        if (members.isEmpty()) {
            System.out.println("Belum ada anggota terdaftar.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }

    // Mencari anggota berdasarkan ID
    private static Member findMemberByID(String id) {
        for (Member member : members) {
            if (member.getID().equals(id)) {
                return member;
            }
        }
        return null;
    }

    // Mencari buku berdasarkan judul
    private static Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
