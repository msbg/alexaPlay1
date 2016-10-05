package com.edonica.decision.tree;

import com.amazon.speech.speechlet.SpeechletResponse;

public class StateUnknown extends StateGeneric {
    @Override
    public SpeechletResponse GetAmbientText() {
        return makeFullFatResponse("We are in an unknown state.  Maybe say 'Stop'");
    }

    @Override
    public String getStateID() {
        return "StateUnknown";
    }

    @Override
    public SpeechletResponse HandleRequest(RequestContext request) {
        return super.HandleRequest(request);
    }
}
