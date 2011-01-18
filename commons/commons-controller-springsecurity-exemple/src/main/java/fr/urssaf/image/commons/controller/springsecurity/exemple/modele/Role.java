package fr.urssaf.image.commons.controller.springsecurity.exemple.modele;

public enum Role {

   role_admin("ROLE_ADMIN"), role_user("ROLE_USER");

   String authority;

   Role(String authority) {
      this.authority = authority;
   }

   public String getAuthority() {
      return this.authority;
   }
}
