package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import seedu.address.commons.events.ui.FailedLoginEvent;
import seedu.address.commons.events.ui.ListPickerSelectionChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ListPicker extends UiPart<Region> {
  private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
  private static final String FXML = "ListPicker.fxml";

  @FXML
  private Button archiveList;

  @FXML
  private Button activeList;

 public ListPicker() {
    super(FXML);
    archiveList.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        archiveList.setStyle("-fx-background-color: #28B463; -fx-border-color: #28B463;");
        activeList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
        raise(new ListPickerSelectionChangedEvent(2));
      }
    });
   activeList.setOnAction(new EventHandler<ActionEvent>() {
     @Override public void handle(ActionEvent e) {
       activeList.setStyle("-fx-border-color: #28B463; -fx-background-color: #28B463;");
       archiveList.setStyle("-fx-background-color: #95A5A6; -fx-border-color: #95A5A6;");
       raise(new ListPickerSelectionChangedEvent(1));
     }
   });
    registerAsAnEventHandler(this);

  }
}