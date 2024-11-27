# build du pipeline maven

## version agent "any" => exécution sur jenkins en local

* guide : [doc](https://www.jenkins.io/blog/2017/02/07/declarative-maven-project/)

1. préparer les outils prérequis
  - maven
  - un jdk 17

2. ajouter maven et le jdk dans 
  - Administrer Jenkins
  - tools
  - maven > nom  + version
  - jdk > nom

3. le plugin maven + pipeline

4. ajouter la section tools dans le Jenkinsfile pour selectionner les éléments prréexistant

5. scrutation du dépôt git
  - version naïve: 
     + projet dev 
     + configurer 
     + section Triggers
     + scrutation SCM > planning H/4 * * * *
  - version performante
     + hook post-commit 

### job units

* stage TEST
  - steps: sh 'mwn test' > puisque les plugins de rapports test / couverture dans le pom.xml
  - post: remontée des rapports
    + junit pour surefire
    + recordCoverage pour jacoco 