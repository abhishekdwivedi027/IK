package ds.trees;

public class Tree {

    /*
        Data Structures

        1. Array: contiguous DS; random access

        a. unsorted

        Search - 0(n)
        Insert - 0(1)
        Delete - 0(n) + 0(1) + 0(n) => search, delete and left shift => 0(n)

        b. sorted

        Search - 0(log n) - binary search
        Insert - 0(n) + 0(1) + 0(n) => locate position, insert, and right shift => 0(n)
        Delete - 0(log n) + 0(1) + 0(n) => search, delete and left shift => 0(n)

        2. Linked List: (singly or doubly) linked DS; sequential access

        a. unsorted

        Search - 0(n)
        Insert - 0(1) => link with head
        Delete - 0(n) + 0(1) + 0(1) => search, delete and link => 0(n)

        b. sorted

        Search - 0(n) - sequential access; no binary search; sorting doesn't help
        Insert - 0(n) + 0(1) + 0(1) => locate position, insert and link => 0(n)
        Delete - 0(n) + 0(1) + 0(1) => search, delete and link => 0(n)

        sorted LL performs WORSE than unsorted LL !!!

        QUESTION: How to keep log complexity in search without having linear complexity in insert and delete?

        ANSWER: Dictionary ADT (special case - SET ADT - when value is null)

        1. Hash Table: array of linked lists; random access with HASH FUNCTION

        hash(key) --> array index - 0(1)
        map non-integer keys to array indices

        Search - 0(1) amortized
        Insert - 0(1)
        Delete - 0(1)

        However, hashtable is not ordered or sorted.
        JDK provides LinkedHashMap which keeps insertion order (by default) and access order (like in LRU cache) if needed

        2. Binary Tree: left & right links instead of next & prev links; sequential but not linear access

        Special Case - BST - (sorted) binary ds.tree where binary search can be applied

        Search - 0(log n) - if h ~ log n - if BST is balanced/complete (all levels must be filled except possibly the last)
        special case of balanced/complete ds.tree - AVL ds.tree - abs(height(subTree1) - height(subTree2)) <= 1
        Insert - 0(log n)
        Delete - 0(1)

        Rem: insert and delete operations skew/unbalance the BST and deteriorate search performance
        Re-balancing a BST is a 0(log n) operation
        TODO Balancing a Tree

        TreeNode<K> {
            K key;
            TreeNode<E> left;
            TreeNode<E> right;
        }

        TreeNode<K, V> {
            K key;
            V value;
            TreeNode<K, V> left;
            TreeNode<K, V> right;
        }

        TreeNode<K, V> {
            K key;
            V value;
            List<TreeNode<K, V>> children;
        }

        num(edge) = num(node) - 1

        Because Tree is defined recursively - like linked list - the solutions involving ds.tree are recursive in nature.

        Q. what's usage of ds.tree without BST?
        A. to represent hierarchical data - (N-ary ds.tree)

        Dictionary ADT implementation:

        1. Map/Set => BST - 0(log n)
        2. HashMap/HashSet => Hash Table - 0(1)

        Q. Why do we need BST when we have Hash Table already there with better performance?
        A. Because BST is ordered - sorted - which gives better performance wherever order is important
        - find min/max, successor/predecessor etc.

        You can implement (max/min) Heap using array. You can also implement Heap using BST if you need to add/remove items.
        Because BST gives better performance in search, and it gives good performance in finding max/min.

        Remember: the raison d'etre of ds.tree is - 1. represent hierarchical data, and 2. search performance (BST) - balanced

     */


    public TreeNode search(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        TreeNode current = root;

        while (current != null) {
            if (current.getKey() == key) {
                return current;
            } else if (current.getKey() < key) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }

        return null;
    }

    public TreeNode insert(TreeNode root, int key) {
        TreeNode node = new TreeNode(key, null, null);
        if (root == null) {
            return node;
        }

        TreeNode current = root;
        // insert - need parent - keep track of it while traversing the ds.tree
        TreeNode parent = null;

        while (current != null) {
            if (current.getKey() == key) {
                // found already there => no need to insert
                // throw exception
                return root;
            } else if (current.getKey() < key) {
                parent = current;
                current = current.getRight();
            } else {
                parent = current;
                current = current.getLeft();
            }
        }

        // current is null - leaf
        // attach new node to the parent now
        if (parent.getKey() < key) {
            parent.setRight(node);
        } else {
            parent.setLeft(node);
        }

        return root;
    }

    // getMax will be similar
    // 0(log n)
    // this performance cannot be made in hash table (as it's not sorted)
    public TreeNode getMin(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode current  = root;

        // find the leftmost node
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current;
    }

    public TreeNode getParent(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        TreeNode current  = root;
        TreeNode parent = null;

        while (current != null) {
            if (current.getKey() == key) {
                return parent;
            } else if (current.getKey() < key) {
                parent = current;
                current = current.getRight();
            } else {
                parent = current;
                current = current.getLeft();
            }
        }

        return parent;
    }

    // getPredecessor will be similar
    // 0(log n)
    // this performance cannot be made in hash table (as it's not sorted)
    // successor - next big node
    // successor = 1. downwards  - min (right sub ds.tree) || 2. upwards - go up in the chain - the first right turn is the guy
    public TreeNode getSuccessor(TreeNode root, TreeNode node) {
        if (root == null) {
            return null;
        }

        // do need min
        TreeNode minRightSubtree  = getMin(node.getRight());
        // no successor in right subtree only when there is no right subtree
        if (minRightSubtree != null) {
            return minRightSubtree;
        }

        // don't traverse from node upwards
        // don't traverse from root downwards multiple times
        // find node from root and find successor on the way - search operation
        // successor = the last left turn from root to the node (LLT from root)
        TreeNode current = root;
        TreeNode ancestor = null;
        while (current.getKey() != node.getKey()) {
            if (current.getKey() < node.getKey()) {
                // turning right - don't capture ancestor
                current = current.getRight();
            } else {
                // last left turn from root to node
                ancestor = current;
                current = current.getLeft();
            }
        }

        return ancestor;
    }

    public TreeNode delete(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        TreeNode current = root;
        TreeNode parent = null;

        while (current != null) {
            if (current.getKey() == key) {
                // found the node
                break;
            } else if (current.getKey() < key) {
                parent = current;
                current = current.getRight();
            } else {
                parent = current;
                current = current.getLeft();
            }
        }

        // current node has to be deleted

        // case 1: current node is leaf node
        boolean hasNoChild = current.getLeft() == null && current.getRight() == null;
        if (hasNoChild) {
            // root is the only node in the ds.tree - leaf
            if (parent == null) {
                return null;
            } else if (parent.getKey() < key) {
                parent.setRight(null);
            } else {
                parent.setLeft(null);
            }
        }

        // case 2: current node is internal node

        // 2a - this internal node has only one child
        boolean hasLeftChildOnly = current.getLeft() != null && current.getRight() == null;
        boolean hasRightChildOnly = current.getLeft() == null && current.getRight() != null;
        if (hasLeftChildOnly || hasRightChildOnly) {
            TreeNode child = hasLeftChildOnly ? current.getLeft() : current.getRight();
            // root has only one child
            if (parent == null) {
                root = child;
                return root;
            } else if (parent.getKey() < key) {
                parent.setRight(child);
            } else {
                parent.setLeft(child);
            }
        }

        // 2b - general case - node with two children
        // this node needs to swap values with either predecessor or successor
        // following that the predecessor or the successor should be deleted
        TreeNode successor  = current.getRight();
        TreeNode successorParent = current;
        while (successor.getLeft() != null) {
            successorParent = successor;
            successor = successor.getLeft();
        }

        // now we have the successor and its parent
        // successor maybe a leaf or a non-leaf (with one right child only)
        // this covers even when the deleted node is root itself
        current.setKey(successor.getKey());
        // successor has to be the left child of its parent
        successorParent.setLeft(successor.getRight());

        /*
        if (successor == successorParent.getLeft()) {
            successorParent.setLeft(successor.getRight());
        } else {
            successorParent.setRight(successor.getRight());
        }
        */

        return root;
    }

}
