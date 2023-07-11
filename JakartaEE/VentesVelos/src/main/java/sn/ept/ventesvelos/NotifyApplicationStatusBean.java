package sn.ept.ventesvelos;

 import jakarta.annotation.PostConstruct;
 import jakarta.annotation.PreDestroy;
 import jakarta.ejb.Singleton;
 import jakarta.ejb.Startup;
 import jakarta.inject.Inject;

 @Singleton
 @Startup
public class NotifyApplicationStatusBean {
     @Inject
     private SendEmailBean emailSender;

     @PostConstruct
     public void appStart() {
         emailSender.sendEmail("tunknowed@gmail.com", "Démarrage de l'application", "L'application a été démarrée");
     }

     @PreDestroy
     public void appStop() {
         emailSender.sendEmail("tunknowed@gmail.com", "Arrêt de l'application", "L'application a été arrêtée");
     }
}
