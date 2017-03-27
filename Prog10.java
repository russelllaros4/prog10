/*
CSE17
RUSSELL LAROS
892583512
Program Description: TAKES INFIX EXPRESSION AND CREATES BINARY TREE BASED ON OPERATIONS BEING DONE
Program #10
*/
package prog10;

/**
 *
 * @author Canalis
 */
public class Prog10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        InFixToBinaryTreeConverter fp = new InFixToBinaryTreeConverter();
        fp.run("( ( 6 + 2 ) - 5 ) * 8 / 2");
        
        
    }

}
