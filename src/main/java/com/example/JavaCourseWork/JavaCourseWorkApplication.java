package com.example.JavaCourseWork;

import com.example.JavaCourseWork.model.User;
import com.example.JavaCourseWork.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;


@AllArgsConstructor
@SpringBootApplication
public class JavaCourseWorkApplication {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final FindByIndexNameSessionRepository<? extends Session> sessionRepository;

	@PostConstruct
	public void initAdmin(){
		String password = "Go3GiXZOanCF5FaNEzXK04fLBFWFnKGls0mYJ0ZK";
		if (!userRepository.existsByUsername("admin")) {
			User admin = new User("admin", "", User.Role.ADMIN);
			admin.setPassword(passwordEncoder.encode(password));
			userRepository.save(admin);
			System.out.println("************************************************\n\nAdmin password: " +
					password + "\n\n***************************************");
			for (Session i : sessionRepository.findByPrincipalName("admin").values()) {
				sessionRepository.deleteById(i.getId());
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(JavaCourseWorkApplication.class, args);
	}

}
