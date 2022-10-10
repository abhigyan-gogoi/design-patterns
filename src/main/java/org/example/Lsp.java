package org.example;

// LSP - Liskov Substitution Principle
// Ability to substitute a subclass for a base class
// An API should not break after substitution
public class Lsp {
    static void useIt(Rectangle r){
        int width = r.getWidth();
        r.setHeight(10);
        // area = width*10
        System.out.println(
                "Expected area of "+(width*10) +
                        ", got "+r.getArea()
        );
    }

    public static void main(String[] args) {
        Rectangle r = new Rectangle(2,3);
        useIt(r);

        // Violating LSP
        // Setter sets both width and height without explicit mention
        Square s = new Square();
        s.setWidth(5);
        useIt(s);
        // Preserving LSP
        Rectangle sq = RectangleFactory.newSquare(5);
        useIt(sq);
    }
}

// Preserving LSP using Factory Pattern
// This allows for substitution without breaking the useIt() method
// hence preserving LSP
class RectangleFactory {
    public static Rectangle newRectangle(int width, int height){
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int side){
        return new Rectangle(side, side);
    }
}

class Rectangle {
    protected int height, width;

    public Rectangle (){}

    public Rectangle(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getArea(){
        return width*height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }

    // Preserving LSP
    // Detection for a square instead of a Square class
    public boolean isSquare(){
        return width == height;
    }
}

class Square extends Rectangle {
    public Square(){}

    public Square(int size){
        width = height = size;
    }

    // LSP violation
    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }

    // LSP violation
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }
}
