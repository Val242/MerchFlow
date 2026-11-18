# **MerchFlow – Complete DevOps Project Roadmap**

This is the updated end-to-end roadmap for **MerchFlow**, with checkboxes to track progress.

I've checked the API development phase against our previous decisions. Since your API is complete, I've marked that phase as completed. The remaining phases focus on testing, containerization, CI/CD, security, AWS deployment, and monitoring.

## **Phase 1: API Development**

Completed

### **1. Project setup**

- Create Spring Boot project using Spring Initializr.
- Configure Java and Maven.
- Configure project dependencies.
- Establish the `com.merchflow` package structure.
- Configure PostgreSQL database connectivity.
- Configure application properties.
- Configure environment-based database credentials.

### **2. API modules and functionality**

- Category management API.
- Product management API.
- Warehouse management API.
- Inventory management API.
- Customer management API.
- Order management API.
- Payment API (simulated payment processing).
- Shipment management API.

### **3. Application architecture**

- Create entity classes.
- Create repository interfaces.
- Create service classes.
- Create REST controllers.
- Create DTOs.
- Configure entity relationships.
- Implement request validation.
- Implement global exception handling.
- Organize API endpoints under `/api/v1`.
- Configure Spring Boot Actuator.
- Configure JPA/Hibernate.

### **4. Database preparation**

- Configure PostgreSQL.
- Prepare the database schema.
- Prepare the `backup.sql` database backup.
- Decide to restore the database using PostgreSQL tools rather than Flyway.
- Plan to use `psql` to restore the backup on the target server.

**Database decisions:**

- No Flyway.
- Use `backup.sql` for database restoration.
- Use `spring.jpa.hibernate.ddl-auto=validate` when the restored schema matches the application entities.
- Keep database credentials outside source code.

## **Phase 2: Testing and Build Automation**

Next phase

### **1. Testing setup**

- Configure JUnit 5.
- Configure Mockito.
- Configure Spring Boot Test.
- Configure Testcontainers.
- Configure PostgreSQL Testcontainers.

### **2. Application testing**

- Write service-layer unit tests.
- Write repository tests.
- Write controller/API tests.
- Test CRUD operations.
- Test order creation.
- Test inventory operations.
- Test payment processing.
- Test shipment operations.
- Test validation errors.
- Test exception handling.
- Test database interactions.

### **3. Maven build automation**

- Run `mvn clean`.
- Run `mvn test`.
- Run `mvn verify`.
- Run `mvn package`.
- Fix all build and test failures.
- Generate and inspect test reports.

**Expected outcome:** A tested Spring Boot application with a successful Maven build.

## **Phase 3: Docker Containerization**

### **1. Docker setup**

- Install Docker.
- Verify Docker installation.
- Understand Docker images and containers.
- Create a multi-stage Dockerfile.
- Create `.dockerignore`.
- Build the MerchFlow API image.
- Run the API container.
- Inspect container logs.
- Verify the API health endpoint.

### **2. PostgreSQL containerization**

- Create a PostgreSQL container.
- Configure database environment variables.
- Configure persistent storage using Docker volumes.
- Configure Docker networking.
- Test API-to-PostgreSQL connectivity.

### **3. Docker Compose**

- Create `docker-compose.yml`.
- Configure the API service.
- Configure the PostgreSQL service.
- Configure environment variables.
- Configure health checks.
- Configure service dependencies.
- Start services using Docker Compose.
- Test API endpoints inside the containerized environment.

**Expected outcome:** MerchFlow API and PostgreSQL running together using Docker Compose.

## **Phase 4: Jenkins CI/CD Pipeline**

### **1. Jenkins setup**

- Install Jenkins.
- Configure Jenkins.
- Install required plugins.
- Configure Java and Maven.
- Configure Docker access.
- Connect Jenkins to GitHub.
- Configure GitHub webhooks.
- Configure Jenkins credentials.

### **2. Jenkins pipeline**

- Create `Jenkinsfile`.
- Configure source code checkout.
- Configure Maven build stage.
- Configure automated testing stage.
- Configure test report publishing.
- Configure Docker image build.
- Configure image tagging.
- Configure artifact archiving.
- Configure pipeline notifications.
- Trigger builds automatically after GitHub pushes.

**Expected outcome:** Jenkins automatically builds and tests MerchFlow whenever code changes are pushed.

## **Phase 5: Nexus Artifact Management**

- Install Sonatype Nexus Repository.
- Configure Nexus.
- Create Maven hosted repository.
- Create Maven proxy repository.
- Configure Maven `settings.xml`.
- Configure Nexus credentials in Jenkins.
- Configure Maven deployment.
- Publish MerchFlow JAR artifacts.
- Verify uploaded artifacts.
- Manage artifact versions.

**Expected outcome:** Nexus stores and manages MerchFlow build artifacts.

## **Phase 6: DevSecOps and Security**

### **1. Trivy security scanning**

- Install Trivy.
- Scan application dependencies.
- Scan the filesystem.
- Scan the Docker image.
- Scan configuration files.
- Review vulnerabilities.
- Define vulnerability severity thresholds.
- Add Trivy scanning to Jenkins.
- Configure pipeline security gates.

### **2. Application and infrastructure security**

- Remove hardcoded credentials.
- Configure environment variables.
- Store secrets in Jenkins credentials.
- Apply least-privilege IAM policies.
- Secure PostgreSQL access.
- Restrict security group rules.
- Review Docker image security.
- Secure Terraform state and credentials.

**Expected outcome:** Security checks become part of the CI/CD pipeline.

## **Phase 7: AWS Infrastructure Provisioning with Terraform**

### **1. Terraform setup**

- Install Terraform.
- Configure AWS CLI.
- Configure AWS credentials securely.
- Initialize Terraform.
- Configure Terraform providers.
- Configure remote state storage using S3.

### **2. AWS infrastructure**

- Retrieve the existing AWS Default VPC using a Terraform data source.
- Retrieve existing Default VPC subnets.
- Create security groups.
- Create IAM roles and policies.
- Provision EC2 instance(s).
- Create an ECR repository.
- Provision PostgreSQL hosting.
- Configure infrastructure outputs.
- Run `terraform fmt`.
- Run `terraform validate`.
- Run `terraform plan`.
- Run `terraform apply`.
- Verify deployed resources.

**Important AWS decisions:**

- Use the existing Default VPC.
- Do not create a new VPC.
- Do not create a custom Internet Gateway or route tables.
- Do not provision a NAT Gateway.
- Keep AWS resources cost-conscious.
- Restrict database access to the application infrastructure.

## **Phase 8: Ansible Configuration Management**

- Install Ansible.
- Configure Ansible inventory.
- Configure SSH connectivity where applicable.
- Create Ansible configuration file.
- Create playbooks.
- Create reusable roles.
- Install Docker on EC2.
- Configure Docker permissions.
- Install required packages.
- Configure application deployment prerequisites.
- Execute playbooks.
- Verify server configuration.

**Expected outcome:** Ansible automatically prepares EC2 for MerchFlow deployment.

## **Phase 9: ECR, Database Restoration and Automated Deployment**

### **1. Amazon ECR**

- Configure ECR authentication.
- Configure Jenkins to authenticate with ECR.
- Build the MerchFlow Docker image.
- Tag the image.
- Push the image to ECR.
- Verify the image in ECR.

### **2. PostgreSQL deployment**

- Prepare the PostgreSQL server or managed database.
- Install/configure PostgreSQL if using EC2.
- Create the MerchFlow database.
- Transfer `backup.sql` securely.
- Restore the database using `psql`.
- Verify tables and records.
- Configure database credentials securely.
- Verify application-to-database connectivity.

### **3. Application deployment**

- Configure EC2 to pull images from ECR.
- Configure EC2 IAM permissions.
- Pull the MerchFlow image.
- Configure application environment variables.
- Run the application container.
- Verify application health.
- Test API endpoints.
- Integrate deployment into Jenkins.
- Configure automated deployment.
- Implement rollback procedures.

**Expected outcome:** Jenkins builds, pushes, and deploys MerchFlow automatically to AWS.

## **Phase 10: Monitoring and Observability**

### **1. Application monitoring**

- Configure Spring Boot Actuator.
- Configure Micrometer.
- Add Prometheus dependency.
- Expose Prometheus metrics.
- Configure Prometheus scraping.

### **2. Infrastructure monitoring**

- Install Node Exporter.
- Configure EC2 monitoring.
- Configure CPU monitoring.
- Configure memory monitoring.
- Configure disk monitoring.
- Monitor PostgreSQL connectivity.

### **3. Grafana**

- Install Grafana.
- Connect Grafana to Prometheus.
- Create application dashboards.
- Create infrastructure dashboards.
- Monitor API response times.
- Monitor HTTP error rates.
- Monitor JVM metrics.
- Configure alerts.

### **4. AWS monitoring**

- Configure CloudWatch.
- Monitor EC2 metrics.
- Configure log collection.
- Configure alarms.

**Expected outcome:** You can monitor MerchFlow application health and AWS infrastructure from Grafana and CloudWatch.

## **Phase 11: Reliability, Recovery and Optimization**

- Configure PostgreSQL backups.
- Test database restoration.
- Test application recovery.
- Test container restart policies.
- Test EC2 failure scenarios.
- Test deployment rollback.
- Review application logs.
- Review monitoring alerts.
- Optimize Docker images.
- Review AWS resource utilization.
- Review AWS costs.
- Document recovery procedures.

## **Phase 12: Documentation and Portfolio Preparation**

- Write a professional README.
- Document application setup.
- Document API endpoints.
- Document testing procedures.
- Document Docker configuration.
- Document Jenkins pipeline.
- Document Nexus configuration.
- Document Trivy security scanning.
- Document Terraform infrastructure.
- Document Ansible configuration.
- Document AWS deployment.
- Document database restoration.
- Document monitoring setup.
- Add architecture diagrams.
- Add screenshots of the pipeline.
- Add screenshots of Grafana dashboards.
- Document challenges and solutions.
- Prepare the project for your DevOps portfolio.

