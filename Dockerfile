FROM openjdk:11 
VOLUME /tmp        
EXPOSE 8080       
ADD ./target/casa-do-codigo-0.0.1-SNAPSHOT.jar nome.jar    
ENTRYPOINT ["java","-jar","/nome.jar"]   