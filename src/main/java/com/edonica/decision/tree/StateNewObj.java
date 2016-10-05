package com.edonica.decision.tree;

import com.amazon.speech.speechlet.SpeechletResponse;

public class StateNewObj extends StateGeneric {
    @Override
    public String getStateID() {
        return "StatePromptNew";
    }

    @Override
    public SpeechletResponse GetAmbientText() {
        return MakeFullFatResponse("What were you thinking of?");
    }

    @Override
    public SpeechletResponse HandleRequest(Request request) {
        if( request.getIntentName().equals(IntentNames.FreeText)) {
            return MakeFullFatResponse("TODO - Persist this and set next state");
        }
        return super.HandleRequest(request);
    }
}
