package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import testcases.Base;

public class Screenshot extends Base{
	public static String getScreenShot(String fileName) throws IOException{
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy h-m-s");
        Date date = new Date();
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String detination=System.getProperty("user.dir")+"/src/test/java/Screenshot/"+fileName+"-"+dateFormat.format(date)+".png";
        File target=new File(detination);
        FileUtils.copyFile(scrFile, target);
        return detination;
	}

}
