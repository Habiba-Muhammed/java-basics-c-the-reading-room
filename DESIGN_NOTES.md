# Design Notes: The Reading Room Lending Tracker

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