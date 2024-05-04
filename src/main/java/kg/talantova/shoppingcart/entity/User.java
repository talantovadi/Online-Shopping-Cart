package kg.talantova.shoppingcart.entity;

import jakarta.persistence.*;
import kg.talantova.shoppingcart.enums.Roles;
import kg.talantova.shoppingcart.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.management.relation.Role;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;

    @Column(length = 150, unique = true)
    private String email;

    private String password;
    @ManyToMany()
    @JoinTable(
            name = "users_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> userCart;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user")
    private ConfirmationToken confirmationToken;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
