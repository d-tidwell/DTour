package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.CreateProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreateProfileResult;

public class CreateProfileLambda extends LambdaActivityRunner<CreateProfileRequest, CreateProfileResult>
implements RequestHandler<AuthenticatedLambdaRequest<CreateProfileRequest>,LambdaResponse> {
    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    //?? we only want exactly what is needed to create a minimum profile
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateProfileRequest unauthenticatedRequest = input.fromBody(CreateProfileRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateProfileRequest.builder()
                                    .withId(claims.get("email"))
                                    .withFirstName(unauthenticatedRequest.getFirstName())
                                    .withLastName(unauthenticatedRequest.getLastName())
                                    .withLocation(unauthenticatedRequest.getLocation())
                                    .withGender(unauthenticatedRequest.getGender())
                                    .withDateOfBirth(unauthenticatedRequest.getDateOfBirth())
                                    .build());

                },
                (request,serviceComponent) ->
                        serviceComponent.provideCreateProfileActivity().handleRequest(request)
        );

    }
}
