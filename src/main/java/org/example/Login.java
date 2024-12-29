package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class Login {
    public static void main(String[] args) {
        // Configuration du chemin vers WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        // Initialisation de WebDriver
        WebDriver driver = new ChromeDriver();
        try {
            // URL de l'application
            String appUrl = "http://localhost:4200/login"; // Changez selon l'URL de votre application
            driver.get(appUrl);

            // Maximiser la fenêtre du navigateur
            driver.manage().window().maximize();

            // Attente explicite
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Entrer un email valide
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-auth-layout/div/app-login/div[2]/div/div/div[1]/div[2]/form/div[1]/div/input")));
            emailField.sendKeys("alouan01@gmail.com");

            // Entrer un mot de passe valide
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
            passwordField.sendKeys("1213");

            // Clic sur le bouton de connexion
            WebElement loginButton = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath("/html/body/app-root/app-auth-layout/div/app-login/div[2]/div/div/div[1]/div[2]/form/div[4]/button")));

            // Option 1: Utiliser Selenium pour cliquer
            try {
                loginButton.click();
            } catch (Exception e) {
                // Option 2: Forcer le clic avec JavaScript
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", loginButton);
            }

            // Vérifier si le tableau de bord est chargé après la connexion
            boolean isDashboardLoaded = wait.until(ExpectedConditions.urlContains("/dashboard")); // Adaptez le chemin selon votre application
            if (isDashboardLoaded) {
                System.out.println("Connexion réussie et tableau de bord chargé.");
            } else {
                System.out.println("Échec de la connexion ou redirection incorrecte.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fermeture du navigateur
            driver.quit();
        }
    }
}
