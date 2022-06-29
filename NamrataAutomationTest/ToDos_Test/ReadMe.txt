Author : Namrata Markale
Automation Tool used: Selenium
Programming language: Core-Java
IDE used for coding: Eclipse


I have first designed 13 manual test cases including negative and positive flows (path: \ToDos_Test\ManualTestCases\TestCases_TODOlist_NamrataMarkale.xlsx) and then automated the same flows in selenium-java.
Due to lack of time, I have not designed a framework or a POM designed pattern.
Code consist of Test data and webelements in the same class.
 (Please allow me sometime to separate test data and webelements from a script and designed a hybrid framework which would take one more day if its a part of assessment).

I have created Maven project in java along with TestNG library.
All test cases will run in sequence as per provided test-Priority.

To run complete code follow below steps -
1. Import the project in Eclipse as a Maven Project.
2. POM.xml contains all required dependencies so just click on Save.
3. Right click on testng.xml file (\ToDos_Test\testng.xml) 
4. Run as >> TestNG Suite
6. It will run all test test cases.

Test cases written in class : \ToDos_Test\src\test\java\testSuite\
 
Execution: 
Total test cases -11
Tase cases passed - 8
Fail test cases - 2
 
Following test cases will fail in executions  -
TC11- Verify Duplicate To-Do items should not get added.
TC12- Verify Single special char should not get added.
These two test cases marked failed deliberately.

Please email or call me in case more details required.
Email: Namratamarkale1995@gmail.com
Contact no: 0685257553

Below Selenium/TestNG features used while designing automation scripts -
1. Java Inheritance (public class ToDosTest extends TestBase)
2. TestNG annotations: @BeforeClass, @AfterClass, @Test
3. TestNG: Test Priority
4. TestNG: dependsOnMethods  
5. TestNG: Assertions
6. Soft Assert
7. Selenium: Xpath locators
8. Selenium: findElements(List<WebElement>)
9. Java collections: ArrayList
10: Selenium: Click, getText, getAttribute, xpath axes etc.  