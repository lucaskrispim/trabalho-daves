package competicao;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
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

public class CompeticaoTest {

    private WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }


    @Test
    public void competicaoSucess() {
        //Iniciando uma competicao
        driver.get("http://localhost:9000/login");
        driver.manage().window().setSize(new Dimension(1078, 1078));
        threadWait(1);
        driver.findElement(By.id("username")).click();
        threadWait(1);
        driver.findElement(By.id("username")).sendKeys("daves");
        threadWait(1);
        driver.findElement(By.id("password")).click();
        threadWait(1);
        driver.findElement(By.id("password")).sendKeys("123456");
        threadWait(1);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div/div[2]/form/div[2]/div/button")).click();
        threadWait(1);
        driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/ul/li[2]/a/p")).click();
        threadWait(1);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div/div/div[1]/a")).click();
        for(int i=0; i<10; i++){

            threadWait(1);
            String aux = driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div[2]/h1")).getText();
            System.out.println(aux);
            String[] slicedStrings = aux.split(" ");
            Double result = performOperation(Double.parseDouble(slicedStrings[0]),slicedStrings[1],Double.parseDouble(slicedStrings[2]));
            driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div[3]/div/div/div[2]/form/h5/div/input")).click();
            driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div[3]/div/div/div[2]/form/h5/div/input")).sendKeys(String.valueOf(result));
            threadWait(1);
            driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div[3]/div/div/div[2]/form/h5/div/button")).click();
        }
        threadWait(1);
        assertThat(driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div[2]/h1/span[1]")).getText(), is("Acertos :: 10"));

    }


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

    public static double performOperation(double number1, String operator, double number2) {
        double result = 0.0;

        if (operator.equals("-")) {
            result = number1 - number2;
        } else if (operator.equals("+")) {
            result = number1 + number2;
        } else if (operator.equals("*")) {
            result = number1 * number2;
        } else if (operator.equals("/")) {
            result = (int) Math.floor(number1 / number2);
        }

        return result;
    }


}
