package app.models;

public class Quote {
    private String text;
    private String author;

    public Quote() {
        this("", "");
    }

    public Quote(String text, String author) {
        setText(text);
        setAuthor(author);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
