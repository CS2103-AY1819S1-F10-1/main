package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Salary("10000"), getProjectSet(new Project(
                            "friends", "John",getTagSet("Application to find friends")))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Salary("10000"), getProjectSet(new Project("friends",
                    "John", getTagSet("Application to find friends")))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Salary("10000"), getProjectSet(new Project(
                            "friends", "John", getTagSet("Application to find friends")))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Salary("10000"), getProjectSet(new Project("friends", "John",
                    getTagSet("Application to find friends")))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Salary("10000"), getProjectSet(new Project(
                            "Falcon", "Smith", getTagSet("Map application for travelling.")))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Salary("10000"), getProjectSet(new Project(
                            "OASIS", "Dymith", getTagSet("Management system."))))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a project set containing the list of strings given.
     */
    public static Set<Project> getProjectSet(Project... strings) {
        Set<Project> set = new HashSet<>();
        for (Project string : strings) {
            Project project = new Project(string.getpName(), string.getAuthor(), string.getDescription());
            set.add(project);
        }
        return set;
    }

    /**
     * Returns a date set containing the list of dates given.
     */
    public static Set<Date> getDateSet(Date... dates) {
        return Arrays.stream(dates)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
