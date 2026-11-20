variable "ami_id" {
  description = "AMI ID for the server."
  type        = string
}

variable "instance_type" {
  description = "EC2 instance type for the server."
  type        = string
}

variable "sonar_server_security_group_id" {
  description = "Security group ID for the SonarQube server."
  type        = string
}

variable "instance_name" {
  description = "Name tag for the server."
  type        = string
}

variable "key_name" {
  description = "Name of the EC2 key pair."
  type        = string
}

variable "environment" {
  description = "Environment tag applied to the EC2 key pair."
  type        = string
}

variable "app_public_key_path" {
  description = "Path to the public key file, relative to this module."
  type        = string
}
