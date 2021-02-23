package be.relive.Global.group.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

@Entity(name = "Groups")
@Table(name = "Groups",
        indexes = {@Index(name = "group_index",  columnList="key", unique = true)})
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "key", nullable = false)
    private String key;

    @Column( name = "description")
    private String description;

    @Column( name = "title")
    private String title;

    @OneToMany(mappedBy="group", fetch = FetchType.EAGER)
    private Collection<Subscriber> subscribers = new ArrayList<>();

    private Group() {
    }

    private Group(String key, String description, String title) {
        this.key = key;
        this.description = description;
        this.title = title;
    }

    public static Group newGroup(String description, String title) {
        return new Group(createRandomKey(), description, title);
    }

    public static Group group(String key, String description, String title) {
        return new Group(key, description, title);
    }

    private static String createRandomKey() {
        // save a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // save random string builder
        StringBuilder sb = new StringBuilder();

        // save an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Collection<Subscriber> getSubscribers() {
        return subscribers;
    }
}
