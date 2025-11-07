# Plan de Refactoring - MavenSensorHub

## Architecture cible

### Objectif
Réduire drastiquement les dépendances croisées entre modules en appliquant les principes de cohésion et de séparation des responsabilités.

### Nouvelle structure des modules

```
MavenSensorHub (parent)
├── utils (aucune dépendance interne)
├── shared-model (nouveau - aucune dépendance interne)
│   └── Contient : SensorData, SensorType, ISensor
├── sensor-data-collection
│   └── Dépend de : shared-model, utils
├── data-management
│   └── Dépend de : shared-model, utils
├── report-generation
│   └── Dépend de : shared-model, utils
├── user-interface
│   └── Dépend de : shared-model, data-management (via interface)
├── main-application
│   └── Dépend de : tous les autres modules (normal)
└── test
    └── Dépend de : tous les modules (normal)
```

## Analyse des dépendances actuelles

### Problèmes identifiés

1. **sensor-data-collection** contient des classes partagées :
   - `SensorData` (record) - utilisé par data-management, report-generation, user-interface
   - `SensorType` (enum) - utilisé par data-management, report-generation, user-interface
   - `ISensor` (interface) - utilisé par data-management

2. **Dépendances croisées** :
   - `data-management` → `sensor-data-collection` (pour SensorData, SensorType, ISensor)
   - `report-generation` → `sensor-data-collection` (pour SensorData)
   - `user-interface` → `sensor-data-collection` (pour SensorData, SensorType)
   - `user-interface` → `data-management` (pour IDataManager)

3. **Violation du principe de dépendance inversée (DIP)** :
   - Les modules dépendent directement de `sensor-data-collection` au lieu d'une abstraction partagée

## Solution : Création du module shared-model

### Principe
Créer un module dédié aux modèles partagés (SPI - Service Provider Interface) qui contiendra :
- Les classes de données communes (records, enums)
- Les interfaces communes utilisées par plusieurs modules

### Avantages
- Réduction des dépendances croisées
- Respect du principe de séparation des préoccupations
- Application légère du DIP (Dependency Inversion Principle)
- Meilleure maintenabilité et évolutivité

## Liste des déplacements prévus

### 1. Création du module shared-model
- Créer le dossier `shared-model/`
- Créer `shared-model/pom.xml`
- Créer la structure de packages `com/jad/sharedmodel/`

### 2. Déplacement des classes depuis sensor-data-collection vers shared-model
- `SensorData.java` → `shared-model/src/main/java/com/jad/sharedmodel/SensorData.java`
- `SensorType.java` → `shared-model/src/main/java/com/jad/sharedmodel/SensorType.java`
- `ISensor.java` → `shared-model/src/main/java/com/jad/sharedmodel/ISensor.java`

### 3. Mise à jour des imports
- Dans `sensor-data-collection` : mettre à jour les imports pour utiliser `com.jad.sharedmodel`
- Dans `data-management` : remplacer `com.jad.sensordata` par `com.jad.sharedmodel`
- Dans `report-generation` : remplacer `com.jad.sensordata` par `com.jad.sharedmodel`
- Dans `user-interface` : remplacer `com.jad.sensordata` par `com.jad.sharedmodel`
- Dans `main-application` : mettre à jour les imports si nécessaire

### 4. Mise à jour des POMs

#### POM parent
- Ajouter `shared-model` dans la liste des modules
- Centraliser les versions et plugins (déjà fait partiellement)
- Activer `maven-enforcer-plugin` avec règles Java 21 et convergence

#### POM shared-model
- Aucune dépendance interne (sauf utils si nécessaire)
- Version Java 21

#### POM sensor-data-collection
- Ajouter dépendance vers `shared-model`
- Supprimer les classes déplacées (SensorData, SensorType, ISensor)
- Garder uniquement : AbstractSensor, SensorFactory, ISensorFactory, et les implémentations concrètes

#### POM data-management
- Remplacer dépendance `sensor-data-collection` par `shared-model`
- Garder dépendance `utils`

#### POM report-generation
- Remplacer dépendance `sensor-data-collection` par `shared-model`
- Garder dépendance `utils`

#### POM user-interface
- Remplacer dépendance `sensor-data-collection` par `shared-model`
- Garder dépendance `data-management` (via interface IDataManager)

#### POM main-application
- Ajouter dépendance vers `shared-model`
- Garder toutes les autres dépendances

#### POM test
- Ajouter dépendance vers `shared-model`
- Garder toutes les autres dépendances

## Ordre d'exécution

1. ✅ Générer `dependency-tree.before.txt`
2. Créer le module `shared-model` avec sa structure
3. Déplacer les classes partagées
4. Mettre à jour les imports dans tous les modules
5. Mettre à jour tous les POMs
6. Vérifier la compilation : `mvn clean compile`
7. Exécuter les tests : `mvn clean verify`
8. Générer `dependency-tree.after.txt`
9. Mettre à jour le diagramme de packages
10. Compléter `REFLEXION.md`

## Résultat attendu

### Avant
- `data-management` → `sensor-data-collection`
- `report-generation` → `sensor-data-collection`
- `user-interface` → `sensor-data-collection`
- `user-interface` → `data-management`

### Après
- `data-management` → `shared-model`
- `report-generation` → `shared-model`
- `user-interface` → `shared-model`
- `user-interface` → `data-management` (via interface, acceptable)
- `sensor-data-collection` → `shared-model`

**Réduction des dépendances croisées :** Les modules ne dépendent plus directement de `sensor-data-collection` mais d'un module de modèles partagés, respectant mieux la séparation des préoccupations.

