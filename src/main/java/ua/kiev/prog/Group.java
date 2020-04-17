package ua.kiev.prog;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Groups1")
@XmlRootElement(name = "group")
public class Group {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy="group", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<Contact>();

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlTransient
    public List<Contact> getContacts() {
        return contacts;
    }


    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
