package com.mgmf.monglaivemonfoie.event;

import android.annotation.SuppressLint;

import com.mgmf.monglaivemonfoie.model.Assignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @SuppressLint("NewApi")
    @Override
    public String play() {
        return assignments.stream().map(a -> a.getPlayer().getName() + " " + getAction() + " " + a.getRole()).collect(Collectors.joining("\n"));
    }


}
