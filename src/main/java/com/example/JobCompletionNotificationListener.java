package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final CityCountryRepository cityCountryRepository;

	@Autowired
	public JobCompletionNotificationListener(CityCountryRepository cityCountryRepository) {
		this.cityCountryRepository = cityCountryRepository;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<CityCountry> results = cityCountryRepository.findAll();
			/*List<Place> results = jdbcTemplate.query("SELECT c.places FROM CityCountry c", new RowMapper<Place>() {
				@Override
				public Place mapRow(ResultSet rs, int row) throws SQLException {
					return (Place)rs.getObject(1);
				}
			});*/

			for (Place p : results.get(0).getPlaces()) {
				log.info("Found <" + p + "> in the database.");
			}

		}
	}
}
