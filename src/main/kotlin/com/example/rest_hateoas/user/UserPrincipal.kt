package com.example.rest_hateoas.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserPrincipal(user: User) : UserDetails {
    val id = user.id
    private val password = user.password
    private val username = user.username
    private val enabled: Boolean
    private val groups: Set<GrantedAuthority>

    init {
        this.enabled = user.enabled
        this.groups = user.groups.stream()
            .filter { g: UserGroup -> g.enabled }
            .map<SimpleGrantedAuthority> { g: UserGroup -> SimpleGrantedAuthority(g.name) }
            .collect(Collectors.toSet<GrantedAuthority>())
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return groups
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        // TODO implement account expired
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        // TODO implement account locked
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        // TODO implement credential expiry
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}
