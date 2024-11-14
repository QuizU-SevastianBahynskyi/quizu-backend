package org.quizu.REST.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.quizu.domain.models.Quiz;
import org.quizu.services.logic.QuizLogicService;

import java.net.URI;

@Path("/quiz")
public class QuizController {
    @Inject
    QuizLogicService logicService;

    @POST
    public Response createQuiz(Quiz quiz) {
        logicService.createQuiz(quiz);
        return Response
                .created(URI.create("/quiz/created/" + quiz.getTitle().toLowerCase().replace(" ", "_"))) // TODO: change to ID
                .entity(quiz).build();
    }
}
