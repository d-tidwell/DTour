package com.nashss.se.musicplaylistservice.lambda;

import com.nashss.se.musicplaylistservice.activity.requests.AddEventToProfileRequest;
import com.nashss.se.musicplaylistservice.activity.requests.AddSongToPlaylistRequest;
import com.nashss.se.musicplaylistservice.activity.results.AddEventToProfileResult;
import com.nashss.se.musicplaylistservice.activity.results.AddSongToPlaylistResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddEventToProfileLambda
        extends LambdaActivityRunner<AddEventToProfileRequest, AddEventToProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddEventToProfileRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddEventToProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddEventToProfileRequest unauthenticatedRequest = input.fromBody(AddEventToProfileRequest.class);
                    return input.fromUserClaims(claims ->
                            AddEventToProfileRequest.builder()
                                    .withEventId(unauthenticatedRequest.getEventId())
                                    .withName(unauthenticatedRequest.getName())
                                    .withAddress(unauthenticatedRequest.getAddress())
                                    .withEventCreator(unauthenticatedRequest.getEventCreator())
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withDateTime(unauthenticatedRequest.getDateTime())
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .withAttendees(unauthenticatedRequest.getAttendees())
                                    .withProfileId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddEventToProfileActivity().handleRequest(request)
        );
    }
}

