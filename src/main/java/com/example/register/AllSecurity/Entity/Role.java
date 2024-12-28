package com.example.register.AllSecurity.Entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Getter
@Setter



@Table(name="role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Enumerated(EnumType.STRING)
    private TypeRole  libelle;


    public TypeRole getLibelle() {
        return libelle;
    }
}
