package ds.trees;

import java.util.*;
import java.util.stream.Collectors;

public class TreeTraversal {

    /*

        Tree Traversal 0(n)

        1. BFS: level-order traversal - the code hits a node exactly once
        2. DFS: the code hits a node exactly thrice
            a. preorder - hits on the left - at very first; before left and right subtree
            b. inorder - hits on the bottom - after left subtree and before right subtree
            c. postorder - hits on the right - at very end; after left and right subtree
     */

    public void levelOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        // before traversing any level

        while (!q.isEmpty()) {

            // level traversal
            // num of nodes at this level - frozen - though q size is changing in the loop
            int nodeNum = q.size();
            while (nodeNum-- > 0) {
                TreeNode node = q.poll();
                // count++
                System.out.println(node.getKey());

                // add children
                // from left to right
                if (node.getLeft() != null) {
                    q.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    q.add(node.getRight());
                }
            }

            // node num zero -- done with nodes of one level
            // here we can reverse the order in one level
            // level++
        }

        // q empty
        // after traversing all levels
        // here we can reverse the whole ds.trees upside down
    }

    // lazy manager works upfront
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        preOrderHelper(root);
    }

    private void preOrderHelper(TreeNode root) {
        System.out.println(root.getKey());
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    // no inorder traversal for N-ary trees
    // can be used to find successors and predecessors of a given node
    // Important: ds.trees algo.sort - make/balance a BST - 0(log n) - followed by inorder traversal - 0(n)
    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        inOrderHelper(root);
    }

    private void inOrderHelper(TreeNode root) {
        inOrder(root.getLeft());
        System.out.println(root.getKey());
        inOrder(root.getRight());
    }

    // lazy manager works last
    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        postOrderHelper(root);
    }

    private void postOrderHelper(TreeNode root) {
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.println(root.getKey());
    }

    /*
        Application of Tree Traversal
     */

    public List<TreeNode> getRightBoundary(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<TreeNode> rightBoundaryNodes = new ArrayList<>();

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {

            int nodeNum = q.size();
            // right boundary => last node in the level => stack
            Stack<TreeNode> levelNodes = new Stack<>();
            while(nodeNum-- > 0) {
                TreeNode node = q.poll();
                levelNodes.push(node);
                if (node.getLeft() != null) {
                    q.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    q.add(node.getRight());
                }
            }

            // after traversing level adding only the rightmost node to the result
            rightBoundaryNodes.add(levelNodes.pop());
        }

        return rightBoundaryNodes;
    }

    public List<TreeNode> getZigZagOrder(TreeNode root) {
        if (root == null) {
            return null;
        }

        List<TreeNode> zigzagNodes = new ArrayList<>();

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        boolean leftToRight = false;

        while (!q.isEmpty()) {

            int nodeNum = q.size();
            List<TreeNode> levelNodes = new ArrayList<>();
            while (nodeNum-- > 0) {
                TreeNode node = q.poll();
                levelNodes.add(node);

                if (node.getLeft() != null) {
                    q.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    q.add(node.getRight());
                }

            }

            if (!leftToRight) {
                Collections.reverse(levelNodes);
            }
            zigzagNodes.addAll(levelNodes);
            leftToRight = !leftToRight;
        }
        return  zigzagNodes;
    }

    // template for DFS
    // global var for top-down flow
    // vertical path => top-down will work
    private boolean hasPathSum;
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        hasPathSumHelper(root, sum);

        return hasPathSum;
    }

    private void hasPathSumHelper(TreeNode node, int targetSum) {

        // backtracking
        if (hasPathSum) { // or root == null // null child of leaf or internal??
            return;
        }

        // base case - leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            hasPathSum = node.getKey() == targetSum;
            return;
        }

        // recursion case(s) - internal nodes
        if (node.getLeft() != null) {
            hasPathSumHelper(node.getLeft(), targetSum - node.getKey());
        }
        if (node.getRight() != null) {
            hasPathSumHelper(node.getRight(), targetSum - node.getKey());
        }
    }

    // global var for top-down flow
    private List<List<Integer>> pathSums;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) {
            return null;
        }

        List<Integer> pathSum = new ArrayList<>();
        pathSumHelper(root, sum, pathSum);

        return pathSums;
    }

    // 0(n * log n) - both space and time complexity
    private void pathSumHelper(TreeNode node, int targetSum, List<Integer> pathSum) {

        // base case - leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            if (node.getKey() == targetSum) {
                pathSum.add(targetSum);
                pathSums.add(pathSum.stream().collect(Collectors.toList()));
                pathSum.remove(pathSum.lastIndexOf(targetSum));
            }
            return;
        }

        // recursion case(s) - internal nodes
        int val = node.getLeft().getKey();
        pathSum.add(val);
        if (node.getLeft() != null) {
            pathSumHelper(node.getLeft(), targetSum - node.getKey(), pathSum);
        }
        if (node.getRight() != null) {
            pathSumHelper(node.getRight(), targetSum - node.getKey(), pathSum);
        }
        pathSum.remove(pathSum.lastIndexOf(val));
    }

    // diameter - inverted V
    // space and time complexity O(n)
    private int globalDiameter = 0;
    public int getDiameter(TreeNode root) {
        if (root == null) {
            return 0;
        }

        getDiameterHelper(root);

        return globalDiameter;
    }

    // special case - global var with return helper
    private int getDiameterHelper(TreeNode node) {
        // leaf node
        // height = 0
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        }

        // we are doing two things here
        // 1. calculating and sending maxHeight to calling node (parent) - return
        // 2. calculating localDiameter and updating globalDiameter, if needed - global var

        int localDiameter = 0;

        int leftHeight = 0;
        if (node.getLeft() != null) {
            leftHeight = getDiameterHelper(node.getLeft());
            localDiameter += leftHeight + 1;
        }

        int rightHeight = 0;
        if (node.getRight() != null) {
            rightHeight = getDiameterHelper(node.getRight());
            localDiameter += rightHeight + 1;
        }

        // updates global diameter
        globalDiameter = Math.max(globalDiameter, localDiameter);

        // returns maxHeight to parent
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // count num of uni-val subtrees
    // topdown traversal won't help
    private int globalUniValSubTreesCount = 0;
    public int countUniValSubTrees(TreeNode root) {
        if (root == null) {
            return 0;
        }

        countUniValSubTreesHelper(root);

        return globalUniValSubTreesCount;
    }

    // special case - global var with return helper
    private boolean countUniValSubTreesHelper(TreeNode node) {
        // leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            globalUniValSubTreesCount++;
            return true;
        }

        // we are doing two things here
        // 1. calculating and sending if this subtree is unival to calling parent - return true/false
        // 2. updating globalUniValSubTreesCount, if this subtree is unival

        // internal node
        boolean isNodeUniVal = true;

        if (node.getLeft() != null) {
            // don't short circuit here - calculate count in left subtree
            isNodeUniVal = countUniValSubTreesHelper(node.getLeft()) && isNodeUniVal;
            isNodeUniVal = isNodeUniVal && node.getLeft().getKey() == node.getKey();
        }

        if (node.getRight() != null) {
            // don't short circuit here - calculate count in right subtree
            isNodeUniVal = countUniValSubTreesHelper(node.getRight()) && isNodeUniVal;
            isNodeUniVal = isNodeUniVal && node.getRight().getKey() == node.getKey();

        }

        if (isNodeUniVal) {
            globalUniValSubTreesCount++;
        }

        return isNodeUniVal;
    }

    // take a general node
    // see what it gives to/takes from its subtrees and parent
    public TreeNode lca(TreeNode root, int a, int b) {
        if (root == null) {
            return null;
        }

        return lcaHelper(root, a, b);
    }

    private TreeNode lcaHelper(TreeNode node, int a, int b) {
        // base case
        if (node.getKey() == a || node.getKey() == b) {
            return node;
        }

        // leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            return null;
        }

        // internal node
        TreeNode leftLca = null;
        if (node.getLeft() != null) {
            leftLca = lcaHelper(node.getLeft(), a, b);
        }

        TreeNode rightLca = null;
        if (node.getRight() != null) {
            rightLca = lcaHelper(node.getRight(), a, b);
        }

        TreeNode localLca = null;
        if (leftLca != null && rightLca != null) {
            localLca = node;
        } else if (leftLca != null) {
            localLca = leftLca;
        } else if (rightLca != null) {
            localLca = rightLca;
        }

        return localLca;
    }

}
