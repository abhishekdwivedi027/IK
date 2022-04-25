package ds.graphs.hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hierarchy {

    /**
     * Map<Long, List<Item>> parentChildrenMap = new HashMap<>();
     * @param args
     */

    // general hierarchy - parent (key) and list of children (value)
    private Map<Long, List<CommentNode>> parentChildrenMap = new HashMap<>();

    public static void main(String[] args) {

        Hierarchy hierarchy = new Hierarchy();

        // example Input
        // examine the nature of input and question your inherent assumptions
        List <Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, null));
        comments.add(new Comment(2L, 1L));
        comments.add(new Comment(3L, 1L));
        comments.add(new Comment(4L, null));
        comments.add(new Comment(5L, 4L));
        comments.add(new Comment(6L, 5L));

        // treeNodes should contain two CommentNodes
        List<CommentNode> treeNodes = hierarchy.flatToTree(comments);
        hierarchy.printTree(treeNodes);
    }

    public List<CommentNode> flatToTree(List<Comment> comments) {
        /**
         * Convert the comments from a flat list to a hierarchical structure.
         * The comments should be returned in the same order provided to the function.
         */
        List<CommentNode> commentNodes = new ArrayList<>();

        for (Comment comment: comments) {
            CommentNode commentNode = new CommentNode(comment, null);
            if (comment.getParentId() == null) {
                commentNodes.add(commentNode);
            } else {
                long parentId = comment.getParentId();
                if (!parentChildrenMap.containsKey(parentId)) {
                    List<CommentNode> children = new ArrayList<>();
                    parentChildrenMap.put(parentId, children);
                }
                parentChildrenMap.get(parentId).add(commentNode);
            }
        }

        return commentNodes;
    }

    // traverse the hierarchy - not graph/tree traversal
    public void printTree(List<CommentNode> roots) {
        /*
            1
                2
                3
            4
                5
                    6
        */
        printTreeHelper(roots, "");
    }

    private void printTreeHelper(List<CommentNode> roots, String space) {
        for (CommentNode root: roots) {
            Comment comment = root.getComment();
            long id = comment.getId();
            System.out.println(space + id);
            List<CommentNode> children = parentChildrenMap.get(id);
            if (children != null && !children.isEmpty()) {
                printTreeHelper(children, space + "    ");
            }
        }
    }
}
