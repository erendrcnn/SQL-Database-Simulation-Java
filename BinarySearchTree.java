class Node {
    int data;
    Row line;
    Node left;
    Node right;

    public Node(int data, Row line) {
        this.data = data;
        this.line = line;
        left = null;
        right = null;
    }
}

public class BinarySearchTree {
    public static Node root;

    public BinarySearchTree() {
        root = null;
    }

    public Row find(int id) {
        Node current = root;

        while (current != null) {
            if (current.data == id)
                return current.line;
            else if (current.data > id)
                current = current.left;
            else
                current = current.right;
        }
        return null;
    }

    public boolean delete(int id) {
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;

        while (current.data != id) {
            parent = current;
            if (current.data > id) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
                return false;
        }

        if (current.left == null && current.right == null) {
            if (current == root)
                root = null;
            if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        }

        else if (current.right == null) {
            if (current == root)
                root = current.left;
            else if (isLeftChild)
                parent.left = current.left;
            else
                parent.right = current.left;

        } else if (current.left == null) {
            if (current == root)
                root = current.right;
            else if (isLeftChild)
                parent.left = current.right;
            else
                parent.right = current.right;

        } else {

            Node successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.left = successor;
            else
                parent.right = successor;

            successor.left = current.left;
        }
        return true;
    }

    public Node getSuccessor(Node deleleNode) {
        Node successsor = null;
        Node successsorParent = null;
        Node current = deleleNode.right;

        while (current != null) {
            successsorParent = successsor;
            successsor = current;
            current = current.left;
        }

        if (successsor != deleleNode.right) {
            successsorParent.left = successsor.right;
            successsor.right = deleleNode.right;
        }
        return successsor;
    }

    public void insert(int id, Row line) {
        Node newNode = new Node(id, line);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (id < current.data) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            }
        }
    }

    public void display(Node root) {
        if (root != null) {
            display(root.left);
            System.out.print(" " + root.data);
            display(root.right);
        }
    }
}