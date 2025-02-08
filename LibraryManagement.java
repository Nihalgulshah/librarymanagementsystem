import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class Book {
    String id;
    String title;
    String author;
    String isbn;
    int copiesAvailable;
    int copiesTotal;

    public Book(String id, String title, String author, String isbn, int copiesTotal) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;  // Initially all copies are available
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", ISBN: " + isbn +
               ", Available Copies: " + copiesAvailable + ", Total Copies: " + copiesTotal;
    }
}

class Member {
    String id;
    String name;
    String address;
    String phone;

    public Member(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Address: " + address + ", Phone: " + phone;
    }
}

class Transaction {
    String bookId;
    String memberId;
    Date issueDate;
    Date returnDate;

    public Transaction(String bookId, String memberId, Date issueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = null;  // Initially not returned
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Book ID: " + bookId + ", Member ID: " + memberId + ", Issue Date: " + dateFormat.format(issueDate) +
               (returnDate != null ? ", Return Date: " + dateFormat.format(returnDate) : ", Not Returned");
    }
}

class LibraryManagementSystem {
    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<Transaction> transactions;
    private int nextBookId = 1;
    private int nextMemberId = 1;

    public LibraryManagementSystem() {
        books = new HashMap<>();
        members = new HashMap<>();
        transactions = new ArrayList<>();
    }

    public void addBook(String title, String author, String isbn, int copies) {
        String bookId = String.valueOf(nextBookId++);
        Book book = new Book(bookId, title, author, isbn, copies);
        books.put(bookId, book);
        System.out.println("Book added with ID: " + bookId);
    }

    public void addMember(String name, String address, String phone) {
        String memberId = String.valueOf(nextMemberId++);
        Member member = new Member(memberId, name, address, phone);
        members.put(memberId, member);
        System.out.println("Member added with ID: " + memberId);
    }

    public void issueBook(String bookId, String memberId, Date issueDate) {
        Book book = books.get(bookId);
        Member member = members.get(memberId);

        if (book != null && member != null && book.copiesAvailable > 0) {
            Transaction transaction = new Transaction(bookId, memberId, issueDate);
            transactions.add(transaction);
            book.copiesAvailable--;
            System.out.println("Book issued: " + transaction);
        } else {
            System.out.println("Invalid book or member ID, or book not available.");
        }
    }

    public void returnBook(String bookId, String memberId, Date returnDate) {
        for (Transaction transaction : transactions) {
            if (transaction.bookId.equals(bookId) && transaction.memberId.equals(memberId) && transaction.returnDate == null) {
                transaction.returnDate = returnDate;
                books.get(bookId).copiesAvailable++;
                System.out.println("Book returned: " + transaction);
                return;
            }
        }
        System.out.println("No matching issued book found for return.");
    }

    public void listBooks() {
        System.out.println("\nBooks:");
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    public void listMembers() {
        System.out.println("\nMembers:");
        for (Member member : members.values()) {
            System.out.println(member);
        }
    }

    public void listTransactions() {
        System.out.println("\nTransactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. List Books");
            System.out.println("6. List Members");
            System.out.println("7. List Transactions");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Enter number of copies: ");
                        int copies = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        system.addBook(title, author, isbn, copies);
                        break;
                    case 2:
                        System.out.print("Enter member name: ");
                        String memberName = scanner.nextLine();
                        System.out.print("Enter address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter phone: ");
                        String phone = scanner.nextLine();
                        system.addMember(memberName, address, phone);
                        break;
                    case 3:
                        System.out.print("Enter book ID: ");
                        String issueBookId = scanner.nextLine();
                        System.out.print("Enter member ID: ");
                        String issueMemberId = scanner.nextLine();
                        System.out.print("Enter issue date (yyyy-MM-dd): ");
                        Date issueDate = dateFormat.parse(scanner.nextLine());
                        system.issueBook(issueBookId, issueMemberId, issueDate);
                        break;
                    case 4:
                        System.out.print("Enter book ID: ");
                        String returnBookId = scanner.nextLine();
                        System.out.print("Enter member ID: ");
                        String returnMemberId = scanner.nextLine();
                        System.out.print("Enter return date (yyyy-MM-dd): ");
                        Date returnDate = dateFormat.parse(scanner.nextLine());
                        system.returnBook(returnBookId, returnMemberId, returnDate);
                        break;
                    case 5:
                        system.listBooks();
                        break;
                    case 6:
                        system.listMembers();
                        break;
                    case 7:
                        system.listTransactions();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid data.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}
