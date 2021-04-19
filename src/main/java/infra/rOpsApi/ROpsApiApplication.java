package infra.rOpsApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class ROpsApiApplication {
	/*@Autowired
	private ProductRepository productRepository;

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			productRepository.save(new Product(1, "iPad 4 Mini", 500.01, 2));
			productRepository.save(new Product(2, "H&M T-Shirt White", 10.99, 10));
			productRepository.save(new Product(3, "Charli XCX - Sucker CD", 19.99, 5));
		};
	}
	@Autowired
	private DataChartRepository dataChartRepository;
	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			dataChartRepository.getDataChartByPeriod();
		};
	}*/
	public static void main(String[] args) {
		/*new SpringApplicationBuilder()
				.sources(ROpsApiApplication.class)
				.build()
				.run(args);*/

		SpringApplication.run(ROpsApiApplication.class, args);
	}

}
