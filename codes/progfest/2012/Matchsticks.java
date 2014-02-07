// 2012 Progfest Problem 3 - Matchstick Mayhem
// Author: David Huang
//
// Algorithms involved:
//    1. Dynamic Programming (DP)
//    2. Enumerate Algorithm
//    3. Recursion
//
// Explanation:
//    Let N be amount of matchsticks
//    Let D be the exact digit length we want
//
//    F(N, D) will return all the number combination from the given N and D
//
//    For example:
//    F(4, 2) means we have 4 matches ane we want to build two digits number.
//    In this case, the only possible way to build two digits number is building
//    2 of number 1, which costs 4 matches.
//
//    --------------------------
//
//    First, we build an array to store the cost of each number from 0 to 9, and
//    construct an an array to store the calculation for future reference.
//
//    In order to do recursion, we define the base condition:
//    If D is 1, then compare the amount of sticks to the cost of each number.
//    If the amount is greater than the cost, then it means we have enough sticks
//    to build the number. So, we count is as one combination. (Because we just
//    need one digit, every valid build will be a combination)
//
//    --------------------------
//
//    Then, the idea of recursion is the following:
//
//    For N matchsticks and D digit, we pick number to be the leading digit (except 0)
//
//    Let's say I pick number 1, which costs 2 matchsticks to build it.
//    (Be careful, there must be enough sticks to build the number)
//    Then the possible combination will be:
//       possible combination = F(N - 2, D -1)
//    Since we used 2 sticks to build number 1, the amount left will be N minus the cost.
//    Therefore, we pass the amount left into the next level (recursion). Since we have
//    decided the leading digit (in this case is number 1), the digit amount for the next
//    level will be D minus one.
//
//    --------------------------
//
//    Thus, this problem is solved.
//
//    Typically, Dynamic Programming (DP) will be used with recursion, and Enumerate algorithm
//    provides enough conditions for using DP. If you are not familiar with these algorithm,
//    please take a look of its wiki page.
//
//    Happy Hacking!


import java.util.Scanner;
import java.util.Arrays;

public class Matchsticks {
   static int[] cost = {6, 2, 5, 5, 4, 5, 6, 3, 7, 5};
   static long[][] table = new long[81][41];

   public static void main(String[] args) {
      initTable();

      Scanner sc = new Scanner(System.in);

      while (sc.hasNext()) {
         int numOfMatch = sc.nextInt();
         int mostDigit = numOfMatch / 2;  // Due to the smallest required is number 1 -> 2 matches
         long sum = 0L;

         // Enumerate Algorithm
         for (int digit = 1; digit <= mostDigit; ++digit) {
            sum += search(numOfMatch, digit);
         }
         System.out.println(sum);
      }
   }

   public static void initTable() {
      for (int row = 0; row < 81; ++row) {
         for (int col = 0; col < 41; ++col) {
            table[row][col] = -1L;
         }
      }
   }

   // Dynamic Programming
   public static long search(int numOfMatch, int digit) {

      // To see if this has been calculated. If so, return the number.
      if (table[numOfMatch][digit] != -1L) {
         return table[numOfMatch][digit];
      }

      // No Match can't build any number
      if (numOfMatch == 0) {
         table[numOfMatch][digit] = 0L;
         return 0L;
      }

      // Base condition
      if (digit == 1) {

         // Count of combination
         long count = 0L;

         // Go through each number. If the number of matches is greater or
         // equal to the cost, count it as a valid build. Since this is a
         // single digit request, every possible build is one combination.
         for (int i = 0; i < cost.length; ++i) {
            if (numOfMatch >= cost[i])
               ++count;
         }

         // Memorize the count of combination into the table
         table[numOfMatch][digit] = count;
         return count;
      }

      // If the digit requested is greater than 1, do the following

      // Count of combination
      long count = 0L;

      // Here is the tricky part. Notice that I set the starting point to
      // the first number, which is number 1 with index of cost at 1.
      // The reason for this is because number 0 can't be used as the leading
      // digit to build a valid combination.
      //
      // For example:
      //    0125 is invalid
      for (int i = 1; i < cost.length; ++i) {

         // Be careful, there must be enough sticks to build the number.
         if (numOfMatch >= cost[i])
            count += search(numOfMatch - cost[i], digit - 1);
      }

      table[numOfMatch][digit] = count;
      return count;

   }
}
