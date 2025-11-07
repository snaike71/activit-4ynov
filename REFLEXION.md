# Réflexion sur le refactoring MavenSensorHub

## Ce qui a été fait

J'ai créé deux nouveaux modules pour réduire les dépendances croisées :

1. **`shared-model`** : contient les classes partagées (SensorData, SensorType, ISensor) qui étaient dans sensor-data-collection mais utilisées partout
2. **`data-api`** : contient uniquement les interfaces IDataManager et IDataProcessor, pour que user-interface ne dépende pas directement de data-management

## Dépendances supprimées

Avant, on avait plein de dépendances croisées :
- data-management → sensor-data-collection
- report-generation → sensor-data-collection  
- user-interface → sensor-data-collection
- user-interface → data-management

Maintenant :
- Tout le monde dépend de `shared-model` au lieu de `sensor-data-collection`
- `user-interface` dépend de `data-api` (juste les interfaces) au lieu de `data-management` directement

Résultat : plus de dépendances croisées problématiques !

## Principes appliqués

- **Séparation des responsabilités** : chaque module a un rôle clair
- **DIP (Dependency Inversion Principle)** : on dépend des abstractions (interfaces) plutôt que des implémentations
- **Cohésion** : les classes sont regroupées par fonctionnalité

## Configuration Maven

J'ai centralisé les versions dans le POM parent et activé le `maven-enforcer-plugin` pour :
- Forcer Java 21
- Vérifier la convergence des dépendances

Ça évite les problèmes de versions différentes entre modules.

## Résultat

Tous les tests passent (26/26) et l'architecture est beaucoup plus propre. Les modules peuvent maintenant évoluer indépendamment sans se marcher dessus.
