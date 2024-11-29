# branches locales

## création

1. création d'un pointeur de branche pointant sur le commit courant HEAD

   * `git branch <branch_name>`

2. supprimer une branche: être toujours sur une autre branche
   * `git branch -d <branch_name>`
   * `git branch -D <branch_name>`: force: si on veut supprimer des commits non fusionnés

3. basculer de branche

   * `git checkout <branch_name>`

4. basculer proprement en cas de modifications WIP
   * `git stash push -u -m <explanation-of-the-modifs>`

5. restaurer les modifications remisées
   * `git stash apply stash^{/explanation-of-the-modifs}`
   * `git stash drop | clear`

6. fusion normale

* se placer sur la branche de reception: `git checkout <rcv-branch>`
* fusionner : `git merge <send-branch>`
  - s'il y a un commit intermittent sur la branche de reception ET pas de conflit
    => commit de fusion
  - idem mais conflit : résolution de conflit (cd infra)
  - pas de commit intermittent: fast-forward 

7. fusion non fast forward

  * par défaut la fusion sans intermittant dabs la branche de réception ne crée pas de commit de fusion 
    => ajoute directement les commits de la branche d'envoi sur la branche de reception
  * pour forcer un commit de fusion:
    - one-shot: `git merge --no-ff <send-branch>`
    - par défaut: `git config --global merge.ff false`
 