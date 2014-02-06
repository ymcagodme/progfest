import java.util.*;

/*
   Compile: javac Checkers.java
   Run: java Checkers
   
   Input:
   2,1,3,1,5,6,2,4,2,6,4,2,4,6,6,6,4,4   [then hit Enter]
   
   Output:
   [board displays]
   [then hit CTRL+Z, ^Z shows up, hit Enter]
   [program ends]
   
   This is an incomplete program--the program is not solved in any way,
   but I just was playing around with the input to see how I can create
   a 2D array to keep track of my checker pieces. 
   
   I am going to have to think about how I am going to implement the solution
   a little more. But for now, you can see what I am playing with.

*/**********************************

public class Checkers
{
   public static char[][] board = new char[9][9];   // Simulates board, use values of X and Y coordinates to determine
   public static Scanner input = new Scanner(System.in);
   
   public static int maxMoves;    // Keeps track of max # of moves so far
   
   public static int myPieceCount;       // Keeps track of my number of pieces
   public static int theirPieceCount;    // Keeps track of their number of pieces
   
   public static void main(String[] args)
   {    
      while(input.hasNext())
      {  // Accept the first line of input
         String line = input.nextLine();
         // Mark commas as delimiters
         Scanner locations = new Scanner(line).useDelimiter("\\s*,\\s*");
         
         // As long as locations has more tokens, continue loop
         while(locations.hasNext())
         {  
            // Create 4 ArrayLists myX, myY, and theirX, theirY to
            // store values for the coordinates of the checker pieces
            ArrayList<Integer> myX = new ArrayList<Integer>();
            ArrayList<Integer> myY = new ArrayList<Integer>();
            ArrayList<Integer> theirX = new ArrayList<Integer>();
            ArrayList<Integer> theirY = new ArrayList<Integer>();
            
            // Store the number of pieces I have in myPieceCount
            myPieceCount = locations.nextInt();
            
            // Add the rest of my X and Y values for checker pieces in 
            // parallel ArrayLists myX and myY
            for(int i = 1; i <= myPieceCount; i++)
            {
               myX.add(locations.nextInt());
               myY.add(locations.nextInt());
            }
            
            // Store the number of pieces they have in theirPieceCount
            theirPieceCount = locations.nextInt();
            
            // Add the rest of their X and Y values for checker pieces in
            // parallel ArrayLists theirX and theirY
            for(int i = 1; i <= theirPieceCount; i++)
            {
               theirX.add(locations.nextInt());
               theirY.add(locations.nextInt());
            }
            
            // Print the array lists to verify that it has the correct data
            int my = myX.size();
            int their = theirX.size();
            
            System.out.println("Mine:");
            
            for(int i = 0; i < my; i++)
            {  
               System.out.print("(" + myX.get(i) + "," + myY.get(i) + ")");
            }
            
            System.out.println("\nTheir:");
            
            for(int i = 0; i < their; i++)
            {
               System.out.print("(" + theirX.get(i) + "," + theirY.get(i) + ")");
            }
            
            System.out.println("\n");
            
            setUpBoard(myX, myY, theirX, theirY);
         }
      }
   }
   // Remember default value for char is '\0', the null character
   public static void setUpBoard(ArrayList<Integer> myX, ArrayList<Integer> myY, ArrayList<Integer> theirX, ArrayList<Integer> theirY)
   {
      // Initialize the board's string values to "empty"
      for (int row = 1; row <= 8; row ++)
      {
         for (int col = 1; col <= 8; col++)
         {
            board[row][col] = ('_');
         }
      }
      
      int my = myX.size();
      int their = theirX.size();
      
      // Locations of my pieces are assigned String value "mine"
      // in the board array
      // x = row number and y = column number
      for(int i = 0; i < my; i++)
      {  
         int x = myX.get(i);
         int y = myY.get(i);
         board[x][y]= 'M';
      }
      
      // Locations of their pieces are assigned String value "their"
      // in the board array
      for(int i = 0; i < their; i++)
      {  
         int x = theirX.get(i);
         int y = theirY.get(i);
         board[x][y]= 'T';
      }
      
      // Check to see what the board looks like
      for (int row = 8; row >= 1; row--)
      {  System.out.print(row);     // Displays row number for board
         for (int col = 1; col <= 8; col++)
         {
            System.out.printf("%2c", board[row][col]);
         }
         System.out.println();
      }
      // Shows column number for board
      System.out.println("  1 2 3 4 5 6 7 8");
      
   }
}