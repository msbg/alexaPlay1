package com.edonica.decision.tree.transitions;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.edonica.decision.tree.RequestContext;

import java.util.UUID;

/**
 * Created by me on 05/10/2016.
 */
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

    void save() {
        DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient());

        Table table = dynamoDB.getTable("DecisionTrees");

        Item item = new Item()
                .withPrimaryKey("ID", id);

        if( value != null) {
            item.withString("Value", value);
        }
        if( question != null) {
            item.withString("Question", question);
        }
        if( yesId != null) {
            item.withString("YesId", yesId);
        }
        if( noId != null) {
            item.withString("NoId", noId);
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

        Table table = dynamoDB.getTable("DecisionTrees");

        Item item = table.getItem("ID", nodeId);
        if(item == null) {
            return null;
        }

        DataNode dn = new DataNode();
        dn.setId(nodeId);
        dn.setValue(item.getString("Value"));
        dn.setQuestion(item.getString("Question"));
        dn.setYesId(item.getString("YesId"));
        dn.setNoId(item.getString("NoId"));

        return dn;
    }

    public static DataNode fromContext(RequestContext context) {

        String explicitNode = context.getSessionString(DataNode.class.getName());
        if(explicitNode!=null) {
            return load(explicitNode);
        }

        //If we don't have a node, just start off with the users UUID
        return load(context.getUserId());
    }
}
