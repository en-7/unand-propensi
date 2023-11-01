package protensi.sita.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll().antMatchers("/js/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login-sso", "/validate-ticket").permitAll()
                .antMatchers("/create-dummy").permitAll()
                .antMatchers("/create-dummy-whitelist").permitAll()
                // .antMatchers("/obat/viewall").hasAnyRole( "ADMIN", "APOTEKER")
                // .antMatchers("/resep/viewall").hasAnyRole( "ADMIN", "APOTEKER")
                // .antMatchers("/appointment/viewall").not().hasRole("APOTEKER")
                // .antMatchers("/obat/update").hasRole("APOTEKER")
                // .antMatchers("/appointment/viewall").not().hasRole("APOTEKER")
                .antMatchers("/mahasiswa/**").hasRole("ADMIN")
                // .antMatchers("/pasien/**").hasRole("ADMIN")
                // .antMatchers("/dokter/**").hasRole("ADMIN")
                .antMatchers("/whitelist/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    /*
     * @Autowired
     * public void configureGlobal(AuthenticationManagerBuilder auth) throws
     * Exception {
     * auth.inMemoryAuthentication()
     * .passwordEncoder(encoder())
     * .withUser("admin")
     * .password(encoder().encode("admin"))
     * .roles("ADMIN");
     * auth.inMemoryAuthentication()
     * .passwordEncoder(encoder())
     * .withUser("dokter")
     * .password(encoder().encode("dokter"))
     * .roles("DOKTER");
     * auth.inMemoryAuthentication()
     * .passwordEncoder(encoder())
     * .withUser("apoteker")
     * .password(encoder().encode("apoteker"))
     * .roles("APOTEKER");
     * auth.inMemoryAuthentication()
     * .passwordEncoder(encoder())
     * .withUser("pasien")
     * .password(encoder().encode("pasien"))
     * .roles("PASIEN");
     * }
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
