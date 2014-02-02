//http://progfest.calstatela.edu:8080/progfest/downloadProblemSet.html?year=2013

//This is a semi-functional solution to Problem 1 of Progfest 2013 (link above)
//Ideally, one would input a list of words and would get returned a list of answers
//This program only allows the user to enter one word but it returns the proper(?) Soundex Code

//In order to have the user input a list and the program return a list, 
//I tried working with 2-dimensional ArrayLists but it got messy and decided to upload what I have so far
//I might simply move to the less elegant solution of using Arrays and creating a large one, maybe an array of size 100
//A 2-dimensional array (ex. A[0][0] = "word", A[0][1] = "code")
//or two 1-dimensional arrays (ex. A[0] = "word", B[0] = "code") would work

//There are a few issues:
//1. The first step in the algorithm tells us to "and an 'h'..." I think 'and' = 'add'
//2. An example changes "glacier" into "riecalg" when it should be "reicalg"
//3. In the sample output, "action" and "certification" recieve code n1 but I believe it should be n3
//4. "race" and "space" have code y2 which I believe should not be possible
//I believe these are typos 

import java.util.*;

public class Soundex
{
   public static ArrayList<String> vowels = new ArrayList<String>();
   public static ArrayList<String> groupOne = new ArrayList<String>();
   public static ArrayList<String> groupTwo = new ArrayList<String>();
   public static ArrayList<String> groupThree = new ArrayList<String>();
   public static ArrayList<String> groupFour = new ArrayList<String>();
   public static ArrayList<String> groupFive = new ArrayList<String>();
   
   public static String[] word = new String[100];

   public static ArrayList<String> code = new ArrayList<String>();
   public static ArrayList<String> uniqueCode = new ArrayList<String>();
   
   public static void main( String[] args)
   {
      //Fills each ArrayList, groupOne contains all group vowels that are replaced by "1" in the algorithm
      //groupTwo is replaced by "2" and so on.
      vowels.add("a");
      vowels.add("e");
      vowels.add("i");
      vowels.add("o");
      vowels.add("u");
      vowels.add("h");
      vowels.add("w");
      vowels.add("y");
      
      groupOne.add("a");
      groupOne.add("aa");
      groupOne.add("ea");
      groupOne.add("ha");
      groupOne.add("ia");
      groupOne.add("ya");
      groupOne.add("ye");
      groupOne.add("yh");
      groupOne.add("ei");
      groupOne.add("yu");
      
      groupTwo.add("e");
      groupTwo.add("ae");
      groupTwo.add("ee");
      groupTwo.add("he");
      groupTwo.add("ie");
      groupTwo.add("hi");
      groupTwo.add("y");
      
      groupThree.add("i");
      groupThree.add("ai");
      groupThree.add("hi");
      groupThree.add("ii");
      groupThree.add("oi");
      groupThree.add("ui");
      groupThree.add("yi");
      
      groupFour.add("oa");
      groupFour.add("ua");
      groupFour.add("wa");
      groupFour.add("oe");
      groupFour.add("o");
      groupFour.add("ao");
      groupFour.add("eo");
      groupFour.add("ho");
      groupFour.add("io");
      groupFour.add("wo");
      groupFour.add("yo");
      groupFour.add("iu");
      
      groupFive.add("ue");
      groupFive.add("we");
      groupFive.add("wi");
      groupFive.add("oo");
      groupFive.add("uo");
      groupFive.add("u");
      groupFive.add("au");
      groupFive.add("eu");
      groupFive.add("hu");
      groupFive.add("ou");
      groupFive.add("uu");
      
      // This entire section needs to be adjusted, but I just wanted to make sure
      // that the information could be processed in an organized way
      // and was curious about how I could do that
      
      Scanner input = new Scanner(System.in);
      
      System.out.println("Enter words in a line separated by a space");
      
      // User enters a line of data
      String line = input.nextLine();             
      
      // That line of data is entered into another Scanner method so only that line of data is processed by Scanne
      Scanner inputWord = new Scanner(line);     
      
      int i = 0;
      
      // As long as the line of data finds another token (in this case, hopefully a String),
      // then it will continue entering the next token into the word array
      while(inputWord.hasNext())                  
      {
         word[i] = inputWord.next();
         i++;
      }
      
      System.out.println();
      
      int wordCount = i;         // Keeps track of the number of words in array
      int uniqueCodeCount = 0;   // Determines the number of soundexCodes that are unique
      
      // soundexCodes are generated for each word using the algorithm method
      // That soundexCode is added to the code ArrayList -- which would be parallel to the word array
      // I checked for the uniqueness of the soundexCode because at the bottom of the output
      // in the problem, it looks like the output is organized by each code so I figured this would be one way
      // to tag or group soundexCodes
      
      // I figured there might be a way to use uniqueCode ArrayList to process the code ArrayList for matching soundexCodes
      // The associated words could be retrieved using the index of the code ArrayList
      // The only problem with this method is that it does not alphabetically order the code ArrayList or the words (like the output wants)
      for(int j = 0; j < wordCount-1; j++)
      {
         String soundexCode = algorithm(word[j]);
         code.add(soundexCode);
         
         System.out.print(word[j] + " ");     // Here I was just testing to see if the code at the given index matched the word
         System.out.println(code.get(j));     // associated with it (by using the same index)
         
         boolean unique = checkUniqueCode(soundexCode);
         
         if(unique)
         {
            uniqueCode.add(soundexCode);
            uniqueCodeCount++;
         }
      }
      
   }
   
   //Performs the Soundex Algorithm
   public static String algorithm(String s)
   {
      s = wordEnd(s);
      s = wordReverse(s);
      s = replaceConsonants(s);
      s = groupConsonants(s);
      s = dropExcess(s);
      s = soundexCode(s);
      return s;
   }

  //Performs Step 1 of the Algorithm
   //This method adds an 'h' at the end of a word if necessary
   public static String wordEnd(String s)
   {
      String letter = s.substring(s.length()-1, s.length());
      if(vowels.contains(letter))
         s = s + "h";
      return s;
   }
   
   //Performs Step 2 of Algorithm
   //This method reverses the word
   //The loop looks for the last letter of the word and adds it a temp string
   //It then looks at the second last letter, adds it, and so on..
   public static String wordReverse(String s)
   {
      String temp = "";
      for(int i = s.length()-1; i >= 0; i--)
      {
         temp = temp + s.substring(i, i+1);
      }
      return temp;
   }
   
   //The following two methods (replaceConsonants and groupConsonants) perform Step 3 of the Algorithm
   //Examines each letter (not including the first one) and if it is not a vowel, it is replaced by a "-"
   //Notice that the substring method is defined as substring(int beginIndex, int endIndex) 
   //where beginIndex - the beginning index, inclusive. endIndex - the ending index, exclusive.
   public static String replaceConsonants(String s)
   {
      for(int i = 1; i < s.length(); i++)
      {
         String letter = s.substring(i, i+1);
         if(!vowels.contains(letter))
            s = s.substring(0, i) + "-" + s.substring(i+1, s.length());
      }
      return s;
   }
   
   //The previous method turned "hojkleh" into ho---eh
   //This method turns ho---eh into ho--eh and then ho--eh into ho-eh
   public static String groupConsonants(String s)
   {
      while(s.contains("--"))
      {
         s = s.replace("--", "-");
      }
      return s;
   }
   
   //Notice that our output is only concerned with the first vowel group after the first letter
   //Therefore I only drop "Excess vowels" from the first vowel group and anything beyond the first vowel group
   public static String dropExcess(String s)
   {
      String firstLetter = s.substring(0, 1);
      String vowelGroup = s.substring(1, s.length());
      int firstDash = vowelGroup.indexOf("-");
      if (firstDash == 0)  
      {   
         vowelGroup = vowelGroup.substring(1, vowelGroup.length());
         firstDash = vowelGroup.indexOf("-");
      }
      if (firstDash == 1)
         vowelGroup = vowelGroup.substring(0, 1);
      else
         vowelGroup = vowelGroup.substring(0, 2);
      return (firstLetter+vowelGroup);
   }
   
   //Separates the first letter from the vowel group and then replaces the vowel group with the
   //designated number, then it returns the code
   public static String soundexCode(String s)
   {
      String firstLetter = s.substring(0, 1);
      String vowelGroup = s.substring(1, s.length());
      String code = firstLetter;
      if(groupOne.contains(vowelGroup))
         code = code + "1";
      else if(groupTwo.contains(vowelGroup))
         code = code + "2";
      else if (groupThree.contains(vowelGroup))
         code = code + "3";
      else if (groupFour.contains(vowelGroup))
         code = code + "4";
      else if (groupFive.contains(vowelGroup))
         code = code + "5";
      else
         code = code + "6";
      return code;
   }
   
   /* Zero pads the soundexcode
      In the final output, only the first two characters of the soundex code are output
      so this is not used in the program (to get rid of unnecessary processing)
   */
   public static String zeroPad(String s)
   {
      if (s.length() < 3)
         s = s + "0";
      return s;
   }
   
   /* Determines the uniqueness of a soundexCode. If the soundexCode entered
      is already in the uniqueCode ArrayList then, boolean returns false.
   */
   public static boolean checkUniqueCode(String s)
   {
      return(uniqueCode.contains(s));
   }
   
   // This method adds a soundexCode to the uniqueCode Array List.
   // It should be used after checking for uniqueness by using the checkUniqueCode method
   public static void addToCodeList(String s)
   {
         uniqueCode.add(s);
   }
}