import java.util.ArrayList;
import java.util.List;

class Member {
    private final String name;
    private final MemberType kind; // "STANDARD" or "STUDENT"
    private final int id;
    private final List<String> history = new ArrayList<>();
    private static int counter = 1;

    public Member(String name, MemberType kind) {
        this.id = counter++;
        this.name = name;
        this.kind = kind;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public int getId() {
        return id;
    }

    public MemberType getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    //adding a borrowed book to a member's history
    public void addHistory(String entry) {
        history.add(entry);
    }
}