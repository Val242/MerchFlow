output "repository_name" {
  description = "Name of the MerchFlow ECR repository."
  value       = aws_ecr_repository.app_repo.name
}

output "repository_arn" {
  description = "ARN of the MerchFlow ECR repository."
  value       = aws_ecr_repository.app_repo.arn
}

output "repository_url" {
  description = "URL used to push and pull images from the repository."
  value       = aws_ecr_repository.app_repo.repository_url
}

output "registry_id" {
  description = "AWS account ID that owns the ECR repository."
  value       = aws_ecr_repository.app_repo.registry_id
}
