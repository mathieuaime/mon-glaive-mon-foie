package com.mgmf.monglaivemonfoie.event;

/**
 * Created by Mathieu on 07/12/2017.
 */

public class AttackEvent extends Event {
    private int nb;

    public AttackEvent(int nb) {
        this.nb = nb;
    }
}
