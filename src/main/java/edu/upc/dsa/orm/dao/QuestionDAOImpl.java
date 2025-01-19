package edu.upc.dsa.orm.dao;

import edu.upc.dsa.models.Question;
import edu.upc.dsa.orm.dao.QuestionDAO;
import edu.upc.dsa.orm.FactorySession;
import edu.upc.dsa.orm.Session;

import java.util.List;

public class QuestionDAOImpl implements QuestionDAO {

    public QuestionDAOImpl(Session session) {
    }

    @Override
    public String addQuestion(String title, String message, String sender) {
        Session session = null;
        int result = 0;
        try {
            session = FactorySession.openSession();
            Question question = new Question();
            question.setTitle(title);
            question.setMessage(message);
            question.setSender(sender);
            result = session.save(question);
        } catch (Exception e) {
            return "Error";
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result == -1 ? "Error" : "Success";
    }

    @Override
    public void updateQuestion(String id, String title, String message) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            Question question = (Question) session.get(Question.class, id);
            if (question != null) {
                question.setTitle(title);
                question.setMessage(message);
                session.update(question);
            }
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteQuestionByID(String id) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            Question question = (Question) session.get(Question.class, id);
            if (question != null) {
                session.delete(question);
            }
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Question> getQuestions() {
        Session session = null;
        List<Question> questions = null;
        try {
            session = FactorySession.openSession();
            questions = session.findAll(Question.class);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return questions;
    }

    @Override
    public Question getQuestionByID(String id) {
        Session session = null;
        Question question = null;
        try {
            session = FactorySession.openSession();
            question = (Question) session.get(Question.class, id);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return question;
    }
}
