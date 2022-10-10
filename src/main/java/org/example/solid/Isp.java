package org.example.solid;

// ISP - Interface Segregation Principle
// Recommendation on how to split interfaces into smaller interfaces
public class Isp {
}

class Document {
}

interface Machine {
    void print(Document d);
    void fax(Document d);
    void scan(Document d);
}

// Preserving ISP
// Split Machine interface into smaller interfaces
interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

// Make the class implement just the interface needed
class JustAPrinter implements Printer {

    @Override
    public void print(Document d) {
        // Logic
    }
}

// NOTE: YAGNI - You Ain't Going to Need It
// Preserving ISP by implementing multiple interfaces
class PhotoCopier implements Printer, Scanner {

    @Override
    public void print(Document d) {
        // Logic
    }

    @Override
    public void scan(Document d) {
        // Logic
    }
}

// Preserving ISP by implementing an interface which extends multiple interfaces
interface PhotoCopierInterface extends Printer, Scanner{}

// Using Decorator design pattern
// Build a decorator: reuse functionality of printer and scanner
class PhotoCopier2 implements PhotoCopierInterface {
    private Printer printer;
    private Scanner scanner;

    public PhotoCopier2(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document d) {
        printer.print(d);
    }

    @Override
    public void scan(Document d) {
        scanner.scan(d);
    }
}

class MultiFunctionPrinter implements Machine {

    @Override
    public void print(Document d) {
        // Logic
    }

    @Override
    public void fax(Document d) {
        // Logic
    }

    @Override
    public void scan(Document d) {
        // Logic
    }
}

// Violates ISP
// As old printer can't fax or scan
// Giving user a false expectation when implementing Machine interface
class OldFashionedPrinter implements Machine {

    @Override
    public void print(Document d) {
        // Logic - Can do this
    }

    // Can throw exception in this method
    // But we have to propagate exception handling all the way to the machine interface
    @Override
    public void fax(Document d) {
        // Logic - CANNOT do this
    }

    @Override
    public void scan(Document d) {
        // Logic - CANNOT do this
    }
}