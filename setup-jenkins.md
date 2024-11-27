# SETUP JENKINS

---

## structure

### vM

* VM devian + git + docker + java 17
* stack jenkins avec
   - gestion du TLS (non signé)
   - extra_hosts car pas de service dns (maquette)

### conteneur / image jenkins

* `~/jenkins/compose.yml` gère le lancement de jenkins
```bash
# lancement, dans le dossier contenant compose.yml
docker compose up -d
# arrêt
docker compose down
# checker
docker compose ps
``` 

### type de jenkins

* pipeline:
   - défini par Jenkinsfile
   - fichier contenu dans un dépôt git
   - scruté par le projet jenkins
   - utilisant l'agent docker => chaque job automatisé exécuté dans un conteneur

* procédure:
   - Administrer Jenkins
   - plugins
   - plugins disponibles
     + pipeline
     + git

---

## projet jenkins

### création

* Dashboard
  - new item => appelé **dev**
  - type "pipeline"

### configuration du projet
  - bypasser la confirmation "known_hosts"
    + Administrer Jenkins
    + sécurité
    + section "Git Host Key Verification Configuration" > **no verification**
  - espace du projet dev
  - configurer
  - section pipeline
    + definition => **pipeline from SCM**
    + SCM > git
    + URL: git@jenkins.myusine.fr:dev.git
    + créer un **credential** (cf infra)
  - ajouter credential
    + identifiants globaux (illimité)
    + ssh username et clé
    + id: jenkins-pkey
    + username: **git**
    + contenu: de la clé privée `~/.ssh/jenkins` (côté host)
    + la passphrase: **roottoor**