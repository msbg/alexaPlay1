package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.SpeechHelpers;

public class AddFirstItem extends AbstractTransition {
    public AddFirstItem() {
        super(GameState.FirstWhatIsIt, GameState.Welcome);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentFreeText)
                && context.getDataNode()==null;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String newItem = request.getStringFromComponents();

        //TODO - confirm text for first added item

        DataNode dn = new DataNode();

        //First node is stored against the user ID rather than a GUID
        dn.setId(request.getUserId());
        dn.setValue(newItem);
        dn.save();

        return SpeechHelpers.makeFullFatResponse("You added " + newItem + ", your first object in this game.  Say New Game to play again");
    }
}
