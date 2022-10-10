package org.example.gamma;

public class builder {
    public static void main(String[] args) {
        // Simple example
        String hello = "hello";
        System.out.println("<p>" + hello +"</p>");
        // Complex example
        String [] words = {"hello", "world"};
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        for (String word:words){
            sb.append(String.format("\t<li>%s</li>\n", word));
        }
        sb.append("</ul>");
        System.out.println(sb);
    }
}
