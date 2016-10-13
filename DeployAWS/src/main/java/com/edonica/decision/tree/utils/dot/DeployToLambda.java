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

        String inputFile = args[0];
        System.out.println("Reading input JAR : " + inputFile );
        byte[] byteArray = IOUtils.toByteArray(new FileInputStream(new File(inputFile)));
        ByteBuffer byteBuff = ByteBuffer.wrap(byteArray);
        System.out.println("Read bytes :" +  byteArray.length);

        AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .build();


        String functionName = "AlexaDecisionTreeSpeechlet";
        System.out.println("Uploading to function : " + functionName);
        UpdateFunctionCodeRequest updateFunctionCodeRequest = new UpdateFunctionCodeRequest();
        updateFunctionCodeRequest.setFunctionName(functionName);
        updateFunctionCodeRequest.setZipFile(byteBuff);
        UpdateFunctionCodeResult result = awsLambda.updateFunctionCode(updateFunctionCodeRequest);
        System.out.println("Upload result : " + result.toString());

        awsLambda.shutdown();

        System.out.println("Lambda shutdown done");
    }
}
