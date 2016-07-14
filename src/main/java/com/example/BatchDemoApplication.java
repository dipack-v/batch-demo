package com.example;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.example.wsdl.GetCitiesByCountry;
import com.example.wsdl.GetCitiesByCountryResponse;

@SpringBootApplication
public class BatchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchDemoApplication.class, args);
	}
	
	/*@Bean
	CommandLineRunner lookup(WeatherClient weatherClient) {
		return args -> {
			String zipCode = "94304";

			if (args.length > 0) {
				zipCode = args[0];
			}
			GetCitiesByCountryResponse response = weatherClient.getCityForecastByZip("India");
			System.out.println(response.getGetCitiesByCountryResult());
			//weatherClient.printResponse(response);
		};
	}*/
}





@Configuration
@EnableBatchProcessing
class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
   
    @Autowired
    public DataSource dataSource;
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    CityCountryRepository cityCountryRepository;

    // tag::readerwriterprocessor[]
    @Bean
    public WebServiceItemReader<GetCitiesByCountryResponse> reader() {
    	WebServiceItemReader<GetCitiesByCountryResponse> reader = new WebServiceItemReader<GetCitiesByCountryResponse>();
        reader.setRequest(new GetCitiesByCountry(){{
        	setCountryName("India");
        	
        }});
        reader.setMarshaller(marshaller());
        reader.setUnmarshaller(marshaller());
       
        return reader;
    }

    @Bean
    public PlaceItemProcessor processor() {
        return new PlaceItemProcessor(){{setUnmarshaller(getMarshaller());}};
    }

    @Bean
    public JpaItemWriter<CityCountry> writer() {
    	JpaItemWriter<CityCountry> writer = new JpaItemWriter<CityCountry>();
    	writer.setEntityManagerFactory(entityManagerFactory);
    
    	
    	
     
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::listener[]

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener(cityCountryRepository);
    }

    // end::listener[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<GetCitiesByCountryResponse, CityCountry> chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
    
    @Bean
	public Jaxb2Marshaller getMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(CityCountry.class,Place.class);
		return marshaller;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.example.wsdl");
		return marshaller;
	}

	@Bean
	public WeatherClient weatherClient(Jaxb2Marshaller marshaller) {
		WeatherClient client = new WeatherClient();
		client.setDefaultUri("http://ws.cdyne.com/WeatherWS");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
