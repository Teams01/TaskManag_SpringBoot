package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddWorkspace {

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
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-auth-layout/div/app-login/div[2]/div/div/div[1]/div[2]/form/div[4]/button")));

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

                try {
                    // URL de l'application
                    String appUrl1 = "http://localhost:4200/#/dashboard"; // Changez selon l'URL de votre application
                    driver.get(appUrl1);

                    // Maximiser la fenêtre du navigateur
                    driver.manage().window().maximize();

                    // Attente explicite
                    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));

                    // Accéder au modal pour ajouter un espace de travail
                    WebElement addWorkspaceButton = wait1.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-bs-toggle='modal'][data-bs-target='#exampleModal']")));
                    addWorkspaceButton.click();

                    // Remplir le formulaire d'ajout d'espace de travail
                    WebElement nameField = wait1.until(ExpectedConditions.elementToBeClickable(By.id("name")));
                    nameField.sendKeys("New Workspace");

                    WebElement descriptionField = wait1.until(ExpectedConditions.elementToBeClickable(By.id("description")));
                    descriptionField.sendKeys("Description for new workspace");

                    // Soumettre le formulaire
                    WebElement saveButton = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"exampleModal\"]/div/div/div[2]/div/div/form/button")));

                    // Utilisation de JavaScript pour forcer le clic si Selenium ne fonctionne pas
                    try {
                        saveButton.click(); // Essaie le clic classique
                    } catch (Exception e) {
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", saveButton); // Utilise JavaScript si clic Selenium échoue
                    }

                    // Attendre la confirmation ou la redirection vers la page du dashboard
                    boolean isDashboardLoaded1 = wait.until(ExpectedConditions.urlContains("/dashboard"));
                    if (isDashboardLoaded1) {
                        System.out.println("Workspace ajouté avec succès !");
                        WebElement Btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-admin-layout/div/app-dashboard/div[2]/div/div/div/div[2]/div[1]/a")));
                        try {
                            Btn.click();
                        } catch (Exception e) {
                            // Option 2: Forcer le clic avec JavaScript
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].click();", Btn);
                        }
                        WebElement Btn1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-admin-layout/div/app-project-details/div[2]/div/div/div/div[1]/div[2]/button[2]")));
                        try {
                            Btn1.click();
                        } catch (Exception e) {
                            // Option 2: Forcer le clic avec JavaScript
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].click();", Btn1);
                        }
                        WebElement Btn2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"deleteModal\"]/div/div/div[3]/button[2]")));
                        try {
                            Btn2.click();
                        } catch (Exception e) {
                            // Option 2: Forcer le clic avec JavaScript
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].click();", Btn2);
                        }

                        System.out.println("Task supprimer avec succes");





                    } else {
                        System.out.println("Échec de l'ajout du workspace.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // Fermeture du navigateur
                    driver.quit();
                }

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
