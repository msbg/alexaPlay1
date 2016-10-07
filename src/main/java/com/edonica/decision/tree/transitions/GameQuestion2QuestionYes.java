package com.edonica.decision.tree.transitions;


import com.amazon.speech.speechlet.SpeechletResponse;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.SpeechHelpers;

public class GameQuestion2QuestionYes extends AbstractTransition {
    public GameQuestion2QuestionYes() {
        super(GameState.Question, GameState.Question);
    }

    @Override
    protected boolean isValidTransition(RequestContext context) {
        return context.isIntent(IntentName.IntentYes)
                && context.getDataNode() != null
                && context.getDataNode().hasChildren();
    }
    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String newNodeId = request.getDataNode().getYesId();
        request.setSessionString(DataNode.class.getName(), newNodeId );
        DataNode newNode = DataNode.load(newNodeId);

        return SpeechHelpers.makeFullFatResponse("Question. " + newNode.getQuestion());
    }
}
