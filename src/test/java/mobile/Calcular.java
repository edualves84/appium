package mobile;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertEquals;



public class Calcular {

    private AndroidDriver<MobileElement> driver;
    private DesiredCapabilities desiredCapabilities;
    private URL remoteUrl;


    public void print (String nomePrint) throws IOException {
        File foto = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(foto, new File("target/" +nomePrint+ ".png"));

    }


    @Before
    public void setUp() throws MalformedURLException {

        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("automationName", "UIAutomator2");
        desiredCapabilities.setCapability("appPackage", "com.google.android.calculator");
        desiredCapabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
        desiredCapabilities.setCapability("platformVersion", "10.0");
        desiredCapabilities.setCapability("ensureWebviewsHavePages", true);

        remoteUrl = new URL("http://localhost:4723/wd/hub");

        driver = new AndroidDriver<MobileElement>(remoteUrl, desiredCapabilities);
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("^abro a calculadora do google no meu smartphone$")
    public void abroACalculadoraDoGoogleNoMeuSmartphone() {
        driver = new AndroidDriver<MobileElement>(remoteUrl, desiredCapabilities);


    }

    @When("^seleciono o botao \"([^\"]*)\" mais botao \"([^\"]*)\"$")
    public void selecionoOBotaoMaisBotao(String num1, String num2) throws IOException {
        print("Somar 2 numeros positivos - passo 1 - abrir calculadora");
        MobileElement btnA = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_"+ num1);
        btnA.click();
        print("Somar 2 numeros positivos - passo 2 - clicou no botão" + num1);
        MobileElement btnSoma = (MobileElement) driver.findElementByAccessibilityId("plus");
        btnSoma.click();
        print("Somar 2 numeros positivos - passo 3 - clicou no botão soma");
        MobileElement btnB = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_"+ num2);
        btnB.click();
        print("Somar 2 numeros positivos - passo 4 - clicou no botão"+ num2);
        MobileElement btnIgual = (MobileElement) driver.findElementByAccessibilityId("equals");
        btnIgual.click();
        print("Somar 2 numeros positivos - passo 5 - exibiu a soma dos 2 numeros");


    }

    @Then("^retorna o resultado \"([^\"]*)\"$")
    public void retornaOResultado(String resultadoEsperado)  {

        MobileElement resultadoAtual = (MobileElement) driver.findElementById("com.google.android.calculator:id/result_final");
        assertEquals(resultadoEsperado,resultadoAtual.getText());

    }
}
