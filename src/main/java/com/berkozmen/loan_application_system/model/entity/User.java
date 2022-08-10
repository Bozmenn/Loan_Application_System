package com.berkozmen.loan_application_system.model.entity;

import com.berkozmen.loan_application_system.annotation.IdentityNumberValidation;
import com.berkozmen.loan_application_system.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
//@ApiModel(value = "User Api model documentation", description = "Model")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@ApiModelProperty(value = "Unique id field of User object")
    private Long id;

    @Column(nullable = false,unique = true)
    private String identityNumber;

    @Column()
    //@ApiModelProperty(value = "User name field of User object")
    private String name;

    @Column()
    //@ApiModelProperty(value = "User name field of User object")
    private String surname;

    @Column(nullable = false)
    //@ApiModelProperty(value = "Password field of User object")
    private String password;

    @Column()
    //@ApiModelProperty(value = "Email field of User object")
    private Long monthlySalary;

    @Column()
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_score_id")
    private CreditScore creditScore;

    @ElementCollection(fetch = FetchType.EAGER)
    //@ApiModelProperty(value = "Roles field of User object")
    private List<Role> roles;

    public User(String identityNumber, String password) {
        this.identityNumber = identityNumber;
        this.password = password;
    }
}
