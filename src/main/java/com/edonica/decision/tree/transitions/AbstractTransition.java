package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;

/**
 * Created by me on 05/10/2016.
 */
abstract public class AbstractTransition {
    final GameState from;
    final GameState to;

    public AbstractTransition(GameState from, GameState to) {
        this.from = from;
        this.to = to;
    }


    public boolean isValidTransition(GameState from, RequestContext context) {
        return from.equals(this.from) && isValidTransition(context);
    }

    abstract protected boolean isValidTransition(RequestContext context);

    public SpeechletResponse handleRequest(RequestContext request) {
        SpeechletResponse response = internalHandleRequest(request);
        request.setSessionString(GameState.class.getName(), to.toString());
        return response;
    }

    protected abstract SpeechletResponse internalHandleRequest(RequestContext request);
}
