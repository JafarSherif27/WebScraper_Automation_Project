# WebScraper_Automation_Project



    /*
     * testCase01:
     * Step 1: Go to homepage and click on "Hockey Teams: Forms, Searching and Pagination"
     * Step 2: From 1st 4 pages get the teams' details (Team Name, Year and win%)
     * for teams with win<40% (0.40) store it in ArrayList<HashMap<String, Object>>
     * Step 3: Convert the ArrayList object to a JSON file named hockey-team-data.json.
     * 
     * Requirements: Each Hashmap object should contain: Epoch Time of Scrape, Team Name, Year
     * and Win %
     * 
     */




    /*
     * testCase02:
     * Step 1: Go to homepage and click on "Oscar Winning Films"
     * Step 2: Click on each year present on the screen and find the top 5 movies on the
     * list and store them in an ArrayList
     * Step 3: Keep a Boolean variable "isWinner" which will be true only for the best
     * picture winner of that year.
     * Step 4: Keep a variable to maintain the year from which the data is scraped.
     * Step 5: Convert the ArrayList object to a JSON file named oscar-winner-data.json.
     * Step 6: Store the file in the output folder in the root directory.
     * Step 7: Assert using TestNG that the file is present and not empty.
     * 
     * Requirements: Each Hashmap object should contain: Epoch Time of Scrape, Year, Title,
     * Nomination, Awards and isWinner
     * 
     */
