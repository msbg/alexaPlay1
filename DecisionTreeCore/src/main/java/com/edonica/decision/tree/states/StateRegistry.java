package com.edonica.decision.tree.states;

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
        return stateMap.get(from);
    }

    public Map<GameState, StateBase> getStateMap() {
        return stateMap;
    }
}
