## Seed code - Boilerplate for step 3 - Database Engine Assignment

### Problem Statement

In this Assignment, we will 

    1. Read the header.  
    2. Identify the Data type of each field 

**Read the header**
The CSV file contains a set of data, where the first line is the header, for example: in our ipl.csv the following are the header 
**id, season, city, date, team1, team2, toss_winner, toss_decision, result, dl_applied, winner, win_by_runs, win_by_wickets, player_of_match, venue, umpire1, umpire2, umpire3** 

**Identify the Datatype of each field**
We cannot identify the data type of the field just by reading the header. We need to read a row which contains actual data.

All the data in the CSV file are in string format. We need to convert the nonstring data into its respective data type, this is needed because whenever we perform some sort of conditional operations we will be in need of the respective data type. 

For example, the following is the single line data of the CSV file 
**1, 2008, Bangalore, 2008-04-18, Kolkata Knight Riders, Royal Challengers Bangalore, Royal Challengers Bangalore, field, normal, 0, Kolkata Knight Riders, 140, 0, BB McCullum, M Chinnaswamy Stadium, Asad Rauf, RE Koertzen,**

here you can easily find data which is not a string **(for example 2008, 2008-04-18, 140 etc.,).** The numeric data should be converted into an integer or double. 

However, for this assignment, we will not try to convert date. It will be addressed in the next assignment.

**Note : Once you have cloned boilerplate from the given gitlab URL, import the project into eclipse. 
Your project’s test cases might show compile time errors for methods, as you have not written the complete code.**

### Expected Output
Display each column header along with its data type
     
                Enter the file name:
                (Input from User) data/ipl.csv
                
                Output
                id:java.lang.Integer
                season:java.lang.Integer
                city:java.lang.String
                date:java.lang.String
                team1:java.lang.String
                team2:java.lang.String
                toss_winner:java.lang.String
                toss_decision:java.lang.String
                result:java.lang.String
                dl_applied:java.lang.Integer
                winner:java.lang.String
                win_by_runs:java.lang.Integer
                win_by_wickets:java.lang.Integer
                player_of_match:java.lang.String
                venue:java.lang.String
                umpire1:java.lang.String
                umpire2:java.lang.String
                umpire3:java.lang.String
