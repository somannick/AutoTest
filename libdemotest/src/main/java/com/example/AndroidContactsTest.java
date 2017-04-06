package com.example;



import io.appium.java_client.android.AndroidDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.List;


public class AndroidContactsTest {
    private AndroidDriver driver;

    @Before
    public void setUp() throws Exception {
        //设置apk的路径,获得程序当前路径System.getProperty("user.dir"),
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "ContactManager.apk");

        //设置自动化相关参数,运行平台为Android（或ios）,与browser_name相矛盾，不能共存，
        //运行的设备为模拟器  Android Emulator
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        //adb devices 查询deviceName
        capabilities.setCapability("deviceName", "192.168.56.101:5555");

        //设置安卓系统版本,要和启动的模拟器平台保持一致
        capabilities.setCapability("platformVersion", "4.4");
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath());

        //设置app的主包名和主类名，包名和类名稍后介绍如何获取
        capabilities.setCapability("appPackage", "com.example.android.contactmanager");
        capabilities.setCapability("appActivity", ".ContactManager");

        //不要再次安装apk
        capabilities.setCapability("noReset", true);

        //初始化，在模拟器上启动安装apk
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void addContact() {
        //添加一个联系人到app中
        //appium 1.5开始name 属性已经废弃 用id  by.name 换为by.id 不然报错 findElementByName 换为findElementById
        //org.openqa.selenium.InvalidSelectorException: Locator Strategy 'name' is not supported for this session
        WebElement el = driver.findElement(By.id("Add Contact"));
        el.click();
        List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
        textFieldsList.get(0).sendKeys("Some Name");
        textFieldsList.get(2).sendKeys("Some@example.com");
        driver.swipe(100, 500, 100, 100, 2);
        driver.findElementById("Save").click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}