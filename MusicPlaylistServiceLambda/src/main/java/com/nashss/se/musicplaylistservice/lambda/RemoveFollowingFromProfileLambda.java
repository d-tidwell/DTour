package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.RemoveFollowingFromProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.RemoveFollowingFromProfileResult;

public class RemoveFollowingFromProfileLambda extends LambdaActivityRunner<RemoveFollowingFromProfileRequest, RemoveFollowingFromProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<RemoveFollowingFromProfileRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RemoveFollowingFromProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    RemoveFollowingFromProfileRequest unauthenticatedRequest = input.fromBody(RemoveFollowingFromProfileRequest.class);
                    return input.fromUserClaims(claims ->
                            RemoveFollowingFromProfileRequest.builder()
                                    .withProfileIdToRemove(unauthenticatedRequest.getProfileIdToRemove())
                                    .withId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRemoveFromFollowingActivity().handleRequest(request)
        );
    }
}