package ru.zhidev.lunch_voting_system;

import org.springframework.boot.SpringApplication;

public class TestLunchVotingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(LunchVotingSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
