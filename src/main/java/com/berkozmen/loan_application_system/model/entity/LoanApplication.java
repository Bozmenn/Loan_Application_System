package com.berkozmen.loan_application_system.model.entity;

import com.berkozmen.loan_application_system.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_applications")
//@ApiModel(value = "User Api model documentation", description = "Model")
public class LoanApplication {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long allowedCreditLimit;


}
