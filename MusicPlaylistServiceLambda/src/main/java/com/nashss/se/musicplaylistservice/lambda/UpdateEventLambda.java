package com.nashss.se.musicplaylistservice.lambda;

import com.nashss.se.musicplaylistservice.activity.requests.UpdateEventRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateEventResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateEventLambda
        extends LambdaActivityRunner<UpdateEventRequest, UpdateEventResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateEventRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateEventRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateEventRequest unauthenticatedRequest = input.fromBody(UpdateEventRequest.class);
                    String eventIdFromPath = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            UpdateEventRequest.builder()
                                .withProfileId(claims.get("email"))
                                .withEventId(eventIdFromPath)
                                .withName(unauthenticatedRequest.getName())
                                .withEventCreator(claims.get("email"))
                                .withAddress(unauthenticatedRequest.getAddress())
                                .withDescription(unauthenticatedRequest.getDescription())
                                .withDateTime(unauthenticatedRequest.getDateTime())
                                .withCategory(unauthenticatedRequest.getCategory())
                                .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateEventActivity().handleRequest(request)
        );
    }
}
