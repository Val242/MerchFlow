
module "iam" {
  source = "../modules/iam"

  iam_user_name   = var.iam_user_name
  iam_user_path   = var.iam_user_path
  iam_user_tags   = var.iam_user_tags
  iam_policy_name = var.iam_policy_name
}

module "ecr" {
  source = "../modules/ecr"

  repository_name = "merchflow-${var.environment}-app-repo"
  repository_tags = {
    Environment = var.environment
    ManagedBy   = "terraform"
  }
}

module "security" {
  source = "../modules/security"

  my_ip_cidr = var.my_ip_cidr
}

module "jenkins" {
  source = "../modules/jenkins"

  ami_id                           = var.ubuntu_ami_id
  instance_type                    = var.jenkins_instance_type
  jenkins_server_security_group_id = module.security.jenkins_server_security_group_id
  instance_name                    = "jenkins-server"
  key_name                         = "merchflow-${var.environment}-jenkins-key"
  environment                      = var.environment
  app_public_key_path              = var.app_public_key_path
}

module "nexus" {
  source = "../modules/nexus"

  ami_id                         = var.amazon_linux_ami_id
  instance_type                  = var.nexus_instance_type
  nexus_server_security_group_id = module.security.nexus_server_security_group_id
  instance_name                  = "nexus-server"
  key_name                       = "merchflow-${var.environment}-nexus-key"
  environment                    = var.environment
  app_public_key_path            = var.app_public_key_path
}

module "sonar" {
  source = "../modules/sonar"

  ami_id                         = var.ubuntu_ami_id
  instance_type                  = var.sonar_instance_type
  sonar_server_security_group_id = module.security.sonar_server_security_group_id
  instance_name                  = "sonar-server"
  key_name                       = "merchflow-${var.environment}-sonar-key"
  environment                    = var.environment
  app_public_key_path            = var.app_public_key_path
}