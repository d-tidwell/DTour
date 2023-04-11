package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.AddProfileToFollowingRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddProfileToFollowingResult;

public class AddProfileToFollowingLambda extends LambdaActivityRunner<AddProfileToFollowingRequest, AddProfileToFollowingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddProfileToFollowingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddProfileToFollowingRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddProfileToFollowingRequest unauthenticatedRequest = input.fromBody(AddProfileToFollowingRequest.class);
                    return input.fromUserClaims(claims ->
                            AddProfileToFollowingRequest.builder()
                                    .withId(unauthenticatedRequest.getId())
                                    .withId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddProfileToFollowingActivity().handleRequest(request)
        );
    }
}

