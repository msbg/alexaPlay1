package com.edonica.decision.tree.utils.dot;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeRequest;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeResult;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DeployToLambda {

    public static void main(String[] args) throws IOException {

        System.out.println("Got file of:" + args[0]);
        //File toUpload = new File("C:\\Users\\me\\IdeaProjects\\lambatest1\\DecisionTreeCore\\target", "DecisionTreeCore-1.0-SNAPSHOT.jar");
        File toUpload = new File(args[0]);

        ByteBuffer byteBuff = ByteBuffer.wrap(IOUtils.toByteArray(new FileInputStream(toUpload)));


        AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .build();
        UpdateFunctionCodeRequest updateFunctionCodeRequest = new UpdateFunctionCodeRequest();
        updateFunctionCodeRequest.setFunctionName("AlexaDecisionTreeSpeechlet");
        updateFunctionCodeRequest.setZipFile(byteBuff);
        UpdateFunctionCodeResult result = awsLambda.updateFunctionCode(updateFunctionCodeRequest);
        System.out.println(result.toString());
        awsLambda.shutdown();

    }
}
