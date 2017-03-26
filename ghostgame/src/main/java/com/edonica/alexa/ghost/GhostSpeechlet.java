package com.edonica.alexa.ghost;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.edonica.alexa.ghost.model.AlexaIntent;
import com.edonica.alexa.ghost.model.GhostStateContext;
import com.edonica.alexa.ghost.model.GhostStateMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GhostSpeechlet implements Speechlet {
    
    public static void log(String message) {
        System.out.println(message);
    }
    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        log("Session started: " + session);
        GhostStateMachine ghostStateMachine = new GhostStateMachine(new GhostStateContext());
        ghostStateMachine.fireTrigger(AlexaIntent.Session_start);
        updateSessionState(session, ghostStateMachine.getGhostStateContext());
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        GhostStateMachine ghostStateMachine = hydrateContextFromSessionState(session);
        ghostStateMachine.fireTrigger(AlexaIntent.Session_launch);
        updateSessionState(session, ghostStateMachine.getGhostStateContext());
        return getSpeechletResponseFromContext(ghostStateMachine);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        AlexaIntent intent = AlexaIntent.getIntentFromRequest(request);
        GhostStateMachine ghostStateMachine = hydrateContextFromSessionState(session);
        ghostStateMachine.fireTrigger(intent);
        updateSessionState(session, ghostStateMachine.getGhostStateContext());
        return getSpeechletResponseFromContext(ghostStateMachine);
    }


    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        log("Session ended: " + session);
    }

    private void updateSessionState(Session session, GhostStateContext ghostStateContext) throws SpeechletException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String contextAsString = objectMapper.writeValueAsString(ghostStateContext);
            session.setAttribute(GhostStateContext.GHOST_STATE_CONTEXT,contextAsString);
        } catch (JsonProcessingException e) {
            throw new SpeechletException("Could not update ghost state context ", e);
        }
    }
    private GhostStateMachine hydrateContextFromSessionState(Session session) throws SpeechletException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonGhostStateContext = (String) session.getAttribute(GhostStateContext.GHOST_STATE_CONTEXT);
            return new GhostStateMachine(objectMapper.readValue(jsonGhostStateContext, GhostStateContext.class));
        } catch (IOException e) {
            throw new SpeechletException("Could not hydrate session state ", e);
        }
    }

    private SpeechletResponse getSpeechletResponseFromContext(GhostStateMachine ghostStateContext) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(ghostStateContext.getSpeechText());

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        if( ghostStateContext.isEndConversation()) {
            return SpeechletResponse.newTellResponse(speech);
        } else {
            return SpeechletResponse.newAskResponse(speech, reprompt);
        }
    }
}
