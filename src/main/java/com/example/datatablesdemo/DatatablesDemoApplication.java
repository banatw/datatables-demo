package com.example.datatablesdemo;

import com.example.datatablesdemo.entity.Mahasiswa;
import com.example.datatablesdemo.repository.MahasiswaRepository;
import com.github.javafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatatablesDemoApplication implements CommandLineRunner {
	private MahasiswaRepository repo;

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
			Mahasiswa mahasiswa = new Mahasiswa(faker.name().fullName());
			this.repo.save(mahasiswa);
		}
	}

}
