package fr.urssaf.image.commons.controller.spring.exemple.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionController implements
		org.springframework.web.servlet.mvc.Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("exceptionView");
	}

}
