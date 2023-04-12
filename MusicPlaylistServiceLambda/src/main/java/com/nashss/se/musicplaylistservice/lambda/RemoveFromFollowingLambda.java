package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.RemoveFromFollowingRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveFromFollowingResult;

public class RemoveFromFollowingLambda extends LambdaActivityRunner<RemoveFromFollowingRequest, RemoveFromFollowingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<RemoveFromFollowingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RemoveFromFollowingRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    RemoveFromFollowingRequest unauthenticatedRequest = input.fromBody(RemoveFromFollowingRequest.class);
                    return input.fromUserClaims(claims ->
                            RemoveFromFollowingRequest.builder()
                                    .withProfileIdToRemove(unauthenticatedRequest.getProfileIdToRemove())
                                    .withId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRemoveFromFollowingActivity().handleRequest(request)
        );
    }
}