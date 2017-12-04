package myclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class testTime {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

		// visit a page
		driver.manage().window().maximize();
		driver.get("http://google.co.in");
		double time = getEventResponseTime(driver);
		System.out.println("webpage load time "+time + " seconds\n");
		
		driver.findElement(By.name("q")).sendKeys("Hello");
		
		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
		time = getEventResponseTime(driver);
		
		driver.quit();
		System.out.println("search load time "+time + " seconds");	
	}
	
	public static double getEventResponseTime(WebDriver driver)
	{
		Long eventStartTIme = (Long)((JavascriptExecutor)driver).executeScript(
			    "return performance.timing.navigationStart;");
		
		Long eventEndTIme = (Long)((JavascriptExecutor)driver).executeScript(
			    "return performance.timing.loadEventEnd");
		
		System.out.println("EventStartTime : " + eventStartTIme + " " + "EventEndTime : " + eventEndTIme);
		
		Long loadtime = eventEndTIme - eventStartTIme ;
		
		return loadtime.doubleValue()/1000;
	}

}
