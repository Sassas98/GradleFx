package it.unicam.controller.checker;

import it.unicam.model.entity.BasicTurnGame;

public abstract class CheckerDecorator<T extends BasicTurnGame> {

    private CheckerDecorator<T> next;

    public abstract boolean check(T c, int[] in, int[] out);

    public boolean checkNext(T c, int[] in, int[] out){
        if(next == null) return true;
        return next.check(c, in, out);
    }

    public CheckerDecorator<T> setAsNext(CheckerDecorator<T> c){
        c.next = this;
        return c;
    }

}
