package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Project.
 */
public class XmlAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    @XmlValue
    private String projectName;
    private String author;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedProject.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProject() {}

    /**
     * Constructs a {@code XmlAdaptedProject} with the given {@code projectName}.
     */
    public XmlAdaptedProject(String pName, String author, List<XmlAdaptedTag> tagged) {

        this.projectName = pName;
        this.author = author;
        if(tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }

    }

    /**
     * Converts a given Project into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedProject(Project source) {
        projectName = source.getpName();
        author = source.getAuthor();
        tagged = source.getDescription().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Project toModelType() throws IllegalValueException {
        final List<Tag> projectTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            projectTags.add(tag.toModelType());
        }

        if (projectName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ProjectName.class.getSimpleName()));
        }
        if (!ProjectName.isValidName(projectName)) {
            throw new IllegalValueException(ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS);
        }
        final ProjectName modelProjectName = new ProjectName(projectName);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(author)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelAuthor = new Name(author);

        final Set<Tag> modelTags = new HashSet<>(projectTags);
        return new Project(modelProjectName.toString(), modelAuthor.toString(), modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProject)) {
            return false;
        }

        XmlAdaptedProject otherProject = (XmlAdaptedProject) other;
        return Objects.equals(projectName, otherProject.projectName)
                && Objects.equals(author, otherProject.author)
                && tagged.equals(otherProject.tagged);
    }
}
