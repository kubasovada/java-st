package ru.stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    //int before = app.getGroupHelper().getGroupCount();
    // Переменная before содержала количество эл-в, теперь будет содержать список объектов типа GrD
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    //int after = app.getGroupHelper().getGroupCount();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);

  }
}
