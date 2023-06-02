package dsassxj;

/*import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    String data;
    Node left, right;

    public Node(String item) {
        data = item;
        left = right = null;
    }
}

class LowestCommonAncestor {
    Node root;

    List<String> findLCA(String name1, String name2) {
        List<String> lcas = new ArrayList<>();
        findLCAHelper(root, name1, name2, lcas);
        return lcas;
    }

    private boolean findLCAHelper(Node node, String name1, String name2, List<String> lcas) {
        if (node == null)
            return false;

        boolean isLCA = false;
        if (node.data.equals(name1) || node.data.equals(name2))
            isLCA = true;

        boolean left = findLCAHelper(node.left, name1, name2, lcas);
        boolean right = findLCAHelper(node.right, name1, name2, lcas);

        if ((left && right) || ((isLCA && left) || (isLCA && right))) {
            lcas.add(node.data);
        }

        return left || right || isLCA;
    }

    public static void main(String[] args) {
        LowestCommonAncestor tree = new LowestCommonAncestor();
        tree.root = new Node("George Joestar I and Mary Joestar");
        tree.root.left = new Node("Jonathan Joestar and Erina Joestar");
        tree.root.right = new Node("Jonathan Joestar and DIO");
        tree.root.left.left = new Node("George Joestar II and Lisa Lisa");
        tree.root.right.right = new Node("Giorno Giovanna");
        tree.root.left.left.left = new Node("Joseph Joestar and Suzi Q");
        tree.root.left.left.left.left = new Node("Holy Kujo and Sadao Kujo");
        tree.root.left.left.right = new Node("Joseph Joestar and Tomoko Higashikata");
        tree.root.left.left.right.right = new Node("Josuke Higashikata");
        tree.root.left.left.left.left = new Node("Holy Kujo and Sadao Kujo");
        tree.root.left.left.left.left.left = new Node("Jotaro Kujo");
        tree.root.left.left.left.left.left.left = new Node("Jolyne Cujoh");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the first Joestar: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter the name of the second Joestar: ");
        String name2 = scanner.nextLine();

        List<String> lcas = tree.findLCA(name1, name2);
        if (!lcas.isEmpty()) {
            System.out.printf("Lowest Common Ancestors of %s and %s:\n", name1, name2);
            for (String lca : lcas) {
                System.out.println(lca);
            }
        } else {
            System.out.println("Names entered are not in the Joestar family.");
        }
    }
}*/


/*import java.util.Scanner;

class Node {

    String data;
    Node left, right;

    public Node(String item) {
        data = item;
        left = right = null;
    }
}

class LowestCommonAncestor {

    Node root;

    Node findLCA(String n1, String n2) {
        return findLCA(root, n1, n2);
    }

    
    Node findLCA(Node node, String n1, String n2) {
        if (node == null) {
            return null;
        }
        // key becomes LCA
        if (node.data.equals(n1) || node.data.equals(n2)) { //if either input matches the root key
            return node;
        }

        Node left_lca = findLCA(node.left, n1, n2);
        Node right_lca = findLCA(node.right, n1, n2);

        if (left_lca != null && right_lca != null) {
            return node;
        }
        return (left_lca != null) ? left_lca : right_lca;
    }

    public static void main(String args[]) {
        LowestCommonAncestor tree = new LowestCommonAncestor();
        tree.root = new Node("George Joestar I and Mary Joestar");
        tree.root.left = new Node("Jonathan Joestar and Erina Joestar");
        tree.root.right = new Node("Jonathan Joestar and DIO");
        tree.root.left.left = new Node("George Joestar II and Lisa Lisa");
        tree.root.right.right = new Node("Giorno Giovanna");
        tree.root.left.left.left = new Node("Jospeh  Joestar and Suzi Q");
        tree.root.left.left.left.left = new Node("Holy Kujo and Sadao Kujo");
        tree.root.left.left.right = new Node("Jospeh Joestar and Tomoko Higashikata");
        tree.root.left.left.right.right = new Node("Josuke Higashikata");
        tree.root.left.left.left.left = new Node("Holy Kujo and Sadao Kujo");
        tree.root.left.left.left.left.left = new Node("Jotaro Kujo");
        tree.root.left.left.left.left.left.left = new Node("Jolyne Cujoh");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the first Joestar: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter the name of the second Joestar: ");
        String name2 = scanner.nextLine();

        String lca = tree.findLCA(name1, name2).data;
        if (lca != null) {
            System.out.printf("Lowest Common Ancestor of %s and %s is: %s" ,name1,name2, lca);
        } else {
            System.out.println("Name entered is not in the Joestar family.");
        }
    }
}*/






