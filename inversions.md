# commandes d'inversions

## écraser / restaurer les modifs 

* `git checkout -- <fic> ...`

## désindexer des mofifs dans l'index

* `git reset -- <fic> ... | .`

## désaffiliation d'un fichier dans le dépôt

* `git rm [-r] <fic|dir> && git commit -m <msg>`: suppression phys + désaffiliation dans le dépôt 

* `git rm [-r] --cached <fic|dir> && git commit -m <msg>`: désaffiliation dans le dépôt 