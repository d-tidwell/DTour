package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.GetEventRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetEventResult;

public class GetEventLambda extends LambdaActivityRunner<GetEventRequest, GetEventResult>
implements RequestHandler<AuthenticatedLambdaRequest<GetEventRequest>, LambdaResponse> {


/**
 * Handles a Lambda Function request
 *
 * @param input   The Lambda Function input
 * @param context The Lambda execution environment context object.
 * @return The Lambda Function output
 */
@Override
public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetEventRequest> input, Context context) {
                return super.runActivity(
        () -> input.fromPath(path ->
                GetEventRequest.builder()
                        .withId(path.get("id"))
                        .build()),
                (request,serviceComponent)->
                        serviceComponent.provideGetEventActivity().handleRequest(request));
                }
        }
