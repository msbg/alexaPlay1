package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;


public class NewGameNoData extends AbstractTransition {
    public NewGameNoData() {
        super(GameState.Welcome, GameState.FirstWhatIsIt);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNewGame)
                && context.getDataNode()==null;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("OK.  My mind is blank and I need to learn things.  Please think of an object and tell me what it is.  Using more than one word to describe it will help my voice recognition.");
    }
}
