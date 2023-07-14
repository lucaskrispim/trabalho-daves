package cadastro;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CadastroTest {

    private WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    //GERADOR DE DADOS DE USUÁRIOS ALEATÓRIO
    Faker faker = new Faker();
    String nome = faker.name().firstName();
    String sobreNome = faker.name().lastName();
    String login = nome.toLowerCase()+sobreNome.toLowerCase();
    String senha = faker.phoneNumber().phoneNumber();
    String email = nome.toLowerCase()+sobreNome.toLowerCase()+"@gmail.com";
    String cidade = faker.address().cityName();
    Date dataNascimento = faker.date().birthday(18,65);


    @Test
    public void cadastroSucess() {
        //Dados de cadastro respeitando as regras de negócio do sistema
        driver.get("http://localhost:9000/user");
        driver.manage().window().setSize(new Dimension(980, 1078));

        driver.findElement(By.id("cpNome")).click();

        driver.findElement(By.id("cpNome")).sendKeys(nome+" "+sobreNome);

        driver.findElement(By.id("cpLogin")).click();

        driver.findElement(By.id("cpLogin")).sendKeys(login);

        driver.findElement(By.id("cpSenha")).click();

        driver.findElement(By.id("cpSenha")).sendKeys(senha);

        driver.findElement(By.id("cpEmail")).click();

        driver.findElement(By.id("cpEmail")).sendKeys(email);

        driver.findElement(By.id("cpCidade")).click();

        driver.findElement(By.id("cpCidade")).sendKeys(cidade);

        driver.findElement(By.id("cpData")).click();

        driver.findElement(By.id("cpData")).sendKeys("13-03-1987");
        threadWait(1);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div/div/div[2]/form/div[4]/div/button")).click();
        threadWait(1);
        String searchMessage = "cadastro criado com sucesso.";
        threadWait(1);
        Alert alert = searchAlert(driver, searchMessage);
        if (alert != null) {
            assertThat( alert.getText(), is("cadastro criado com sucesso."));
        }else{
            assertEquals(1,2);
        }
    }

//    @Test
//    public void cadastroNotSucess1() {
//        //Dados de cadastro repetindo um usuário já cadastrado
//        driver.get("http://localhost:9000/user");
//        driver.manage().window().setSize(new Dimension(980, 1078));
//        //threadWait(1);
//        driver.findElement(By.id("cpNome")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpNome")).sendKeys(nome + " " + sobreNome);
//        //threadWait(1);
//        driver.findElement(By.id("cpLogin")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpLogin")).sendKeys("daves");
//        //threadWait(1);
//        driver.findElement(By.id("cpSenha")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpSenha")).sendKeys(senha);
//        //threadWait(1);
//        driver.findElement(By.id("cpEmail")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpEmail")).sendKeys(email);
//        //threadWait(1);
//        driver.findElement(By.id("cpCidade")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpCidade")).sendKeys(cidade);
//        //threadWait(1);
//        driver.findElement(By.id("cpData")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpData")).sendKeys("13-03-1999");
//        //threadWait(1);
//        driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div/div/div[2]/form/div[4]/div/button")).click();
//        threadWait(1);
//        assertThat(driver.findElement(By.xpath("/html/body/script")).getText() , is(""));
//
//    }
//
//    @Test
//    public void cadastroNotSucess2() {
//        //Deixando o campo Cidade vazio
//        driver.get("http://localhost:9000/user");
//        driver.manage().window().setSize(new Dimension(980, 1078));
//        //threadWait(1);
//        driver.findElement(By.id("cpNome")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpNome")).sendKeys(nome + " " + sobreNome);
//        //threadWait(1);
//        driver.findElement(By.id("cpLogin")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpLogin")).sendKeys(login);
//        //threadWait(1);
//        driver.findElement(By.id("cpSenha")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpSenha")).sendKeys(senha);
//        //threadWait(1);
//        driver.findElement(By.id("cpEmail")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpEmail")).sendKeys(email);
//        //threadWait(1);
//        driver.findElement(By.id("cpCidade")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpCidade")).sendKeys("");
//        //threadWait(1);
//        driver.findElement(By.id("cpData")).click();
//        //threadWait(1);
//        driver.findElement(By.id("cpData")).sendKeys("20-05-2001");
//        //threadWait(1);
//        driver.findElement(By.xpath("/html/body/div/div[2]/div/main/div/div/div/div[2]/form/div[4]/div/button")).click();
//        threadWait(1);
//        driver.findElement(By.xpath("/html/body/script")).click();
//        threadWait(1);
//        assertThat(driver.findElement(By.xpath("/html/body/h1")).getText() , is("Whitelabel Error Page"));
//
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

    private static Alert searchAlert(WebDriver driver, String searchMessage) {
        Alert alert = null;
        try {
            alert = driver.switchTo().alert();
            if (alert.getText().equals(searchMessage)) {
                return alert;
            }
        } catch (Exception e) {
            // Alert not present
        }
        return null;
    }

}
