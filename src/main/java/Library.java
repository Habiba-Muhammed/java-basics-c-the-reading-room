import java.util.ArrayList;
import java.util.List;

public class Library {
    private final String name;
    private final List<LendingItem> items = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    public Library(String name) {
        this.name = name;
    }

    public void addItem(LendingItem i) {
        items.add(i);
    }

    public void addMember(Member m) {
        members.add(m);
    }




    // Lend an item to a member. The loan period depends on the kind of item and member.
    public void borrow(Member m, LendingItem item) {
    // if person is not a member
        if (m == null || !members.contains(m)) {
            throw new IllegalArgumentException("Error: Member is not registered with this library!");
        }

        if (!item.isAvailable()) {
            throw new IllegalStateException("Item \"" + item.getTitle() + "\" is already on loan!");
        }

        if (!item.isAvailable()) {
            throw new IllegalStateException("Item \"" + item.getTitle() + "\" is already on loan!");
        }

        int loanDays = 0;
        if (item.getType() == ItemType.BOOK) {
            loanDays = (m.getKind() == MemberType.STUDENT) ? 28 : 14;
        } else if (item.getType() == ItemType.AUDIO_KIT) {
            loanDays = (m.getKind() == MemberType.STUDENT) ? 14 : 7;
        }

        // Use encapsulated domain methods instead of raw field assignments
        item.checkout(m);
        m.addHistory("Borrowed " + item.getTitle());
        loans.add(new Loan(m.getName(), item.getTitle(), loanDays));

        System.out.println("Lent \"" + item.getTitle() + "\" to " + m.getName() + " for " + loanDays + " days");
    }

    public void returnItem(LendingItem item) {
        // Reset item status via domain method
        item.returnItem();

        // Complete the active loan via its domain method
        for (Loan l : loans) {
            if (l.getItemTitle().equals(item.getTitle()) && l.getStatus() == LoanStatus.ACTIVE) {
                l.completeLoan();
            }
        }
        System.out.println("Returned \"" + item.getTitle() + "\"");
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    public List<LendingItem> getItems() {
        return new ArrayList<>(items);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public String getName() {
        return name;
    }

    public void printReport() {
        System.out.println();
        System.out.println("=== " + name + " - end of day ===");
        System.out.println("Items:");
        for (LendingItem i : items) {
            System.out.println("  \"" + i.getTitle() + "\" (" + i.getType() + "): " + i.getStatus()
                    + (i.getCurrentBorrower() != null ? " - with " + i.getCurrentBorrower() : ""));
        }
        System.out.println("Active loans:");
        for (Loan l : loans) {
            if (l.getStatus() == LoanStatus.ACTIVE) {
                System.out.println("  Loan #" + l.getId() + ": \"" + l.getItemTitle() + "\" -> " + l.getMemberName());
            }
        }
    }
}
