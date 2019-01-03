package propra2.person;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String jahresLohn;
    private String kontaktDaten;
    private String skills;
}
