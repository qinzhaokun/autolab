package com.autolab.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishuang on 15/4/3.
 */

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "restservice";


    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {


            //先禁用了Oauth2权限拦截。
            http.formLogin()
                    .loginPage("/user/login").permitAll()
                    .and().authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated();


        }


    }


    @Autowired
    DataSource dataSource;


    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Value("${autolab.client.secret}")
        String clientSecret;

        @Autowired
        @Qualifier("authenticationManagerBean")
        AuthenticationManager authenticationManager;

        @Autowired
        TokenStore tokenStore;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {




            endpoints
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .setClientDetailsService(clientDetailsService());


            TokenGranter oldTokenGranter = endpoints.getTokenGranter();
            List<TokenGranter> granters = new ArrayList<>();
            granters.add(oldTokenGranter);
            granters.add(new JaccountTokenGranter(
                    authenticationManager,
                    endpoints.getTokenServices(),
                    endpoints.getClientDetailsService(),
                    endpoints.getOAuth2RequestFactory()));
            endpoints.tokenGranter(new CompositeTokenGranter(granters));

        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService());
        }

        private ClientDetailsService clientDetailsService;

        private ClientDetailsService clientDetailsService() throws Exception {
            if (clientDetailsService == null) {
                InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
                builder.withClient("clientapp")
                        .authorizedGrantTypes("password", "refresh_token", JaccountTokenGranter.GRANT_TYPE)
                        .authorities("USER")
                        .scopes("read", "write")
                        .resourceIds(RESOURCE_ID)
                        //有效时间24小时
                        .accessTokenValiditySeconds(8640000)

                        .secret(clientSecret);
                clientDetailsService = builder.build();
            }
            return clientDetailsService;
        }


    }

}

