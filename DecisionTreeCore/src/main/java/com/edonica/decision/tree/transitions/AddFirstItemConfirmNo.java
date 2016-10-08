package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class AddFirstItemConfirmNo extends AbstractTransition {
    public AddFirstItemConfirmNo() {
        super(GameState.FirstWhatIsItConfirm, GameState.FirstWhatIsIt);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNo);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("OK, try saying it again please.");
    }
}
