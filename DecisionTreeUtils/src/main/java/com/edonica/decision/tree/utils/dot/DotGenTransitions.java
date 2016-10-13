package com.edonica.decision.tree.utils.dot;


import com.edonica.decision.tree.model.AbstractTransition;
import com.edonica.decision.tree.model.GameState;
import com.edonica.decision.tree.model.TransitionRegistry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DotGenTransitions {

    public static void main(String args[]) {
        DotGenTransitions dotGen = new DotGenTransitions();
        dotGen.generate();

    }

    private void generate() {
        TransitionRegistry transitionRegistry = new TransitionRegistry();
        StringBuffer buff = new StringBuffer();
        buff.append("digraph {\n");
        for(Map.Entry<GameState, List<AbstractTransition>> kv:transitionRegistry.getFromMap().entrySet()) {
            for(AbstractTransition abstractTransition : kv.getValue()) {
                buff.append(
                        abstractTransition.getFrom().toString() +
                                "->" +
                                abstractTransition.getTo() +
                                "[label=\"" +
                                abstractTransition.getClass().getSimpleName() +
                                "\"]\n");
            }
        }
        buff.append("}");

        try(FileWriter fw = new FileWriter(new File("DecisionTree.Transitions.dot"))) {
            fw.write(buff.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
