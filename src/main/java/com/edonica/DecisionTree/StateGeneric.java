package com.edonica.DecisionTree;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


public class StateGeneric {

    public String getStateID() {
        return "StateGeneric";
    }

    public SpeechletResponse GetAmbientText() {
        return MakeFullFatResponse("Ambient text - this should be more helpful");
    }

    public SpeechletResponse HandleRequest(Request request) {
        String intentName = request.getIntentName();

        if(intentName.equals(IntentNames.Stop)) {
            Log("Saying goodbye");
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("Goodbye");
            return SpeechletResponse.newTellResponse(speech);
        }

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("Unknown state");
        return SpeechletResponse.newTellResponse(speech);
    }

    void Log(String s) {
        System.out.println("Edonica : " + s);
    }

    void SetState(Request request, StateGeneric newState) {
        Log("Switching from state " + getStateID() + " to " + newState.getStateID());
        request.setState(newState.getStateID());
    }

    SpeechletResponse SetStateAndReturnText(Request request, StateGeneric newState) {
        SetState(request, newState);
        return newState.GetAmbientText();
    }

    public static class AttributeKeys {
        public static final String State = "State";
    }

    public static class IntentNames {
        public static final String NewGame = "IntentNewGame";
        public static final String Stop = "IntentStop";
        public static final String FreeText = "IntentFreeText";
    }

    static public SpeechletResponse MakeFullFatResponse(String speechText) {
        SimpleCard card = new SimpleCard();
        card.setTitle("Decision Tree");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

}
