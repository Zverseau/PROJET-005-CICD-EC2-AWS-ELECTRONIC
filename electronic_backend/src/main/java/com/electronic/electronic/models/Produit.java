package com.electronic.electronic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produit extends AuditTable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @Column(nullable = false)
    private String nomProduit;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private double prixUnitaire;
    @Column(nullable = false)
    private String photoProduit;
    @Column(nullable = false)
    private boolean actif;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
    @OneToMany(mappedBy = "produit")
    private List<LigneCommande> lignesCommande;
}
