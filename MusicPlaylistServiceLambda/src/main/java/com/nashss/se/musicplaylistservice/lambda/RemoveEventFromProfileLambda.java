package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.RemoveEventFromProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveEventFromProfileResult;


public class RemoveEventFromProfileLambda extends LambdaActivityRunner<RemoveEventFromProfileRequest, RemoveEventFromProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<RemoveEventFromProfileRequest>, LambdaResponse> {
    
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RemoveEventFromProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    RemoveEventFromProfileRequest unauthenticatedRequest = input.fromBody(RemoveEventFromProfileRequest.class);
                    return input.fromUserClaims(claims ->
                            RemoveEventFromProfileRequest.builder()
                                    .withProfileId(claims.get("email"))
                                    .withEventId(unauthenticatedRequest.getEventId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRemoveEventFromProfileActivity().handleRequest(request)
        );
    }
}
