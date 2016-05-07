package BabyNames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kevin on 4/27/2016.
 * <p>
 * This class provides a tool for searching text files named babynames and nametypes
 * for a specific name a user might be interested in and identifies what gender and
 * the number of times it appears in the list.
 */

public class BabyNameFinder
{
    final static int NUMBER_OF_LINES_BABY_NAME = 16754;
    final static int NUMBER_OF_LINES_BABY_TYPE = 200;
    final static int SPLIT_NUMBER = 2;

    private static Scanner console = new Scanner(System.in);
    private static int usrInput = 0;
    private static String babyName = null;
    private static String[] babyNameTxtArray = new String[NUMBER_OF_LINES_BABY_NAME];
    private static String[] babyTypeTxtArray = new String[NUMBER_OF_LINES_BABY_TYPE * SPLIT_NUMBER]; //note: array is split into 2 values
    private static String[][] babyTypeWrapperArray = new String[NUMBER_OF_LINES_BABY_TYPE][SPLIT_NUMBER];

    /**
     * @param args Accepts strings as arguments.
     * @author Kevin Smith
     * @version 1.0
     */
    public static void main(String[] args)
    {
        readBabyNameFile();
        readNameTypesFile();
        runProgram();


    }

    private static void runProgram()
    {
        welcomeMssg();
        optionMssg();
        readInput();
        continueCheck();
    }

    private static void readInput()
    {
        try
        {
            usrInput = console.nextInt();

            if (usrInput == 1)
            {
                usrInputBabyName();
                printUsrSearch();
                compareArrays();
            } else if (usrInput == 2)
            {
                displaySearchResults();
            } else if (usrInput == 3)
            {
                //delete user search history
                zeroOutUsrSearch();

                //exist message
                System.out.println("Thank you for visiting, I hope this tool helped you to decide");
                System.out.println("on a good baby name, for the new addition to the family!!!");

                //close console
                System.exit(0);
            }
        } catch (InputMismatchException e)
        {
            System.out.println("System encountered a input missmatch exception, would you like to try again?");
            console.next();
            continueCheck();
        } catch (RuntimeException e)
        {
            System.out.println("System encountered a run time error, the program will restart.");
            console.next();
            continueCheck();
        }
    }

    private static void welcomeMssg()
    {
        System.out.println("What is your favorite baby name?");
        System.out.println("The follwing program prints statistics for the top");
        System.out.println("100 popular boy and gril baby names in Oregon during 2013");
        System.out.println();
    }

    private static void optionMssg()
    {
        System.out.println("Enter option 1, 2, or 3");
        System.out.println("1. enter a baby name!");
        System.out.println("2. view your search history!");
        System.out.println("3. exit this program!");
    }

    private static void usrInputBabyName()
    {
        System.out.println("Please enter baby name:");
        try
        {
            //accept user input
            babyName = console.next().toUpperCase();
        } catch (InputMismatchException e)
        {
            System.out.println("Error: User input did not match expected format: " + e.getMessage());
            System.out.println("Please try again: ");
            usrInputBabyName();
        } catch (RuntimeException e)
        {
            System.out.println("Something went wrong please read error message: " + e.getMessage());
            System.out.println("Please try again: ");
            usrInputBabyName();
        }
    }

    private static void displaySearchResults()
    {
        //source file name
        String sourceFile = "usrsearch.txt";

        //
        Scanner usrSearchBabyName = null;

        try
        {
            usrSearchBabyName = new Scanner(new FileInputStream(sourceFile));

            System.out.println("You have recently searched for these baby names:");

            while (usrSearchBabyName.hasNextLine())
            {
                String searchedBabyName = usrSearchBabyName.nextLine();
                System.out.println(searchedBabyName);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Error reading file: " + e.getMessage());
        } finally
        {
            if (usrSearchBabyName != null)
            {
                usrSearchBabyName.close();
            }
        }
    }

    private static void readBabyNameFile()
    {
        //source file name
        String sourceFile = "babynames.txt";

        //initialize scanner
        Scanner babyNameFile = null;

        //initialize while loop counter
        int counter = 0;

        try
        {
            babyNameFile = new Scanner(new FileInputStream(sourceFile));

            while (babyNameFile.hasNextLine())
            {
                //grab line from file and
                String line = babyNameFile.nextLine();

                //insert line into array
                babyNameTxtArray[counter] = line;

                //count number of times while loop, loops
                counter++;
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("The source file " + sourceFile + " has changed, beyond recognition.");
            System.out.println("Error reading from file: " + e.getMessage());
            System.exit(0);
        } catch (NullPointerException e)
        {
            System.out.println("The source file " + sourceFile + " has changed, beyond recognition.");
            System.out.println("Null Pointer Exception occurred: " + e.getMessage());
            System.exit(0);
        } catch (RuntimeException e)
        {
            System.out.println("The source file " + sourceFile + " has changed, beyond recognition.");
            System.out.println("Run time exception occurred: " + e.getMessage());
            System.exit(0);
        } finally
        {
            if (babyNameFile != null)
            {
                babyNameFile.close();
            }
        }
    }

    private static void readNameTypesFile()
    {
        //initialize scanner
        Scanner nameTypeFile = null;

        //initialize while loop counter
        int counter = 0;

        try

        {
            nameTypeFile = new Scanner(new FileInputStream("nametypes.txt"));

            while (nameTypeFile.hasNextLine())
            {
                //grab line from file and
                String line = nameTypeFile.nextLine();

                //split string
                babyTypeTxtArray = line.split(" - ");

                //create multidimensional array
                babyTypeWrapperArray[counter] = babyTypeTxtArray;

                //count number of times while loop, loops
                counter++;
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Error reading from file: " + e.getMessage());
            System.exit(0);
        } finally
        {
            if (nameTypeFile != null)
            {
                nameTypeFile.close();
            }
        }
    }

    private static void compareArrays()
    {
        //initialize name count
        int nameCount = 0;

        //name does not exist variable
        boolean doesNotExist = true;

        for (int i = 0; i < babyNameTxtArray.length; i++)
        {
            if (babyName.equals(babyNameTxtArray[i]))
            {
                nameCount++;
            }
        }
        for (int i = 0; i < babyTypeWrapperArray.length; i++)
        {
            if (babyName.equals(babyTypeWrapperArray[i][0]))
            {
                doesNotExist = false;

                System.out.println(babyName + " is a " + babyTypeWrapperArray[i][1] +
                        " name and appears " + nameCount + " times in babynames.txt");
            }
        }
        if (doesNotExist)
        {
            System.out.println("The name you entered in not listed in babynames.txt, sorry.");
        }
    }

    private static void printUsrSearch()
    {
        //local user search variable
        PrintWriter usrSearch = null;

        try
        {
            //instantiate print writer
            usrSearch = new PrintWriter(new FileOutputStream("usrsearch.txt", true));

            //print to file
            usrSearch.println(babyName);
        } catch (FileNotFoundException e)
        {
            System.out.println("Problem writing to a file: " + e.getMessage());
        } finally
        {
            if (usrSearch != null)
            {
                usrSearch.close();
            }
        }
    }

    private static void continueCheck()
    {
        System.out.println("Would you like to check another name?");
        System.out.println("Type \"yes\" to continue");
        String usrAnswer = console.next().toLowerCase();

        if (usrAnswer.equals("yes"))
        {
            usrInput = 0;
            babyName = null;
            runProgram();
        } else
        {
            //delete user search history
            zeroOutUsrSearch();

            //exist message
            System.out.println("The input you entered was not \"yes\", the program will be closed.");
            System.out.println("Thank you for visiting, I hope this tool helped you to decide");
            System.out.println("on a good baby name, for the new addition to the family!!!");

            //close program
            System.exit(0);
        }
    }

    private static void zeroOutUsrSearch()
    {
        //local zero out search history
        PrintWriter zeroSearch = null;

        try
        {
            //instantiate print writer
            zeroSearch = new PrintWriter(new FileOutputStream("usrsearch.txt"));

            //print to nothing to file
            zeroSearch.print("");
        } catch (FileNotFoundException e)
        {
            System.out.println("Problem writing to a file: " + e.getMessage());
        } finally
        {
            if (zeroSearch != null)
            {
                zeroSearch.close();
            }
        }
    }
}
