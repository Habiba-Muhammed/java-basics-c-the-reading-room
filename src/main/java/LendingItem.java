class LendingItem {
    private final String title;
    private final String type;   // "BOOK" or "AUDIO_KIT"
    private ItemStatus status; // "AVAILABLE" or "ON_LOAN"
    private Member currentBorrower;
    private final int id;
    private static int counter = 1;

    public LendingItem(String title, String type) {
        this.id = counter++;
        this.title = title;
        this.type = type;
        this.status = ItemStatus.AVAILABLE;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public Member getCurrentBorrower() {
        return currentBorrower;
    }

    public boolean isAvailable() {
        return status == ItemStatus.AVAILABLE;
    }

    public void checkout(Member borrower) {
        if (!isAvailable()) {
            throw new IllegalStateException("Item is already on loan!");
        }
        this.status = ItemStatus.ON_LOAN;
        this.currentBorrower = borrower;
    }

    public void returnItem() {
        this.status = ItemStatus.AVAILABLE;
        this.currentBorrower = null;
    }

}