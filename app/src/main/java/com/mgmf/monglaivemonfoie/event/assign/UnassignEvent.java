package com.mgmf.monglaivemonfoie.event.assign;

import android.content.res.Resources;

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
        return Resources.getSystem().getString(R.string.unassign);
    }
}
