/*
This program answers problem 9 of Progfest 2013
It starts by asking for which number to start with and how many numbers in the sequence to find
It stores the starting number in a String because the substring method is helpful in solving the problem
*/

import java.util.*;

public class LookAndSay
{
   //If the sample input/output were entered... 
   //sequence would store: 1, 11, 21, 1211, 111221, 312211
   //indexesWithPalindrome would store: 5, 6
   //longestPalindrome would store: 1221
   public static ArrayList<String> sequence = new ArrayList<String>();
   public static ArrayList<Integer> indexesWithPalindrome = new ArrayList<Integer>();
   public static String longestPalindrome = "";
   
   //Stores d as a String, n as an integer
   //First for-loop finds first n numbers of the sequence and displays them
   //Second for-loop finds the longest palindrome
   //Third for-loop displays the indexes which contain the longest palindrome 
   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);
      String number = input.next();
      int iterations = input.nextInt();
      sequence.add(number);
      System.out.println();
      for(int i = 0; i < iterations - 1; i++)
      {
         sequence.add(nextNumber(sequence.get(i)));
         System.out.print(sequence.get(i) +", ");
      }
      System.out.println(sequence.get(sequence.size()-1));
      for(int i = 0; i < sequence.size(); i++)
      {
         findPalindrome(sequence.get(i));
      }
      System.out.println("\nLongest palindrome: " +longestPalindrome);
      containsPalindrome();
      System.out.print("At numbers: ");
      for(int i = 0; i < indexesWithPalindrome.size() -1; i++)
         System.out.print(indexesWithPalindrome.get(i) +", ");
      System.out.println(indexesWithPalindrome.get(indexesWithPalindrome.size()-1));
   }
   
   /*
      Example run, say number = 111221:
      Outer-loop 1st try
         Executes
         currentNumber = 1 (as it's the left-most character)
         counter = 1
         Since number.length != 1, number = 11221
         Inner-loop 1st try
            It does execute because number.length > 0, currentNumber == 1
            counter = 2
            The else statement executes
               number = 1221
         Inner-loop 2nd try
            Executes
            counter = 3
            Else statement executes
               number = 221
         Inner-loop 3rd try
            Doesn't execute
         Concatenate 'counter' and 'currentNumber' to temp
         temp = temp + "3" + "1" 
      Next Outer-loop tries to execute...
   */
   public static String nextNumber(String number)
   {
      String temp = "";
      do
      {
         int currentNumber = Integer.parseInt(number.substring(0,1));
         int counter = 1;
         if(number.length() == 1)
            number = "";
         else
            number = number.substring(1,number.length());
         while( (number.length() > 0) && (currentNumber == Integer.parseInt(number.substring(0,1))))
         {
            counter++;
            if(number.length() == 1)
               number = "";
            else
               number = number.substring(1,number.length());
         }
         temp = temp + String.valueOf(counter) + String.valueOf(currentNumber);
      }while(!number.equals(""));
      return (temp);
   }
   
   //Reverse a string
   //hello becomes olleh
   //12345 becomes 54321
   public static String wordReverse(String s)
   {
      String temp = "";
      for(int i = s.length()-1; i >= 0; i--)
      {
         temp = temp + s.substring(i, i+1);
      }
      return temp;
   }
   
   //Accepts a String and divides it into a first and second half
   //If the String is odd, the middle number is ignored
   //Ex: 5678765: first = 567, second = 765
   //The second half is reverse: secondReverse = 567
   //If first.equals(secondReverse) [567 = 567]
   //Method returns true, otherwise method returns false
   public static boolean isPalindrome(String s)
   {
      String first;
      String second;
      int midpoint = s.length()/2;
      if(s.length()%2 == 0)
      {
         first = s.substring(0, midpoint);
         second = s.substring(midpoint, s.length());
      }
      else
      {
         first = s.substring(0, midpoint);
         second = s.substring(midpoint + 1, s.length());
      }
      String secondReverse = wordReverse(second);
      if(first.equals(secondReverse))
         return true;
      else
         return false;
   }
   
   
   //Brute force check of a String to determine if it has a palindrome
   //Checks every possible substring of a String to determine if it is a palindrome
   public static void findPalindrome(String s)
   {
      for(int i = 0; i < s.length(); i++)
      {
         for(int j = i + 1; j <= s.length(); j++)
         {
            if(isPalindrome(s.substring(i,j)))
            {
               if(s.substring(i,j).length() > longestPalindrome.length())
               {
                  longestPalindrome = s.substring(i,j);
               }
            }
         }
      }
   }
   
   //Checks if an index of the ArrayList sequence contains the palindrome
   //Keep in mind, nth index is n+1 number entered
   public static void containsPalindrome()
   {
      for(int i = 0; i < sequence.size(); i++)
      {
         if(sequence.get(i).contains(longestPalindrome))
            indexesWithPalindrome.add(i+1);
      }
   }
}