import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryGUI {
    private Library library;

    public LibraryGUI(Library library) {
        this.library = library;

        JFrame frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Panel Utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tombol
        JButton showBooksButton = new JButton("Show Books");
        JButton showMembersButton = new JButton("Show Members");
        JButton addBookButton = new JButton("Add Book");
        JButton addMemberButton = new JButton("Add Member");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(showBooksButton);
        buttonPanel.add(showMembersButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(addMemberButton);
        buttonPanel.add(borrowBookButton);
        buttonPanel.add(returnBookButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Area Teks
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);

        // Tombol "Show Books"
        showBooksButton.addActionListener(e -> {
            displayArea.setText("Books in Library:\n");
            if (library.getBooks().isEmpty()) {
                displayArea.append("No books available.\n");
            } else {
                for (Book book : library.getBooks()) {
                    // Cek jika buku sudah dipinjam, jika sudah skip buku ini
                    if (book.isBorrowed()) {
                        continue; // Skip buku yang sudah dipinjam
                    }

                    displayArea.append("Title: " + book.getTitle() + "\n");
                    displayArea.append("Author Name: " + book.getAuthor().getName() + "\n");
                    displayArea.append("Publisher Name: " + book.getPublisher().getName() + "\n");
                    if (book.getPublisher().getAddress() != null) {
                        displayArea.append("Publisher Street: " + book.getPublisher().getAddress().getStreet() + "\n");
                        displayArea.append("Publisher City: " + book.getPublisher().getAddress().getCity() + "\n");
                    } else {
                        displayArea.append("Publisher Address: Not Available\n");
                    }
                    displayArea.append("--------------------------\n");
                }
            }
        });

        // Tombol "Show Members"
        showMembersButton.addActionListener(e -> {
            displayArea.setText("Members in Library:\n");
            if (library.getMembers().isEmpty()) {
                displayArea.append("No members registered.\n");
            } else {
                for (Member member : library.getMembers()) {
                    displayArea.append("ID: " + member.getID() + "\n");
                    displayArea.append("Name: " + member.getName() + "\n");
                    displayArea.append("--------------------------\n");
                }
            }
        });

        // Tombol "Add Book"
        addBookButton.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField publisherField = new JTextField();
            JTextField streetField = new JTextField();
            JTextField cityField = new JTextField();

            Object[] message = {
                    "Title:", titleField,
                    "Author Name:", authorField,
                    "Publisher Name:", publisherField,
                    "Publisher Street:", streetField,
                    "Publisher City:", cityField,
            };

            int option = JOptionPane.showConfirmDialog(frame, message, "Add Book", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Membuat objek Author
                Author author = new Author(authorField.getText(), ""); // Bio kosong sementara

                // Membuat objek Address untuk Publisher
                Address publisherAddress = new Address(streetField.getText(), cityField.getText());

                // Membuat objek Publisher
                Publisher publisher = new Publisher(publisherField.getText(), publisherAddress);

                // Membuat dan menambahkan buku ke library
                Book book = new Book(titleField.getText(), author, publisher);
                library.addBook(book);

                JOptionPane.showMessageDialog(frame, "Book added successfully!");
            }
        });

        // Tombol "Add Member"
        addMemberButton.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField streetField = new JTextField();
            JTextField cityField = new JTextField();

            Object[] message = {
                    "ID:", idField,
                    "Name:", nameField,
                    "Street:", streetField,
                    "City:", cityField,
            };

            int option = JOptionPane.showConfirmDialog(frame, message, "Add Member", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Address address = new Address(streetField.getText(), cityField.getText());
                Member member = new Member(idField.getText(), nameField.getText(), address);
                library.addMember(member);
                JOptionPane.showMessageDialog(frame, "Member added successfully!");
            }
        });

        // Tombol "Borrow Book"
        borrowBookButton.addActionListener(e -> {
            // Dropdown untuk memilih member
            String[] memberIds = library.getMembers().stream().map(Member::getID).toArray(String[]::new);
            JComboBox<String> memberDropdown = new JComboBox<>(memberIds);
            String[] availableBooks = library.getBooks().stream()
                    .filter(book -> !book.isBorrowed()) // Menampilkan buku yang tersedia
                    .map(Book::getTitle).toArray(String[]::new);
            JComboBox<String> bookDropdown = new JComboBox<>(availableBooks);

            Object[] borrowMessage = {
                    "Select Member:", memberDropdown,
                    "Select Book:", bookDropdown,
            };

            int option = JOptionPane.showConfirmDialog(frame, borrowMessage, "Borrow Book",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String memberId = (String) memberDropdown.getSelectedItem();
                String bookTitle = (String) bookDropdown.getSelectedItem();

                Member member = findMemberById(memberId);
                Book book = findBookByTitle(bookTitle);

                if (member != null && book != null) {
                    book.setBorrowed(true);
                    member.borrowBook(book);
                    displayArea.setText("Book borrowed successfully!\n");
                } else {
                    displayArea.setText("Error borrowing the book.\n");
                }
            }
        });

        // Tombol "Return Book"
        returnBookButton.addActionListener(e -> {
            // Dropdown untuk memilih member
            String[] memberIds = library.getMembers().stream().map(Member::getID).toArray(String[]::new);
            JComboBox<String> memberDropdown = new JComboBox<>(memberIds);

            Object[] returnMessage = {
                    "Select Member:", memberDropdown,
            };

            int option = JOptionPane.showConfirmDialog(frame, returnMessage, "Return Book",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String memberId = (String) memberDropdown.getSelectedItem();
                Member member = findMemberById(memberId);

                if (member != null) {
                    // Tampilkan buku yang dipinjam oleh member
                    String[] borrowedBooks = member.getBorrowedBooks().stream().map(Book::getTitle)
                            .toArray(String[]::new);
                    JComboBox<String> bookDropdown = new JComboBox<>(borrowedBooks);

                    Object[] returnBookMessage = {
                            "Select Book to Return:", bookDropdown,
                    };

                    int returnOption = JOptionPane.showConfirmDialog(frame, returnBookMessage, "Return Book",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (returnOption == JOptionPane.OK_OPTION) {
                        String bookTitle = (String) bookDropdown.getSelectedItem();
                        Book book = member.findBorrowedBookByTitle(bookTitle);

                        if (book != null) {
                            book.setBorrowed(false);
                            member.returnBook(book);
                            displayArea.setText("Book returned successfully!\n");
                        } else {
                            displayArea.setText("Error returning the book.\n");
                        }
                    }
                } else {
                    displayArea.setText("Member not found.\n");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Library library = new Library("My Library");
        new LibraryGUI(library);
    }

    public Member findMemberById(String id) {
        for (Member member : library.getMembers()) {
            if (member.getID().equals(id)) {
                return member;
            }
        }
        return null;
    }

    public Book findBookByTitle(String title) {
        for (Book book : library.getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
