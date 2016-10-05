package com.edonica.DecisionTree;

import com.amazon.speech.speechlet.SpeechletResponse;

public class StateUnknown extends StateGeneric {
    @Override
    public SpeechletResponse GetAmbientText() {
        return MakeFullFatResponse("We are in an unknown state.  Maybe say 'Stop'");
    }

    @Override
    public String getStateID() {
        return "StateUnknown";
    }

    @Override
    public SpeechletResponse HandleRequest(Request request) {
        return super.HandleRequest(request);
    }
}
