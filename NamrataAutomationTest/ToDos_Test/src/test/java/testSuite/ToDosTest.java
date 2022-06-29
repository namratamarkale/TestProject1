package testSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import base.TestBase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ToDosTest extends TestBase{	

	SoftAssert s_Assert;
				
	@BeforeClass   // Launch TODO web app in chrome browser
	public void launchApp() {
		String Url = "https://todomvc.com/examples/angular2/";
		setBrowser("Chrome");
		launch(Url);				
	}
	
	@AfterClass //Close Browser
    public void terminateBrowser(){
        driver.close();
    }
	
  // TC01 Verify UI for to-Do web app.
   @Test(priority=1)
  public void verifyUI_Header_Label()   {
      System.out.println("Running test case TC01: Verify UI for to-Do web app, check label and header");
      String header =wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//header[@class='header']//child::h1[1]")))).getText();
      System.out.println("Actual header in app is "+ header);
      s_Assert = new SoftAssert();
      s_Assert.assertEquals(header, "todos"); // Check web-app header
      String label = driver.findElement(By.xpath("//header[@class='header']//child::input[1]")).getAttribute("placeholder");
      System.out.println("Actual label of to-do test field is -"+ label);
      s_Assert.assertEquals(label, "What needs to be done?"); // check web-app input text field label
      s_Assert.assertAll();
    
  }
   
   
   // TC02 Verify that to-do item should get added in the list.
   @Test(priority=2)
  public void Verify_todo_item_should_get_added_in_the_list() throws InterruptedException   {
      System.out.println("Running test case TC02: Verify that to-do item should get added in the list.");
      WebElement inputToDo = driver.findElement(By.xpath("//header[@class='header']/input"));
      inputToDo.sendKeys("Meeting01"); // Enter Meeting01 as a to-do task and press enter.
      inputToDo.sendKeys(Keys.ENTER);
      Thread.sleep(1000);
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li")); // fetch all TO-Dos from task list
      System.out.println(ToDos_List.get(0).findElement(By.tagName("label")).getText());
      Assert.assertEquals(ToDos_List.get(0).getText(), "Meeting01"); // Check added Meeting01 is present in task list.          
             
  }
  
   // TC03 Verify functionality of Delete To-Do item.
   @Test(priority=3, dependsOnMethods = "Verify_todo_item_should_get_added_in_the_list")
  public void verify_functionality_of_Delete_ToDo_item() throws InterruptedException   {
	   System.out.println("Running test case TC03: Verify functionality of Delete To-Do item.");
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      System.out.println(ToDos_List.size());
      Thread.sleep(2000);
      ToDos_List.get(0).click();
      ToDos_List.get(0).findElement(By.tagName("button")).click(); //Delete first to-do task "Meeting01" from the list      
      List<WebElement> ToDos_List_AfterDelete=driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      System.out.println(ToDos_List_AfterDelete.size());       
      Assert.assertTrue(ToDos_List_AfterDelete.isEmpty(), "Assertion fail as there are still todo items in the list"); //Check no task present in the list after delete
  } 
   
   // TC04 Verify that user can add multiple to-do items in list.
   @Test(priority=4)
  public void verify_user_can_add_multiple_TODO_items_in_list() throws InterruptedException   {
      System.out.println("Running test case TC04: Verify that user can add multiple to-do items in list.");
      ArrayList<String> multipleTODOitems = new ArrayList<String>(Arrays.asList("Meeting01","Meeting02","Meeting03"));
      WebElement inputToDo = driver.findElement(By.xpath("//header[@class='header']/input"));
      for(String eachTODOitem:multipleTODOitems) {   // Add all three to-do tasks i.e. Meeting01, Meeting02 & Meeting03.
    	  inputToDo.sendKeys(eachTODOitem);
    	  inputToDo.sendKeys(Keys.ENTER);
          Thread.sleep(1000);
      }         
      ArrayList<String> ActualTODOitems = new ArrayList<String>();      
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));  //fetch all tasks from the list
      for(int i=0;i<ToDos_List.size();i++) {
    	  ActualTODOitems.add(ToDos_List.get(i).findElement(By.tagName("label")).getText()) ;
      }
      
      Assert.assertTrue(multipleTODOitems.equals(ActualTODOitems));  // check all three added tasks are present in the list.  
           
  }
   
   //TC05: Verify completed to-do items
   @Test(priority=5, dependsOnMethods = "verify_user_can_add_multiple_TODO_items_in_list")
  public void verify_completed_TODO_items() {
      System.out.println("Running test case TC05: Verify completed to-do items");
     
      driver.findElement(By.xpath("//ul[@class='todo-list']//label[text()='Meeting01']//preceding::input[1]")).click();  // click on checkbox tick for Meeting01.
      String TODOitemStatus = driver.findElement(By.xpath("//ul[@class='todo-list']//label[text()='Meeting01']//ancestor::li[1]")).getAttribute("class"); // fetch completed status of task from list
      System.out.println(TODOitemStatus);
      Assert.assertEquals(TODOitemStatus, "completed"); // check status of Meeting01 task is completed.     
           
  }
   
   
   //TC06, TC07 : Verify functionality of "Clear completed".
   @Test(priority=6, dependsOnMethods = "verify_completed_TODO_items")
  public void verify_functionality_of_Clear_Completed()   {
      System.out.println("TC06, TC07 : Verify functionality of \"Clear completed\".");          
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@class='clear-completed']")))).click(); // Click on Clear Complete link at footer
      
      ArrayList<String> ActualTODOitems = new ArrayList<String>();      
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li")); // fetch all tasks from the list which are still present after clear complete event.
      for(int i=0;i<ToDos_List.size();i++) {
    	  ActualTODOitems.add(ToDos_List.get(i).findElement(By.tagName("label")).getText()) ;
      }
      
      s_Assert.assertFalse(ActualTODOitems.contains("Meeting01")); // check Meeting01 is not present in list as its a completed task.
      s_Assert.assertTrue(ActualTODOitems.contains("Meeting02"));  // check Meeting02 must be present in list as its not a completed task.    
      s_Assert.assertAll();      
       
  }
   
   
   
   //TC08 : Verify functionality of "Items Left" count.
   @Test(priority=7, dependsOnMethods = "verify_functionality_of_Clear_Completed")
  public void verify_functionality_of_Items_Left_count() throws InterruptedException   {
      System.out.println("TC08 : Verify functionality of Items Left count.");          
      driver.findElement(By.xpath("//ul[@class='todo-list']//label[text()='Meeting02']//preceding::input[1]")).click(); // Complete task Meeting02
      Thread.sleep(1000);
      driver.findElement(By.xpath("//ul[@class='todo-list']//label[text()='Meeting03']//preceding::input[1]")).click(); //Complete task Meeting03
      Thread.sleep(1000);      
      Assert.assertEquals(driver.findElement(By.xpath("//span[@class='todo-count']//strong")).getText(), "0"); // Item left count should be 0 after completing all tasks from the list.
       
  }
  

   //TC09 : Verify REVERT functionality of completed to-do item
   @Test(priority=8, dependsOnMethods = "verify_functionality_of_Items_Left_count")
  public void verify_revert_functionality_of_completed_TODE_item() throws InterruptedException   {
      System.out.println("TC08 : Verify revert functionality of completed to-do item ");          
      driver.findElement(By.xpath("//ul[@class='todo-list']//label[text()='Meeting02']//preceding::input[1]")).click(); // Meeting02 task was already completed, click on it again
      Thread.sleep(1000);
      String TODOitemStatus = driver.findElement(By.xpath("//ul[@class='todo-list']//label[text()='Meeting02']//ancestor::li[1]")).getAttribute("class"); // check class value of Meeting02 is empty, means its non-completed now.
      Assert.assertTrue(TODOitemStatus.isEmpty());
   
  }
   
   
   //TC10 : Verify Blank or null to-do item should not get added.
   @Test(priority=9)
  public void verify_Blank_or_null_TODO_item_not_get_added() throws InterruptedException   {
      System.out.println("TC10 : Verify Blank or null to-do item should not get added. ");
      // First delete all existing todo tasks
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      System.out.println(ToDos_List.size());
      if(ToDos_List.size()>0) {
    	  for(int i=0;i<ToDos_List.size();i++) {
    		  ToDos_List.get(i).click();
    	      ToDos_List.get(i).findElement(By.tagName("button")).click();    
    	      Thread.sleep(1000);
    	  }
      }
     // All existing tasks in the list are now deleted. 
     
      WebElement inputToDo = driver.findElement(By.xpath("//header[@class='header']/input"));
      inputToDo.sendKeys("    "); //Enter only white spaces as a task.
      inputToDo.sendKeys(Keys.ENTER);
      Thread.sleep(1000);
            
      List<WebElement> ToDos_List_AfterBlankAdd = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      Assert.assertTrue(ToDos_List_AfterBlankAdd.isEmpty()); // check task with white spaces should not get added in the list.
   
     } 
   
   
   //TC11 : Verify Duplicate To-Do items should not get added.
   @Test(priority=10)
  public void verify_Duplicate_TODO_item_not_get_added() throws InterruptedException   {
      System.out.println("TC11 : Verify Duplicate To-Do items should not get added.");
      // First delete all existing todo tasks
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      System.out.println(ToDos_List.size());
      if(ToDos_List.size()>0) {
    	  for(int i=0;i<ToDos_List.size();i++) {
    		  ToDos_List.get(i).click();
    	      ToDos_List.get(i).findElement(By.tagName("button")).click();    
    	      Thread.sleep(1000);
    	  }
      }
     // All existing tasks in the list are now deleted. 
     //Add task and again add same task 
    
      WebElement inputToDo = driver.findElement(By.xpath("//header[@class='header']/input"));
      inputToDo.sendKeys("Meeting09"); //Add Meeting09 as a task
      inputToDo.sendKeys(Keys.ENTER);
      Thread.sleep(1000);
      inputToDo.sendKeys("Meeting09"); // Add again same Meeting09 task as a duplicate.
      inputToDo.sendKeys(Keys.ENTER);
      Thread.sleep(1000);
      //Verify here if there is any Warning message as per requirement e.g. "To-Do is already present in list".
            
      List<WebElement> ToDos_List_AfterBlankAdd = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      Assert.assertEquals(ToDos_List_AfterBlankAdd.size(), 1, "Test Case Fail : TODOES accepting duplicate tasks"); //check in list only single task should be present.   
     }  
   
   //TC12 : Verify Single special char should not get added.
   @Test(priority=11)
  public void verify_SingleSpecialChar_in_TODO_item_not_get_added() throws InterruptedException   {
      System.out.println("TC12 : Verify Single special char should not get added.");
      // First delete all existing todo tasks
      List<WebElement> ToDos_List = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      System.out.println(ToDos_List.size());
      if(ToDos_List.size()>0) {
    	  for(int i=0;i<ToDos_List.size();i++) {
    		  ToDos_List.get(i).click();
    	      ToDos_List.get(i).findElement(By.tagName("button")).click();    
    	      Thread.sleep(1000);
    	  }
      }
     // All existing tasks in the list are now deleted. 
      
     //Add task with Single special char    
      WebElement inputToDo = driver.findElement(By.xpath("//header[@class='header']/input"));
      inputToDo.sendKeys("$"); // Add task with only $ as a special char
      inputToDo.sendKeys(Keys.ENTER);
      Thread.sleep(1000);
            
      List<WebElement> ToDos_List_AfterBlankAdd = driver.findElements(By.xpath("//ul[@class='todo-list']//li"));
      Assert.assertEquals(ToDos_List_AfterBlankAdd.size(), 0, "Test Case Fail : TODOES accepting taks with special char only");  //check task should not get added in list with only special char. 
     }  
   
   
}
