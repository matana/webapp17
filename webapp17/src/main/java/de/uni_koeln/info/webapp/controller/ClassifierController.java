package de.uni_koeln.info.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.textengineering.ir.classifier.TextClassifier;

@Controller
@RequestMapping(value = "classify/")
public class ClassifierController {

	@Autowired
	private TextClassifier knn;
	
}
