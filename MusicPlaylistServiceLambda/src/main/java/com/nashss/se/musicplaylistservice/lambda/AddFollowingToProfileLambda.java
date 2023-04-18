package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.AddFollowingToProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddFollowingToProfileResult;

public class AddFollowingToProfileLambda extends LambdaActivityRunner<AddFollowingToProfileRequest, AddFollowingToProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddFollowingToProfileRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddFollowingToProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddFollowingToProfileRequest unauthenticatedRequest = input.fromBody(AddFollowingToProfileRequest.class);
                    return input.fromUserClaims(claims ->
                            AddFollowingToProfileRequest.builder()
                                    .withIdToAdd(unauthenticatedRequest.getIdToAdd())
                                    .withId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddProfileToFollowingActivity().handleRequest(request)
        );
    }
}

