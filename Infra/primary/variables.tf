variable "environment" {
  description = "Environment name for MerchFlow infrastructure."
  type        = string
}

variable "region" {
  description = "AWS region for the primary environment."
  type        = string
}

variable "ubuntu_ami_id" {
  description = "Ubuntu AMI ID used by the Jenkins and SonarQube servers."
  type        = string
}

variable "amazon_linux_ami_id" {
  description = "Amazon Linux AMI ID used by the Nexus server."
  type        = string
}

variable "app_public_key_path" {
  description = "Path to the shared MerchFlow public key, relative to each service module."
  type        = string
}

variable "my_ip_cidr" {
  description = "Administrator public IPv4 CIDR, restricted to one address."
  type        = string
}

variable "jenkins_instance_type" {
  description = "EC2 instance type for the Jenkins server."
  type        = string
  default     = "c7i-flex.large"
}

variable "nexus_instance_type" {
  description = "EC2 instance type for the Nexus server."
  type        = string
  default     = "c7i-flex.large"
}

variable "sonar_instance_type" {
  description = "EC2 instance type for the SonarQube server."
  type        = string
  default     = "c7i-flex.large"
}