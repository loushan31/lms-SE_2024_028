package lms;

import lms.Model.*;
import lms.Service.Library;
import lms.exception.ItemNotAvailableException;
import lms.exception.MemberNotFoundException;

public class Main {

    public static void attempt(Library library, String itemId, String memberId) {

        try {
            library.borrowItem(itemId, memberId);

        } catch (ItemNotAvailableException e) {
            System.out.println("Cannot borrow: " + e.getMessage());

        } catch (MemberNotFoundException e) {
            System.out.println("Unknown member: " + e.getMemberId());

        } finally {
            System.out.println("    (attempt finished: item " +
                    itemId + ", member " + memberId + ")");
        }
    }

    public static void main(String[] args) {

        Library library = new Library();

        Book b1 = new Book("B001", "Clean Code", "Robert C. Martin");
        Book b2 = new Book("B002", "Effective Java", "Joshua Bloch");
        Book b3 = new Book("B003", "Java Programming", "John Smith");
        Book b4 = new Book("B004", "Head First Java", "Kathy Sierra");

        DVD d1 = new DVD("D001", "Introduction to Algorithms", 120);
        Magazine m1 = new Magazine("MG001", "Software Weekly", 12);

        library.addItem(b1);
        library.addItem(b2);
        library.addItem(b3);
        library.addItem(b4);
        library.addItem(d1);
        library.addItem(m1);

        Student student = new Student("M001", "Nadeesha Perera");
        Staff staff = new Staff("M002", "Mr. Kasun Silva");

        library.addMember(student);
        library.addMember(staff);

        System.out.println("\n--- 1. Successful borrow ---");
        attempt(library, "B001", "M001");

        System.out.println("\n--- 2. Already on loan (checked exception) ---");
        attempt(library, "B001", "M002");

        System.out.println("\n--- 3. Unknown member (unchecked exception) ---");
        attempt(library, "D001", "M999");

        System.out.println("\n--- 4. Unknown item ---");
        attempt(library, "X404", "M001");

        System.out.println("\n--- 5. Returning something not on loan ---");

        try {
            library.returnItem("D001");
        } catch (ItemNotAvailableException e) {
            System.out.println("Cannot return: " + e.getMessage());
        }

        System.out.println("\n--- 6. Bad data (IllegalArgumentException) ---");

        try {
            Book invalidBook = new Book("B005", " ", "Someone");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid book: " + e.getMessage());
        }

        System.out.println("\n--- 7. Student borrowing limit ---");

        attempt(library, "B002", "M001");
        attempt(library, "B003", "M001");
        attempt(library, "B004", "M001");

        System.out.println("\n--- 8. Return frees a slot ---");

        try {
            library.returnItem("B001");
        } catch (ItemNotAvailableException e) {
            System.out.println("Cannot return: " + e.getMessage());
        }

        attempt(library, "B004", "M001");
    }
}
