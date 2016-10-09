package com.edonica.decision.tree.model;

public class StateHelp {
    static class HelpText {
        public HelpText( String failText, String helpText) {
            this.failText = failText;
            this.helpText = helpText;
        }
        String failText;
        String helpText;
    }

    private static HelpText getStateInfo( GameState state ) {
        switch(state) {
            case Welcome:
                return new HelpText(
                        "Say New Game to start",
                        "This guess the number game will let you build a tree of yes/no questions to identify an object"
                );
            case FirstWhatIsIt:
                return new HelpText(
                        "What was it you were thinking of?",
                        "Say the name of what you were thinking of.  You may find it helps to make it slightly more than one word"
                );
            case FirstWhatIsItConfirm:
                return new HelpText(
                        "Say Yes or No to confirm.  If in doubt, no is good.",
                        "We need to make sure we heard you right.  Please say Yes or No to indicate."
                );
            case WhatIsIt:
                return new HelpText(
                        "What was it?",
                        "Let me know what you were thinking of so I can increase my knowledge."
                );
            case WhatIsItConfirm:
                return new HelpText(
                        "What was it?",
                        "Let me know what you were thinking of so I can increase my knowledge."
                );
            case IsItA:
                return new HelpText(
                        "Please answer yes or no",
                        "Let me know if we got it right so I can expand my wisdom"
                );
            case Question:
                return new HelpText(
                        "Please answer the question with a yes or no",
                        "You need to answer these yes no questions so I can suggest an object"
                );
            case WhatQuestion:
                return new HelpText(
                        "Please say a yes no question to help distinguish",
                        "Please say a question.  Talk clearly and keep individual words fairly distinct"
                );
            case WhatQuestionConfirm:
                return new HelpText(
                        "Did I hear you right?",
                        "Please confirm that the question I heard was correct"
                );
            case WhatIsItQuestionAnswer:
                return new HelpText(
                        "Please say yes or no to indicate the answer for your thing",
                        "I have a question and I have an object but I don't know the answer to the question for this object.  Is it yes or no?"
                );
            default:
                return new HelpText(
                        "Unknown State",
                        "Sorry, we appear to be in an unknown state.  This is a bug.  Try saying STOP to exit"
                );
        }

    }

    public static String getFailText( GameState state ) {
        return getStateInfo(state).failText;
    }

    public static String getHelpText( GameState state ) {
        return getStateInfo(state).helpText;
    }



}
