package edu.upc.dsa.orm.dao;

import java.util.List;
import edu.upc.dsa.models.Question;

public interface QuestionDAO {
    public String addQuestion(String title, String message, String sender);
    public void updateQuestion(String id, String title, String message);
    public void deleteQuestionByID(String id);
    public List<Question> getQuestions();
    public Question getQuestionByID(String id);
}