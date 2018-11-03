package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ArchivedListChangedEvent;
import seedu.address.commons.events.model.AssignmentListChangedEvent;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.model.project.Assignment;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedAssignmentList versionedAssignmentList;
    private final VersionedArchiveList versionedArchiveList;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<LeaveApplicationWithEmployee> filteredLeaveApplications;
    private final FilteredList<Person> archivedPersons;
    private User loggedInUser;

    private final FilteredList<Assignment> filteredAssignments;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyAssignmentList assignmentList,
                        ReadOnlyArchiveList archiveList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedAssignmentList = new VersionedAssignmentList(assignmentList);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredAssignments = new FilteredList<>(versionedAssignmentList.getAssignmentList());
        versionedArchiveList = new VersionedArchiveList(archiveList);
        filteredLeaveApplications = new FilteredList<>(versionedAddressBook.getLeaveApplicationList());
        archivedPersons = new FilteredList<>(versionedArchiveList.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new AssignmentList(), new ArchiveList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
        indicateAssignmentListChanged();
        indicateArchivedListChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyAssignmentList getAssignmentList() {
        return versionedAssignmentList;
    }

    @Override
    public ReadOnlyArchiveList getArchiveList() {
        return versionedArchiveList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateArchivedListChanged() {
        raise(new ArchivedListChangedEvent(versionedArchiveList));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        versionedArchiveList.addPerson(target);
        indicateAddressBookChanged();
        indicateArchivedListChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);

        //Update logged in user
        if (getLoggedInUser() != null && !getLoggedInUser().isAdminUser()
            && target.isSamePerson(getLoggedInUser().getPerson())) {
            setLoggedInUser(new User(editedPerson));
        }
        indicateAddressBookChanged();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User u) {
        loggedInUser = u;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Person> getArchivedPersonList() {
        return FXCollections.unmodifiableObservableList(archivedPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Leave Application List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code LeaveApplication} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<LeaveApplicationWithEmployee> getFilteredLeaveApplicationList() {
        return FXCollections.unmodifiableObservableList(filteredLeaveApplications);
    }

    @Override
    public void updateFilteredLeaveApplicationList(Predicate<LeaveApplicationWithEmployee> predicate) {
        requireNonNull(predicate);
        filteredLeaveApplications.setPredicate(predicate);
    }

    @Override
    public void updateFilteredLeaveApplicationListForPerson(Person person) {
        requireNonNull(person);
        updateFilteredLeaveApplicationList(leaveApplication
            -> leaveApplication.getEmployee().isSamePerson(loggedInUser.getPerson()));
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void restartAddressBook() {
        versionedAddressBook.restart();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredLeaveApplications.equals(other.filteredLeaveApplications);
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAssignmentListChanged() {
        raise(new AssignmentListChangedEvent(versionedAssignmentList));
    }

    @Override
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return versionedAssignmentList.hasAssignment(assignment);
    }

    @Override
    public void deleteAssignment(Assignment target) {
        versionedAssignmentList.removeAssignmnet(target);
        indicateAssignmentListChanged();
    }

    @Override
    public void addAssignment(Assignment assignment) {
        versionedAssignmentList.addAssignment(assignment);
        updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        indicateAssignmentListChanged();
    }

    @Override
    public void updateAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);

        versionedAssignmentList.updateAssignment(target, editedAssignment);
        indicateAssignmentListChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return FXCollections.unmodifiableObservableList(filteredAssignments);
    }

    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

}
