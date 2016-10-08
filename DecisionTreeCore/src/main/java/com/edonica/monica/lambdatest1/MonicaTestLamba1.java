package com.edonica.monica.lambdatest1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MonicaTestLamba1 implements RequestHandler<Request, Response>{


    public Response handleRequest(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("received : " + request.toString() + ":" + context.toString());
        String greetingString = String.format("Hello %s %s.", request.firstNameX, request.lastName);
        return new Response(greetingString);
    }
}
