package com.wipro.asg;

public class Objects {
	
	//Please use only xpath
	
	public final static String myaccount="//span[contains(text(),'My Account')]";
	public final static String login="//a[contains(text(),'Login')]";
	public final static String username="//input[@name='email']";
	public final static String password="//input[@name='password']";
	public final static String login_BTN="//input[@type='submit']";
	public final static String home_page="//a[contains(text(),'Edit')]";
	public final static String home_logo="//a[contains(text(),'Your Store')]";
	public final static String cart="//div[@id='cart']/button";
	public final static String cart_remove="//button[@title='Remove']";
	public final static String cart_empty="//div//li/p";
	public final static String cart_total="//span[@id='cart-total']";  //total cart 
	public final static String search_box="//input[@name='search']";
	public final static String search_BTN="//span[@class='input-group-btn']";
	public final static String products_display="//div[@class='product-thumb']";
	public final static String show_DD ="//select[@id='input-limit']";
	public final static String macbook="//a[contains(text(),'MacBook')]"; //div[1]/div[@class='product-thumb']
	public final static String addtocart="//div[1][contains(@class,'product-layout')]//span[contains(text(),'Add to Cart')]";
	public final static String addtocart2="//div[2][contains(@class,'product-layout')]//span[contains(text(),'Add to Cart')]";
	public final static String getprice2="//div[2][contains(@class,'product-layout')]//p[@class='price']/span";
	public final static String addtocart3="//div[3][contains(@class,'product-layout')]//span[contains(text(),'Add to Cart')]";
	public final static String getprice3="//div[3][contains(@class,'product-layout')]//p[@class='price']/span";
	public final static String prod_description="//a[contains(text(),'Description')]";
	public final static String success="//div[contains(@class,'alert')]";
	public final static String cart_ascending="//table/tbody/tr/td[4]";
	public final static String product1_delete="//tr[1]/td[5]//button[@title='Remove']";
	public final static String product2_delete="//tr[2]/td[5]//button[@title='Remove']";
	public final static String logout="//a[contains(text(),'Logout')]";
	public final static String checkout="//a[2]/strong/i";
	public final static String shopping_cart="//div[@id='content']/h1[contains(text(),'Shopping')]";	
	public final static String logout_page="//div[@id='content']/h1[contains(text(),'Account Logout')]";
}
