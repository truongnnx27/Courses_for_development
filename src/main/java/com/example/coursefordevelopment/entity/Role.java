package com.example.coursefordevelopment.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;
    @Override
    public String toString() {
        return "Role{id=" + id + ", roleName='" + roleName + "'}"; // Chỉ hiển thị những thông tin cần thiết
    }
}
