import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.*;

/**
 * For simplicity lets not use the Generics.
 * 
 * Complexity:
 * ----------
 * O(n) - time
 * O(1) - space
 * 
 * BB:
 * ---
 * 103
 * 
 */
public class ComputeHashOfATree<E> {

    private TreeNode<E> root;
    
    public ComputeHashOfATree(List<E> items) {
        create(items);
    }
    
    private void create (List<E> items) {   
        if (items.size() == 0) {
            root = null;
        } else {
            root = new TreeNode<E>(items.get(0));
        }
        final Queue<TreeNode<E>> queue = new LinkedList<TreeNode<E>>();
        queue.add(root);

        final int half = items.size() / 2;
        
        for (int i = 0; i < half; i++) {
            if (items.get(i) != null) {
                final TreeNode current = queue.poll();
                final int left = 2 * i + 1;
                final int right = 2 * i + 2;

                if (items.get(left) != null) {
                    current.left = new TreeNode<E>(items.get(left));
                    queue.add(current.left);
                }
                if (right < items.size() && items.get(right) != null) {
                    current.right = new TreeNode<E>(items.get(right));
                    queue.add(current.right);
                }
            }
        }
    }
    
    public static class TreeNode<E> {
        private TreeNode<E> left;
        private E item;
        private TreeNode<E> right;
        
        TreeNode(E item) {
            this.item = item;
        }
    }
    
    
    @Override
    public int hashCode() {
        return hashCompute(root, 1);
    }
    
    /*   For Strings:
     *   ------------
     *   int h = 0;
     *   for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            
         For Linkedlist:
         ---------------
         for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());   
            
            
         Basically: 
         ----------
         31 * ( val [ i ] ) + val[i+1];   
           
         Basically:
         ----------
         To extent this concept to a tree:
              A
            /   \
           B     C
         
     *  31 * ((31 * B) + A) + C  
     *     
     *  Basically:
     *  -----------
     *  31 * (smaller then me) + me
     *       
     *    
     *    
     *  public static int recurse(String x, int y) {
        if (x.equals("left")) { 
            return y + 5;
        }
        
        if (x.equals("data")) {
            return y + 10;
        }
        
        if (x.equals("right")) {
            return y + 15;
        }
        
        int L = recurse ("left", y);
        int D = recurse ("data", L);
        int R = recurse ("right", D);
        
        return R;
    } 
     *       
     *       
     */
    private int hashCompute (TreeNode<E> node, int item) {
        if (node == null) return item;
        item = 31 * hashCompute (node.left, item) + node.item.hashCode(); // OR: node.item.hashCode() if generics are used.
        return hashCompute(node.right, item);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComputeHashOfATree<E> other = (ComputeHashOfATree<E>) obj;
        return equal(root, other.root);
    }

    private  boolean equal(TreeNode<E> node1,  TreeNode<E> node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null) return false;
        if (node1.item != node2.item) {
            return false;
        }
        return equal(node1.left, node2.left) && equal(node1.right, node2.right);
    }
    
    public  static void main(String[] args) {
        ComputeHashOfATree<Integer> cht1 = new ComputeHashOfATree<>(Arrays.asList(1, 2, 3));
        assertEquals(31747, cht1.hashCode());
        
        ComputeHashOfATree<Integer> cht2 = new ComputeHashOfATree<>(Arrays.asList(1, 2, 3));
        assertEquals(cht1, cht2);
    }
}
