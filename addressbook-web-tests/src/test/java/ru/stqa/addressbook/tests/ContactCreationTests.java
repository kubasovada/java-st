package ru.stqa.addressbook.tests;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.Contacts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    //List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
    String xml = "";
    String line =  reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return  contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();

    //list.add(new Object[] {new ContactData().withFirstname("firstname1").withLastname("lastname1").withMobilePhone("79991112233").withWorkPhone("111").withHomePhone("222").withEmail("email1@email.ru").withGroup("test1")});
    //list.add(new Object[] {new ContactData().withFirstname("firstname2").withLastname("lastname2").withMobilePhone("79991112233").withWorkPhone("111").withHomePhone("222").withEmail("email2@email.ru").withGroup("test1")});
    //list.add(new Object[] {new ContactData().withFirstname("firstname3").withLastname("lastname3").withMobilePhone("79991112233").withWorkPhone("111").withHomePhone("222").withEmail("email3@email.ru").withGroup("test1")});
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
    String json = "";
    String line =  reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
    return  contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
  }



  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/stru.png");
    app.contact().createContact(contact);
    assertThat(app.contact().contactCount(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    //mapToInt превращает в поток идентификаторов, целых чисел
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

  }

  @Test (enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/stru.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }


  @Test(enabled = false)
  public void testBadNameContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before =  app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Name1'").withLastname("LastName1").withMobilePhone("79997775533").withEmail("email@email.ru").withGroup("test1");
    app.contact().createContact(contact);
    assertThat(app.contact().contactCount(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before));
  }

}
