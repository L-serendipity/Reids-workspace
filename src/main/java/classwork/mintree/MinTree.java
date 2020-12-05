package classwork.mintree;

public class MinTree {
    private int minNum;
    private TreeNode minRoot;

    public TreeNode findSubTree(TreeNode root){
        minNum = Integer.MAX_VALUE;
        minRoot = null;
        getSum(root);
        return minRoot;
    }

    private int getSum(TreeNode root){
        if(root == null){
            return 0;
        }

        int sum = getSum(root.left) + getSum(root.right) + root.val;
        if(sum < minNum){
            minNum = sum;
            minRoot = root;
        }

        return sum;
    }
}
