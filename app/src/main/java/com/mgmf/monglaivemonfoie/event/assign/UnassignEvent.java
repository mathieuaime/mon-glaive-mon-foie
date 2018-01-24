package com.mgmf.monglaivemonfoie.event.assign;

import com.mgmf.App;
import com.mgmf.monglaivemonfoie.R;
import com.mgmf.monglaivemonfoie.event.AssignEvent;
import com.mgmf.monglaivemonfoie.model.Assignment;

/**
 * Event for the unassignment of a role.
 *
 * @author Mathieu Aimé
 */

public class UnassignEvent extends AssignEvent {

    public UnassignEvent(Assignment... assignments) {
        super(assignments);
    }

    @Override
    protected String getAction() {
        return App.getAppContext().getString(R.string.unassign);
    }
}
