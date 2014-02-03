/*This program is a solution to Problem 7 of Progfest 2013
It stores user input in ArrayList parents and childs. 

The three conditions for a collection of nodes/edges to be a tree are:
1. Exactly one root, to which no directed edges point
2. Every node except the root has exactly one edge pointing to it
3. There is a unique sequence of directed edges from the root to each node.

I believe 2 implies 3. That is, if 2 is true, then 3 is true. My informal proof/explanation is as follows:
I assume (2 is true) AND (3 is false).
Suppose that every node except the root has exactly one edge pointing to it AND that there is not a unique sequence of
directed edges from the root to each node. Pick an arbitrary node n that does not have a unique sequences of directed edges from the root
to the node. That means there are at least 2 ways (or paths) to travel from the root to the node. Let the two paths be P1 and P2.
Both P1 and P2 start at the root (r) and end at the node (n).
P1 = r-a-b-c-...-y-z-n
P2 = r-A-B-C-...-Y-Z-n
Since (2) is true, z = Z (if z != Z, that means n has more than one edge pointing to it)
Similarly, y = Y. In order for P1 to be different from P2, there needs to be a point in the paths where
s-t is part of P1 and S-t is part of P2 and s != S. But if this is true, then there are TWO directed edges pointing toward t, 
which violates (2). Hence, P1 and P2 cannot be different. P1 = P2, and there is a unique sequence of directed edges from the root to each node.

Method hasRoot() determines whether the tree entered has a root.
Method uniquePath() determines whether every node except the root has exactly one edge pointing to it, and as a result that every node has a unique path.
*/
import java.util.*;

public class IsItATree
{
   public static ArrayList<Integer> parents = new ArrayList<>();
   public static ArrayList<Integer> childs = new ArrayList<>();
   public static ArrayList<Integer> possibleRoots = new ArrayList<>();
   
   public static ArrayList<Boolean> trees = new ArrayList<Boolean>();
   
   
   //Note that input is made in pairs, the first of the pair is the parent and the second is the child. 
   public static void main(String[] args)
   {
      Scanner keyboard = new Scanner(System.in);
      int inputParent;
      int inputChild;
      do
      {
          do
          {
            inputParent = keyboard.nextInt();
            if(inputParent < 0)
               break;
            inputChild = keyboard.nextInt();
            if(inputParent > 0 && inputChild > 0)
            {
               parents.add(inputParent);
               childs.add(inputChild);
            }
            else
            {
               System.out.println();
               trees.add(isTree());
            }
         }while(inputParent > 0 && inputChild > 0);
         parents.clear();
         childs.clear();
         possibleRoots.clear();
      }while(inputParent >= 0);
      System.out.println("\n");
      for(int i = 0; i < trees.size(); i++)
      {
         if(trees.get(i))
            System.out.println("Case " + (i+1) +" is a tree.");
         else
            System.out.println("Case " + (i+1) +" is not a tree.");
      }
   }
   //checks every node in ArrayList parents and determines if it exists in ArrayList childs
   //If a node exists in parent but not in child, it means no directed edges point to it, aka root
   //Returns true if there is exactly one such node, returns false otherwise
   public static boolean hasRoot()
   {
      int rootNumber = 0;
      for(int i = 0; i < parents.size(); i++)
      {
         if(!childs.contains(parents.get(i)) && !possibleRoots.contains(parents.get(i)))
         {
            rootNumber++;
            possibleRoots.add(parents.get(i));
         }
      }
      if(rootNumber == 1)
         return true;
      else
         return false;
   }

   
   
   //Assumes every child has one parent. If a child appears twice on the list, then it has two parents
   //That is, if the first index of a node (i) is different from the last index of the node equal to it
   //(child.lastIndexOf(node)), that means it appears at least twice, and hence there is more than one way to 
   //get to a certain node
   public static boolean uniquePath()
   {
      boolean unique = true;
      int i = 0;
      while( i < childs.size() && unique == true)
      {
         int node = childs.get(i);
         if( i != childs.lastIndexOf(node))
            unique = false;
         i++;
      }
      return unique;
   }
   
   //Checks that a potential tree has a root and has a unique path
   public static boolean isTree()
   {
      return (hasRoot() && uniquePath());
   }
}