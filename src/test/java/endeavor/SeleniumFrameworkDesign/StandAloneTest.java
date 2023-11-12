package endeavor.SeleniumFrameworkDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		String productname = "IPHONE 13 PRO";
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("suraj.prakash.blr@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Qwert12345");
		driver.findElement(By.xpath("//input[@id='login']")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
      //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
       
        List<WebElement> products=driver.findElements(By.cssSelector(".col-lg-4"));
        WebElement prod = products.stream().filter(product->
        product.findElement(By.cssSelector("b")).getText().equals(productname)).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
       
      List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
    Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productname));
        Assert.assertTrue(match);
        driver.findElement(By.cssSelector(".totalRow button")).click();
        
        Actions a= new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();
        String confirmMessage =driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
        //.ng-animating
        //.ta-results button:nth-of-type(2)
        //)[2](//button[contains(@class,'ta-item')]
        
        
        
        
	}

}
