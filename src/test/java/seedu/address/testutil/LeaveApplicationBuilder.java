package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveId;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class LeaveApplicationBuilder {

    public static final Integer DEFAULT_ID = 0;
    public static final String DEFAULT_DESCRIPTION = "Family holiday to Thailand";
    public static final StatusEnum.Status DEFAULT_STATUS = StatusEnum.Status.PENDING;

    private LeaveId leaveId;
    private Description description;
    private LeaveStatus leaveStatus;
    private List<LocalDate> dates;

    public LeaveApplicationBuilder() {
        leaveId = new LeaveId(DEFAULT_ID);
        description = new Description(DEFAULT_DESCRIPTION);
        leaveStatus = new LeaveStatus(DEFAULT_STATUS.toString());
        dates = new ArrayList<>();
    }

    /**
     * Initializes the LeaveApplicationBuilder with the data of {@code leaveApplicationToCopy}.
     */
    public LeaveApplicationBuilder(LeaveApplication leaveApplicationToCopy) {
        leaveId = leaveApplicationToCopy.getId();
        description = leaveApplicationToCopy.getDescription();
        leaveStatus = leaveApplicationToCopy.getLeaveStatus();
        dates = new ArrayList<>(leaveApplicationToCopy.getDates());
    }

    /**
     * Sets the {@code Description} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code dates} into a {@code List<Date>} and set it to the {@code LeaveApplication}
     * that we are building.
     */
    public LeaveApplicationBuilder withDates(LocalDate ... dates) {
        this.dates = SampleDataUtil.getDateList(dates);
        return this;
    }

    /**
     * Sets the {@code LeaveId} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withId(Integer leaveId) {
        this.leaveId = new LeaveId(leaveId);
        return this;
    }

    /**
     * Sets the {@code LeaveStatus} of the {@code LeaveApplication} that we are building.
     */
    public LeaveApplicationBuilder withStatus(StatusEnum.Status status) {
        this.leaveStatus = new LeaveStatus(status.toString());
        return this;
    }

    public LeaveApplication build() {
        return new LeaveApplication(leaveId, description, leaveStatus, dates);
    }

}
