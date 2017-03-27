    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog10;

/**
 *
 * @author Canalis
 */
//import necessary classes
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class InFixToBinaryTreeConverter {
    //create stacks to hold the expression and nodes
    private Stack<String> inFix;
    private Stack<Node> btstack;
    private String expression;

    public InFixToBinaryTreeConverter() {//intialize the stacks
        inFix = new Stack();
        btstack = new Stack();
    }

    public void run(String ex) {
        System.out.println(ex + " ORIGINAL EXPRESSION");//print expression
        createInFix(ex);//make the expression into the infix stack
        createBinaryTree();//create the binary tree

        for (int i = 0; i < btstack.size(); i++) {//print out the tree
            System.out.println(btstack.get(i));
        }
        System.out.println(inOrder(btstack.get(0)) + " INORDER");//print the tree in order
        System.out.println(preOrder(btstack.get(0)) + " PREORDER");//print it in pre order
        System.out.println(postOrder(btstack.get(0)) + " POSTORDER");//print it in post order
        System.out.println((optimize(btstack.get(0))) + " OPTIMIZED");//show the final optimized node
    }

    public void createInFix(String ex) {

        expression = ex;
        String[] temp = expression.split("");//split it up
        Collections.reverse(Arrays.asList(temp));//reverse it because it's backwards

        for (int x = 0; x < temp.length; x++) {//add the stuff to the infix list only if its a number or operator
            if (isNumber(temp[x])) {
                inFix.add(temp[x]);
            } else if (isOperator(temp[x])) {
                inFix.add(temp[x]);
            }//essentially got rid of the parenthesis and spaces
        }
    }

    public void createBinaryTree() {

        while (!inFix.isEmpty()) {//while there are still items in the infix stack

            if (isNumber(inFix.peek()) && btstack.isEmpty()) {//if the top item in inFix is a number and theres nothing in btstack
                Node node = new Node(inFix.pop(), null, null);//create a new node with the number as the base
                btstack.push(node);//push it on the stack of nodes
            }
            if (isNumber(inFix.peek()) && isOperator(btstack.peek().first)) {//if the next item in the stack is a number and the top node on bt stack is an operator
                btstack.peek().right = new Node(inFix.pop(), null, null);//add it to the right child node of the operator
            }

            if (!inFix.isEmpty() && isOperator(inFix.peek())) {//if the top item on inFix is an operator 
                Node left = null;
                Node right = null;
                String op = inFix.pop();//create a new string to be the base identity of the node

                if (!btstack.isEmpty()) {//if btstack is not empty
                    left = btstack.pop();//pop the top node off the stack(should be a number)
                }

                Node node = new Node(op, right, left);//make a new node with the poped nodes and string from inFix
                btstack.push(node);//push it onto the node stack
            }
        }
    }

    public String preOrder(Node node) {//preorder is easy, just the rough binary tree minus all comas and parenthesis
        String s = "";
        String[] x = node.toString0().split("");//convert to string array
        for (int i = 0; i < x.length; i++) {
            if (x[i].equals("(")) {
                x[i] = "";
            } else if (x[i].equals(",")) {//get rid of all comas and parans
                x[i] = "";
            } else if (x[i].equals(")")) {
                x[i] = "";
            }
        }
        for (int i = 0; i < x.length; i++) {
            s += x[i];//and return it as a string
        }
        return s;
    }

    public String postOrder(Node node) {//post order is a switched up version of toString0 in class Node. check recursive method post() in Node
        String string = node.post() + 6;
        String reverse = new StringBuffer(string).reverse().toString();//had to reverse it though because it was backwards
        return reverse;
    }

    public String inOrder(Node node) {//inOrder is the same as how the expression came in so I took out Parans spaces and comas
        String s = "";
        String[] x = expression.split("");
        for (int i = 0; i < x.length; i++) {
            if (x[i].equals("(")) {
                x[i] = "";
            } else if (x[i].equals(",")) {
                x[i] = "";
            } else if (x[i].equals(")")) {
                x[i] = "";
            } else if (x[i].equals(" ")) {
                x[i] = "";
            }
        }
        for (int i = 0; i < x.length; i++) {
            s += x[i];//make new string and return it
        }
        return s;
    }

    public Node optimize(Node node) {

        if (node != null) {//if the node isnt null

            node.right = optimize(node.right);//optimize both child nodes
            node.left = optimize(node.left);

            if ("*".equals(node.first)) {//if its a multiply

                if ("0".equals(node.right.first) || "0".equals(node.left.first)) {//essentially followed directions
                    node.first = "0";
                } else if ("1".equals(node.right.first) && !"1".equals(node.left.first)) {
                    node.first = node.left.first;
                } else if (!"1".equals(node.right.first) && "1".equals(node.left.first)) {
                    node.first = node.right.first;
                } else {
                    int parsed = (Integer.parseInt(node.left.first)) * (Integer.parseInt(node.right.first));
                    node.first = Integer.toString(parsed);//parsed to an int then made it back into a string
                }//SAME FOR EVERY ELSE IF STATEMENT
            } else if ("/".equals(node.first)) {
                if ("0".equals(node.right.first) || "0".equals(node.left.first)) {
                    node.first = "1";
                } else if ("1".equals(node.right.first) && !"1".equals(node.left.first)) {
                    node.first = node.left.first;
                } else if (!"1".equals(node.right.first) && "1".equals(node.left.first)) {
                    node.first = node.right.first;
                } else {
                    int parsed = (Integer.parseInt(node.left.first)) / (Integer.parseInt(node.right.first));
                    node.first = Integer.toString(parsed);
                }
            } else if ("+".equals(node.first)) {
                if ("0".equals(node.right.first) && !"0".equals(node.left.first)) {
                    node.first = node.left.first;
                } else if (!"0".equals(node.right.first) && node.left.first == "0") {
                    node.first = node.right.first;
                } else if ("1".equals(node.right.first) && !"1".equals(node.left.first)) {
                    double parsed = 1 + (Double.parseDouble(node.left.first));
                    node.first = Double.toString(parsed);
                } else if (!"1".equals(node.right.first) && "1".equals(node.left.first)) {
                    double parsed = (Double.parseDouble(node.right.first)) + 1;
                    node.first = Double.toString(parsed);
                } else {
                    int parsed = (Integer.parseInt(node.left.first)) + (Integer.parseInt(node.right.first));
                    node.first = Integer.toString(parsed);
                }
            } else if ("-".equals(node.first)) {
                if ("0".equals(node.right.first) && !"0".equals(node.left.first)) {
                    node.first = node.left.first;
                } else if (!"0".equals(node.right.first) && node.left.first == "0") {
                    node.first = node.right.first;
                } else if ("1".equals(node.right.first) && !"1".equals(node.left.first)) {
                    double parsed = (Double.parseDouble(node.left.first)) - 1;
                    node.first = Double.toString(parsed);
                } else if (!"1".equals(node.right.first) && "1".equals(node.left.first)) {
                    double parsed = (Double.parseDouble(node.right.first)) - 1;
                    node.first = Double.toString(parsed);
                } else {
                    int parsed = (Integer.parseInt(node.left.first)) - (Integer.parseInt(node.right.first));
                    node.first = Integer.toString(parsed);
                }
            }
        }
        return node;//RETURNED NEW NODE
    }
    //MODULARIZING METHODS
    public boolean isNumber(String num) {
        boolean check = true;
        try {//CHECKS FOR A NUM BY TRY CATCH AND TRYING TO PARSE
            int n = Integer.parseInt(num);
        } catch (NumberFormatException nf) {
            check = false;
        }
        return check;
    }

    public boolean isOperator(String op) {
        boolean check = false;//CHECKS BY USING IF STATEMENT CHECKING THE IDENTITY OF THE STRING
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
            check = true;
        }
        return check;
    }

}
