test:
	./mvnw test

coverage:
	mvn jacoco:report
	open target/site/jacoco/index.html

verify:
	./mvnw verify

run:
	./mvnw spring-boot:run

clean:
	./mvnw clean
