package org.example;

import java.util.ArrayList;
import java.util.List;

public class CuratorJournal {
    private final List<JournalEntry> entries = new ArrayList<>();

    public void addEntry(JournalEntry entry) {
        entries.add(entry);
        System.out.println("\nЗапис успішно додано до журналу.\n");
    }

    public void showAllEntries() {
        if (entries.isEmpty()) {
            System.out.println("\nЖурнал порожній.\n");
            return;
        }

        System.out.println("\nУсі записи журналу:\n");
        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("Запис #%d:%n%s%n", i + 1, entries.get(i));
        }
    }
}
