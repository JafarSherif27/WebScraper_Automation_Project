# 🕸️ WebScraper_Automation_Project

## 📘 Project Description

**Web Scraper** is a UI automation project built using **Gradle**, designed to extract data from dynamic web tables and store it in structured JSON format.  
The project focuses on two types of datasets:

1. **Hockey Teams** – Extracted from paginated, form-based tables.
2. **Oscar Winning Films** – Fetched from a dynamic, AJAX-powered webpage.

UI automation techniques are used to simulate real user interactions for data collection, and all scraped data is persisted as JSON files for further analysis or reference.

---

## 🔗 Important Links

- 🏒 Hockey Team Table: [https://www.scrapethissite.com/pages/forms/](https://www.scrapethissite.com/pages/forms/)  
- 🎬 Oscar Winning Films: [https://www.scrapethissite.com/pages/ajax-javascript](https://www.scrapethissite.com/pages/ajax-javascript)

---

## 🧪 Test Cases

### ✅ `testCase01` – Hockey Team Data Scraper

**Steps:**

1. Navigate to the homepage and click on **"Hockey Teams: Forms, Searching and Pagination"**.
2. Iterate over the **first 4 pages** and fetch details for each team:  
   - **Team Name**
   - **Year**
   - **Win %**
3. For teams with **Win% < 40% (0.40)**:
   - Store the data in `ArrayList<HashMap<String, Object>>`
   - Each HashMap should include:
     - Epoch Time of Scrape
     - Team Name
     - Year
     - Win %
4. Convert this list into a JSON file named **`hockey-team-data.json`**.

---

### ✅ `testCase02` – Oscar Winning Films Scraper

**Steps:**

1. Navigate to the homepage and click on **"Oscar Winning Films"**.
2. Click on each year shown.
3. For each year:
   - Extract the **top 5 movies**
   - Set a boolean **`isWinner`** to `true` only for the **Best Picture winner**.
   - Maintain a variable to track the **year**.
4. Store the movie data in `ArrayList<HashMap<String, Object>>` with fields:
   - Epoch Time of Scrape
   - Year
   - Title
   - Nomination
   - Awards
   - isWinner
5. Convert this list into a JSON file named **`oscar-winner-data.json`**.
6. Store this JSON file in the `output/` folder at the **project root**.
7. Use **TestNG** to assert:
   - JSON file is **present**
   - JSON file is **not empty**

---

## 🧰 Technologies & Tools Used

- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Gradle**
- **JSON**
- **Web Automation**
- **XPath & CSS Selectors**

---

## 📂 Directory Structure

