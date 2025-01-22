
# Application de Covoiturage Local

Bienvenue dans le projet d'application de covoiturage local. Cette application est développée en utilisant Android Studio et respecte le modèle d'architecture MVC (Model-View-Controller).

## Table des Matières

- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Technologies Utilisées](#technologies-utilisées)
- [Structure du Projet](#structure-du-projet)
- [Installation](#installation)
- [Contributions](#contributions)
- [Licence](#licence)

## Aperçu

L'application de covoiturage local permet aux utilisateurs de :
- Rechercher des trajets disponibles.
- Proposer des trajets.
- Gérer leurs réservations et trajets.

## Fonctionnalités

1. **Authentification** : Connexion/inscription des utilisateurs.
2. **Recherche de Trajets** : Recherche et filtrage des trajets par destination.
3. **Proposition de Trajets** : Ajout de trajets par les conducteurs.
4. **Gestion des Réservations** : Suivi des trajets réservés et proposés.
5. **Profil Utilisateur** : Affichage et édition des informations personnelles.

## Technologies Utilisées

- **Langage** : Kotlin
- **Base de données** : Firebase
- **Interface utilisateur** : XML, Material Design
- **Architecture** : MVC (Model-View-Controller)

## Structure du Projet
Voici la structure du projet, organisée selon le modèle MVC :

```
app/
├── java/com/example/covoiturage
│   ├── model/
│   │   ├── Utilisateur.kt                // Modèle utilisateur
│   │   ├── Trajet.kt                // Modèle trajet
│   │   ├── Reservation.kt         // Modèle réservation
│   │   └── Conducteur.kt   
│   │   └── DatabaseManager.kt     // Gestion des données avec Realm
│   │
│   ├── view/
│   │   ├── LoginActivity.kt       // Écran de connexion
│   │   ├── SignupActivity.kt      // Écran d'inscription
│   │   ├── HomeActivity.kt        // Écran d'accueil
│   │   ├── RideDetailsActivity.kt // Détails d'un trajet
│   │   └── ProfileActivity.kt     // Profil utilisateur
│   │
│   ├── controller/
│   │   ├── AuthController.kt      // Contrôleur pour l'authentification
│   │   ├── RideController.kt      // Contrôleur pour les trajets
│   │   ├── ReservationController.kt // Contrôleur pour les réservations
│   │   └── ProfileController.kt   // Contrôleur pour le profil
│   │
│   └── utils/
│       ├── Constants.kt           // Fichiers de constantes
│       └── Helpers.kt             // Fonctions utilitaires
│
├── res/
│   ├── layout/                    // Fichiers XML pour les interfaces utilisateur
│   ├── drawable/                  // Ressources graphiques
│   ├── values/                    // Fichiers de ressources comme strings.xml
│   └── mipmap/                    // Icônes de l'application
│
└── AndroidManifest.xml            // Configuration de l'application
```

## Installation

1. Clonez le dépôt :  
   ```bash
   git clone https://github.com/GildasEdoh/carpooling-project.git
   ```
2. Ouvrez le projet dans Android Studio.
3. Configurez Firebase dans le fichier `DatabaseManager.kt`.
4. Compilez et exécutez l'application sur un émulateur ou un appareil Android.

## Contributions

Les contributions sont les bienvenues ! Veuillez ouvrir une issue ou soumettre une pull request.

## Licence

Ce projet est sous licence [MIT](LICENSE).

---
