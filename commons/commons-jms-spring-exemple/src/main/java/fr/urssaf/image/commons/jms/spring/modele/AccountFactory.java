package fr.urssaf.image.commons.jms.spring.modele;

/**
 * Factory pour Account
 * 
 * 
 */
public final class AccountFactory {
   
   private AccountFactory(){
      
   }

   /**
    * instanciation d'un objet {@link Account}
    * 
    * @param firstname
    *           prénom-
    * @param lastname
    *           nom
    * @return instance de compte
    */
   public static Account createAccount(String firstname, String lastname) {

      Account account = new Account();
      account.setFirstname(firstname);
      account.setLastname(lastname);
      account.setIdAccount(getSequence());

      return account;
   }

   private static long sequence = 0;

   protected static long getSequence() {

      synchronized (AccountFactory.class) {
         try {
            return sequence;
         } finally {
            sequence++;
         }
      }

   }
}
