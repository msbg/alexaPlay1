package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.edonica.decision.tree.RequestContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransitionRegistry {
    public TransitionRegistry() {
        register(new NewGameNoData());
        register(new AddFirstItem());

        register(new NewGameSingleData());
        register(new IsItSuccess());
        register(new IsItFail());
        register(new WhatIsItQuestion());
        register(new WhatIsItQuestionAnswer());
        register(new WhatIsItQuestionAnswerYesNo());
        register(new Question2IsItA());
        register(new Question2Question());

        register(new NewGameWithData());
    }

    final Map<GameState, List<AbstractTransition>> fromMap = new HashMap<>();
    private void register(AbstractTransition abstractTransition) {
        if(!fromMap.containsKey(abstractTransition.from)) {
            fromMap.put(abstractTransition.from, new ArrayList<>());
        }
        fromMap.get(abstractTransition.from).add(abstractTransition);
    }

    public AbstractTransition getTransition(GameState from, RequestContext context) {
        List<AbstractTransition> transitionList = fromMap.get(from);
        for(AbstractTransition abstractTransition:transitionList) {
            if(abstractTransition.isValidTransition(from, context)) {
                return abstractTransition;
            }
        }
        return new AbstractTransition(GameState.Welcome, GameState.Welcome) {
            @Override
            protected boolean isValidTransition(RequestContext context) {
                return true;
            }

            @Override
            protected SpeechletResponse internalHandleRequest(RequestContext request) {
                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                if( request.getIntentName().equals( IntentName.IntentStop.toString())) {
                    speech.setText("Byeeee.  See ya!");
                } else {
                    speech.setText("Hmm, I don't understand " +request.getIntentName()+ ".  This tends to happen when you say short words.  Bouncing out of app to be safe.  BOING!");
                }
                return SpeechletResponse.newTellResponse(speech);
            }
        };
    }
}
