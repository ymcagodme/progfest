// ACM 2013 Problem 1

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

public class SwampCounty
{   
   public static int[] pointValueAToZPlusWhite = new int[27];
   public static Scanner input = new Scanner(System.in);

   public static void main(String[] args)
   {
      problem1();
   }
   
   public static void problem1()
   {     
      assignLetterValues();
      
      /*TODO:  Find a way to continually accept input for words even
               though we don't know how many words the judges will enter.
               Should I just make a really big array?
      */
      
      // Creates an array to store point values for each word
      int[] pointsOfWords = new int[4];
      
      // Accepts squareTypes (point value for each letter in the specific word)
      // Accepts word input, then converts word to uppercase
      // letterScalingFactor and wordScalingFactor is used to assign overall point value for each word
      for (int c = 0; c < 4; c++)
      {
         System.out.println("Enter square types:");
         String squareType = input.next();
         System.out.printf("%30s\n", squareType);
         System.out.println("Enter word");
         String word = input.next();
         System.out.printf("%30s\n", word);
         String wordUpper = word.toUpperCase();
         System.out.printf("%30s\n", wordUpper);
         
         int[] letterScalingFactor = new int[squareType.length()];
         
         int wordScalingFactor = 1;
         int pointTotal = 0;

         for (int i = 0; i < squareType.length(); i++)
         {
            letterScalingFactor[i] = 1;
            
            if (squareType.charAt(i) == '.')
               continue;
            else if (squareType.charAt(i) == '2')
               letterScalingFactor[i] = 2;
            else if (squareType.charAt(i) == '3')
               letterScalingFactor[i] = 3;
            else if (squareType.charAt(i) == 'd')
               wordScalingFactor *= 2;
            else if (squareType.charAt(i) == 't')
               wordScalingFactor *= 3;               
         }
         for (int i = 0; i < wordUpper.length(); i++)
         {   
            if (wordUpper.charAt(i) == '_')
               pointTotal = pointTotal + (letterScalingFactor[i]*pointValueAToZPlusWhite[26]);
            else
               pointTotal = pointTotal + (letterScalingFactor[i]*pointValueAToZPlusWhite[(int)wordUpper.charAt(i)-65]);
         }
         
         pointsOfWords[c] = wordScalingFactor*pointTotal;
      }

      
      for (int i = 0; i < 4; i++)
      {
         System.out.println(pointsOfWords[i]);
      }
   }
   
   public static void assignLetterValues()
   {
      String letterLine1 = input.nextLine();
      
      String letterLine2 = input.nextLine();
      
      String letterLine3 = input.nextLine();
      
      Scanner readLine1 = new Scanner(letterLine1);
      
      Scanner readLine2 = new Scanner(letterLine2);
      
      Scanner readLine3 = new Scanner(letterLine3);
      
      // Input point value data for the letters and white space
      /* TODO: Make column width of input max 80 columns,
               validate input to be 0-99 inclusive,
               separate input out to 3 lines, 9 integers each line,
               consider re-entering input (9 ints) per line if there is an inputMismatch,
               consider using input.nextLine() to make 3 lines of input and then
               traverse that data to pull out the integers [but the issues with that
               might be--converting the data to integers, then handling the exceptions after
               the conversions]
      */
      
      int counter = 0;
      
      while (counter < 9)
      {
         try      // Need to brush up on exception handling and where in the stack program is after catch block
         {  
            pointValueAToZPlusWhite[counter] = readLine1.nextInt();
            counter++;
         }
         catch (InputMismatchException inputMismatchException)
         {
            System.err.printf("\nException: %s\n", inputMismatchException);
            input.nextLine();
            System.out.println("The last value you entered was not an integer. Please re-enter a valid value. \n");
         }
      }
      
      while (counter < 18)
      {
         try      // Need to brush up on exception handling and where in the stack program is after catch block
         {  
            pointValueAToZPlusWhite[counter] = readLine2.nextInt();
            counter++;
         }
         catch (InputMismatchException inputMismatchException)
         {
            System.err.printf("\nException: %s\n", inputMismatchException);
            input.nextLine();
            System.out.println("The last value you entered was not an integer. Please re-enter a valid value. \n");
         }
      }
      
      while (counter < 27)
      {
         try      // Need to brush up on exception handling and where in the stack program is after catch block
         {  
            pointValueAToZPlusWhite[counter] = readLine3.nextInt();
            counter++;
         }
         catch (InputMismatchException inputMismatchException)
         {
            System.err.printf("\nException: %s\n", inputMismatchException);
            input.nextLine();
            System.out.println("The last value you entered was not an integer. Please re-enter a valid value. \n");
         }
      }
      
      System.out.println(Arrays.toString(pointValueAToZPlusWhite));
      
   }
   
}