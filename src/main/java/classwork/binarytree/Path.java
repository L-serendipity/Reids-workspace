package classwork.binarytree;

import java.util.ArrayList;
import java.util.List;

public class Path {
    public List<String> binaryTreePaths(TreeNode root){
        ArrayList<String> paths = new ArrayList<String>();

        if (root != null) {
            List<String> leftPaths = binaryTreePaths(root.left);
            List<String> rightPaths = binaryTreePaths(root.right);
            for (String path : leftPaths) {
                paths.add(root.val + "->" + path);
            }
            for (String path : rightPaths) {
                paths.add(root.val + "->" + path);
            }
            if (paths.size() == 0) {
                paths.add("" + root.val);
            }

        }
        return paths;

    }
}
