package com.electronic.electronic.utils;

import com.electronic.electronic.repositories.PaiementRepository;

import java.security.SecureRandom;
import java.util.Random;

public class ReferenceGenerator {
    private static final Random RANDOM = new SecureRandom();


    public static String genererReferencePaiement() {
        // Génère un nombre entre 100000 et 999999
        int reference = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(reference);
    }


    public static boolean estReferenceUnique(String reference, PaiementRepository paiementRepository) {
        return !paiementRepository.existsByReferencePaiement(reference);
    }


    public static String genererReferenceUnique(PaiementRepository paiementRepository) {
        String reference;
        do {
            reference = genererReferencePaiement();
        } while (!estReferenceUnique(reference, paiementRepository));
        return reference;
    }
}
