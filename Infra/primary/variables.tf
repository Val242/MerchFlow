variable "environment" {
  description = "Environment name for MerchFlow infrastructure."
  type        = string
}

variable "region" {
  description = "AWS region for the primary environment."
  type        = string
}

variable "ami_id" {
  description = "AMI ID used by the server modules."
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