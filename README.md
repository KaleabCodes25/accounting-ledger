# Accounting Ledger 
A java application that allows users to track personal or business transactions.

<!-- HOW TO USE THIS TEMPLATE
     Save it as README.md at the root of your project, next to pom.xml. Replace every comment like this one
     with your own text; comments do not show on GitHub, so what you leave untouched shows as an empty heading.
     Two sections are optional and say so: Class Diagram, and Data and configuration. When your project does not
     need one, delete the whole section, heading included. Every other section stays. -->

<!-- One sentence under the title: what the app is, for whom.
     Example: A console app that keeps a store's inventory in a csv file and lets a clerk search it. -->

## Description of the Project
The Accounting Ledger application allows users to keep track of their financial transactions.
They can do that by recording deposits and payments, view their transaction history and also,
organize by using different ledger options. All transactions are stored in a file called "transactions.csv",
allowing the records to be saved and loaded whenever needed.

<!-- Three to five sentences. What the application does, who uses it, what problem it solves.
     Write it for someone who has never seen the workbook: a recruiter, a classmate, you in six months. -->

## Features

<!-- One bullet per thing the app can do, in the order of your menu. Mark bonuses.
     For an API (Capstone 3), one line per endpoint instead: method, path, what it does.
     Be honest: list what works. If something is half done, say so under Future Work instead.
     Example:
     - Shows every product in the store
     - Searches products by name (bonus) -->

- Add deposits .
- Record payments(Expenses).
- Save transactions to the file "transactions.csv".
- Display All transactions, with newest being the first one.
- Display deposits.
- Display payments.
- Show month to date reports.
- Show previous month reports.
- Show year to date reports
- Show previous year reports.
- Search transactions by vendor.
- Use custom feature, to combine different search options.

## User Stories

<!-- The stories you worked from, one per bullet, in the format you learned in Workbook 3b:
     As a [type of user], I want [some goal] so that [some reason].
     Same text as the cards on your GitHub project board. -->
- As a user, I want to add a deposit so that I can record money received into my account.
- As a user, I want to record a payment so that I can track money spent from my account.
- As a user, I want to view all transactions so that I can review my complete financial history.
- As a user, I want to view only deposits so that I can review money received.
- As a user, I want to view only payments so that I can review money spent.
- As a user, I want to view transactions from the beginning of the current month through today so that I can review my monthly activity.
- As a user, I want to view transactions from the previous calendar month so that I can review past monthly activity.
- As a user, I want to view transactions from the beginning of the current year through today so that I can review my yearly activity.
- As a user, I want to view transactions from the previous calendar year so that I can review my previous year's activity.
- As a user, I want to search for transactions by vendor so that I can see all financial activity associated with a specific vendor.
- As a user, I want to search transactions using multiple criteria so that I can find specific financial records.



## Setup

### Prerequisites

- Java 17 (Amazon Corretto), the version used in class.
- IntelliJ IDEA Community Edition: <https://www.jetbrains.com/idea/download/>

### Running the application

1. Clone this repository, or download it as a zip and unzip it.
2. In IntelliJ: File > Open, pick the project folder (the one that holds `pom.xml`), press Open.
3. Wait for IntelliJ to finish indexing (progress bar at the bottom).
4. Open `src/main/java/com/pluralsight/YourMainClassName.java`. <!-- replace YourMainClassName with the class that has main -->
5. Right-click inside the file and choose Run 'YourMainClassName.main()'.

### Data and configuration

<!-- OPTIONAL. Anything the program needs that is not code: data files and where they must sit, files the
     program creates, a database script to run first, a settings file to edit (connection, port).
     If there is nothing, delete this subsection, heading included.
     Example:
     - `inventory.csv` sits at the project root, next to `pom.xml`. The program reads it at start.
     - `logs.txt` is created by the program at the project root on the first run. -->

- "transactions.csv" sits at the project root, next to `pom.xml`. The program reads it at start.

## Technologies Used

- Java 17
- IntelliJ IDEA
<!-- Add anything else you used: a library, Maven plugins, an external tool. For a console app this list is short. That is fine. -->

## Demo

<!-- Pictures of the app running: the console for a console app, Insomnia or Swagger for an API.
     Screenshots are enough; a GIF is a bonus.
     1. Create a folder named `images` at the project root, next to `pom.xml`.
     2. Take screenshots of the app in the IntelliJ console (the menu, one feature, one error message):
          Windows: Win + Shift + S, drag a rectangle, then paste into Paint and save as PNG, or open the
                   notification and save. Save into the `images` folder.
          Mac:     Shift + Cmd + 4, drag a rectangle. The file lands on the Desktop; move it into `images`.
     3. Name the files by what they show: `images/menu.png`, `images/search.png`.
     4. Reference each one below with a relative path. No spaces in file names.
     5. Commit the images with the rest of your work (Git > Commit shows them as unversioned files: tick them).
     Optional GIF: LICEcap (free, Windows and Mac, <https://www.cockos.com/licecap/>) records a region of the
     screen straight to a .gif. Keep it under 10 seconds and under 10 MB, save it into `images`. -->



Demo (images/Screen%20Recording.MENU%202026-11-06%20063915.gif)
Demo (images/Screen%20Recording.FEATURE%202026-11-06%20064009.gif)
Demo (images/Screen%20Recording.ERROR%202026-11-06%20064246.gif)

## A piece of code I am proud of

<!-- Paste one short block of your own code (5 to 15 lines) between the three backticks, then write two
     or three sentences: what it does and why you chose to write it that way. This is what you show on
     demo day and what you talk about in an interview. -->

```java
  try {
        System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
        dateTimeInput = scanner.nextLine();
        LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
  } catch (Exception e) {
        System.err.println("Enter the date in required format: " + e.getMessage());
        return;
        }

```
The try-catch block has been one of the most useful tools I've learned so far. It validates the user's date
and time input and catches parsing errors, preventing invalid input from breaking the application.
I chose this piece of code because it makes the application more reliable and provide users with a helpful error message.

## Future Work

<!-- What you would add or fix next, one bullet each. Unfinished bonuses belong here. -->

- Improve the user interface by making it more professional and appealing.
- Add more options regarding the custom search. 
- Customize the menu screens to be more organized.

## Resources

<!-- What helped you: the workbook, the week's hub pages, a tutorial, a documentation page. Real links. -->

- Java YearUp Fall 2026 hub: <https://raymaroun.github.io/yearup-java-fall2026/>
- ANSI Escape Codes.
- docs.Oracle.com https://docs.oracle.com/en/java/javase/17/docs/api/index.html
- textKool.com https://textkool.com/
## Author

<!-- Your name and, if you like, your GitHub profile link. For a team workshop, one line per person with what each one built. -->

- **Kaleab Tsegaye**

## Thanks

<!-- Optional. The people who helped: the instructor, a classmate who debugged with you, a mentor. -->

- Thank you to Raymond Maroun, also know as Potato Sensei, for providing valuable resources and guidance.