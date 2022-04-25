package ds.graphs.hierarchy;

public class Comment {

    private final Long id;
    private final Long parentId;
    private String text;

    public Comment(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
