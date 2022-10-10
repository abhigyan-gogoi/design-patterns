package org.example.solid;


// OCP - Open-Closed Principle
// Open for Extension - OK to inherent things or implement interfaces
// Closed for Modification
// Design Pattern - Enterprise Engineering - Specification

import java.util.List;
import java.util.stream.Stream;

enum Color {
    RED, GREEN ,BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE, HUGE
}

class Product {
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

// 2 criteria requires 3 methods
// 3 criteria requires 7 methods
// Exponential increase in code modification
// Need for code which is open to extension
// But closed for modification
// Violation of OCP as we have to modify class to implement more filters
class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.size == size);
    }

    public Stream<Product> filterBySizeAndColor(List<Product> products,
                                                Size size,
                                                Color color) {
        return products.stream().filter(p -> p.size == size && p.color == color);
    }
}

// Preserve OCP using Specification design pattern
interface Specification<T> {
    boolean isSatisfied(T item);
}

// Combining specifications
// Called a combinator
class AndSpecification<T> implements Specification<T> {
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.size == size;
    }
}

// Make a better filter
class BetterFilter implements Filter<Product> {
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(product -> spec.isSatisfied(product));
    }
}

public class Ocp {
    public static void main(String[] args) {
        Product apple = new Product("Apple", Color.RED, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
        Product car = new Product("Honda", Color.BLUE, Size.MEDIUM);

        List<Product> products = List.of(apple, tree, car);
        // Using non-OCP filter
        ProductFilter pf = new ProductFilter();
        System.out.println("Green Products (OLD IMPL): ");
        pf.filterByColor(products, Color.GREEN)
                .forEach(product -> System.out.println(" - "+product.name+" is GREEN"));

        // Using OCP filter (viable due to Specification design pattern)
        BetterFilter bf = new BetterFilter();
        System.out.println("Green Products (NEW IMPL): ");
        bf.filter(products, new ColorSpecification(Color.GREEN))
                .forEach(product -> System.out.println(" - "+product.name+" is GREEN"));

        // Using combinator
        // Filter BLUE Color and MEDIUM Size
        System.out.println("MEDIUM BLUE item: ");
        bf.filter(products,
                new AndSpecification<>(
                        new ColorSpecification(Color.BLUE),
                        new SizeSpecification(Size.MEDIUM)))
                .forEach(product -> System.out.println(" - "+product.name+" is MEDIUM and BLUE"));
    }
}
