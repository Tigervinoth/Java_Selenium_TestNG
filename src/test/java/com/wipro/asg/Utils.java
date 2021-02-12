package com.wipro.asg;




import java.util.List;
import java.util.concurrent.TimeUnit;











import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Utils {


	static WebDriver driver;
	static int AUT_MAX_WAIT=60;
	static Logger logger=Logger.getLogger(Utils.class);

		public static WebDriver browser(String browser,String strURL )
		{
			
			boolean browserFound= true;

			logger.info("Execution Started");
			
			if(browser.toUpperCase().equals("CHROME")){
				
				//System.out.println(" setupTest :: proxy: " + proxy);
				//System.out.println(" setupTest :: driver: " + driver);
				
		    	//System.setProperty("webdriver.chrome.driver", "C:\\Users\\sreg\\Downloads\\chromedriver_win32_v81\\chromedriver.exe");
		    	
				System.setProperty("webdriver.chrome.driver", System.getProperty("driver"));

		             ChromeOptions options = new ChromeOptions();
				        options.addArguments("--headless");
				        options.addArguments("--no-sandbox");
				        options.addArguments("window-size=1920,1080");
				        options.addArguments("--start-maximized");
				        options.addArguments("--disable-gpu");
				        options.addArguments("--disable-extensions");
				        options.addArguments("--disable-dev-shm-usage");
				        options.addArguments("--ignore-certificate-errors");
 
		        
		        //Create a new ChromeDriver
		        driver = new ChromeDriver(options);
		      
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			}

			else if(browser.toUpperCase().equals("FIREFOX")){
				
				System.setProperty("webdriver.gecko.driver","src/test/resources/geckodriver.exe");
				driver=new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			}
								
			else{
				browserFound = false;
			}		
			
			
			if (browserFound) {
				driver.manage().window().maximize();
				driver.get(strURL);
				logger.info(browser+" Opened Successfully");
				return driver;
			}else{
				logger.debug(browser+" Not Opened. Investigate...");
				return null;
			}

	}
		

		public static void input(String UIName, String objTechName, String data){
			try{
				WebElement element=driver.findElement(By.xpath(objTechName));
				WebDriverWait wait = new WebDriverWait(driver, AUT_MAX_WAIT);
				wait.until(ExpectedConditions.visibilityOf(element));
				element.clear();
				element.sendKeys(data);
				
				logger.info(UIName);
			 }
			 catch(Exception e){
				logger.debug(UIName);
				System.out.println("<<< Couldn't Find " + UIName + " >>>");
			
			 	}
			}
		
		public static void click(String UIName, String objTechName){
			
			try{
				WebElement element=driver.findElement(By.xpath(objTechName));
				WebDriverWait wait = new WebDriverWait(driver, AUT_MAX_WAIT); 
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
		
				logger.info(UIName);
				
			}catch(Exception e){
				logger.debug(UIName);
				System.out.println("<<< Couldn't Find " + UIName + " >>>");
				
			
			}
		}
		
		public static List <WebElement>  elements(String UIName, String objTechName){
			
			try{
				List <WebElement> ele= driver.findElements(By.xpath(objTechName));
				WebDriverWait wait = new WebDriverWait(driver, 5);
		        wait.until(ExpectedConditions.visibilityOfAllElements(ele));
				logger.info(UIName);
				return ele;
				
			}catch(Exception e){
				logger.debug(UIName);
				System.out.println("<<< Couldn't Find " + UIName + " >>>");
				return null;
				
			
			}
		}
		

		public static String gettext(String UIName, String objTechName){
			
			try{
				WebElement element=driver.findElement(By.xpath(objTechName));
				WebDriverWait wait = new WebDriverWait(driver, AUT_MAX_WAIT); 
				wait.until(ExpectedConditions.elementToBeClickable(element));
				String text=element.getText().trim();
				logger.info(UIName);
				return text;
				
				
			}catch(Exception e){
				 logger.debug(UIName);
				 System.out.println("<<< Couldn't Find " + UIName + " >>>");
				 return "error";
				 
			}
			
		}

	

		public static void select(String UIName, String objTechName, String data){
			try{
				WebElement element=driver.findElement(By.xpath(objTechName));		
				final Select selectBox = new Select(element);			
				selectBox.selectByVisibleText(data);
				logger.info(UIName);
				
			 }catch(Exception e){
				 logger.debug(UIName);
				System.out.println("<<< Couldn't Find " + UIName + " >>>");
				 
			 	}
			}
	
		public enum properties{
			DISPLAYED,VISIBLE;
		}
		
		public static boolean ValidateObject(String UIName, String objTechName, String PropertyToBeVerified) {
			
			
			boolean ActualPropertyValue = false;
			String prop=PropertyToBeVerified.toUpperCase();
			try{
				WebElement element=driver.findElement(By.xpath(objTechName));
				WebDriverWait wait = new WebDriverWait(driver, 5);
		        wait.until(ExpectedConditions.visibilityOf(element));
		        
				switch (properties.valueOf(prop)){
				case DISPLAYED:
					ActualPropertyValue = element.isDisplayed();
					break;	
				case VISIBLE:						
				ActualPropertyValue = element.isEnabled();
				break;
				default: break;}
			}
			catch(Exception e){
												
						System.out.println("<<< Couldn't Find " + UIName + " >>> ");
		
					}
					if(ActualPropertyValue==true){		
						logger.info(UIName);
						return true;	
				
					}
					else{
						logger.debug(UIName);
						return false;
					}
			
		}
		
	
		
	}   
