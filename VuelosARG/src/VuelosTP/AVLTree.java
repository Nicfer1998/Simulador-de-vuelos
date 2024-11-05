package VuelosTP;

class Node<T> {
    T data;
    Node<T> left, right;
    int height;

    Node(T data) {
        this.data = data;
        height = 1;
    }
}

public class AVLTree<T extends Comparable<T>> {
    private Node<T> root;

    public boolean insertar(T data) {
        root = insertarRec(root, data);
        return true; // Retornar true si se inserta correctamente
    }

    private Node<T> insertarRec(Node<T> node, T data) {
        if (node == null) {
            return new Node<>(data);
        }
        if (data.compareTo(node.data) < 0) {
            node.left = insertarRec(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertarRec(node.right, data);
        } else {
            return node; // No se permiten duplicados
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    private Node<T> balance(Node<T> node) {
        int balanceFactor = getBalance(node);
        
        // Left Left Case
        if (balanceFactor > 1 && node.data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balanceFactor < -1 && node.data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balanceFactor > 1 && node.data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balanceFactor < -1 && node.data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node<T> rightRotate(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node<T> leftRotate(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void imprimirInorden() {
        imprimirInordenRec(root);
    }

    private void imprimirInordenRec(Node<T> node) {
        if (node != null) {
            imprimirInordenRec(node.left);
            System.out.println(node.data.toString());
            imprimirInordenRec(node.right);
        }
    }
}
