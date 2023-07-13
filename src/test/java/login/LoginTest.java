package login;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    public void loginSucess(){
        driver.get("http://localhost:9000/login");
        driver.manage().window().setSize(new Dimension(980,1078));
        threadWait(2);
        driver.findElement(By.id("username")).click();
        threadWait(2);
        driver.findElement(By.id("username")).sendKeys("daves");
        threadWait(2);
        driver.findElement(By.id("password")).click();
        threadWait(2);
        driver.findElement(By.id("password")).sendKeys("123456");
        threadWait(2);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div/div[2]/form/div[2]/div/button") ).click();
        threadWait(2);
        assertThat( driver.findElement(By.xpath("/html/body/div/div[2]/nav/div/div/a/span")).getText() , is("Bem Vindo, Daves Martins") );
    }

//    @Test
//    public void loginNotSucess(){
//        driver.get("http://localhost:9000/login");
//        driver.manage().window().setSize(new Dimension(980,1078));
//        driver.findElement(By.id("username")).click();
//        driver.findElement(By.id("username")).sendKeys("111");
//        driver.findElement(By.id("password")).click();
//        driver.findElement(By.id("password")).sendKeys("112");
//        driver.findElement(By.cssSelector(".login100-form-btn")).click();
//
//        assertThat( driver.findElement( By.cssSelector("p") ).getText() , is("Login ou Senha incorreta") );
//    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }



    public static void threadWait(int... argument) {
        int seconds = 1;

        if (argument != null && argument.length == 1) {
            seconds = argument[0];
        }

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

