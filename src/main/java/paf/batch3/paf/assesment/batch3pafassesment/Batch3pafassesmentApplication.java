package paf.batch3.paf.assesment.batch3pafassesment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import paf.batch3.paf.assesment.batch3pafassesment.repo.ListingRepo;

@SpringBootApplication
public class Batch3pafassesmentApplication {
	// implements CommandLineRunner

	@Autowired
	ListingRepo listingRepo;

	public static void main(String[] args) {
		SpringApplication.run(Batch3pafassesmentApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// 	listingRepo.getListOfCountry();
		
	// }

}
