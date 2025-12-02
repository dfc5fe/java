import java.util.Random;
import java.util.Scanner;

// Клас вузла дерева
class Node {
    int data;
    Node left;
    Node right;
    Node parent;
    boolean isRed; // true = Red, false = Black

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.isRed = true; // За правилами RBT нові вузли спочатку червоні
    }
}

// Клас самого дерева
class RBTree {
    private Node root;
    private final Node TNULL; // Спеціальний "порожній" чорний вузол (лист)

    public RBTree() {
        TNULL = new Node(0);
        TNULL.isRed = false;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    // Блок балансування

    // Лівий поворот навколо вузла x
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // Правий поворот навколо вузла x
    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // Відновлення властивостей RBT після вставки
    private void fixInsert(Node k) {
        Node u; // дядько (uncle)
        while (k.parent.isRed) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.isRed) {
                    // Випадок 1: дядько червоний -> перефарбовуємо
                    u.isRed = false;
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // Випадок 2: дядько чорний, k - лівий син -> правий поворот
                        k = k.parent;
                        rightRotate(k);
                    }
                    // Випадок 3: дядько чорний, k - правий син -> лівий поворот + перефарбування
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    leftRotate(k.parent.parent);
                }
            } else {
                // Дзеркальна ситуація (ліва сторона)
                u = k.parent.parent.right;
                if (u.isRed) {
                    u.isRed = false;
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.isRed = false;
                    k.parent.parent.isRed = true;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.isRed = false; // Корінь завжди чорний
    }

    // Публічний метод вставки
    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;
        node.isRed = true; // 1. Вставити як у звичайне BST

        Node y = null;
        Node x = root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        // Якщо це корінь, просто фарбуємо в чорний
        if (node.parent == null) {
            node.isRed = false;
            return;
        }
        // Якщо батько чорний, нічого робити не треба
        if (node.parent.parent == null) {
            return;
        }

        // Виправити порушення RBT
        fixInsert(node);
    }

    // --- Блок відображення дерева ---
    public void printTree() {
        if (root == TNULL) {
            System.out.println("Дерево порожнє.");
            return;
        }
        printHelper(this.root, "", true);
    }

    // Рекурсивний вивід дерева "лежачи"
    private void printHelper(Node root, String indent, boolean last) {
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }
            String sColor = root.isRed ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }
}

// Головний клас для запуску Hard Task
public class HardTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBTree tree = new RBTree();
        Random random = new Random();

        System.out.println("=== HARD TASK: RED-BLACK TREE ===");

        while (true) {
            System.out.println("\nМеню операцій:");
            System.out.println("1. Додати одне число (вручну)");
            System.out.println("2. Згенерувати масив випадкових чисел (без порядку)");
            System.out.println("3. Згенерувати масив відсортованих чисел");
            System.out.println("4. Показати дерево");
            System.out.println("0. Вихід");
            System.out.print("Твій вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введи ціле число: ");
                    try {
                        int val = Integer.parseInt(scanner.nextLine());
                        tree.insert(val);
                        System.out.println("Число " + val + " додано.");
                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: введи коректне число!");
                    }
                    break;

                case "2":
                    System.out.print("Скільки чисел згенерувати? ");
                    try {
                        int count = Integer.parseInt(scanner.nextLine());
                        System.out.print("Додано: ");
                        for (int i = 0; i < count; i++) {
                            int r = random.nextInt(100); // Числа від 0 до 99
                            System.out.print(r + " ");
                            tree.insert(r);
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("Помилка вводу.");
                    }
                    break;

                case "3":
                    System.out.print("Скільки чисел додати (будуть від 1 до N)? ");
                    try {
                        int countSorted = Integer.parseInt(scanner.nextLine());
                        System.out.println("Додаю числа по порядку...");
                        for (int i = 1; i <= countSorted; i++) {
                            tree.insert(i);
                        }
                        System.out.println("Готово.");
                    } catch (Exception e) {
                        System.out.println("Помилка вводу.");
                    }
                    break;

                case "4":
                    System.out.println("\nСтруктура дерева (R - правий, L - лівий):");
                    tree.printTree();
                    break;

                case "0":
                    System.out.println("Завершення роботи.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невідома команда.");
            }
        }
    }
}