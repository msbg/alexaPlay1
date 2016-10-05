package com.edonica.decision.tree;

import com.amazon.speech.speechlet.SpeechletResponse;

public class StateNewObj extends StateGeneric {
    @Override
    public String getStateID() {
        return "StatePromptNew";
    }

    @Override
    public SpeechletResponse GetAmbientText() {
        return makeFullFatResponse("What were you thinking of?");
    }

    @Override
    public SpeechletResponse HandleRequest(RequestContext request) {
        if( request.getIntentName().equals(IntentNames.FreeText)) {
            return makeFullFatResponse("TODO - Persist this and set next state");
        }
        return super.HandleRequest(request);
    }
}
