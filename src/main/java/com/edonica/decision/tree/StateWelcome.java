package com.edonica.decision.tree;

import com.amazon.speech.speechlet.SpeechletResponse;

public class StateWelcome extends StateGeneric {

    @Override
    public String getStateID() {
        return "StateWelcome";
    }

    public SpeechletResponse HandleRequest(RequestContext request) {
        String intentName = request.getIntentName();
        Log("StateWelcome handling " + intentName);
        if( intentName.equals(IntentNames.NewGame)) {
            Log("StateWelcome starting new game");
            String firstNode = null;
            StateGeneric newState = GameHelper.GetStateFromNode(request, firstNode);
            return SetStateAndReturnText(request, newState);
        }
        else if(intentName.equals("IntentSetNumber")) {
            String number = request.getSlot("number");
            request.setSessionString("number", number);
            return makeFullFatResponse("Setting number to " + number);
        }
        else if(intentName.equals("IntentGetNumber")) {
            String number = request.getSessionString("number");
            return makeFullFatResponse("Your session number is " + number);
        } else {
            return super.HandleRequest(request);
        }

    }

    @Override
    public SpeechletResponse GetAmbientText() {
        return super.GetAmbientText();
    }
/*
    public SpeechletResponse Activate() {
        if( intentName.equals(IntentNames.NewGame)) {
            return makeFullFatResponse("Now would be a good time to start a game");
        }

        else if(intentName.equals("IntentFreeText")) {
            String speechText = request.getStringFromComponents();
            // Create the Simple card content.
            return makeFullFatResponse(speechText);
        }
        else {
            return makeFullFatResponse("Unrecognised intent " + intentName);
        }
    }
    */
}
