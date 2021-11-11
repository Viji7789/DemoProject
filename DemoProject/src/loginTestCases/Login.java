package loginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
//import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//import org.testng.asserts.Assertion;

public class Login {
//file location C:\\Users\\viji7\\Downloads\\Excel Files\\DataProviderExcel.xls
//we can even use Object[][]-which is the parent and includes all types of variables
	String [][] data=null;
	WebDriver driver;
		
	
	@DataProvider(name="loginData")
	public String[][] loginDetails() throws BiffException, IOException
	{
		data=getExcelData();
		return data;
	}
	
	public String[][] getExcelData() throws BiffException, IOException {
		FileInputStream excel=new FileInputStream("C:\\Users\\viji7\\Downloads\\Excel Files\\DataProviderExcel.xls");
	
	    Workbook workbook=Workbook.getWorkbook(excel);
	    
	    Sheet sheet=workbook.getSheet(0);
	    
	    int noofRows=sheet.getRows();
	    int noofColumns=sheet.getColumns();
	    
	    String testData[][]=new String[noofRows-1][noofColumns];
	    
	    for(int i=1;i<noofRows;i++) {
	    	for(int j=0;j<noofColumns;j++) {
	    		testData[i-1][j]=sheet.getCell(j, i).getContents();
	    	}
	    }
	    return testData;
	}
	@BeforeTest
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		driver=new ChromeDriver();
	}
	
	
	@Test(dataProvider = "loginData")
	public void loginWithAllCombinations(String username,String password)
	{
		
		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		WebElement uname=driver.findElement(By.id("txtUsername"));
		uname.sendKeys(username);
		
		WebElement pwd=driver.findElement(By.id("txtPassword"));
		pwd.sendKeys(password);
		
		WebElement loginButton=driver.findElement(By.id("btnLogin"));
		loginButton.click();
		
	}
	
	@AfterTest
	public void CloseBrowser() {
		driver.quit();
	}
}
