package org.example;

import javax.crypto.Mac;

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