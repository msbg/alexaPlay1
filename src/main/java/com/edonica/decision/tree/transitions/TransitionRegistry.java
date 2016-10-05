package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.edonica.decision.tree.transitions.GameState.WhatIsIt;


public class TransitionRegistry {
    public TransitionRegistry() {
        register(new NewGameNoData());
        register(new AddFirstItem());

        register(new NewGameSingleData());
        register(new WhatIsIt());
        register(new WhatIsItQuestion());
        register(new WhatIsItQuestionAnswer());

        register(new NewGameWithData());
        register(new AnswerRestart());
    }

    Map<GameState, List<AbstractTransition>> fromMap = new HashMap<>();
    private void register(AbstractTransition abstractTransition) {
        if(!fromMap.containsKey(abstractTransition.from)) {
            fromMap.put(abstractTransition.from, new ArrayList<>());
        }
        fromMap.get(abstractTransition.from).add(abstractTransition);
    }

    public AbstractTransition getTransition(GameState from, IntentName intentName, DataNode dn) {
        List<AbstractTransition> transitionList = fromMap.get(from);
        for(AbstractTransition abstractTransition:transitionList) {
            if(abstractTransition.isValidTransition(from, intentName, dn)) {
                return abstractTransition;
            }
        }
        return new AbstractTransition(GameState.Welcome, GameState.Welcome, IntentName.IntentStop) {
            @Override
            protected boolean isValidTransition(DataNode dn) {
                return true;
            }

            @Override
            protected SpeechletResponse internalHandleRequest(RequestContext request) {
                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                if( request.getIntentName().equals( IntentName.IntentStop.toString())) {
                    speech.setText("Byeeee.  See ya!");
                } else {
                    speech.setText("Couldn't determine transition... bouncing back to the start... BOING!");
                }
                return SpeechletResponse.newTellResponse(speech);
            }
        };
    }
}
