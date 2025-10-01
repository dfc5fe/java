package org.example;

import java.util.List;

public class CuratorJournal {
    private final MyList<JournalEntry> journal = new MyArrayList<>();

    public void addJournalEntry(JournalEntry record) {
        journal.add(record);
    }

    public List<JournalEntry> getAllEntries() {
        return journal.toList();
    }
}
