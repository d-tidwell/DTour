package com.nashss.se.musicplaylistservice.lambda;

import com.nashss.se.musicplaylistservice.activity.requests.CreateEventRequest;
import com.nashss.se.musicplaylistservice.activity.requests.CreatePlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreateEventResult;
import com.nashss.se.musicplaylistservice.activity.results.CreatePlaylistResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateEventLambda
        extends LambdaActivityRunner<CreateEventRequest, CreateEventResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateEventRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateEventRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateEventRequest unauthenticatedRequest = input.fromBody(CreateEventRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateEventRequest.builder()
                                    .withName(unauthenticatedRequest.getName())
                                    .withEventCreator(unauthenticatedRequest.getEventCreator())
                                    .withAddress(unauthenticatedRequest.getAddress())
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withDateTime(unauthenticatedRequest.getDateTime())
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateEventActivity().handleRequest(request)
        );
    }
}