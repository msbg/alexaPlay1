package com.edonica.decision.tree.states;

import com.edonica.decision.tree.model.*;

public class StateQuestion extends StateBase {
    StateQuestion() {
        super(GameState.Question);
        addTransition(new Transition(new IntentName[]{IntentName.AMAZON_YesIntent,IntentName.AMAZON_NoIntent}, GameState.IsItA){
            @Override
            boolean isValidTransition(RequestContext context) {
                if( context.getDataNode() == null || !context.getDataNode().hasChildren())
                    return false;

                DataNode targetChild = context.getChildNodeFromYesNoIntent();
                return targetChild != null && !targetChild.hasChildren();
            }

            @Override
            void handleRequest(RequestContext request) {
                DataNode targetChild = request.getChildNodeFromYesNoIntent();
                request.setSessionString(SessionKey.DataNode, targetChild.getId() );
            }
        });

        addTransition(new Transition(new IntentName[]{IntentName.AMAZON_YesIntent,IntentName.AMAZON_NoIntent}, GameState.Question){
            @Override
            boolean isValidTransition(RequestContext context) {
                if( context.getDataNode() == null || !context.getDataNode().hasChildren())
                    return false;

                DataNode targetChild = context.getChildNodeFromYesNoIntent();
                return targetChild != null && targetChild.hasChildren();
            }

            @Override
            void handleRequest(RequestContext request) {
                DataNode targetChild = request.getChildNodeFromYesNoIntent();
                request.setSessionString(SessionKey.DataNode, targetChild.getId() );
            }
        });

    }

    @Override
    public String getText(RequestContext request) {
        System.out.println("Generating text for question at node " + request.getSessionString(SessionKey.DataNode));

        DataNode node = request.getDataNode();
        return node.getQuestion() + "?";
    }
}
