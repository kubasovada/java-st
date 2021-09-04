package ru.stqa.pft.soap.appmanager;

import org.openqa.selenium.By;


public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    //click(By.cssSelector("button[class='width-100 width-40 pull-right btn btn-success btn-inverse bigger-110']"));
    click(By.cssSelector("button[type='submit']"));
  }

  public void login (String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Вход']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Вход']"));
  }

  public void resetUserPassword(int id) {
    click(By.cssSelector("button#menu-toggler"));
    click(By.cssSelector("ul.nav-list> li:nth-child(7)  span.menu-text"));
    click(By.cssSelector("ul.nav-tabs li:nth-child(2)"));
    click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
    //click(By.cssSelector("table.table tr:nth-child(2)>td a"));
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public void finishResetUserPassword (String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.cssSelector("#password"), password);
    type(By.cssSelector("#password-confirm"), password);
    click(By.cssSelector("button[type='submit']"));
  }
}
