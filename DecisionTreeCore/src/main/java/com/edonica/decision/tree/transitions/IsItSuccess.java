package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class IsItSuccess extends AbstractTransition {
    public IsItSuccess() {
        super(GameState.IsItA, GameState.Welcome);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.AMAZON_YesIntent);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        request.resetState();
        return SpeechHelpers.makeFullFatResponse("Great!  Say New Game to play again");
    }
}
