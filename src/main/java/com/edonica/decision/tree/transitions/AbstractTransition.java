package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;

/**
 * Created by me on 05/10/2016.
 */
abstract public class AbstractTransition {
    final GameState from;
    final GameState to;

    final IntentName intentName;

    public AbstractTransition(GameState from, GameState to, IntentName intentName) {
        this.from = from;
        this.to = to;
        this.intentName = intentName;
    }


    public boolean isValidTransition(GameState from, IntentName intentName, DataNode dn) {
        return  from.equals(this.from) && intentName.equals(this.intentName) && isValidTransition(dn);
    }

    abstract protected boolean isValidTransition(DataNode dn);

    public SpeechletResponse handleRequest(RequestContext request) {
        SpeechletResponse response = internalHandleRequest(request);
        request.setSessionString(GameState.class.getName(), to.toString());
        return response;
    }

    protected abstract SpeechletResponse internalHandleRequest(RequestContext request);
}
