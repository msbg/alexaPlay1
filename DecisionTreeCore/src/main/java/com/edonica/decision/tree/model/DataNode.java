package com.edonica.decision.tree.model;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.UUID;

public class DataNode {
    public boolean hasChildren()
    {
        return yesId != null && noId != null;
    }

    public void addAlternative( String userQuestion, String userAnimal, boolean answerForUserAnimal)
    {
        //Create our two child nodes
        DataNode nodeExisting = new DataNode();
        nodeExisting.id = UUID.randomUUID().toString();
        nodeExisting.setValue( this.getValue());
        nodeExisting.save();

        DataNode nodeUser = new DataNode();
        nodeUser.id = UUID.randomUUID().toString();
        nodeUser.setValue(userAnimal);
        nodeUser.save();

        this.setValue(null);
        this.setQuestion(userQuestion);
        this.setYesId(answerForUserAnimal ? nodeUser.id : nodeExisting.id);
        this.setNoId(answerForUserAnimal ? nodeExisting.id : nodeUser.id);
        this.save();
    }

    public void save() {
        DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient());

        Table table = dynamoDB.getTable(DYNAMO_TABLE_DECISIONS);

        Item item = new Item()
                .withPrimaryKey(DYNAMO_FIELD_ID, id);

        if( value != null) {
            item.withString(DYNAMO_FIELD_VALUE, value);
        }
        if( question != null) {
            item.withString(DYNAMO_FIELD_QUESTION, question);
        }
        if( yesId != null) {
            item.withString(DYNAMO_FIELD_YES_ID, yesId);
        }
        if( noId != null) {
            item.withString(DYNAMO_FIELD_NO_ID, noId);
        }

        PutItemOutcome outcome = table.putItem(item);
        System.out.println("Outcome of node save : " + outcome.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getYesId() {
        return yesId;
    }

    public void setYesId(String yesId) {
        this.yesId = yesId;
    }

    public String getNoId() {
        return noId;
    }

    public void setNoId(String noId) {
        this.noId = noId;
    }

    private String id;
    private String value;
    private String question;
    private String yesId;
    private String noId;

    public static DataNode load(String nodeId) {
        DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient());

        Table table = dynamoDB.getTable(DYNAMO_TABLE_DECISIONS);

        Item item = table.getItem(DYNAMO_FIELD_ID, nodeId);
        if(item == null) {
            return null;
        }

        DataNode dn = new DataNode();
        dn.setId(nodeId);
        dn.setValue(item.getString(DYNAMO_FIELD_VALUE));
        dn.setQuestion(item.getString(DYNAMO_FIELD_QUESTION));
        dn.setYesId(item.getString(DYNAMO_FIELD_YES_ID));
        dn.setNoId(item.getString(DYNAMO_FIELD_NO_ID));

        return dn;
    }

    public static DataNode fromContext(RequestContext context) {

        String explicitNode = context.getSessionString(SessionKey.DataNode);
        if(explicitNode!=null) {
            return load(explicitNode);
        }

        //If we don't have a node set, start off with the users UUID
        return load(context.getUserId());
    }

    private static final String DYNAMO_TABLE_DECISIONS = "DecisionTrees";
    private static final String DYNAMO_FIELD_ID = "ID";
    private static final String DYNAMO_FIELD_VALUE = "Value";
    private static final String DYNAMO_FIELD_QUESTION = "Question";
    private static final String DYNAMO_FIELD_YES_ID = "YesId";
    private static final String DYNAMO_FIELD_NO_ID = "NoId";
}
