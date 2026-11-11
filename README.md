# The Reading Room — Community Library Lending Tracker

This is the starter repository for the **Java Basics** applied lab. Read the lab
brief alongside this repository; it explains the business, what you are being
asked to deliver, and how your work is assessed.

## What this is

A small program The Reading Room uses to track what is borrowed. It works, but it has become risky to change. Your task is to diagnose it
and rebuild it well — **not** to change what it does, but to make it safe to
change and impossible to leave in an invalid state.

## Running it

The program is plain Java (no external dependencies). You need JDK 21.

**With Maven:**

```bash
mvn compile
mvn exec:java -Dexec.mainClass=LendingTracker
```

**Or directly with the JDK:**

```bash
javac -d target/classes src/main/java/LendingTracker.java
java -cp target/classes LendingTracker
```

Run it first, before you change anything, so you can see how it currently
behaves. Look carefully at the end-of-day report.

## What to hand in

See the lab brief for the full list: a written diagnosis, your rebuilt version
(behaving exactly as the original does today), a short note on your key design
decisions, and one improvement beyond the brief. You will talk through your work
in a live review.

## Design Notes: The Reading Room Lending Tracker

## 1. Architectural Overview
The original code was just a single, messy script where everything could access and change everything else, and important safety checks were missing.

I fixed this by switching to a clean **Object-Oriented Design**. Now, responsibilities are split between clear categories (`Library`, `LendingItem`, `Member`, and `Loan`), data is safely locked down, and raw text strings have been replaced with reliable choice lists (Enums).

---

## 2. Key Design Decisions

### A. Swapping Text Strings for Choice Lists (Enums)
* **Decision:** Replaced loose text strings (like `"BOOK"` or `"ACTIVE"`) with strict Java Enums (`ItemType`, `MemberType`, `ItemStatus`, `LoanStatus`).
* **Why:** Typing text manually leads to silly typos (like writing `"Book "` with an extra space). Enums lock the options down so the computer catches mistakes immediately.

### B. Locking Down Data & Preventing Double-Loans
* **Decision:** Made all internal data private and forced state changes to go through specific methods (like `item.checkout()` or `item.returnItem()`).
* **Why:** In the old code, anything could accidentally change a book's status. Now, the system actively checks rules before letting anything happen. For example, if someone tries to borrow a book that is already out, it instantly blocks the action so a book can never be double-loaned.

### C. Shielding Lists from Outside Messing
* **Decision:** Whenever code asks for the list of items or loans, the system hands over a safe copy instead of the original list (`new ArrayList<>(loans)`).
* **Why:** This stops outside code from accidentally deleting or messing up the library's main records.

### D. Volunteer-Friendly Error Handling & Member Validation
* **What I did:** Added simple check-ins to make sure members are registered before borrowing, and caught program errors so they show up as clear warning banners (like `⚠️ ACTION BLOCKED`).
* **Why I did it:** Library volunteers shouldn't be scared off by messy Java error screens (stack traces). Clear messages guide the user smoothly, and verifying members keeps random walk-ins from breaking system records.


### E. Robust Object-Based Loans & Unique Item Tracking
* **Decision:** Updated the `Loan` class to hold direct references to the `Member` and `LendingItem` objects rather than plain text strings (`itemTitle` and `memberName`).
* **Why:** In the previous iteration, loans matched items by their string titles. This created a severe robustness risk where stocking two identical copies of a book would cause returning one copy to prematurely close active loans for both. By shifting to object-reference and unique instance tracking (`l.getItem().equals(item)`), multiple copies of the same item can now be borrowed, tracked, and returned completely independently.

---

## 3. Future-Proofing: Making E-Loans Easy to Add

The library wants to add **e-loans** (digital items) soon without breaking what's already built.

* **What the old version would have forced:**
  In the old messy script, adding digital items would mean digging into a giant maze of `if-else` statements. I'd risk breaking physical book checkouts just to add a digital file.
* **How the new structure makes it easy:**
  Because my code keeps item types separate and manages rules cleanly, adding e-loans is simple. I can just introduce a new digital item type without rewriting the core borrowing process at all.
