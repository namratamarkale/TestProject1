package base;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	

	public static WebDriver driver;
	public static WebDriverWait wait; //explicit wait
	
	public static void setBrowser(String browser){
		//Chrome Browser
		if(browser.equalsIgnoreCase("Chrome")){
		    WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			wait=new WebDriverWait(driver, 30);
			}
		
		//Firefox Browser
		if(browser.equalsIgnoreCase("Firefox")){
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			wait=new WebDriverWait(driver, 30);
		}
		//Edge Browser
		if(browser.equalsIgnoreCase("Edge")){
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		//IE Browser
		if (browser.equalsIgnoreCase("IE")) {

			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
			driver.manage().window().maximize();
			wait=new WebDriverWait(driver, 30);
			
		}
		
	}
	
	public static void launch(String URL){
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}


}
