package Model;


public class MessageModel {
    private long id;
    private String author;
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

    @Override
    public String toString() {
        return "MessageModel{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
