package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class IsItSuccess extends AbstractTransition {
    public IsItSuccess() {
        super(GameState.IsItA, GameState.Welcome);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentYes);
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        request.resetState();
        return StateGeneric.makeFullFatResponse("Great!  Say New Game to play again");
    }
}
