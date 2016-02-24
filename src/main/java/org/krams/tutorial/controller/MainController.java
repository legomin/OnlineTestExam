package org.krams.tutorial.controller;

import org.apache.log4j.Logger;
import org.krams.tutorial.domain.Answer;
import org.krams.tutorial.domain.Person;
import org.krams.tutorial.domain.Question;
import org.krams.tutorial.domain.Test;
import org.krams.tutorial.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Handles and retrieves person request
 */
@Controller
@RequestMapping("/main")
public class MainController {

    protected static Logger logger = Logger.getLogger("controller");

    private Map<Integer, Integer> marksMap;
    private Person person;
    private int duration;
    private Test test;
    private List<Question> questionNumbesList;

    @Resource(name = "personService")
    private PersonService personService;


    /**
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAuthorization(Model model) {

        test = personService.getTest();

        model.addAttribute("test", test);

        return "firstPage";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String Authorization(Model model, @RequestParam(value = "login", required = true) String login,
                              @RequestParam(value = "pass", required = true) String pass) {

        person = personService.getUser(login, pass);

        if (person == null) {
            model.addAttribute("test", test);
            model.addAttribute("error", "Y");
            return "firstPage";
        } else {
            duration = test.getDuration();
            marksMap = new HashMap<Integer, Integer>();

            return "redirect:/krams/main/questions?question=1";
        }
    }

    /**
     */
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String getQuestion(Model model, @RequestParam(value = "question", required = true) int questionNumber) {
        if (duration <= 0) { return "redirect:/krams/main/submit?anywhere=1";   }
        if (person == null) { return "redirect:/krams/main/auth";   }

        Question question = personService.getQuestionByNumber(questionNumber, test.getId());
        model.addAttribute("question", question);

        List<Answer> answerList = personService.getListAnswersByQuestionId(question.getId());
        model.addAttribute("answerList", answerList);

        questionNumbesList = personService.getListNumbersOfQuestion(test.getId().intValue());
        model.addAttribute("questionNumbesList", questionNumbesList);

        AnswerBean ab = new AnswerBean();
        model.addAttribute("ab", ab);

        model.addAttribute("duration", duration);

        return "secondPage";
    }

    /**
     */
    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String add( Model model, @Valid @ModelAttribute("ab") AnswerBean ab, BindingResult result) {

        if (duration <= 0) { return "redirect:/krams/main/submit?anywhere=1";   }

        Question question = personService.getQuestionByID(Integer.parseInt(ab.getId()));
        List<Answer> answerList = personService.getListAnswersByQuestionId(question.getId());

        if (ab.getAnswer() == null ||ab.getAnswer().size() == 0 ) {
            marksMap.put(question.getNumber(), 0);
        }else {
          boolean answerCoorect = true;
            for (Answer a : answerList) {
                boolean found = false;
                for (Object o : ab.getAnswer()) {
                    int answerId = Integer.parseInt((String) o);
                    if (a.getId() == answerId && !a.getCorrect()) {
                        answerCoorect = false;
                    } else if (a.getId() == answerId) {
                        found = true;
                    }
                }

                if (a.getCorrect() && !found) {
                    answerCoorect = false;
                }
            }
            marksMap.put(question.getNumber(), answerCoorect ? 1 : 0);
        }

        int nextQuestionNumber = getNextQuestionNumber(question.getNumber());
        if (nextQuestionNumber != 0)
        {
            return "redirect:/krams/main/questions?question=" + nextQuestionNumber;
        }else
        {
            if (marksMap.size() == questionNumbesList.size())
            {
                return "redirect:/krams/main/submit?anywhere=1";
            }else
            {
                return "redirect:/krams/main/submit";
            }
        }

    }

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String toSubmit( Model model,  @RequestParam(value = "anywhere", required = false) Integer anywhere) {
        if (person == null) { return "redirect:/krams/main/auth";   }
        if (anywhere == null && duration > 0)
        {
            Test test = personService.getTest();
            List<Integer> listMissedQuestions = getListMissedQuestions(marksMap, questionNumbesList);
            model.addAttribute("questionNumbesList", listMissedQuestions);
            model.addAttribute("duration", duration);
            return "endPage";
        }else
        {
            int score =  getScore();
            personService.saveUserScore(score, person);
            model.addAttribute("score", score);
            return "endPage";
        }
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit( Model model) {

        int score =  getScore();
        personService.saveUserScore(score, person);
        model.addAttribute("score", score);
        return "endPage";

    }


    @RequestMapping(value = "/timer", method = RequestMethod.GET)
    public String setTimer(@RequestParam(value = "duration", required = true) int duration) {

        if (this.duration > duration) this.duration = duration;
        return "OK";

    }


    private int getNextQuestionNumber(int questionNumber)
    {
        if (questionNumber == questionNumbesList.size())
            return 0;

        if (marksMap.get(questionNumber+1) == null)
            return questionNumber+1;
        else return getNextQuestionNumber(questionNumber + 1);
    }

    private List<Integer> getListMissedQuestions(Map<Integer, Integer> marksMap, List<Question> listNubers) {
        List<Integer> missedList = new ArrayList<Integer>();
        for (Question q : listNubers)
        {
            if (marksMap.get(q.getNumber())==null)
            {
                missedList.add(q.getNumber());
            }
        }
        return missedList;
    }

    private int getScore() {
        int score = 0;
        for (Map.Entry<Integer, Integer> pair : marksMap.entrySet())
        {
            score += pair.getValue();
        }
        return score;
    }


}
