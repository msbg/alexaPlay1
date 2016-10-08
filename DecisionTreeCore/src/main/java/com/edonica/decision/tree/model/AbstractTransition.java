package com.edonica.decision.tree.model;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.RequestContext;
import com.edonica.decision.tree.model.SessionKey;

abstract public class AbstractTransition {
    final GameState from;
    final GameState to;

    public AbstractTransition(GameState from, GameState to) {
        this.from = from;
        this.to = to;
    }

    abstract protected boolean isValidTransition(RequestContext context);

    protected abstract SpeechletResponse internalHandleRequest(RequestContext request);

    public GameState getFrom() {
        return from;
    }

    public GameState getTo() {
        return to;
    }

    public boolean isValidTransition(GameState from, RequestContext context) {
        return from.equals(this.from) && isValidTransition(context);
    }

    public SpeechletResponse handleRequest(RequestContext request) {
        SpeechletResponse response = internalHandleRequest(request);
        request.setSessionString(SessionKey.GameState, to.toString());
        return response;
    }

}
