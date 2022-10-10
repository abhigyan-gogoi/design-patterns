package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// SRP - Single Responsibility Principle
// Allows for easier refactoring, management, and understandability
public class Srp
{
    public static void main( String[] args ) throws Exception{
        System.out.println( "SRP - Example" );
        Journal j = new Journal();
        j.addEntry("1st entry");
        j.addEntry("Depression text. Lorem Ipsum");
        System.out.println(j);

        Persistence p = new Persistence();
        String fileName = "c:\\temp\\journal.txt";
        p.saveToFile(j, fileName, true);

        Runtime.getRuntime().exec("notepad.exe " + fileName);
    }
}

// Breaking SRP - God Object
class Journal {
    private final List<String> entries = new ArrayList<>();
    private int count = 0;

    public void addEntry(String text) {
        entries.add("" + (++count) + ": " + text);
    }

    public void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    // Breaking SRP
    // Taking on a new concern: saving and loading files
    // Along with handling the adding/removal of entries
    // Also handling persistence (a separate concern)
    // Move persistence handlers to a separate class to preserve SRP
    public void save(String fileName) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(fileName)){
            out.println(toString());
        }
    }
    public void load (String fileName) {
        // Logic here
    }
}

// Preserving SRP
// Separating persistence handlers to its own class
class Persistence {
    public void saveToFile (Journal journal, String fileName, Boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(fileName).exists()) {
            try (PrintStream out = new PrintStream(fileName)){
                out.println(journal.toString());
            }
        }
    }

    public Journal load(String fileName) {
        // Logic here
        return null;
    }
}
