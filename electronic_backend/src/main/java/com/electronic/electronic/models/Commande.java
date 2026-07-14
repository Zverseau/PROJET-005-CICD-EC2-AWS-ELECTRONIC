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
public class Commande extends AuditTable implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @Column(nullable = false)
    private String nomClient;
    @Column(nullable = false)
    private String prenomClient;
    @Column(nullable = false)
    private String adresseClient;
    @Column(nullable = false)
    private String telephoneClient;
    @Enumerated(EnumType.STRING)
    private StatutCommande statutCommande;
    @OneToMany(mappedBy = "commande",
            cascade = CascadeType.ALL)
    private List<LigneCommande> lignesCommande;

    @OneToOne(mappedBy = "commande",
            cascade = CascadeType.ALL)
    private Paiement paiement;
}
