package org.acme.REST.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.acme.domain.models.Quiz;
import org.acme.services.logic.QuizLogicService;

import java.net.URI;

@Path("/quiz")
public class QuizController {
    @Inject
    QuizLogicService logicService;

    @POST
    public Response createQuiz(Quiz quiz) {
        logicService.createQuiz(quiz);
        return Response
                .created(URI.create("/quiz" + quiz.getTitle())) // TODO: change to ID
                .entity("User successfully created a quiz: " + quiz)
                .build();
    }
}
