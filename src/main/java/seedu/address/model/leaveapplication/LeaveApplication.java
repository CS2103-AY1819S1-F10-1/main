package seedu.address.model.leaveapplication;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a LeaveApplication in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class LeaveApplication {
    // Identity fields
    private final LeaveId id;

    // Data fields
    private final Description description;
    private final LeaveStatus leaveStatus;
    private final List<LocalDateTime> dates = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public LeaveApplication(LeaveId id, Description description, LeaveStatus leaveStatus, List<LocalDateTime> dates) {
        requireAllNonNull(id, description, leaveStatus, dates);
        this.id = id;
        this.description = description;
        this.leaveStatus = leaveStatus;
        this.dates.addAll(dates);
    }

    public LeaveId getId() {
        return id;
    }

    public Description getDescription() {
        return description;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<LocalDateTime> getDates() {
        return Collections.unmodifiableList(dates);
    }

    /**
     * Returns true if both leave applications have the same identity and data fields.
     * This defines a stronger notion of equality between two leave applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LeaveApplication)) {
            return false;
        }

        LeaveApplication otherLeaveApplication = (LeaveApplication) other;
        return otherLeaveApplication.getId().equals(getId())
                && otherLeaveApplication.getDescription().equals(getDescription())
                && otherLeaveApplication.getLeaveStatus().equals(getLeaveStatus())
                && otherLeaveApplication.getDates().equals(getDates());
//                && otherLeaveApplication.getDates().stream()
//                    .map(Date::getTime)
//                    .collect(Collectors.toList())
//                    .equals(getDates().stream()
//                            .map(Date::getTime)
//                            .collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, description, leaveStatus, dates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getId())
                .append(" Description: ")
                .append(getDescription())
                .append(" Status: ")
                .append(getLeaveStatus())
                .append(" Dates: ");
        getDates().forEach(builder::append);
        return builder.toString();
    }
}
