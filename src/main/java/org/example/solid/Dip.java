package org.example.solid;

// DIP - Dependency Inversion Principle
// Not directly connected to the idea of Dependency Injection
// Two things for DIP
// 1. High-level modules should not depend on low-level modules.
// Both should depend on abstractions (abstract class/interface)
// 2. Abstraction should not depend on details.
// Details should depend on abstraction (signature of something which performs an operation, not the concrete impl)
// 2. Allows for substitution of one impl for another without breaking anything

import org.javatuples.Triplet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


enum Relationship {
    PARENT, CHILD, SIBLING
}

public class Dip {
    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentChild(parent, child1);
        relationships.addParentChild(parent, child2);

        new Research(relationships);
    }
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

// Preserve DIP
// Create an abstraction
interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

// Low-level module
// Related to data storage
class Relationships implements RelationshipBrowser{
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    // Search in low-level module
    // High-level module search requires us to refactor a lot of code
    // e.g.: Change data types
    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(x -> Objects.equals(x.getValue0().name, name)
                && x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}

// Breaking DIP
// Exposing private member as a public getter
// Depends on low-level Relationships module
//
// High-level module
// Performs operations on low-level constructs
class Research {
    // Depending on a low-level module
//    public Research(Relationships relationships) {
//        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream()
//                .filter(x -> x.getValue0().name.equals("John")
//                && x.getValue1() == Relationship.PARENT)
//                .forEach(ch -> System.out.println("John has a child called " + ch.getValue2().name));
//    }

    // depending on an abstraction
    public Research(RelationshipBrowser browser) {
        List<Person> children = browser.findAllChildrenOf("John");
        for (Person child: children)
            System.out.println("John has a child called "+child.name);
    }
}
