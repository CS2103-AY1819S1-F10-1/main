package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Salary("10000"), new Username("Alex Yeoh"),
                    new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Salary("10000"),
                    new Username("Bernice Yu"), new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Salary("10000"),
                    new Username("Charlotte Oliveiro"), new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Salary("10000"),
                    new Username("David Li"), new Password("Pa55w0rd"), getProjectSet("friends")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Salary("10000"),
                    new Username("Irfan Ibrahim"), new Password("Pa55w0rd"), getProjectSet("falcon")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Salary("10000"),
                    new Username("Roy Balakrishnan"), new Password("Pa55w0rd"), getProjectSet("oasis"))
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
    public static Set<Project> getProjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Project::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a date list containing the list of dates given.
     */
    public static List<LocalDateTime> getDateList(LocalDateTime... dates) {
        return Arrays.stream(dates)
                .collect(Collectors.toList());
    }
}
