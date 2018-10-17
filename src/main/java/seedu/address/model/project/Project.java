package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Project in the OASIS.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Project {

    // Identity fields
    private final ProjectName pName;
    private final Name author;

    //Data fields
    private final Set<Tag> description = new HashSet<>();


    public Project(ProjectName pName, Name author, Set<Tag> description) {
        requireAllNonNull(pName, author, description);
        this.pName = pName;
        this.author = author;
        this.description.addAll(description);
    }


    public ProjectName getpName() {
        return pName;
    }

    public Name getAuthor() {
        return author;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getDescription() {
        return Collections.unmodifiableSet(description);
    }

    /**
     * Returns true if both projects of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.getpName().equals(getpName())
                && (otherProject.getAuthor().equals(getAuthor()));
    }

    private final ObservableList<Project> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Project toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProject);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Project toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    public ObservableList<Project> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns true if both projects have the same identity and data fields.
     * This defines a stronger notion of equality between two projects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Project)) {
            return false;
        }

        Project otherProject = (Project) other;
        return otherProject.getpName().equals(getpName())
                && otherProject.getAuthor().equals(getAuthor())
                && otherProject.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(pName, author, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getpName())
                .append(" Author: ")
                .append(getAuthor())
                .append(" Description: ");
        getDescription().forEach(builder::append);
        return builder.toString();
    }
}


