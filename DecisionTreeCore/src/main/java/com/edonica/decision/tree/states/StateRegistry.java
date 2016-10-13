package com.edonica.decision.tree.states;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.edonica.decision.tree.model.*;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.*;

public class StateRegistry {

    public StateRegistry() {
        Reflections reflections = new Reflections(ConfigurationBuilder.build("com.edonica.decision.tree.states"));
        Set<?> listOfStates = reflections.getSubTypesOf(StateBase.class);
        for( Object foundClass : listOfStates) {
            Class<? extends StateBase> klass = (Class<? extends StateBase>) foundClass;
            try {
                StateBase instance = klass.newInstance();
                register(instance);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    final Map<GameState, StateBase> stateMap = new HashMap<>();

    private void register(StateBase state) {
        stateMap.put(state.getGameState(), state);
    }

    public StateBase getState(GameState from) {
        StateBase state = stateMap.get(from);
        return state;
    }

    public Map<GameState, StateBase> getStateMap() {
        return stateMap;
    }
}
