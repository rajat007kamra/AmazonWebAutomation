# 🧪 Amazon Web Automation Framework

This is a robust **Test Automation Framework** built using **Java**, **Selenium WebDriver**, **TestNG**, and follows the **Page Object Model (POM)** design pattern. It supports **parallel execution**, **custom HTML reporting with ExtentReports**, **automatic screenshot capture**, **retry logic**, and is designed for **CI/CD integration** using **Jenkins**.

---

## 📁 Project Structure

AmazonWebAutomation/
├── src/
│ ├── main/
│ │ └── java/com/amazon/webautomation/
│ │ ├── base/ # BaseTest and reusable setup
│ │ ├── config/ # ConfigReader (fetch properties)
│ │ ├── driver/ # WebDriver manager (DriverFactory)
│ │ ├── pages/ # Page classes using POM
│ │ ├── reports/ # ExtentReport setup & managers
│ │ ├── utils/ # LogUtil, ScreenshotUtil, etc.
│ │ └── listeners/ # TestNG Listeners (Retry, Extent, Logging)
│
│ └── test/
│ └── java/com/amazon/webautomation/tests/
│ ├── HomePageTest.java
│ ├── CartTest.java
│ └── ProductDetailPageTest.java
│
├── testng.xml # Master TestNG suite
├── parallelTestng.xml # Parallel execution suite
├── JenkinsFile.groovy # Jenkins CI/CD pipeline
├── extent-config.xml # Extent report theming
├── README.md # Project Documentation
└── pom.xml # Maven build config

---

## 🚀 Key Features

| Feature                     | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| ✅ POM Design Pattern       | Logical separation of test & page logic                                     |
| ✅ Selenium WebDriver       | Browser automation using ChromeDriver                                       |
| ✅ TestNG Framework         | Test lifecycle, annotations, parallel execution                             |
| ✅ ExtentReports            | Visual HTML reports with screenshots & step-wise logging                    |
| ✅ RetryAnalyzer            | Automatically retries failed tests                                          |
| ✅ Screenshot Capture       | On every failure and optional step logging                                  |
| ✅ Parallel Test Execution  | Run tests concurrently via `parallelTestng.xml`                             |
| ✅ Jenkins Integration      | CI/CD ready using `JenkinsFile.groovy`                                      |
| ✅ Thread-safe Utilities    | ThreadLocal usage for drivers, logs, and reports                            |
| ✅ Configurable Properties  | Load environment/config from `.properties`                                 |

---

## 🛠️ Tech Stack

- **Java 11+**
- **Selenium WebDriver**
- **TestNG**
- **ExtentReports 5.x**
- **Maven**
- **Jenkins**
- **Git / GitHub**

---

## 🔧 Prerequisites

- Java JDK 11+
- Maven
- Chrome / ChromeDriver
- IDE (IntelliJ / Eclipse)
- Git

---

## 🏗️ How to Run

### 1. Clone the repo:

```bash
git clone https://github.com/your-username/AmazonWebAutomationLatest.git
cd AmazonWebAutomationLatest

2. Install dependencies:
mvn clean install

3. Run tests via Maven:
mvn test -DsuiteXmlFile=testng.xml
or for parallel execution:
mvn test -DsuiteXmlFile=parallelTestng.xml

📸 Reports & Logs:

After execution, find:
📁 test-output/ExtentReports/
index.html – Full clickable report
/screenshots/ – Screenshot images

📁 logs/ (if added later)

✅ Sample Console Output

Test Case 1 :: Executing HomePageTest -> verifyHomePageElements
Test Case 2 :: Executing CartTest -> verifyCartFunctionality
Test Case 3 :: Executing ProductDetailPageTest -> verifyProductDetails

🧪 Jenkins Integration
Uses JenkinsFile.groovy

You can configure your pipeline to:
1. Check out the repo
2. Install dependencies
3. Run test suite
4. Archive reports and screenshots
5.✏️ Customize ExtentReports
6. Change extent-config.xml to update:
7 .Theme (dark/light)
8 . Report title
9. Timestamp format
10. Chart visibility

🧠 Author
👨‍💻 Rajat Kamra
