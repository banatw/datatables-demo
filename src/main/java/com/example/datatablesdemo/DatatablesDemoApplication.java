package com.example.datatablesdemo;

import com.example.datatablesdemo.entity.AppUser;
import com.example.datatablesdemo.entity.City;
import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.repository.AppUserRepository;
import com.example.datatablesdemo.repository.CityRepo;
import com.example.datatablesdemo.repository.MahasiswaRepository;
import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DatatablesDemoApplication implements CommandLineRunner {
	@Autowired
	private MahasiswaRepository repo;
	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private AppUserRepository appUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(DatatablesDemoApplication.class, args);
	}

	public DatatablesDemoApplication(MahasiswaRepository repository) {
		this.repo = repository;
	}

	@Override
	public void run(String... args) throws Exception {
		Faker faker;
		for (int i = 0; i < 100; i++) {
			faker = new Faker();
			Mahasiswa mahasiswa = new Mahasiswa(faker.name().fullName(), faker.address().fullAddress());
			cityRepo.save(new City(faker.address().city()));
			repo.save(mahasiswa);
		}

		AppUser appUser = new AppUser();
		appUser.setUsername("admin");
		appUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
		appUser.setRole("ROLE_ADMIN");
		appUserRepository.save(appUser);
	}

}
