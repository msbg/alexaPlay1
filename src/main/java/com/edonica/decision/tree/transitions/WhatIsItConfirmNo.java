package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class WhatIsItConfirmNo extends AbstractTransition {
    public WhatIsItConfirmNo() {
        super(GameState.WhatIsItConfirm, GameState.WhatIsIt);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentNo);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        return SpeechHelpers.makeFullFatResponse("OK, let's try again.  What were you thinking of?");
    }
}
