package com.azhel.ist41.controller;

import net.azhel.algorithm.AlgorithmOptimization;
import net.azhel.algorithm.function.Function;
import net.azhel.algorithm.simplex.SimplexMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/*")
public class SimplexController {
    @RequestMapping(value = "simplex",method = RequestMethod.GET)
    public String simplex(ModelMap model, Principal principal){
        //username
        String name = principal.getName(); //get logged in username
        model.addAttribute("username", name);
        return "views/simplex";
    }
    @RequestMapping(value = "simplex/result",method = RequestMethod.POST)
    public String simplexCalculate(ModelMap model, @RequestParam String targetFunction,
                                   @RequestParam String countLimit,
                                   @RequestParam String MaxMin,
                                   @RequestParam Map<String,String> allRequestParams, Principal principal){
        List<String> limits = new ArrayList<>();
        for (int i = 0; i < Integer.valueOf(countLimit); i++) {
            limits.add(allRequestParams.get("limit" + i));
        }
        Function function = new Function(targetFunction, limits);
        SimplexMethod simplexMethod = new SimplexMethod(function, MaxMin);
        int[] answerArgument = simplexMethod.calculate();
        List<String[][]> processTable = simplexMethod.getProgressCalculate();
        List<String> variables = new ArrayList<>(function.getVariables());
        String name = principal.getName(); //get logged in username
        //Mode
        model.addAttribute("username", name);
        model.addAttribute("targetFunction", targetFunction);
        model.addAttribute("countLimit", countLimit);
        model.addAttribute("limits", limits);
        model.addAttribute("processTable", processTable);
        model.addAttribute("variables", variables);
        model.addAttribute("variablesJoin", String.join(", ", variables));
        model.addAttribute("answerArgument", answerArgument);
        model.addAttribute("answer", function.f(answerArgument));
        model.addAttribute("maxMin", MaxMin);
        return "views/simplex";
    }
}
