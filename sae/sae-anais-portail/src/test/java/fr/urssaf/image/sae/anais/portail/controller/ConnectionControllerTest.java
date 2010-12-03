package fr.urssaf.image.sae.anais.portail.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import fr.urssaf.image.sae.anais.portail.form.ConnectionForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-servlet.xml" })
@SuppressWarnings( { "PMD.JUnitAssertionsShouldIncludeMessage" })
public class ConnectionControllerTest {

   @Autowired
   private ConnectionController servlet;

   @Test
   public void getDefaultView() {
      Model model = new BindingAwareModelMap();
      assertEquals("connection/connection", servlet.getDefaultView(model));
   }

   @Test
   public void connectSuccess() {

      ConnectionForm form = new ConnectionForm();
      form.setUserLogin("CER6990010");
      form.setUserPassword("CER6990010");

      BindingResult result = new BeanPropertyBindingResult(null, null);
      Model model = new BindingAwareModelMap();

      assertEquals("forward:/success.html", servlet
            .connect(form, result, model));

   }

   @Test
   @Ignore
   public void connectFailure() {

      ConnectionForm form = new ConnectionForm();
      form.setUserLogin("CER6990010");
      form.setUserPassword("");

      BindingResult result = new BeanPropertyBindingResult(null, null);
      Model model = new BindingAwareModelMap();

      assertEquals("connection/connection", servlet
            .connect(form, result, model));

   }

   @Test
   public void connectException() {

      ConnectionForm form = new ConnectionForm();
      form.setUserLogin("CER6990012");
      form.setUserPassword("inconnu");

      BindingResult result = new BeanPropertyBindingResult(null, null);

      Model model = new BindingAwareModelMap();
      

      assertEquals("connection/connection_failure", servlet.connect(form,
            result, model));

   }
}
