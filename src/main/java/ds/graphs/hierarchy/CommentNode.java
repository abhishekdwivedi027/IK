package ds.graphs.hierarchy;

import java.util.ArrayList;
import java.util.List;

public class CommentNode {
    private final Comment comment;
    private CommentNode parentComment;
    private final List<CommentNode> children = new ArrayList<>();

    public CommentNode(Comment comment, CommentNode parentComment) {
        this.comment = comment;
        this.parentComment = parentComment;
    }

    public Comment getComment() {
        return comment;
    }

    public CommentNode getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentNode parentComment) {
        this.parentComment = parentComment;
    }

    public List<CommentNode> getChildren() {
        return children;
    }
}
