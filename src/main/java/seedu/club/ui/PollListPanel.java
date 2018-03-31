package seedu.club.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.HideResultsRequestEvent;
import seedu.club.commons.events.ui.JumpToPollListRequestEvent;
import seedu.club.commons.events.ui.PollPanelSelectionChangedEvent;
import seedu.club.commons.events.ui.ViewResultsRequestEvent;
import seedu.club.model.poll.Poll;

/**
 * Panel containing the list of polls.
 */
public class PollListPanel extends UiPart<Region> {
    private static final String FXML = "PollListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PollListPanel.class);
    private boolean isDisplayingPollResults;
    private ObservableList<Poll> pollList;

    @FXML
    private ListView<PollCard> pollListView;

    public PollListPanel(ObservableList<Poll> pollList) {
        super(FXML);
        setPollListView(pollList);
        registerAsAnEventHandler(this);
    }

    private void setPollListView(ObservableList<Poll> pollList) {
        ObservableList<PollCard> mappedList = EasyBind.map(
                pollList, (poll) -> {
                if (isDisplayingPollResults) {
                    return new PollCard(poll, pollList.indexOf(poll) + 1);
                } else {
                    return new RestrictedPollCard(poll, pollList.indexOf(poll) + 1);
                }
            });
        pollListView.setItems(mappedList);
        pollListView.setCellFactory(listView -> new PollListViewCell());
    }

    /**
     * Shows results of poll
     */
    protected void showPollResults() {
        if (!isDisplayingPollResults) {
            isDisplayingPollResults = true;
            setPollListView(pollList);
        }
    }

    /**
     * Hides results of polls
     */
    protected void hidePollResults() {
        if (isDisplayingPollResults) {
            isDisplayingPollResults = false;
            setPollListView(pollList);
        }
    }

    @Subscribe
    private void handleViewResultsEvent(ViewResultsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPollResults();
    }

    @Subscribe
    private void handleHideResultsEvent(HideResultsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        hidePollResults();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PollCard}.
     */
    class PollListViewCell extends ListCell<PollCard> {

        @Override
        protected void updateItem(PollCard poll, boolean empty) {
            super.updateItem(poll, empty);

            if (empty || poll == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(poll.getRoot());
            }
        }
    }
}
