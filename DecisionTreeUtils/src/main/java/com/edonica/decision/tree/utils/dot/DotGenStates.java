package com.edonica.decision.tree.utils.dot;


import com.edonica.decision.tree.model.AbstractTransition;
import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.IntentName;
import com.edonica.decision.tree.model.TransitionRegistry;
import com.edonica.decision.tree.states.StateBase;
import com.edonica.decision.tree.states.StateRegistry;
import com.edonica.decision.tree.states.Transition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DotGenStates {

    public static void main(String args[]) {
        DotGenStates dotGen = new DotGenStates();
        dotGen.generate();

    }

    private static String intentNames(IntentName[] names) {
        String name = names[0].name();
        for(int c=1;c<names.length;c++) {
            name += "," + names[c].name();
        }
        return name;
    }

    private void generate() {
        StateRegistry registry = new StateRegistry();
        StringBuffer buff = new StringBuffer();
        buff.append("digraph {\n");
        for(Map.Entry<GameState, StateBase> kv:registry.getStateMap().entrySet()) {
            StateBase state = kv.getValue();

            for(Transition transition : state.getTransitions()) {
                buff.append(
                        state.getGameState().toString() +
                                "->" +
                                transition.getTo() +
                                "[label=\"" +
                                intentNames(transition.getMatchingIntents()) +
                                "\"]\n");
            }
        }
        buff.append("}");

        try(FileWriter fw = new FileWriter(new File("DecisionTree.States.dot"))) {
            fw.write(buff.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
