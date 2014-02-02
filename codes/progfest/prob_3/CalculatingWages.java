/*
SAMPLE INPUT:
WEEK 1 START

John Doe,Tuesday,0800,1200,10.00 Adam Smith,Thursday,1530,2000,20.00 John Doe,Tuesday,1230,1700,10.00

WEEK 2 START

John Doe,Friday,1730,2200,10.00 Rudolph Moore,Friday,0700,1200,50.00
*/


//This program addresses problem 3 of Progfest 2013
//It will use the nextLine() method from Scanner to receive the string of:
//<Employee Name>,<Day of week>,<Time In>,<Time Out>,<Amount Paid Per  Hour>
//Use indexOf and substring methods to extract data
//Only stores name and calculated wage (output doesn't ask for day of week, time in, or time out
//Keeps track of data in parallel ArrayLists 
//(ex. If John Doe is stored in the Xth index of ArrayList weekOnePaidNames, then
//the amount John Doe earned in week one is stored in the Xth index of weekOnePaidAmounts

import java.util.*;

public class CalculatingWages
{
   public static ArrayList<String> weekOneNames = new ArrayList<String>();
   public static ArrayList<String> weekOneAmounts = new ArrayList<String>();
   public static ArrayList<String> weekTwoNames = new ArrayList<String>();
   public static ArrayList<String> weekTwoAmounts = new ArrayList<String>();
   public static ArrayList<String> weekOnePaidNames = new ArrayList<String>();
   public static ArrayList<String> weekOnePaidAmounts = new ArrayList<String>();
   public static ArrayList<String> weekTwoPaidNames = new ArrayList<String>();
   public static ArrayList<String> weekTwoPaidAmounts = new ArrayList<String>();
   public static ArrayList<String> totalPaidNames = new ArrayList<String>();
   public static ArrayList<String> totalPaidAmounts = new ArrayList<String>();
   
   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);
      String weekNumber = input.nextLine();
      System.out.println();
      String workSchedule = input.nextLine();
      enterData(weekNumber, workSchedule);
      System.out.println("\n");
      weekNumber = input.nextLine();
      System.out.println();
      workSchedule = input.nextLine();
      enterData(weekNumber, workSchedule);
      paidWeek(1);
      paidWeek(2);
      paidTotal();
      printWeek(1);
      printWeek(2);
      printTotal();
   }
   
   //Determines which week it is by checking if the string contains 1
   public static int determineWeek(String s)
   {
      if(s.contains("1"))
         return 1;
      else
         return 2;
   }
   
   //Schedule:<Employee Name>,<Day of week>,<Time In>,<Time Out>,<Amount Paid Per  Hour>
   //updatedSchedule returns:<Day of week>,<Time In>,<Time Out>,<Amount Paid Per  Hour>
   public static String updatedSchedule(String s)
   {
         s = s.substring(s.indexOf(",") + 1, s.length());
         return s;
   }
   
   //24-hour clock so the 4-digit time inputs are of the form HHMM
   //hours = HH
   //minutes = MM/60
   //1230 becomes 12.5
   public static double decimalHours(String s)
   {
      double hours = Double.parseDouble(s.substring(0,2));
      double minutes = (Double.parseDouble(s.substring(2,4)))/60;
      return (hours+minutes);
   }
   
   
   /*
   Receives string of the form:
   <Employee Name>,<Day of week>,<Time In>,<Time Out>,<Amount Paid Per  Hour>
   Repeatedly extracts data up to the first comma, creates a new string starting after the comma through  the updateSchedule method
   Notice that after the Name, Day of Week, Time In, and Time Out fields there is a comma
   Notice that after Amount Paid Per Hour, there is a space only if another set of data exists (no comma either way)
   Stores name and amount earned
   Stores data into weekOneNames and weekOneAmounts OR weekTwoNames and weekTwoAmounts
   At this point, John Doe may be in weekOneNames multiple times
   */
   public static void enterData(String weekNumber, String workSchedule)
   {
      ArrayList<String> names;
      ArrayList<String> amounts;
      if(determineWeek(weekNumber) == 1)
      {
         names = weekOneNames;
         amounts = weekOneAmounts;
      }
      else
      {
         names = weekTwoNames;
         amounts = weekTwoAmounts;
      }
      do
      {
         String name = workSchedule.substring(0, workSchedule.indexOf(","));
         workSchedule = updatedSchedule(workSchedule);
         String day = workSchedule.substring(0, workSchedule.indexOf(","));
         workSchedule = updatedSchedule(workSchedule);
         String timeIn = workSchedule.substring(0, workSchedule.indexOf(","));
         workSchedule = updatedSchedule(workSchedule);
         String timeOut = workSchedule.substring(0, workSchedule.indexOf(","));
         workSchedule = updatedSchedule(workSchedule);
         String hourlyWage;
         if(workSchedule.contains(" "))
         {
            hourlyWage = workSchedule.substring(0, workSchedule.indexOf(" "));
            workSchedule = workSchedule.substring(workSchedule.indexOf(" ") + 1, workSchedule.length());
         }
         else
            hourlyWage = workSchedule;
         double total = (decimalHours(timeOut) - decimalHours(timeIn)) * Double.parseDouble(hourlyWage);
         String earnings = String.valueOf(total);
         names.add(name);
         amounts.add(earnings);
      }while(workSchedule.contains(" "));
   }
   
   /*
   Takes data from (example) weekOneNames and weekOneAmounts and stores them in weekOnePaidNames and weekOnePaidAmounts
   Where weekOneNames may have multiple occurrences of one name, this method finds each occurrence
   and sums up all corresponding amounts from weekOneAmounts
   
   The outer for-loop runs through each name in (example) weekOneNames. The inner for-loop executes if 
   the name is not found in weekOnePaidNames. When the inner for-loop executes, it finds all occurrences of
   the name that was not in weekOnePaidNames and accumulates earnings. Then the name and amount get stored in
   weekOnePaidNames and weekOnePaidAmounts
   */
   public static void paidWeek(int week)
   {
      ArrayList<String> names;
      ArrayList<String> amounts;
      ArrayList<String> namesPaid;
      ArrayList<String> amountsPaid;
      if(week == 1)
      {
         names = weekOneNames;
         amounts = weekOneAmounts;
         namesPaid = weekOnePaidNames;
         amountsPaid = weekOnePaidAmounts;
      }
      else
      {
         names = weekTwoNames;
         amounts = weekTwoAmounts;
         namesPaid = weekTwoPaidNames;
         amountsPaid = weekTwoPaidAmounts;
      }
      for(int i = 0; i < names.size(); i++)
      {
         if(!namesPaid.contains(names.get(i)))
         {
            double earnings = 0;
            for(int j = i; j < names.size(); j++)
            {
               if(names.get(i).equals(names.get(j)))
               {
                  earnings = earnings + Double.parseDouble(amounts.get(j));
               }
            }
            namesPaid.add(names.get(i));
            String amount = String.valueOf(earnings);
            amountsPaid.add(amount);
         }
      }
   }
   
   /*
   Note: By design, weekOnePaidNames has a name at most one time (weekTwoPaidNames has a name at most one time too)
   
   This method is very similar to the paidWeek method. 
   
   The first for-loop looks in weekOnePaidNames for any name that is NOT in totalPaidNames.
   If it finds a name that is NOT in totalPaidNames, it adds the name to totalPaidNames and the earnings from weekOnePaidAmounts
   to totalPaidAmounts
   It then looks for the name in weekTwoPaidNames, and if it finds it, it adds the earnings from weekTwoPaidAmounts
   Note: if the name is NOT in weekTwoPaidNames, then the indexOf method would return a -1
   
   The second for-loop examines names in weekTwoPaidNames, and if it finds a name that is NOT in totalPaidNames,
   it adds the name and the corresponding earnings
   */
   public static void paidTotal()
   {
      for(int i = 0; i < weekOnePaidNames.size(); i++)
      {
         if(!totalPaidNames.contains(weekOnePaidNames.get(i)))
         {
            double earnings = 0;
            earnings = earnings + Double.parseDouble(weekOnePaidAmounts.get(i));
            int index = weekTwoPaidNames.indexOf(weekOnePaidNames.get(i));
            if(index != -1)
            {
               earnings = earnings + Double.parseDouble(weekTwoPaidAmounts.get(index));
            }
            totalPaidNames.add(weekOnePaidNames.get(i));
            String amount = String.valueOf(earnings);
            totalPaidAmounts.add(amount);
         }
      }
      for(int i = 0; i < weekTwoPaidNames.size(); i++)
      {
         if(!totalPaidNames.contains(weekTwoPaidNames.get(i)))
         {
            double earnings = 0;
            earnings = earnings + Double.parseDouble(weekTwoPaidAmounts.get(i));
            totalPaidNames.add(weekTwoPaidNames.get(i));
            String amount = String.valueOf(earnings);
            totalPaidAmounts.add(amount);
         }
      }
   }
   
   
   //Prints the earnings for a given week
   public static void printWeek(int i)
   {
      ArrayList<String> names;
      ArrayList<String> amounts;
      if(i == 1)
      {
         names = weekOnePaidNames;
         amounts = weekOnePaidAmounts;
      }
      else
      {
         names = weekTwoPaidNames;
         amounts = weekTwoPaidAmounts;
      }
      System.out.println("\nWEEK " +i +"\n");
      for(int j = 0; j < names.size(); j++)
      {
         System.out.print(names.get(j) + " earned $" +amounts.get(j) +"  ");
      }
      System.out.println();
   }
   
   //Prints total earnings
   public static void printTotal()
   {
      System.out.println("\nTOTALS\n");
      for(int i = 0; i < totalPaidNames.size(); i++)
      {
         System.out.print(totalPaidNames.get(i) + "'s total earnings is: $" +totalPaidAmounts.get(i) +"  ");
      }
   }
}