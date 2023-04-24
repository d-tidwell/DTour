package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.DeleteEventFromProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveEventFromProfileResult;


public class DeleteEventFromProfileLambda extends LambdaActivityRunner<DeleteEventFromProfileRequest, RemoveEventFromProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteEventFromProfileRequest>, LambdaResponse> {
    
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteEventFromProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    String eventIdFromPath = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            DeleteEventFromProfileRequest.builder()
                                    .withProfileId(claims.get("email"))
                                    .withEventId(eventIdFromPath)
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteEventFromProfileActivity().handleRequest(request)
        );
    }
}
