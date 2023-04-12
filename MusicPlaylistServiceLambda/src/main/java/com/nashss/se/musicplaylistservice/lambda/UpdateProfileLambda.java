package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.UpdateProfileRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateProfileResult;

public class UpdateProfileLambda extends LambdaActivityRunner<UpdateProfileRequest, UpdateProfileResult>
implements RequestHandler<AuthenticatedLambdaRequest<UpdateProfileRequest>,LambdaResponse> {

    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateProfileRequest unauthenticatedRequest = input.fromBody(UpdateProfileRequest.class);
                    return input.fromPath(path ->
                            UpdateProfileRequest.builder()
                                    .withFirstName(unauthenticatedRequest.getFirstName())
                                    .withLastName(unauthenticatedRequest.getLastName())
                                    .withLocation(unauthenticatedRequest.getLocation())
                                    .withGender(unauthenticatedRequest.getGender())
                                    .withDateOfBirth(unauthenticatedRequest.getDateOfBirth())
                                    .withId(path.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateProfileActivity().handleRequest(request)
        );
    }
}
