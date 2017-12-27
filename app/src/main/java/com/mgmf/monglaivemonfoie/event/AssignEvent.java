package com.mgmf.monglaivemonfoie.event;

import com.mgmf.monglaivemonfoie.model.Assignment;

import java.util.Arrays;
import java.util.List;

/**
 * Event for the assigments.
 *
 * @author Mathieu Aimé
 */

public abstract class AssignEvent extends Event {

    private List<Assignment> assignments;

    protected AssignEvent(Assignment... assignments) {
        this.assignments = Arrays.asList(assignments);
    }

    protected abstract String getAction();

    @Override
    public String play() {
        StringBuilder builder = new StringBuilder();
        for (Assignment a : assignments) {
            if (!builder.toString().equals("")) {
                builder.append('\n');
            }

            builder.append(a.getPlayer().getName())
                    .append(" ")
                    .append(getAction())
                    .append(" ")
                    .append(a.getRole());
        }
        return builder.toString();
    }
}
