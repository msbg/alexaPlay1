package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.model.*;

public class AddFirstItemConfirmYes extends AbstractTransition {
    public AddFirstItemConfirmYes() {
        super(GameState.FirstWhatIsItConfirm, GameState.Welcome);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.AMAZON_YesIntent);
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String newItem = request.getSessionString(SessionKey.ObjectName);

        DataNode dn = new DataNode();

        //First node is stored against the user ID rather than a GUID
        dn.setId(request.getUserId());
        dn.setValue(newItem);
        dn.save();

        return SpeechHelpers.makeFullFatResponse("You added " + newItem + ", your first object in this game.  Say New Game to play again");
    }
}
