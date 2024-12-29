package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterAndLoginTest {

    public static void main(String[] args) {
        // Définir le chemin du ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        // Créer une instance de ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Configurer les temps d'attente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Naviguer vers la page d'inscription
            driver.get("http://localhost:4200/#/register");

            // Remplir le formulaire d'inscription
            fillInputField(driver, "firstName", "Ayoub");
            fillInputField(driver, "lastName", "Test");
            fillInputField(driver, "email", "ayou4b.test@example.com");
            fillInputField(driver, "password", "password123");
            System.out.println("avant");
            // Cliquer sur le bouton "Create account"
            WebElement registerButton = driver.findElement(By.xpath("/html/body/app-root/app-auth-layout/div/app-register/div[2]/div/div/div/div[2]/form/div[4]/button"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);
            System.out.println("apres");

            // Attendre que la redirection vers la page de connexion soit terminée
            wait.until(ExpectedConditions.urlContains("/login"));

            // Remplir le formulaire de connexion
            fillInputField(driver, "email", "ayou4b.test@example.com");
            fillInputField(driver, "password", "password123");

            // Cliquer sur le bouton "Login"
            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
            loginButton.click();

            // Vérifier si la connexion est réussie
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            WebElement dashboardElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dashboard-title")));

            System.out.println("Test réussi : Connexion réussie !");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fermer le navigateur
            driver.quit();
        }
    }

    private static void fillInputField(WebDriver driver, String fieldName, String value) {
        WebElement inputField = driver.findElement(By.name(fieldName));
        inputField.clear();
        inputField.sendKeys(value);
    }
}
