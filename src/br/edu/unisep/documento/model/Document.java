package br.edu.unisep.documento.model;

public class Document {
    private String title;
    private String author;
    private String date;

    public Document(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return title + "," + author + "," + date;
    }

    public static Document fromString(String line) {
        String[] parts = line.split(",");
        return new Document(parts[0], parts[1], parts[2]);
    }
}
