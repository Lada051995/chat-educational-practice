package model;


public class MessageModel {
    private long id;
    private String author;
    private long timestamp;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", author='" + author + '\'' +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}
