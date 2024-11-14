package org.quizu.REST.controllers;


import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.quizu.domain.exceptions.RequiredPropertiesMissingException;
import org.quizu.domain.exceptions.UserAlreadyExistsException;
import org.quizu.domain.exceptions.UserNotFoundException;
import org.quizu.domain.exceptions.WrongPasswordException;
import org.quizu.domain.requests.LoginRequest;
import org.quizu.domain.models.UserAccount;
import org.quizu.domain.requests.UserAccountUpdateRequest;
import org.quizu.domain.responses.ErrorResponse;
import org.quizu.domain.responses.TokenResponse;
import org.quizu.services.logic.UserAccountLogicService;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;

@Path("/user")
public class UserController {
    @Inject
    UserAccountLogicService logicService;
    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/register")
    @APIResponses({
            @APIResponse(responseCode = "" + HttpStatus.SC_OK, description = "Registration successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @APIResponse(responseCode = "" + HttpStatus.SC_BAD_REQUEST, description = "User already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Response registerUser(UserAccount user) {
        try {
            logicService.registerUserAccount(user);
            return Response
                    .created(URI.create("/user/register/" + user.getEmail()))
                    .entity(user.getEmail())
                    .build();
        } catch (UserAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse("User already exists", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        } catch (RequiredPropertiesMissingException e) {
            ErrorResponse errorResponse = new ErrorResponse("Required properties missing", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @Authenticated
    @PATCH
    @APIResponses({
            @APIResponse(responseCode = "" + HttpStatus.SC_OK, description = "Successful login",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @APIResponse(responseCode = "" + HttpStatus.SC_NOT_FOUND, description = "User is not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "" + HttpStatus.SC_UNAUTHORIZED, description = "Wrong password",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Response updateUser(UserAccountUpdateRequest userAccountUpdateRequest) {
        try {
            String email = jwt.getClaim(Claims.sub.name());
            String newToken = logicService.updateUserAccount(email, userAccountUpdateRequest);

            return Response
                    .ok(new TokenResponse(newToken))
                    .build();
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("User not found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (WrongPasswordException e) {
            ErrorResponse errorResponse = new ErrorResponse("Wrong password", e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
        }
    }

    @POST
    @Path("/login")
    @APIResponses({
            @APIResponse(responseCode = "" + HttpStatus.SC_OK, description = "Successful login",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @APIResponse(responseCode = "" + HttpStatus.SC_NOT_FOUND, description = "User is not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "" + HttpStatus.SC_UNAUTHORIZED, description = "Wrong password",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Response login(LoginRequest userLoginData) {
        try {
            String token = logicService.login(userLoginData.getEmail(), userLoginData.getPassword());

            return Response
                    .ok(new TokenResponse(token))
                    .build();
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("User not found", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (WrongPasswordException e) {
            ErrorResponse errorResponse = new ErrorResponse("Wrong password", e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
        }
    }

    @Authenticated
    @DELETE
    @APIResponses({
            @APIResponse(responseCode = "" + HttpStatus.SC_OK, description = "Deleted successfully"),
            @APIResponse(responseCode = "" + HttpStatus.SC_NOT_FOUND, description = "User is not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Response deleteUser() {
        try {
            String email = jwt.getClaim(Claims.sub.name());
            logicService.deleteUserAccount(email);

            return Response
                    .ok()
                    .build();
        } catch (UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("User is not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
    }
}
