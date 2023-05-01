//package HackerRankPraktikum.BST;
import java.util.*;
    public class Solution {
        public static void main(String[] args) {
            Scanner inputUser = new Scanner(System.in);

            Set<Integer> hasNumb;
            hasNumb = new HashSet<>();

            int panjangArray = Integer.parseInt(inputUser.nextLine());

            RBTree newTree;
            newTree = new RBTree();

            while(panjangArray > 0) {
                String[] content = inputUser.nextLine().split(" ", 2);
                int key = Integer.parseInt(content[0]);
                String value = content[1];

                if (hasNumb.contains(key)) {
                    continue;
                }
                int x = 0;
                for (int i = 0; i < value.length(); i++) {
                    if (value.charAt(i) == '\\') {
                        x++;
                    }
                }
                if (value.length() > panjangArray + x) {
                    value = value.substring(0, panjangArray + x);
                }
                newTree.insert(key, value);
                panjangArray -= value.length() - x;
                hasNumb.add(key);
            }

            String programOutput = newTree.inorderTraversal();

            for (int i = 0; i < programOutput.length(); i++) {
                char ding = programOutput.charAt(i);
                if (ding == '\\' && i < programOutput.length() - 1 && programOutput.charAt(i + 1) == 'n') {
                    System.out.println();
                    i++;
                } else {
                    System.out.print(ding);
                }
            }
        }
    }
     class RBTree {
        Node root;
         protected String inorderTraversalHelper(Node currentNode, StringBuilder sb) {
             if (currentNode == null) {
                 return sb.toString();
             }
             inorderTraversalHelper(currentNode.left, sb);
             sb.append(currentNode.segCont);
             inorderTraversalHelper(currentNode.right, sb);
             return sb.toString();
         }
         public String inorderTraversal() {
             StringBuilder sb;
             sb = new StringBuilder("");
             return inorderTraversalHelper(root, sb);
         }
        protected void rebalance(Node newNode) {
            while (newNode.parent != null && newNode.parent.isRed) {
                Node gParent = newNode.parent.parent;
                Node parentLevel = (newNode.parent == gParent.left) ? gParent.right : gParent.left;
                if (parentLevel != null && parentLevel.isRed) {
                    newNode.parent.isRed = false;
                    parentLevel.isRed = false;
                    gParent.isRed = true;
                    newNode = gParent;
                    continue;
                }
                if (newNode == newNode.parent.right && newNode.parent == gParent.left) {
                    newNode = newNode.parent;
                    rotateLeft(newNode);
                } else if (newNode == newNode.parent.left && newNode.parent == gParent.right) {
                    newNode = newNode.parent;
                    rotateRight(newNode);
                }
                newNode.parent.isRed = false;
                gParent.isRed = true;
                if (newNode == newNode.parent.left && newNode.parent == gParent.left) {
                    rotateRight(gParent);
                } else {
                    rotateLeft(gParent);
                }
            }
            root.isRed = false;
        }
        protected void insert(int segNum, String segCont) {
            Node newNode = new Node(segNum, segCont);
            if (root == null) {
                root = newNode;
                root.isRed = false;
            }
            Node parent = null;
            Node current = root;
            while (current != null) {
                parent = current;
                if (segNum < current.segNum) {
                    current = current.left;
                } else if (segNum > current.segNum) {
                    current = current.right;
                } else {
                    return;
                }
            }
            newNode.parent = parent;
            if (segNum < parent.segNum) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            rebalance(newNode);
        }
         protected void rotateRight(Node currentNode) {
             Node anakKiri = currentNode.left;
             currentNode.left = anakKiri.right;
             if (currentNode.left != null) {
                 currentNode.left.parent = currentNode;
             }
             anakKiri.right = currentNode;
             anakKiri.parent = currentNode.parent;
             if (currentNode.parent == null) {
                 root = anakKiri;
             } else if (currentNode == currentNode.parent.left) {
                 currentNode.parent.left = anakKiri;
             } else {
                 currentNode.parent.right = anakKiri;
             }
             currentNode.parent = anakKiri;
         }
        protected void rotateLeft(Node currentNode) {
            Node anakKanan = currentNode.right;
            currentNode.right = anakKanan.left;
            if (anakKanan.left != null) {
                anakKanan.left.parent = currentNode;
            }
            anakKanan.parent = currentNode.parent;
            if (currentNode.parent == null) {
                root = anakKanan;
            } else if (currentNode == currentNode.parent.left) {
                currentNode.parent.left = anakKanan;
            } else {
                currentNode.parent.right = anakKanan;
            }
            anakKanan.left = currentNode;
            currentNode.parent = anakKanan;
        }
    }
    class Node {
        Node parent, left, right;
        int segNum;
        String segCont;
        boolean isRed;
        public Node() {
        }
        public Node(int segNum) {
            this.segNum = segNum;
        }
        public Node(String segCont) {
            this.segCont = segCont;
        }
        public Node(int segNum, String segCont) {
            this.segNum = segNum;
            this.segCont = segCont;
            isRed = true;
        }
        public void setParent(Node parent) {
            this.parent = parent;
        }
        public Node getParent() {
            return parent;
        }
        public void setLeft(Node left) {
            this.left = left;
        }
        public Node getLeft() {
            return left;
        }
        public void setRight(Node right) {
            this.right = right;
        }
        public Node getRight() {
            return right;
        }
        public void setSegNum(int segNum) {
            this.segNum = segNum;
        }
        public int getSegNum() {
            return segNum;
        }
        public void setSegCont(String segCont) {
            this.segCont = segCont;
        }
        public String getSegCont() {
            return segCont;
        }
        public void setIsRed(boolean isRed) {
            this.isRed = isRed;
        }
        public boolean getIsRed() {
            return isRed;
        }
    }