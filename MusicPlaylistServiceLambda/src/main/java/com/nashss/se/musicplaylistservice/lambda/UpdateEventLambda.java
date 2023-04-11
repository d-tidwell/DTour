package com.nashss.se.musicplaylistservice.lambda;

import com.nashss.se.musicplaylistservice.activity.requests.UpdateEventRequest;
import com.nashss.se.musicplaylistservice.activity.requests.UpdatePlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateEventResult;
import com.nashss.se.musicplaylistservice.activity.results.UpdatePlaylistResult;

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
                    return input.fromUserClaims(claims ->
                            UpdateEventRequest.builder()
                                    .withEventId(unauthenticatedRequest.getEventId())
                                    .withName(unauthenticatedRequest.getName())
                                    .withEventCreator(unauthenticatedRequest.getEventCreator())
                                    .withAddress(unauthenticatedRequest.getAddress())
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withDateTime(unauthenticatedRequest.getDateTime())
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .withAttendees(unauthenticatedRequest.getAttendees())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateEventActivity().handleRequest(request)
        );
    }
}
