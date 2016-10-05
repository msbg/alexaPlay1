package com.edonica.decision.tree.transitions;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.edonica.decision.tree.RequestContext;
import com.edonica.decision.tree.StateGeneric;


public class AddFirstItem extends AbstractTransition {
    public AddFirstItem() {
        super(GameState.FirstWhatIsIt, GameState.Welcome, IntentName.IntentFreeText);
    }

    @Override
    protected boolean isValidTransition(DataNode dn) {
        return dn==null;
    }

    @Override
    protected SpeechletResponse internalHandleRequest(RequestContext request) {
        String newItem = request.getStringFromComponents();

        DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient());

        Table table = dynamoDB.getTable("DecisionTrees");

        Item item = new Item()
                .withPrimaryKey("ID", 100)
                .withString("Value", newItem);

        PutItemOutcome outcome = table.putItem(item);
        System.out.println("Outcome : " + outcome.toString());

        //TODO Add the data node here
        return StateGeneric.makeFullFatResponse("You added " + newItem + " to the game");
    }
}
