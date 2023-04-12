package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.GetProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetProfileResult;

public class GetProfileLambda extends LambdaActivityRunner<GetProfileRequest, GetProfileResult>
implements RequestHandler<AuthenticatedLambdaRequest<GetProfileRequest>, LambdaResponse> {


    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetProfileRequest> input, Context context) {
                 return super.runActivity(
             () -> input.fromPath(path ->
                     GetProfileRequest.builder()
                             .withId(path.get("id"))
                             .build()),
                (request,serviceComponent)->
                        //you need to set the service component here so it knows what to call to handle the request
                        serviceComponent.provideGetProfileActivity().handleRequest(request));
        }
    }
