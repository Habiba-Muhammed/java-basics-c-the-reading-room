public class Loan {
    private final int id;
    private final Member member;
    private final LendingItem item;
    private final int loanDays;
    private LoanStatus status; // "ACTIVE" or "RETURNED"
    private static int counter = 1;

    public Loan(Member member, LendingItem item, int loanDays) {
        this.id = counter++;
        this.member = member;
        this.item = item;
        this.loanDays = loanDays;
        this.status = LoanStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getMemberName() {
        return member.getName();
    }

    public LendingItem getItem() {
        return item;
    }

    public String getItemTitle() {
        return item.getTitle();
    }

    public int getLoanDays() {
        return loanDays;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void completeLoan() {
        if (this.status == LoanStatus.RETURNED) {
            throw new IllegalStateException("This loan has already been returned!");
        }
        this.status = LoanStatus.RETURNED;
    }
}