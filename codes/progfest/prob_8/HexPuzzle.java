//Solution to Problem 8 of Progfest 2013

import java.util.Scanner;


public class HexPuzzle 
{
	//There are 7 tiles with 6 labels each
	//int[tilesNumber][labelNumber]
	//Label "a" at index 0, "b" at index 1, ..., "f" at index 5.
	public static int[][] inputs = new int[7][6];
	public static int[][] solutions = new int[7][6];
	public static int[] rotations = {0, 0, 0, 0, 0, 0, 0};
	
	public static void main(String[] args)
	{
		//User enters initial tile positions
		Scanner keyboard = new Scanner(System.in);
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				inputs[i][j] = keyboard.nextInt();
				solutions[i][j] = inputs[i][j];
			}
		}
		
		//The outer for-loop is in charge of rotating Tile 1
		//Tile 1 has 6 unique positions
		//The first position is the starting position
		//The second position is one rotation
		//Third position is the rotation after the first rotation (net 2 rotations)
		//So on...
		//Notice that if you rotate the tile 6 times, you return to the initial position
		boolean solution = false;
		for(int i = 0; i < 6; i++)
		{
			//if-statements ensures there are only 5 rotations
			if(i > 0)
				rotate(0);
			rotations[0] = i;
			
			//This for-loop makes Tile j have a matching adjacent side with Tile j-1 using the makeMatch method
			for(int j = 1; j < 7; j++)
				makeMatch(j);
			
			//breaks if a solution is found before 6 iterations of the outer for-loop
			if(isSolution())
			{
				solution = true;
				break;
			}
		}
		if(solution)
		{
			System.out.println("\nSolution found!");
			for(int i = 0; i < 7; i++)
			{
				int tile = i + 1;
				System.out.print("Tile " + tile + " position: " + rotations[i] + ",rotation: ");
				for(int j = 0; j < 6; j++)
				{
					System.out.print(solutions[i][j] + " ");
				}
				System.out.println();
			}
		}
		else
			System.out.println("\nNo Solution found.");

	}
	
	//Rotates tile once, clock-wise
	public static void rotate(int tile)
	{
		int tempPrevious = solutions[tile][0];
		solutions[tile][0] = solutions[tile][5];
		int tempCurrent;
		for(int i = 1; i < 6; i++)
		{
			tempCurrent = solutions[tile][i]; 
			solutions[tile][i] = tempPrevious;
			tempPrevious = tempCurrent;
		}
		rotations[tile]++;
	}
	
	//Rotates tile until it matches previous tile at necessary location
	//Notice that for tiles 2-6 (indexes 1-5), inclusive, 
	//the Tile's label (tile+4)%6 must match the previous tile's label (previousTile + 2)&6
	//Tile 7 (index 6) is a special case
	public static void makeMatch(int tile)
	{
		int previousTile = tile - 1;
		if(tile < 6)
		{
			int number = solutions[previousTile][(previousTile + 2)%6];
			while(solutions[tile][(tile+4)%6] != number)
				rotate(tile);
			
		}
		else
		{
			int number = solutions[5][2];
			while(solutions[6][5] != number)
				rotate(tile);
		}
	}
	
	
	//By construction Tile n matches Tile n-1. The only checks that need to be done are between
	//the first and second-last tiles, and the last tile with every tile except the second-to-last tile.
	//I count tiles 0 through 6 (seven tiles total) because it's easier to read with the indexes
	public static boolean isSolution()
	{
		boolean fiveAndZero;
		boolean sixAndZero;
		boolean sixAndOne;
		boolean sixAndTwo;
		boolean sixAndThree;
		boolean sixAndFour;
		
		if(solutions[5][1] == solutions[0][4])
			fiveAndZero = true;
		else
			fiveAndZero = false;
		
		if(solutions[6][0] == solutions[0][3])
			sixAndZero = true;
		else
			sixAndZero = false;
		
		if(solutions[6][1] == solutions[1][4])
			sixAndOne = true;
		else
			sixAndOne = false;
		
		if(solutions[6][2] == solutions[2][5])
			sixAndTwo = true;
		else
			sixAndTwo = false;
		
		if(solutions[6][3] == solutions[3][0])
			sixAndThree = true;
		else
			sixAndThree = false;
		
		if(solutions[6][4] == solutions[4][1])
			sixAndFour = true;
		else
			sixAndFour = false;
		
		return (fiveAndZero && sixAndZero && sixAndOne && sixAndTwo && sixAndThree && sixAndFour);
	}
}
