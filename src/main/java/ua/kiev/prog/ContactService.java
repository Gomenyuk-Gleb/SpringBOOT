package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void addContact(Contact contact) {
         addGroup(contact.getGroup());
        contactRepository.save(contact);
    }

    @Transactional
    public void addGroup(Group group) {
        if (group != null && getGroupByName(group.getName()) == null) {
            groupRepository.save(group);
        }
    }

    @Transactional
    public Group getGroupByName(String groupName) {
        return groupRepository.findGroupByName(groupName);
    }

    @Transactional
    public void deleteContacts(long[] idList) {
        for (long id : idList)
            contactRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Contact> findAll(Pageable pageable) {
        return contactRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Contact> findByGroup(Group group, Pageable pageable) {
        return contactRepository.findByGroup(group, pageable);
    }

    @Transactional(readOnly = true)
    public long countByGroup(Group group) {
        return contactRepository.countByGroup(group);
    }

    @Transactional(readOnly = true)
    public List<Contact> findByPattern(String pattern, Pageable pageable) {
        return contactRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return contactRepository.count();
    }

    @Transactional(readOnly = true)
    public Group findGroup(long id) {
        return groupRepository.findOne(id);
    }

    @Transactional
    public void reset() {
        groupRepository.deleteAll();
        contactRepository.deleteAll();

        Group group = new Group("Test");
        Contact contact;

        addGroup(group);

        for (int i = 0; i < 13; i++) {
            contact = new Contact(null, "Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            addContact(contact);
        }
        for (int i = 0; i < 10; i++) {
            contact = new Contact(group, "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            addContact(contact);
        }
    }

    @Transactional
    public void download() throws MarshalException {
        ToXml toXml = new ToXml();
        List<Contact> contacts = contactRepository.findAll();

        for (Contact contact1 : contacts) {
            toXml.addStudentToXml(contact1);
        }
        try {
            File file = new File("Comtacts.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ToXml.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(toXml, file);
            marshaller.marshal(toXml, System.out);
        } catch (
                JAXBException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void save() throws MarshalException {
        File file = new File("download/Comtacts.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ToXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ToXml toXml = (ToXml) unmarshaller.unmarshal(file);
            for (int i = 0; i < toXml.size(); i++) {
                addContact(toXml.contactGet(i));
                System.out.println(toXml.contactGet(i));
            }
        } catch (
                JAXBException e) {
            e.printStackTrace();
        }
    }
}
