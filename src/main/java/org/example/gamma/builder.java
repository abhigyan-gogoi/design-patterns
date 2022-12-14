package org.example.gamma;

import java.util.ArrayList;
import java.util.Collections;


class HtmlElement {
    public String name, text;
    public ArrayList<HtmlElement> elements = new ArrayList<>();
    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();

    public HtmlElement(){}

    public HtmlElement(String name, String text) {
        this.name = name;
        this.text = text;
    }

    private String toStringImpl(int indent){
        StringBuilder sb = new StringBuilder();
        String i = String.join("", Collections.nCopies(indent*indentSize, " "));
        sb.append(String.format("%s<%s>%s", i, name, newLine));
        if (text != null && !text.isEmpty()){
            sb.append(String.join("", Collections.nCopies(indentSize*(indent+1), "")))
                    .append(text)
                    .append(newLine)
            ;
        }
        for (HtmlElement e:elements){
            sb.append(e.toStringImpl(indent+1));
        }
        sb.append(String.format("%s</%s>%s", i, name, newLine));
        return sb.toString();
    }

    @Override
    public String toString() {
        return toStringImpl(0);
    }
}

class HtmlBuilder {
    private final String rootName;
    private HtmlElement root = new HtmlElement();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        root.name = rootName;
    }

    // Changing Custom Builder into a fluent interface
    // Change return type void to the same type (HtmlBuilder)
    public HtmlBuilder addChild(String childName, String childText) {
        HtmlElement e = new HtmlElement(childName, childText);
        root.elements.add(e);
        return this;
    }

    public void clear() {
        root = new HtmlElement();
        root.name = rootName;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}


public class builder {
    public static void main(String[] args) {
        // Simple example
        System.out.println("SIMPLE EXAMPLE");
        String hello = "hello";
        System.out.println("<p>" + hello +"</p>");
        System.out.println("---------------------------------------------");

        // Complex example
        System.out.println("COMPLEX EXAMPLE");
        String [] words = {"hello", "world"};
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        for (String word:words){
            sb.append(String.format("\t<li>%s</li>\n", word));
        }
        sb.append("</ul>");
        System.out.println(sb);
        System.out.println("---------------------------------------------");

        // HTML Builder example
        System.out.println("HTML BUILDER EXAMPLE");
        HtmlBuilder builder = new HtmlBuilder("ul");
        builder.addChild("li", "Hello");
        builder.addChild("li", "World");
        System.out.println(builder);
        System.out.println("---------------------------------------------");

        // Fluent  interface
        System.out.println("FLUENT INTERFACE EXAMPLE");
        StringBuilder s = new StringBuilder();
        s.append("foo") // Return type is a string builder
                .append("bar"); // We can chain StringBuilder methods
        System.out.println(s);
        System.out.println("---------------------------------------------");

        // Fluent  interface Custom builder
        System.out.println("HTML BUILDER FLUENT EXAMPLE");
        HtmlBuilder b = new HtmlBuilder("ul");
        // We can chain methods
        b.addChild("li", "Hello")
                .addChild("li", "World");
        System.out.println(b);
        System.out.println("---------------------------------------------");
    }
}
