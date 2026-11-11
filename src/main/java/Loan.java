public class Loan {
    private final int id;
    private final String memberName;
    private final String itemTitle;
    private final int loanDays;
    private LoanStatus status; // "ACTIVE" or "RETURNED"
    private static int counter = 1;

    public Loan(String memberName, String itemTitle, int loanDays) {
        this.id = counter++;
        this.memberName = memberName;
        this.itemTitle = itemTitle;
        this.loanDays = loanDays;
        this.status = LoanStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getItemTitle() {
        return itemTitle;
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