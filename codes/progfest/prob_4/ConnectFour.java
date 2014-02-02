/*
After any given move, you only needs to examine tokens adjacent to the last token inserted to determine if a player has won.
This is because if there are 4 tokens of the same sign/color "in a row", and the last token inserted is not one of the tokens,
then there was a winner before the last token was inserted and the game should have ended. Hence, after every move, compare the
last token inserted with those adjacent to it. There are 8 groups of comparisons that need to be made:
left; diagonal-up-left; up; diagonal-up-right; right; diagonal-down-right; down; diagonal-down-left.
If at least one of those comparisons yields tokens of the same kind, then a player has won.
I implemented these ideas by keeping track of the position the last token was inserted in the array lastMove
Methods checkAdjacent and checkWin compare the token with the 8 groups (left, diagonal-up-left, etc).

Connect Four is played on a 6 by 7 board. In order to facilitate computation, I will use 13 arrays of length 12
Arrays leftPadOne, leftPadTwo, leftPadThree (and the right ones) will not display
Additionally, only indexes 3 - 8 will be displayed from columnOne, columnTwo, ...
Thus, the bottom-left-most character that will be displayed will be in the String located in column[3][8]
This makes it possible to compare the token located at the bottom-left with 3 tokens to the left of it, for example
Of course, the tokens to the left of it will be "x", not "F" or "S" but it will not yield an OutOfBounds Exception
and it will not require special cases for boundary situations

tokenCounters keeps track of where to insert a token in a given column. tokenCounter[n -1] keeps track of column n (ignoring columns that are not displayed)
For example, if in the first move, Player 1 chooses "2" as his move, then tokenCounter[1] = 8, and
columnTwo[8] = "F"; tokenCounter[1] changes to equal 7, so that the next entry in columnTwo will overwrite the "x"
in columnTwo[7], and so on.
*/

import java.util.*;

public class ConnectFour
{
   public static String[] leftPadOne = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] leftPadTwo = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] leftPadThree = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnOne = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnTwo = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnThree = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnFour = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnFive = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnSix = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] columnSeven = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] rightPadOne = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] rightPadTwo = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   public static String[] rightPadThree = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
   
   public static String[][] columns = {leftPadOne, leftPadTwo, leftPadThree, columnOne, columnTwo, columnThree, columnFour, columnFive, columnSix, columnSeven, rightPadOne, rightPadTwo, rightPadThree};
   public static int[] tokenCounters = {8, 8, 8, 8, 8, 8, 8};
   public static int moveCount = 1; //Starts off odd because it is player 1's turn, turns even when it's player 2's turn
   public static int[] lastMove = new int[2]; //lastMove[0] is the column, lastMove[1] is the row of where the last token was inserted
   
   public static void main(String[] args)
   {
      do
      {
         displayBoard();
         enterMove();
      }while(!checkWin() && moveCount < 43);
      displayBoard();
      if(moveCount%2 == 0) //enterMove increases moveCount, so if player 1 wins, then enterMove changes the value of moveCount to even
         System.out.println("Player 1 wins!");
      else
         System.out.println("Player 2 wins!");
   }
   
   //Displays the third index of columns 1-7, then the second index, and so on
   //Includes indexes 3-8 for a total of 6 rows
   public static void displayBoard()
   {
      System.out.println("\n1234567\n");
      for(int i = 3; i < 9; i++)
      {
         for(int j = 3; j < 10; j++)
         {
            System.out.print(columns[j][i]);
         }
         System.out.println("\n");
      }
   }
   
   
   /*
   If a player enters "3" for his move, that means the player wants to insert a token into columnThree
   Notice that columns[5] = columnThree (in general, columns[move+2] is the column the player wants to insert the token into)
   tokenCounters[2] keeps track of the number of tokens inserted into columnThree (in general tokenCounters[move -1])
   Hence, the player wants to insert his token into columns[5][tokenCounters[2]] (in general columns[move + 2][tokenCounters[move - 1]])
   Update lastMove and tokenCounters
   */
   public static void enterMove()
   {
      Scanner input = new Scanner(System.in);
      String character;
      if(moveCount%2 == 1)
      {
         System.out.print("First ");
         character = "F";
      }
      else
      {
         System.out.print("Second ");
         character = "S";
      }
      System.out.print("Player Please Enter Move: ");
      int move = input.nextInt();
      columns[move + 2][tokenCounters[move -1]] = character;
      lastMove[0] = move + 2;
      lastMove[1] = tokenCounters[move - 1];
      tokenCounters[move - 1] -= 1;
      moveCount++;
   }
   
   //This method examines the String input in the last move ("F" or "S") and compared it with 3 positions next to it
   //The positions are determined by the values of i and j (input only -1, 0, or 1)
   //Ex. If i = 0, j = 1, this method compares the value entered in the last move to the 3 values below it
   //If i = 1, j = 0, it compared 3 values right of it
   public static boolean checkAdjacent(int i, int j)
   {
      int column = lastMove[0];
      int row = lastMove[1];
      return (((columns[column][row].equals(columns[column + i][row + j]) && columns[column][row].equals(columns[column + 2*i][row + 2*j])) &&
         columns[column][row].equals(columns[column + 3*i][row + 3*j])));
   }
   
   //Checks up, diagonal-left-up, left, diagonal-left-down, down, diagonal-right-down, right, diagonal-right-up (respectively)
   public static boolean checkWin()
   {
      return(checkAdjacent(0,-1) || checkAdjacent(-1, -1) || checkAdjacent(-1, 0) || checkAdjacent(-1, 1) || checkAdjacent(0,1) || 
         checkAdjacent(1,1) || checkAdjacent(1,0) || checkAdjacent(1, -1));
   }
}