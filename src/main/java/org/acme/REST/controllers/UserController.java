package org.acme.REST.controllers;


import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.acme.domain.exceptions.RequiredPropertiesMissingException;
import org.acme.domain.exceptions.UserAlreadyExistsException;
import org.acme.domain.exceptions.UserNotFoundException;
import org.acme.domain.exceptions.WrongPasswordException;
import org.acme.domain.requests.LoginRequest;
import org.acme.domain.models.UserAccount;
import org.acme.domain.requests.UserAccountUpdateRequest;
import org.acme.services.logic.UserAccountLogicService;
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
    public Response registerUser(UserAccount user) {

        try {
            logicService.registerUserAccount(user);
            return Response
                    .created(URI.create("/user/register/" + user.getEmail()))
                    .entity("User registered successfully with email: " + user.getEmail())
                    .build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (RequiredPropertiesMissingException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Authenticated
    @PATCH
    public Response updateUser(UserAccountUpdateRequest userAccountUpdateRequest) {
        try {
            String email = jwt.getClaim(Claims.sub.name());
            String newToken = logicService.updateUserAccount(email, userAccountUpdateRequest);


            return Response
                    .ok(newToken)
                    .build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (WrongPasswordException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest userLoginData) {
        try {
            String token = logicService.login(userLoginData.getEmail(), userLoginData.getPassword());

            return Response
                    .ok(token)
                    .build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (WrongPasswordException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @Authenticated
    @DELETE
    public Response deleteUser() {
        try {
            String email = jwt.getClaim(Claims.sub.name());
            logicService.deleteUserAccount(email);

            return Response
                    .ok("User deleted successfully with email: " + email)
                    .build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
