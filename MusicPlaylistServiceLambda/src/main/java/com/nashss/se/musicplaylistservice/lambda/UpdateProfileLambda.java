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
        // Run the activity with the provided input and service component
        return super.runActivity(
                () -> {
                    // Deserialize the request body into an UpdateProfileRequest object
                    UpdateProfileRequest unauthenticatedRequest = input.fromBody(UpdateProfileRequest.class);

                    // Extract the profileId from the path parameter named 'id'
                    String profileIdFromPath = input.getPathParameters().get("id");

                    // Build a new UpdateProfileRequest object with the path parameter 'id' and the body values
                    return UpdateProfileRequest.builder()
                            .withFirstName(unauthenticatedRequest.getFirstName())
                            .withLastName(unauthenticatedRequest.getLastName())
                            .withLocation(unauthenticatedRequest.getLocation())
                            .withGender(unauthenticatedRequest.getGender())
                            .withDateOfBirth(unauthenticatedRequest.getDateOfBirth())
                            .withId(profileIdFromPath)
                            .build();
                },
                (request, serviceComponent) -> serviceComponent.provideUpdateProfileActivity().handleRequest(request)
        );
    }
}
