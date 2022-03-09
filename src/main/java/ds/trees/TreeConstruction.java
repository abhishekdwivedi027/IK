package ds.trees;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TreeConstruction {

    // 0(n) space and time complexity
    private Map<Integer, Integer> indices = new HashMap();
    public TreeNode constructBinaryTree(int[] preordered, int[] inordered) {
        prepareIndices(inordered);
        return constructBinaryTreeHelper(preordered, 0, preordered.length-1, inordered, 0, inordered.length-1);
    }

    private void prepareIndices(int[] inordered) {
        for (int i=0; i<inordered.length; i++) {
            indices.put(inordered[i], i);
        }
    }

    private TreeNode constructBinaryTreeHelper(int[] preordered, int preorderedStart, int preorderedEnd, int[] inordered, int inorderedStart, int inorderedEnd) {
        if (preorderedStart == preordered.length) {
            return null;
        }

        TreeNode root = new TreeNode(preordered[preorderedStart]);
        int inorderedIndex = indices.get(preordered[preorderedStart]);
        int preorderedIndex = preorderedStart + inorderedIndex - inorderedStart;
        root.setLeft(constructBinaryTreeHelper(preordered, preorderedStart+1, preorderedIndex, inordered, inorderedStart, inorderedIndex-1));
        root.setLeft(constructBinaryTreeHelper(preordered, preorderedIndex+1, preorderedEnd, inordered, inorderedIndex+1, inorderedEnd));
        return root;
    }

    // special case
    public TreeNode constructBST(int[] preorder) {
        int[] inordered = Arrays.copyOf(preorder, preorder.length);
        return constructBinaryTree(preorder, inordered);
    }

    public TreeNode constructAVL(int[] sortedArray) {
        return constructAVLHelper(sortedArray, 0, sortedArray.length-1);
    }

    private TreeNode constructAVLHelper(int[] sortedArray, int left, int right) {
        if(left > right) {
            return null;
        }

        // will work without this
        if (left == right) {
            TreeNode node = new TreeNode(sortedArray[left]);
            return node;
        }

        int mid = left + (right - left)/2;
        TreeNode root = new TreeNode(sortedArray[mid]);
        root.setLeft(constructAVLHelper(sortedArray, left, mid-1));
        root.setRight(constructAVLHelper(sortedArray, mid+1, right));
        return root;
    }
}
