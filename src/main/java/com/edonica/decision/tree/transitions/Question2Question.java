package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.SpeechHelpers;

public class Question2Question extends AbstractTransition {
    public Question2Question() {
        super(GameState.Question, GameState.Question);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        if( context.getDataNode() == null || !context.getDataNode().hasChildren())
            return false;

        DataNode targetChild = context.getChildNodeFromYesNoIntent();
        return targetChild != null
                && targetChild.hasChildren();
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext context) {
        DataNode targetChild = context.getChildNodeFromYesNoIntent();
        context.setSessionString(DataNode.class.getName(), targetChild.getId() );
        return SpeechHelpers.makeFullFatResponse(targetChild.getQuestion());
    }
}
