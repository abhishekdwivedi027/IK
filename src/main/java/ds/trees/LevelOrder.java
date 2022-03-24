package ds.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {

    public int[] levelAverage(TreeNode root) {
        List<Integer> averages = new ArrayList<>();
        if (root == null) {
            return null;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {

            int qSize = q.size();
            int levelCount = 0;
            int levelSum = 0;
            while (qSize-- > 0) {
                TreeNode node = q.poll();
                levelCount++;
                levelSum += node.getKey();

                if (node.getLeft() != null) {
                    q.add(node.getLeft());
                }

                if (node.getRight() != null) {
                    q.add(node.getRight());
                }
            }

            Integer levelAverage = levelSum/levelCount;
            averages.add(levelAverage);
        }

        return averages.stream().mapToInt(Integer::intValue).toArray();
    }
}
