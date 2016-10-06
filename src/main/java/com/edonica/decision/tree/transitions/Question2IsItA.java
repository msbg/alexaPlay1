package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

public class Question2IsItA extends AbstractTransition {
    public Question2IsItA() {
        super(GameState.Question, GameState.IsItA);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        if( context.getDataNode() == null || !context.getDataNode().hasChildren())
            return false;

        DataNode targetChild = context.getChildNodeFromYesNoIntent();
        return targetChild != null && !targetChild.hasChildren();
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext context) {
        DataNode targetChild = context.getChildNodeFromYesNoIntent();
        context.setSessionString(DataNode.class.getName(), targetChild.getId() );
        SpeechletResponse response = StateGeneric.makeFullFatResponse("Is it " + targetChild.getValue() + "?");
        return response;
    }
}
