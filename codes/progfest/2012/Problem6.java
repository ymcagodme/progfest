/*
StringBuilder documentation: http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
This program deciphers ciphertexts through a brute-force method
It will be easier to demonstrate the algorithm using concrete i, j, s, t, n.
Suppose the ciphertext is: APPURAAURGEGEWE
If we know the ciphertext AND i, j, s, t, n then we can decode it. 
Suppose s = 1, i = 6, t = 0, j = 8, n = 6.
The algorithm starts at s, concatenates the char at s to firstWord = "", replaces the char at s with  a dash "-",
skips i non-dash characters and concatenates the (i+t1)th non-dash character, then replaces this character with a dash "-".

A P P U R A A U R G E  G  E  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14

Start at s = 1, position = 1
A - P U R A A U R G E  G  E  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
firstWord = "P"

Skip i = 6 non-dash characters after position = 1, grabbing the 7th at position = 8
A - P U R A A U - G E  G  E  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
firstWord = "PR"

Skip i = 6 non-dash characters after position = 8, grabbing the 7th at position = 0
- - P U R A A U - G E  G  E  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
firstWord = "PRA"

Skip i = 6 non-dash characters after position = 0, grabbing the 7th at position = 9
- - P U R A A U - - E  G  E  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
firstWord = "PRAG"

Skip i = 6 non-dash characters after position = 9, grabbing the 7th at position = 3
- - P - R A A U - - E  G  E  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
firstWord = "PRAGU"

Skip i = 6 non-dash characters after position = 3, grabbing the 7th at position = 12
- - P - R A A U - - E  G  -  W  E
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
firstWord = "PRAGUE"

We grabbed a total of n = 6 characters to construct firstWord = "PRAGUE".
Next keep all the dashes "-" in place and start at the first non-dash character after/including t = 0.
We'd skip over j = 8 non-dash characters. , at each step concatenating to secondWord.

If firstWord.equals(secondWord), then it is a possible codeword that we have deciphered.
*/

import java.util.*;


public class Problem6 
{
	public static ArrayList<String> ciphertexts = new ArrayList<String>();
	public static ArrayList<String> decipheredTexts = new ArrayList<String>();
	
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		String input;
		System.out.println("Enter ciphertexts:");
		do
		{
			input = keyboard.next();
			ciphertexts.add(input);
		}while(!input.equals("X"));
		for(int i = 0; i < ciphertexts.size() - 1; i++)
		{
			int codeNumber = i + 1;
			System.out.print("Code " + codeNumber + ": ");
			decipher(ciphertexts.get(i));
			results();
			System.out.println();
		}
	}
	
	
	
	
	//This method checks for every possible value of n, s, t, i, j
	public static void decipher(String ciphertext)
	{
		int m = ciphertext.length();
		decipheredTexts.clear();
		decipheredTexts.add("");
		for(int n = 1; n <= m/2; n++)
		{
			for(int j = 1; j < m; j++)
			{
				for(int i = 0; i < j; i++)
				{
					for(int t = 0; t < m; t++)
					{
						for(int s = 0; s < m; s++)
						{
							
							
							//Grabs first letter of firstWord from s, replacing the character at s with a dash "-"
							StringBuilder text = new StringBuilder(ciphertext);
							String firstWord = "";
							String secondWord = "";
							firstWord = firstWord + text.charAt(s);
							text.replace(s, s+1, "-");
							int position = s;
							
							//Outer for-loop concatenates n-1 characters (since we've already concatenated 1 character)
							for(int nCounter = 1; nCounter < n; nCounter++)
							{
								//Inner for-loop i non-dash characters
								for(int iCounter = 0; iCounter <= i; iCounter++)
								{
									//Since we ignore dashes, we use nextIndex to return the index of the next non-dash character
									if(text.substring(position, position + 1).equals("-"))
										position = nextIndex(position, text.toString());
									if(iCounter < i)
										position = (position+1)%m;
								}
								firstWord = firstWord + text.charAt(position);
								text.replace(position, position+1, "-");
							}
							
							//Finds the first non-dash character after (including) t
							if(text.substring(t, t+1).equals("-"))
							{
								position = nextIndex(t, text.toString());
							}
							else
								position = t;
							//Concatenates the non-dash character and changes it into a dash "-"
							secondWord = secondWord + text.charAt(position);
							text.replace(position, position+1, "-");
							
							//Outer for-loop concatenates n-1 characters (since we've already concatenated 1 character)
							for(int nCounter = 1; nCounter < n; nCounter++)
							{
								//Innter for-loop i non-dash characters
								for(int jCounter = 0; jCounter <= j; jCounter++)
								{
									//Since we ignore dashes, we use nextIndex to return the index of the next non-dash character
									if(text.substring(position, position + 1).equals("-"))
										position = nextIndex(position, text.toString());
									if(jCounter < j)
										position = (position+1)%m;
								}
								secondWord = secondWord + text.charAt(position);
								text.replace(position, position+1, "-");
							}
							
							//Adds the firstWord to decipheredTexts if firstWord.equals(secondWord)
							//Clears list if the length is longer than the length of the first index of decipheredTexts
							if(firstWord.equals(secondWord) && !decipheredTexts.contains(firstWord) && decipheredTexts.get(0).length() < firstWord.length())
							{
								decipheredTexts.clear();
								decipheredTexts.add(firstWord);
							}
							else if (firstWord.equals(secondWord) && !decipheredTexts.contains(firstWord) && decipheredTexts.get(0).length() == firstWord.length())
							{
								decipheredTexts.add(firstWord);
							}
							
							
						}
					}
				}
			}
		}
	}
	
	//Accepts a starting location and a String
	//Returns the index of the next non-dash character starting from location
	//Uses the modulus operator to ensure index is always within bounds
	public static int nextIndex(int location, String word)
	{
		StringBuilder text = new StringBuilder(word);
		int wordLength = word.length();
		int index = location%wordLength;
		while(text.substring(index, index + 1).equals("-"))
		{
			location++;
			index = location%wordLength;
		}
		return index;
	}
	
	//If there is a unique largest codeword, that would mean that size == 1
	//If size == 1, codeword is displayed
	//else if size > 1, that means there is more than one "largest" codeword, hence not unique
	public static void results()
	{
		if(decipheredTexts.size() == 1)
			System.out.print(decipheredTexts.get(0));
		else if (decipheredTexts.size() > 1)
			System.out.print("Codeword not unique");
	}
	
}
