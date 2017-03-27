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
public class Node {
    //CREATE NEW VARIABLES
    String first;

    Node left;
    Node right;

    public Node(String name, Node r, Node l) {
        //INITALIZE THE NODES BASE IDENTITY AND ITS CHILDREN
        first = name;
        left = l;
        right = r;

    }

    public String post() {//USED THIS RECURSIVE METHOD TO RETURN A POSTFIX BINARY TREE
        String s = "";
        if (right !=null && left != null) {
            s = first + right.first + left.post();
            if (left == null && right == null) {
                s = first;
            }
        }
        return s;
    }

    public String toString0() {//TOSTRING0 THAT RETURNS THE BASE THEN LEFT CHILD THEN RIGHT CHILD
        String s = first + "(" + left + "," + right + ")";
        if (left == null && right == null) {
            s = first;
        }
        return s;
    }

    @Override//JUST RETURNS TOSTRING0
    public String toString() {
        return toString0();
    }

}
