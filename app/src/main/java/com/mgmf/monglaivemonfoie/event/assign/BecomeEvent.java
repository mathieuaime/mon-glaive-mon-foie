package com.mgmf.monglaivemonfoie.event.assign;

import com.mgmf.monglaivemonfoie.App;
import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.event.AssignEvent;
import com.mgmf.monglaivemonfoie.model.Assignment;

/**
 * Event for the assignment of a role.
 *
 * @author Mathieu Aimé
 */

public class BecomeEvent extends AssignEvent {
    public BecomeEvent(Assignment... assignments) {
        super(assignments);
    }

    @Override
    protected String getAction() {
        return App.getAppContext().getString(R.string.become);
    }
}
