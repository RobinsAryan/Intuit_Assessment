````markdown
# Java Technical Assessment - Assignments 1 & 2

This repository contains the solutions for two Java technical assignments demonstrating proficiency in **Multithreading** and **Functional Programming**.

1.  Assignment 1: Producer-Consumer Pattern (Thread Synchronization)
2.  Assignment 2: Sales Data Analysis (Java Streams & Lambdas)

---

📂 Project Structure

src/
 └── com/
      └── assignment/
           ├── assignment1/               # Assignment 1: Threading Logic
           │    ├── Main.java             # Visual Demo (Producer-Consumer)
           │    ├── ProducerConsumerTest.java # JUnit 5 Test Suite
           │    ├── CustomBlockingBuffer.java # Monitor Object (Wait/Notify)
           │    └── [Worker Classes...]
           └── assignment2/               # Assignment 2: Sales Analysis
                ├── Main.java             # Visual Demo (Sales Analysis)
                ├── SalesAnalysisTest.java    # JUnit 5 Test Suite
                ├── SalesAnalysisService.java # Stream Logic
                └── CsvLoader.java        # File Parser
sales.csv                                 # Data file (Located in Project Root)
````

-----

## 🛠️ Setup Instructions

### 1\. Clone the Repository

```bash
git clone https://github.com/RobinsAryan/Intuit_Assessment.git
```

### 2\. Open in IntelliJ IDEA

* Go to **File \> Open** and select the project folder.

### 3\. Verify Data File

* Ensure `sales.csv` is located in the **project root folder** (the same level as `src`). If it is missing or inside `src`, Assignment 2 will not be able to load the data.

### 4\. Dependencies

* The project uses **JUnit 5.8.1** (or higher) for testing. Ensure this library is added to your classpath (IntelliJ usually detects this automatically).

-----

## 📝 Assignment 1: Producer-Consumer Pattern

**Objective:** Implement a thread-safe data transfer system using the Monitor Object pattern (`synchronized`, `wait`, `notifyAll`).

### Key Features

* **Thread Safety:** Implemented a `CustomBlockingBuffer` that handles concurrent access without race conditions.
* **Blocking Behavior:**
    * Producers block (wait) when the buffer is full.
    * Consumers block (wait) when the buffer is empty.
* **Testing:** JUnit tests verify data integrity and blocking states under different capacities (1, 3, 50).

### Sample Output (Console Demo)

*Run `src/com/assignment/assignment1/Main.java`*

```text
--- Assignment 1: Producer-Consumer Pattern ---
PRODUCER: Added 1 | Size: 1
PRODUCER: Added 2 | Size: 2
CONSUMER: Removed 1 | Size: 1
PRODUCER: Added 3 | Size: 2
PRODUCER: Added 4 | Size: 3
CONSUMER: Removed 2 | Size: 2
PRODUCER: Added 5 | Size: 3
PRODUCER: Buffer FULL. Waiting...
CONSUMER: Removed 3 | Size: 2
PRODUCER: Added 6 | Size: 3
PRODUCER: Buffer FULL. Waiting...
CONSUMER: Removed 4 | Size: 2
PRODUCER: Added 7 | Size: 3
PRODUCER: Buffer FULL. Waiting...
CONSUMER: Removed 5 | Size: 2
PRODUCER: Added 8 | Size: 3
PRODUCER: Buffer FULL. Waiting...
CONSUMER: Removed 6 | Size: 2
PRODUCER: Added 9 | Size: 3
PRODUCER: Buffer FULL. Waiting...
CONSUMER: Removed 7 | Size: 2
PRODUCER: Added 10 | Size: 3
Producer: Job Finished.
CONSUMER: Removed 8 | Size: 2
CONSUMER: Removed 9 | Size: 1
CONSUMER: Removed 10 | Size: 0
Consumer: Job Finished.
--- Complete ---
SUCCESS: Integrity Verified. Thread synchronization worked.
```

-----

## 📊 Assignment 2: Sales Data Analysis

**Objective:** Perform data analysis on CSV records using **Java Streams**, **Lambda Expressions**, and **Functional Aggregation**.

### Features Implemented

* **Functional Programming:** Used Stream API for all logic (no loops).
* **Aggregation:** Calculated Total Revenue (Sum) and Average Sales.
* **Grouping:** Grouped transactions by 'Category' using `Collectors.groupingBy`.
* **Filtering:** Filtered data dynamically by Region.

### Sample Output (Analysis Results)

*Run `src/com/assignment/assignment2/Main.java`*

```text
--- Assignment 2: Sales Data Analysis ---
Loaded 15 records.

--- [Aggregation] Revenue & Average ---
Total Revenue: $3785.50
Average Sale:  $252.37

--- [Stream Max] Highest Value Sale ---
Sale{id=1, product='Laptop', category='Electronics', region='North', amount=1200.00}

--- [Filtering] Sales in North Region ---
Sale{id=1, product='Laptop', category='Electronics', region='North', amount=1200.00}
Sale{id=3, product='T-Shirt', category='Clothing', region='North', amount=15.00}
Sale{id=7, product='Toaster', category='Home', region='North', amount=30.00}
Sale{id=11, product='Monitor', category='Electronics', region='North', amount=300.00}
Sale{id=15, product='Mixer', category='Home', region='North', amount=60.00}

--- [Grouping] Sales by Category ---
Category: Clothing (4 items)
  -> Example: T-Shirt
Category: Electronics (6 items)
  -> Example: Laptop
Category: Home (5 items)
  -> Example: Blender
```

-----

## ✅ Unit Testing (JUnit 5)

This project relies on **JUnit 5** for verifying grading requirements.

### ⚠️ Important Dependency Note

Assignment 1 uses `@ParameterizedTest`. If you see **Red Marks** on this annotation in IntelliJ:

**Method 1: The Shortcut**

1.  Click on `@ParameterizedTest` in the code.
2.  Press **Alt + Enter** (Windows) or **Option + Enter** (Mac).
3.  Select **"Add 'org.junit.jupiter:junit-jupiter-params' to classpath"**.

**Method 2: Manual Setup (If shortcut fails)**

1.  Go to **File \> Project Structure \> Modules**.
2.  Select the **Dependencies** tab.
3.  Click the **+ (Plus)** icon and select **"Library..." \> "From Maven..."**.
4.  Paste this string into the search bar:
    `org.junit.jupiter:junit-jupiter-params:5.9.2`
5.  Click **OK** and ensure "Transitive dependencies" is checked.
6.  Click **Apply**.

### Test Suites

**1. Assignment 1 Tests (`ProducerConsumerTest`):**

* `testThreadSynchronization`: Verifies data integrity by moving large datasets without corruption.
* `testConcurrentProgramming`: Verifies that Producer and Consumer threads run simultaneously without deadlocking.
* `testBlockingQueueProducer`: Mathematically proves the Producer enters the `WAITING` state when the buffer is full.
* `testWaitNotifyConsumer`: Mathematically proves the Consumer enters the `WAITING` state when the buffer is empty.

**2. Assignment 2 Tests (`SalesAnalysisTest`):**

* `testFunctionalProgramming`: Verifies functional transformation (List to Grouped Map) without manual iteration.
* `testStreamOperations`: Verifies complex stream chains (e.g., finding Max value with Comparators).
* `testDataAggregation`: Verifies `Sum` and `Average` reductions match the data in `sales.csv`.
* `testLambdaExpressions`: Verifies filtering logic using explicit Lambda expressions.

**Test Results:** All 8 tests pass with 100% success rate.

```
```