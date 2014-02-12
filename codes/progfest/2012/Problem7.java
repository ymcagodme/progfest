//StringBuilder documentation: http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
//BigInteger documentation: http://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html#BigInteger%28java.lang.String,%20int%29
//Integer documentation: http://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#decode%28java.lang.String%29

import java.math.BigInteger;
import java.util.*;


public class Problem7 
{
	//inputs[i] stores unprocessed ith input, such as "A23, 16"
	//outputs[i] stores processed String inputs[i], such as "D4D"
	public static String[] inputs = new String[5];
	public static String[] outputs = new String[5];
	
	
	
	//Accepts 5 data sets, then processes the 5 data sets and outputs the results
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		for(int i = 0; i < 5; i++)
		{
			String input = keyboard.nextLine();
			inputs[i] = input;
		}
		System.out.println();
		for(int i = 0; i < 5; i++)
		{
			algorithm(i);
			System.out.println(outputs[i]);
		}
		
	}
	
	
	//This method takes advantage of the capabilities of StringBuilder and BigInteger
	//It accepts an int i that tells it which index of Array inputs the method is working with
	//It uses StringBuilder to reverse the number
	//It uses BigInteger to store the number and its reverse
	//It adds the two BigIntegers using the add method and turns the result into an integer of base 10 using the intValue method
	//it uses the toString method of Integer to convert an integer of base 10 to any given base, returning a String
	//The do-while loop continues until either the sum of the numbers is a palindrome or there have been 10 additions
	//If the number is a palindrome, it is stored in outputs
	//Otherwise, a note is made the algorithm did not yield a palindrome after 10 iterations and the 10th iteration is stored
	public static void algorithm(int i)
	{
		String number = parseNumber(inputs[i]);
		String sum;
		int base = parseBase(inputs[i]);
		int additions = 0;
		do
		{
			StringBuilder temp = new StringBuilder(number);
			String backward = temp.reverse().toString();
			BigInteger forwardNumber = new BigInteger(number, base);
			BigInteger reverseNumber = new BigInteger(backward, base);
			int integerSum = forwardNumber.add(reverseNumber).intValue();
			sum = Integer.toString(integerSum, base);
			number = sum;
			additions++;
		}while(!isPalindrome(number) && additions < 10);
		if(isPalindrome(number))
			outputs[i] = number.toUpperCase();
		else
			outputs[i] = "NONE, " +number.toUpperCase();
	}
	
	
	//Parses a String that looks like "A23, 16" by finding the index of the comma
	//and returning a substring of the original String starting at index 0 and ending
	//at the index of the comma
	//If s = "A23, 16" the method would return "A23" (as String)
	public static String parseNumber(String s)
	{
		int commaIndex = s.indexOf(",");
		String number = s.substring(0, commaIndex);
		return number;
	}
	
	
	//Parses String that looks like "A23, 16" by finding the index of the comma
	//and returning a substring of the original String starting at (index of Comma + 2)
	//and ending at the length of the original String
	//If s = "A23, 16" the method would return 16 (as int)
	public static int parseBase(String s)
	{
		int commaIndex = s.indexOf(",");
		String stringBase = s.substring(commaIndex + 2, s.length());
		int base = Integer.parseInt(stringBase);
		return base;
	}
	
	
	//Accepts a String, uses StringBuilder to reverse the String
	//If the String forward and backward are equal, then it is a palindrome and the method returns true
	//Otherwise the method returns false
	public static boolean isPalindrome(String s)
	{
		StringBuilder temp = new StringBuilder(s);
		String forward = temp.toString();
		String backward = temp.reverse().toString();
		if(forward.equals(backward))
			return true;
		else
			return false;
	}
}
