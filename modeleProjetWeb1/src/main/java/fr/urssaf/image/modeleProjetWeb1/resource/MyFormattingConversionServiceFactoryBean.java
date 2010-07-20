package fr.urssaf.image.modeleProjetWeb1.resource;

import org.springframework.format.support.FormattingConversionServiceFactoryBean;



public class MyFormattingConversionServiceFactoryBean extends
      FormattingConversionServiceFactoryBean {

   @Override
   public void afterPropertiesSet() {
      super.afterPropertiesSet();
   }
}
