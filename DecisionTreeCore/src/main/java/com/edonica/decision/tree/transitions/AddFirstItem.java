package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class AddFirstItem extends AbstractTransition {
    public AddFirstItem() {
        super(GameState.FirstWhatIsIt, GameState.FirstWhatIsItConfirm);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentFreeText)
                && context.getDataNode()==null;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String newItem = request.getStringFromComponents();
        request.setSessionString(SessionKey.ObjectName, newItem);

        return SpeechHelpers.makeFullFatResponse("I heard " + newItem + ", is that right?");
    }
}
