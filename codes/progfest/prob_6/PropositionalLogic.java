//This is a solution to Problem 6 of Progfest 2013
//It recursively finds truth values of an expression by finding the truth values of left and right sides of a connective operator
//Ex. In order to evaluate (((a|~b)&(~a|b))&(a&~b))
//It would determine that leftSide = ((a|~b)&(~a|b)), rightSide = (a&~b), operator = &
//It would then look at leftSide and determine that the left side of leftSide = (a|~b)
//And so on...

import java.util.ArrayList;
import java.util.Scanner;


public class PropositionalLogic 
{
	//truthTable.get(i) stores the list of truth-values for the ith atom
	//truthTable.get(i).get(j) is the jth truth-value of the ith atom
	public static ArrayList<ArrayList<Boolean>> truthTable = new ArrayList<ArrayList<Boolean>>();
	public static ArrayList<String> atoms = new ArrayList<String>();
	public static ArrayList<String> nonAtoms = new ArrayList<String>();
	public static ArrayList<String> expressions = new ArrayList<String>();
	public static ArrayList<String> solutions = new ArrayList<String>();
	
	public static void main(String[] args)
	{
		nonAtoms.add("(");
		nonAtoms.add(")");
		nonAtoms.add("|");
		nonAtoms.add("&");
		nonAtoms.add("~");
		nonAtoms.add("-");
		nonAtoms.add(">");
		
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter expressions. Enter expression X to stop input:");
		String expression = keyboard.next();
		while(!expression.equals("X"))
		{
			atoms.clear();
			truthTable.clear();
			findAtoms(expression);
			expressions.add(expression);
			int numAtoms = atoms.size(); //Number of atoms
			int combinations = (int) Math.pow(2, numAtoms);
			
			//Constructs truth table
			for(int i = 0; i < numAtoms; i++)
			{
				truthTable.add(new ArrayList<Boolean>());
				for(int j = 0; j < combinations; j++)
				{
					int value = (int)Math.pow(-1, j/(int)Math.pow(2, numAtoms - i - 1));
					boolean truthValue;
					if (value == 1)
						truthValue = true;
					else
						truthValue = false;
					truthTable.get(i).add(truthValue);
				}
			}
			
			//Brute-force check of all possible truth values to determine if expression is satisfiable
			boolean satisfiable = false;
			for(int i = 0; i < combinations; i++)
			{
				satisfiable = evaluate(expression, i);
				if(satisfiable)
					break;
			}
			if(satisfiable)
				solutions.add(" is satisfiable");
			else
				solutions.add(" is unsatisfiable");
			expression = keyboard.next();
		}
		
		for(int i = 0; i < expressions.size(); i++)
			System.out.println(expressions.get(i) + solutions.get(i));
	}
	
	
	//Determines if a character is an atom, and if so it adds it to the ArrayList atoms (if it doesn't already have it)
	public static void findAtoms(String expression)
	{
		for(int i = 0; i < expression.length(); i++)
		{
			String character = expression.substring(i, i+1);
			if(!nonAtoms.contains(character) && !atoms.contains(character))
			{
				atoms.add(character);
			}
		}
	}
	
	
	//Evaluates an expression using a given combination of truth-values
	//It drops outer parentheses if the expression has any
	//Special cases when expression has a length of 1 (atom) and when it begins with a ~
	//Finds the left-side of the expression, the right side, and determines the connecting operator
	public static boolean evaluate(String expression, int combination)
	{
		expression = dropOuterParentheses(expression);
		if(expression.length() == 1)
		{
			int index = atoms.indexOf(expression);
			return truthTable.get(index).get(combination);
		}
		else if (expression.substring(0,1).equals("~"))
		{
			return not(expression, combination);
		}
		else
		{
			String leftSide = getLeftSide(expression);
			String rightSide = expression.substring(leftSide.length(), expression.length());
			String operation = rightSide.substring(0,1);
			rightSide = rightSide.substring(1,rightSide.length());
			if(operation.contains("|"))
				return (or(leftSide, rightSide, combination));
			else if(operation.contains("&"))
				return (and(leftSide, rightSide, combination));
			else
			{
				rightSide = rightSide.substring(1,rightSide.length());
				return (implies(leftSide, rightSide, combination));
			}
		}
	}
	
	public static boolean and(String leftSide, String rightSide, int combination)
	{
		boolean leftValue = evaluate(leftSide, combination);
		boolean rightValue = evaluate(rightSide, combination);
		return (leftValue && rightValue);
	}
	
	public static boolean or(String leftSide, String rightSide, int combination)
	{
		boolean leftValue = evaluate(leftSide, combination);
		boolean rightValue = evaluate(rightSide, combination);
		return (leftValue || rightValue);
	}
	
	public static boolean not(String expression, int combination)
	{
		expression = expression.substring(1, expression.length());
		boolean truthValue = evaluate(expression, combination);
		return (!truthValue);
	}
	
	public static boolean implies(String leftSide, String rightSide, int combination)
	{
		boolean leftValue = evaluate(leftSide, combination);
		boolean rightValue = evaluate(rightSide, combination);
		return (!leftValue || rightValue);
	}
	
	
	public static String dropOuterParentheses(String expression)
	{
		String firstCharacter = expression.substring(0,1);
		if(firstCharacter.equals("("))
		{
			expression = expression.substring(1,expression.length()-1);
		}
		return expression;
	}
	
	//This method relies on the fact that the outer parentheses have been removed
	//Ex. the expression would NOT be (((a|~b)&(~a|b))&(a&~b))
	//the expression would be ((a|~b)&(~a|b))&(a&~b)
	//It counts the number of ( by adding 1 and the number of ) by subtracting 1
	//Note that every "(" requires a matching ")".
	public static String getLeftSide(String expression)
	{
		String firstCharacter = expression.substring(0,1);
		if(firstCharacter.equals("("))
		{
			int counter = 1;
			int index =1;
			for(int i = 1; i < expression.length(); i++)
			{
				index = i;
				String character = expression.substring(i, i+1);
				if(character.equals("("))
					counter++;
				if(character.equals(")"))
				{
					counter--;
				}
				if (counter == 0)
					break;
			}
			String leftSide = expression.substring(0,index+1);
			return leftSide;
		}
		else
			return firstCharacter;
	}
}