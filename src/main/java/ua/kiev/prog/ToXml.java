package ua.kiev.prog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "allstuden")
public class ToXml {

    @XmlElement
    private final List<Contact> contacts = new ArrayList<>();

    public void addStudentToXml(Contact contact){
        contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact contactGet(int i){
        return contacts.get(i);
    }
    public int size(){
        return contacts.size();
    }
}
