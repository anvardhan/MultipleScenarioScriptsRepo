package MultipleScenario;

public class BrokenLinkTest {

}


/*
 * How to find all the broken links in a web page ?

Normally a  broken link means is a link on a web page which is not working or no longer exist . Instead of expected website it will display 404 not found error.


Sample Program :

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HandleBrokenLink {
    public static void main(String[] args) throws InterruptedException, IOException {
      
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to("www.qalearningguide.com");
        List<WebElement> elements = driver.findElements(By.tagName("a"));
        System.out.println(elements.size());
      
        for(WebElement linkElement: elements){
            String newlink = linkElement.getAttribute("href");
            System.out.println(newlink );
            if(link!=null){
                 if(!isLink(newlink )){
                     continue;
                 }
            }
            verifyLinkActive(newlink );
        }
        driver.quit();
    }
    public static boolean isLink(String newlink ){
      
        return link.contains("http://") ;
    }
    public static void verifyLinkActive(String linkUrl) throws IOException{
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection Connect = (HttpURLConnection)url.openConnection();
            Connect.setConnectTimeout(3000);
            Connect.connect();
            if(Connect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND){
                System.out.println(linkUrl+"-"+Connect.getResponseMessage()+"-"+
            HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

  
}
 * 
 * 
 */
