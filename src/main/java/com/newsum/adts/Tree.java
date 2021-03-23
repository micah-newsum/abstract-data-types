package com.newsum.adts;

public class Tree {
  private TreeNode root;

  public void insert(int value){
    if (root == null){
      root = new TreeNode(value);
    } else {
      root.insert(value);
    }
  }

  public void traverseInOrder(){
    if (root != null) {
      root.traverseInOrder();
    }
  }

  public TreeNode get(int value){
    if (root != null){
      return root.get(value);
    }
    return null;
  }

  public void delete(int value){
    root = delete(root, value);
  }

  /**
   * Returns replacement node.
   * @param subtreeRoot
   * @param value
   * @return
   */
  private TreeNode delete(TreeNode subtreeRoot, int value){
    // base case for recursion
    if (subtreeRoot == null) {
      return null;
    }

    if (value < subtreeRoot.getData()){
      subtreeRoot.setLeftChild(delete(subtreeRoot.getLeftChild(),value));
    } else if (value > subtreeRoot.getData()){
      subtreeRoot.setRightChild(delete(subtreeRoot.getRightChild(),value));
    } else {
      // Cases 1 and 2: node to delete has 0 or 1 child
      if (subtreeRoot.getLeftChild() == null){
        return subtreeRoot.getRightChild();
      } else if (subtreeRoot.getRightChild() == null) {
        return subtreeRoot.getLeftChild();
        // Case 3: node has 2 children
      } else {
        // replace subtree root with the smallest value from the right subtree
        subtreeRoot.setData(subtreeRoot.getRightChild().min());

        // delete the node that has the smallest value in the right subtree
        subtreeRoot.setRightChild(delete(subtreeRoot.getRightChild(), subtreeRoot.getData()));
      }
    }
    return subtreeRoot;
  }

  public int min() {
    if (root == null){
      return Integer.MIN_VALUE;
    } else {
      return root.min();
    }
  }

  public int max() {
    if (root == null){
      return Integer.MAX_VALUE;
    } else {
      return root.max();
    }
  }

  public static void main(String[] args) {
    Tree inTree = new Tree();
    inTree.insert(25);
    inTree.insert(20);
    inTree.insert(15);
    inTree.insert(17);
    inTree.insert(27);
    inTree.insert(30);
    inTree.insert(29);
    inTree.insert(26);
    inTree.insert(22);
    inTree.insert(32);

    inTree.traverseInOrder();

    System.out.println();
    System.out.println(inTree.get(27));
    System.out.println(inTree.get(15));
    System.out.println(inTree.get(800)); //returns null because value not in tree.

    // print min
    System.out.println("Min: "+inTree.min());

    // print max
    System.out.println("Max: "+inTree.max());
    
    inTree.delete(25);
    inTree.traverseInOrder();
  }
}
