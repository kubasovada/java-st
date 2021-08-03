package ru.stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    //int before = app.getGroupHelper().getGroupCount();
    // Переменная before содержала количество эл-в, теперь будет содержать список объектов типа GrD
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test1111", "test2", "test3");
    app.getGroupHelper().createGroup(group);
    //int after = app.getGroupHelper().getGroupCount();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);


    group.setId(after.stream().max( (o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));


  }
}
