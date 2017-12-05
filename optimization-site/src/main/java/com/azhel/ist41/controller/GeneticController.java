package com.azhel.ist41.controller;

import net.azhel.algorithm.function.Function;
import net.azhel.algorithm.genetic.GeneticAlgorithm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class GeneticController {
    @RequestMapping(value = "genetic",method = RequestMethod.GET)
    public String genetic(ModelMap model, Principal principal){
        //username
        String name = principal.getName(); //get logged in username
        model.addAttribute("username", name);
        return "views/genetic";
    }

    @RequestMapping(value = "genetic",method = RequestMethod.POST)
    public String geneticCalculate(ModelMap model, @RequestParam String targetFunction,
                                   @RequestParam String countLimit,
                                   @RequestParam int countChromosomeInGeneration,
                                   @RequestParam int countGeneration,
                                   @RequestParam String MaxMin,
                                   @RequestParam Map<String,String> allRequestParams, Principal principal){
        List<String> limits = new ArrayList<>();
        for (int i = 0; i < Integer.valueOf(countLimit); i++) {
            limits.add(allRequestParams.get("limit" + i));
        }
        Function function = new Function(targetFunction, limits);
        GeneticAlgorithm geneticAlgorithm =
                new GeneticAlgorithm(function, countGeneration, countChromosomeInGeneration, MaxMin);
        int[] answerArgument = geneticAlgorithm.calculate();
        int[][] progressGeneration = geneticAlgorithm.getProgressGeneration();
        List<String> variables = new ArrayList<>(function.getVariables());
        //username
        String name = principal.getName(); //get logged in username
        model.addAttribute("username", name);
        //Model
        model.addAttribute("targetFunction", targetFunction);
        model.addAttribute("countLimit", countLimit);
        model.addAttribute("limits", limits);
        model.addAttribute("variables", variables);
        model.addAttribute("variablesJoin", String.join(", ", variables));
        model.addAttribute("answerArgument", answerArgument);
        model.addAttribute("function", function);
        model.addAttribute("answer", function.f(answerArgument));
        model.addAttribute("maxMin", MaxMin);
        model.addAttribute("progressGeneration", progressGeneration);
        return "views/genetic";
    }
}
