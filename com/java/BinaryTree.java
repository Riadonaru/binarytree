package binarytree.com.java;

import java.util.LinkedList;
import java.util.Queue;

class Binarytree {

    int lastLvl = 0;
    Node root = null;

    class Node {

        int data;
        int children = 0;
        int level = 0;
        private Node left = null;
        private Node right = null;
        
        public Node(int data) {
            this.data = data;
        }

        public void setLeftChild(Node left) throws ValueNotFoundException {
            this.left = left;
            left.level = search(left.data).length();
            if (lastLvl < left.level) {
                lastLvl = left.level;
            }
            children++;
        }

        public void setRightChild(Node right) throws ValueNotFoundException {
            this.right = right;
            right.level = search(right.data).length();
            if (lastLvl < right.level) {
                lastLvl = right.level;
            }
            children++;
        }
    }

    
    public void insert(int data) throws EqualDataException, ValueNotFoundException {
        Node newNode = new Node(data);
        
        if (root == null) {
            root = newNode;
        } else {
            
            Node focusNode = root;
            
            while (true) {
                if (focusNode.data == data) {
                    throw new EqualDataException();
                }
                if (focusNode.data > data) {
                    if (focusNode.left == null) {
                        focusNode.setLeftChild(newNode);
                        return;
                    } else {
                        focusNode = focusNode.left;
                    }
                } else {
                    if (focusNode.right == null) {
                        focusNode.setRightChild(newNode);
                        return;
                    } else {
                        focusNode = focusNode.right;
                    }
                }
            }
        }
        
    }
    
    public void remove(int data) throws ValueNotFoundException {
        
        if (root.data == data) {
            if (root.left == null && root.right == null) {
                System.out.println("Case 0");
                lastLvl = getHeight() - 1;
                root = null;
                return;
            }
            if (root.left == null || root.right == null) {
                System.out.println("Case 1");
                if (root.left != null) {
                    root = root.left;
                } else {
                    root = root.right;
                }
                lastLvl = getHeight() - 1;
                return;
            }
            System.out.println("Case 2");
            Node largestLeftNode = root.left;
            
            while (largestLeftNode.right != null) {
                largestLeftNode = largestLeftNode.right;
            }
            remove(largestLeftNode.data);
            root.data = largestLeftNode.data;
            lastLvl = getHeight() - 1;
            return;
        }

        Node focusNode = root;
        Node parentNode = null;
        Boolean leftChild = null;
        
        while (true) {
            if (focusNode == null) {
                throw new ValueNotFoundException(data);
            }
            if (focusNode.data == data) {
                if (focusNode.left == null && focusNode.right == null) {
                    System.out.println("Case 0");
                    if (leftChild) {
                        parentNode.left = null;
                    } else {
                        parentNode.right = null;
                    }
                    lastLvl = getHeight() - 1;
                    parentNode.children--;
                    return;
                }
                if (focusNode.left == null || focusNode.right == null) {
                    System.out.println("Case 1");
                    if (focusNode.left != null) {
                        if (leftChild) {
                            parentNode.left = focusNode.left;
                        } else {
                            parentNode.right = focusNode.left;
                        }
                    } else {
                        if (leftChild) {
                            parentNode.left = focusNode.right;
                        } else {
                            parentNode.right = focusNode.right;
                        }
                    }
                    lastLvl = getHeight() - 1;
                    parentNode.children--;
                    focusNode = null;
                    return;
                }
                System.out.println("Case 2");
                Node largestLeftNode = focusNode.left;
                
                while (largestLeftNode.right != null) {
                    largestLeftNode = largestLeftNode.right;
                }
                if (largestLeftNode != parentNode.left && largestLeftNode != parentNode.right) {
                    remove(largestLeftNode.data);
                }
                if (leftChild) {
                    parentNode.left.data = largestLeftNode.data;
                } else {
                    parentNode.right.data = largestLeftNode.data;
                }
                lastLvl = getHeight() - 1;
                parentNode.children--;
                focusNode = null;
                return;
            }
            parentNode = focusNode;
            if (focusNode.data > data) {
                focusNode = focusNode.left;
                leftChild = true;
            } else {
                focusNode = focusNode.right;
                leftChild = false;
            }
        }  
    }
    
    public String search(int treasure) throws ValueNotFoundException {
        
        Node focusNode = root;
        StringBuilder res = new StringBuilder();
        
        while (true) {
            if (focusNode == null) {
                throw new ValueNotFoundException(treasure);
            }
            if (focusNode.data == treasure) {
                return res.toString();
            }
            if (focusNode.data > treasure) {
                focusNode = focusNode.left;
                res.append("l");
            } else {
                focusNode = focusNode.right;
                res.append("r");
            }
        }
    }
    
    private void prePrint(Node node) {
        if (node != null) {
            System.out.println(node.data);
            prePrint(node.left);
            prePrint(node.right);
        }
    }   
    
    private void postPrint(Node node) {
        if (node != null) {
            postPrint(node.left);
            postPrint(node.right);
            System.out.println(node.data);
        }
    }   
    
    private void inPrint(Node node) {
        if (node != null) {
            inPrint(node.left);
            System.out.println(node.data);
            inPrint(node.right);
        }
    }   
    
    private void levelPrint(Queue<Node> queue) {
        Node node = queue.remove();
        System.out.println("Level: " + node.level + " Data: " + node.data);
        if (node.left != null) {
            queue.add(node.left);
        }
        if (node.right != null) {
            queue.add(node.right);
        }
        if (!queue.isEmpty()) {
            levelPrint(queue);
        }       
    }
    
    private void treePrint(Node node, String[] tree, int level) {
        if (level < lastLvl) {
            level++;
            int space = 1;
            String data;
            if (node != null) {
                treePrint(node.left, tree, level);
                treePrint(node.right, tree, level);
                data = String.valueOf(node.data);
                int len = data.length();
                if (len < 2) {
                    for (int i = len; i < 2; i++) {
                        data = " " + data;
                    }
                }
            } else {
                treePrint(null, tree, level);
                treePrint(null, tree, level);
                data = "nl";
            }
            for (int i = 0; i < (lastLvl - level); i++) {
                space = 2 * space + 1;
            }
            tree[level] = putSpaces(tree[level], space);
            tree[level] += data;
            tree[level] = putSpaces(tree[level], space);
        }
        if (level == 0) {
            for (int i = 0; i < lastLvl + 1; i++) {
                System.out.println(tree[i]);
            }
        }
    }
    
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            if (leftHeight > rightHeight) {
                return leftHeight + 1;  
            } 
            return rightHeight + 1;
        }
    }
    
    private static String putSpaces(String str, int spaceNum) {
        for (int i = 0; i < spaceNum; i++) {
            str += " ";
        }
        return str;
    }
    
    public void prePrint() {
        System.out.println("PrePrinting");
        prePrint(root);
    }
    
    public void postPrint() {
        System.out.println("PostPrinting");
        postPrint(root);
    }
    
    public void inPrint() {
        System.out.println("InPrinting");
        inPrint(root);
    }
    
    public void levelPrint() {
        System.out.println("LevelPrinting");
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        levelPrint(queue);
    }
    
    public void treePrint() {
        String[] tree = new String[lastLvl + 1];
        for (int i = 0; i < lastLvl + 1; i++) {
            tree[i] = "";
        }
        treePrint(root, tree, -1);
    }
    
    public int getHeight() {
        return getHeight(root);
    }
    
}

final class EqualDataException extends Exception {
    EqualDataException() {
        super("This Binary tree doesn't support duplicate values!");
    }
}

final class ValueNotFoundException extends Exception {
    ValueNotFoundException(int value) {
        super("The value " + value + " is not stored in this tree!");
    }
}
