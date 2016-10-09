package com.edonica.decision.tree.model;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.*;

public class TransitionRegistry {

    public TransitionRegistry() {
        Reflections reflections = new Reflections(ConfigurationBuilder.build("com.edonica.decision.tree.transitions"));
        Set<?> listOfTransations = reflections.getSubTypesOf(AbstractTransition.class);
        for( Object foundClass : listOfTransations) {
            Class<? extends AbstractTransition> klass = (Class<? extends AbstractTransition>) foundClass;
            try {
                AbstractTransition instance = klass.newInstance();
                register(instance);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    final Map<GameState, List<AbstractTransition>> fromMap = new HashMap<>();
    private void register(AbstractTransition abstractTransition) {
        if(!fromMap.containsKey(abstractTransition.getFrom())) {
            fromMap.put(abstractTransition.getFrom(), new ArrayList<>());
        }
        fromMap.get(abstractTransition.getFrom()).add(abstractTransition);
    }

    public AbstractTransition getTransition(GameState from, RequestContext context) {
        List<AbstractTransition> transitionList = fromMap.get(from);
        if( transitionList != null ) {
            for (AbstractTransition abstractTransition : transitionList) {
                if (abstractTransition.isValidTransition(from, context)) {
                    return abstractTransition;
                }
            }
        }
        return new AbstractTransition(from, from) {
            @Override
            protected boolean isValidTransition(RequestContext context) {
                return true;
            }

            @Override
            protected SpeechletResponse internalHandleRequest(RequestContext request) {
                if( request.isIntent(IntentName.AMAZON_StopIntent)) {
                    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                    speech.setText("Byeeee.  See ya!");
                    return SpeechletResponse.newTellResponse(speech);
                } else {
                    return SpeechHelpers.makeFullFatResponse("Sorry, I don't know what to do with " + request.getIntentName() + " at "+from.toString()+".");
                }
            }
        };
    }

    public Map<GameState, List<AbstractTransition>> getFromMap() {
        return fromMap;
    }
}
