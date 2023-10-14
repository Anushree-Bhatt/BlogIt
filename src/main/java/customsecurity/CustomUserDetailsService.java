	package customsecurity;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import entity.*;


import entity.Role;
import repository.UserRepository;

@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	private Logger log = Logger.getLogger(CustomUserDetailsService.class.getCanonicalName());
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameoremail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		entity.User user =  userRepository.findByEmailOrUsername(usernameoremail, usernameoremail).orElseGet(() -> {throw new UsernameNotFoundException("User is not found!!");});
		log.info(user+"");
		return new User(user.getUsername(), user.getPassword(), authorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> authorities(Set<Role> roles) {
		// TODO Auto-generated method stub
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
	

}
