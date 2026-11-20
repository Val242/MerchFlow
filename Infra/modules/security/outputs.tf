output "jenkins_server_security_group_id" {
  description = "Security group ID for the Jenkins server."
  value       = aws_security_group.jenkins_server.id
}

output "nexus_server_security_group_id" {
  description = "Security group ID for the Nexus server."
  value       = aws_security_group.nexus_server.id
}

output "sonar_server_security_group_id" {
  description = "Security group ID for the SonarQube server."
  value       = aws_security_group.sonar_server.id
}