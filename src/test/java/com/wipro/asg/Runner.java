package com.wipro.asg;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Runner extends Utils {
	

	ExcelDriver excel=new ExcelDriver(System.getProperty("input"),System.getProperty("output"));

	//Output sheet initializing row 
	int row=2;
	
	//** Overall TestCase status
	int passcount;
	static int failcount;
	
	String cart_text,Total_Amount,cart_count;
	
	@DataProvider(name="input")
	public Object[][] excel_data(){	
		
		//Geting all Row values from Input sheet
		Object data[][]=excel.dataprovider(0);
		return data;
		
	}

	@Test(dataProvider="input")
	public void test(Double S_no,String Username,String	Password,String	Empty_Shopping_Cart_message,String Search_Product,
			Double Product_count,String	Product1,String	Product_2,String Product_3,String Comments){
		

		
		try{
			passcount=0;
			failcount=0;
			
			//***Step_No-1
			browser("chrome", "https://demo.opencart.com/");
			
			//***Step_No-2
			click("Test2 - Clicking to Myaccount", Objects.myaccount);
			click("Test2 - Clickin the Login Button", Objects.login);
			
			//***Step_No-3
			input("Test3 - Entering the Username", Objects.username,Username);
			
			//***Step_No-4
			input("Test4 - Entering the Password", Objects.password,Password);
			
			//***Step_No-5
			click("Test5 - Clicking Login", Objects.login_BTN);
			
			//***Step_No-6
			if(!ValidateObject("Test6 - My Account Page Verfication", Objects.home_page, "displayed")){	
				excel.setCellData("Result", "LOGGED IN", row, "FAIL");
				failcount++;
				//This is used to stop the execution and move to nexr Row
				Assert.assertTrue(false,"<<< Couldn't Find Home Page >>> ");			
			}
	
			excel.setCellData("Result", "LOGGED IN", row, "PASS");
			
			//***Step_No-7
			click("Test7 - Home Logo", Objects.home_logo);
			
			//***Step_No-8
			click("Test8 - Clicking Cart",Objects.cart);

			if(ValidateObject("Test7 - Validating Shopping Cart", Objects.cart_remove, "displayed")){	
				List <WebElement> ele=elements("Test7 - Finding all items in cart to remove",Objects.cart_remove);
		        
				for ( WebElement we: ele) {
					try{
							if(ValidateObject("Test8 - Remiving Shopping Cart", Objects.cart_remove, "displayed")){
								Thread.sleep(2000);
								click("8-Remove from cart",Objects.cart_remove);
								Thread.sleep(3000);
								click("8-click Cart",Objects.cart);
							}
					}catch(Exception e){
						System.out.println("Error "+e);
						click("8 - Nothing in cartCart",Objects.cart);
					}					
				}
				
			}
			
				cart_text=gettext("Test8 - Empty Shopping cart Message", Objects.cart_empty);
				resultswithexpceted(Empty_Shopping_Cart_message.trim(), cart_text.trim(), "SHOPPING CART EMPTY MESSAGE");
				
			
			//***Step_No-9
			String zero_count="0";
			cart_count=gettext("Test9 - Geting Shopping Cart Toatal", Objects.cart_total);
			if(cart_count != null) zero_count=String.valueOf(cart_count.charAt(0));
			results("0",zero_count,"SHOPPING CART TOTAL ITEMS");
			
				
			//***Step_No_10
			Total_Amount=cart_count.substring(cart_count.length()-4);
			results("0.00",Total_Amount,"SHOPPING CART TOTAL AMT");
			
			//***Step_No_11
			input("Test11 - Entering Macbook in Search Field", Objects.search_box, Search_Product);
			click("Test11 - Clicking Search button",Objects.search_BTN);
			
			//***Step_No_12
			if(!ValidateObject("Test12 - Validaing Products Search Results", Objects.products_display, "displayed")){	
				excel.setCellData("Result", "PRODUCTS DISPLAYED", row, "FAIL");
				
				//This is used to stop the execution and move to nexr Row
				Assert.assertTrue(false,"<<< Couldn't Find Products >>> ");			
			}
			else{
				excel.setCellData("Result", "PRODUCTS DISPLAYED", row, "PASS");
			}
			
			//***Step_No_13
			select("Test13 - Selecing 100 in Dropdown", Objects.show_DD,"100");
			
			//***Step_No_14
			List <WebElement> ele= elements("Test14 - Verfifying Search results for "+Search_Product,Objects.products_display);
			String count=String.valueOf(ele.size());
			resultswithexpceted("3",count, "SEARCHED PRODUCT COUNT");
			
			//***Step_No_15
			click("Test15 - Clicking the Product1 "+Product1, Objects.macbook);
			
			//***Step_No_16
			if(ValidateObject("Test16 - Navigate Back using browser", Objects.prod_description, "displayed")){
				driver.navigate().back();
			}
			
			//***Step_No_17
			click("Test17 - Adding to cart "+Product1, Objects.addtocart);
			
			//***Step_No_18	
			click("Test18 - Clicking cart to find product count and total amount",Objects.cart);
			cart_count=gettext("Test18 - Getting cart count total", Objects.cart_total);
			resultswithexpceted("1",String.valueOf(cart_count.charAt(0)),"SHOPPING CART COUNT PRODUCT 1");
			
			//***Step_No_19
			String[] split=cart_count.split("-");
			Total_Amount=split[1].substring(2);
			Double price1 = null;
			if(!(Total_Amount.length()>6)) price1=Double.parseDouble(Total_Amount);
			resultswithexpceted("500.00",Total_Amount,"SHOPPING CART AMT PRODUCT 1");
			
			//***Step_No_20
			if(ValidateObject("Test20 - Success message after adding to cart", Objects.success, "displayed")){
				excel.setCellData("Result", "SUCCESS MESSAGE PRODUCT 1", row, "PASS");
				passcount++;
			}else{
				excel.setCellData("Result", "SUCCESS MESSAGE PRODUCT 1", row, "FAIL");
				failcount++;
			}
			
			//***Step_No_21
			click("Test21 - Adding to cart "+Product_2, Objects.addtocart2);
			
			//***Step_No_22
			click("Test22 - Clicking cart to find total count and total amount after adding "+Product_2,Objects.cart);
			cart_count=gettext("Test22 - Getting cart count total2", Objects.cart_total);
			resultswithexpceted("2",String.valueOf(cart_count.charAt(0)),"SHOPPING CART COUNT PRODUCT 2");
			
			//***Step_No_23
			split=cart_count.split("-");
			Total_Amount=split[1].substring(2);
			Total_Amount=Total_Amount.replaceAll(",", "").substring(0,6);
			
			String price=gettext("Test23 - Getting Price details-2", Objects.getprice2);
			String[] price2=price.split(":");
			price=price2[1].trim().substring(0,9).substring(1);	
			String Final_Total_Amount = null;
			Double price22 = null;
				if(price.equals("1,000.00")) {
					price=price.replaceAll(",", "");
					price22=Double.parseDouble(price);
					price22=price1+price22;
					Final_Total_Amount=price22.toString();
				}
			resultswithexpceted(Final_Total_Amount,Total_Amount,"SHOPPING CART AMT PRODUCT 2");

			
			//***Step_No_24
			if(ValidateObject("Test24 - Success Message after adding Product2", Objects.success, "displayed")){
				excel.setCellData("Result", "SUCCESS MESSAGE PRODUCT 2", row, "PASS");
				passcount++;
			}else{
				excel.setCellData("Result", "SUCCESS MESSAGE PRODUCT 2", row, "FAIL");
				failcount++;
			}
			
			//***Step_No_25
			click("Test25 - Add to cart "+Product_3, Objects.addtocart3);
			
			//***Step_No_26
			click("Test26 - clicking cart to get total count",Objects.cart);
			cart_count=gettext("Test26 - Get Cart Count", Objects.cart_total);
			resultswithexpceted("3",String.valueOf(cart_count.charAt(0)),"SHOPPING CART COUNT PRODUCT 3");
			
			//***Step_No_27
			split=cart_count.split("-");
			Total_Amount=split[1].substring(2);
			Total_Amount=Total_Amount.replaceAll(",", "").substring(0,6);
			price=gettext("Test27 - Getting Price details-3", Objects.getprice3);
			price2=price.split(":");
			price=price2[1].trim().substring(0,9).substring(1);	
			
				if(price.equals("2,000.00")) {
					price=price.replaceAll(",", "");
					Double price3=Double.parseDouble(price);
					price3=price22+price3;
					Final_Total_Amount=price3.toString();
				}
			resultswithexpceted(Final_Total_Amount,Total_Amount,"SHOPPING CART AMT PRODUCT 3");
			
			//***Step_No_28`
			if(ValidateObject("Test28 - Success Message after adding Product3", Objects.success, "displayed")){
				excel.setCellData("Result", "SUCCESS MESSAGE PRODUCT 3", row, "PASS");
				passcount++;
				click("Test28 - Switch out from Cart",Objects.cart);
			}else{
				excel.setCellData("Result", "SUCCESS MESSAGE PRODUCT 3", row, "FAIL");
				failcount++;
			}
			//***Step_No_29_30
			click("Test29 - Click Cart to check ascending price",Objects.cart);
			if(ValidateObject("Test29 - Checking Cart Ascending", Objects.cart_ascending, "displayed")){	
				List <WebElement> ele1= elements("Test29 - Checking Cart Products",Objects.cart_ascending);
				
		        String ascending_price1="";
		        if(ele1.size()==3){
		        	for(int i=1;i<=ele1.size();i++){
		        		String ascending_price=gettext("Test30 - Ascending price", "//table/tbody/tr["+i+"]/td[4]").substring(1);
		        		ascending_price1=ascending_price1+" "+ascending_price;
		        	}
		        	resultswithexpceted("500.00 1,000.00 2,000.00",ascending_price1.trim(),"ASCENDING PRICE CHECK");
		        	
		        }
		        else{
		        	excel.setCellData("Result", "ASCENDING PRICE CHECK", row, "FAIL");
		        	failcount++;
		        }
			}
			
			//***Step_No_31--32
			if(Product_2.equalsIgnoreCase("Macbook Air")){
				click("Test31 - Delete Product "+Product_2, Objects.product2_delete);
				click("After Deleting Product2 from Cart",Objects.cart);
				Thread.sleep(3000);
				List <WebElement> ele2= elements("Test31 - Total Price after del Prod2",Objects.cart_ascending);
				resultswithexpceted("2",String.valueOf(ele2.size()),"COUNT AFTER REMOVING PRODUCT 2");
				
				//***Step_No_33
				Double add = 0.0;
				for(int i=1;i<=ele2.size();i++){
	        		String after_price=gettext("In loop - Fetching price", "//table/tbody/tr["+i+"]/td[4]").substring(1).replace(",", "");
	        		add=Double.parseDouble(after_price)+add;      		
	        	}
				resultswithexpceted("2500.0",String.valueOf(add),"TOTAL AFTER REMOVING PRODUCT 2");
			}
			//***Step_No_34--35
			if(Product1.equalsIgnoreCase("Macbook")){
				click("Test34 - Delete Product "+Product1, Objects.product1_delete);
				click("Test34 - After deleting Product1 from Cart",Objects.cart);
				Thread.sleep(3000);
				List <WebElement> ele2= elements("Fetching details from cart",Objects.cart_ascending);
				resultswithexpceted("1",String.valueOf(ele2.size()),"COUNT AFTER REMOVING PRODUCT 1");
				
				//***Step_No_36
				Double add = 0.0;
				for(int i=1;i<=ele2.size();i++){
	        		String after_price=gettext("In loop-Getting details for Ascending price from Cart", "//table/tbody/tr["+i+"]/td[4]").substring(1).replace(",", "");
	        		add=Double.parseDouble(after_price)+add;
	        	}
		   		resultswithexpceted("2000.0",String.valueOf(add),"TOTAL AFTER REMOVING PRODUCT 1");
			}
			//***Step_No_37--39
			//checkout
			click("Test37 - checking out", Objects.checkout);
			if(ValidateObject("Test38 - Verify Shopping Cart Page",Objects.shopping_cart, "displayed")){
				excel.setCellData("Result", "SHOPPING CART PAGE DISPLAYED", row, "PASS");
				
			}
			else{
				excel.setCellData("Result", "SHOPPING CART PAGE DISPLAYED", row, "FAIL");
				Assert.assertTrue(false,"<<< Couldn't Find Shopping Cart Page >>> ");
			}
			
			//***Step_No_40
			click("Test40 - Clicking Myaccount", Objects.myaccount);
			click("Test40 - Clicking Logout", Objects.logout);
			
			//***Step_No_41
			if(ValidateObject("Test41 - Verify Logout page",Objects.logout_page, "displayed")){
				excel.setCellData("Result", "LOGGED OUT", row, "PASS");
				
			}
			else{
				excel.setCellData("Result", "LOGGED OUT", row, "FAIL");
			
			}			
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		
	
	}

	@AfterMethod
	public void aftertest(){
	
		driver.close();
		if(failcount==0) excel.setCellData("Result", "FINAL STATUS", row, "PASS");
		else excel.setCellData("Result", "FINAL STATUS", row, "FAIL");
		row++;
	}
	
	public void results(String EXPECTED,String ACTUAL,String col_name){
		
		if(EXPECTED.equalsIgnoreCase(ACTUAL)){
			excel.setCellData("Result", col_name, row, "PASS");
			passcount++;
		}else{
			excel.setCellData("Result", col_name, row, "FAIL");
			failcount++;
		}		
	}
	
	public void resultswithexpceted(String EXPECTED,String ACTUAL,String col_name){
		
		if(ACTUAL.trim().equalsIgnoreCase(EXPECTED)){
			excel.setCellData("Result", col_name, row, "PASS: EXPECTED "
							+EXPECTED+", ACTUAL "+ACTUAL);
			passcount++;
		}
		else{
			excel.setCellData("Result", col_name, row, "FAIL: EXPECTED "
					+EXPECTED+", ACTUAL "+ACTUAL);
			failcount++;
		}		
	}
	

}
